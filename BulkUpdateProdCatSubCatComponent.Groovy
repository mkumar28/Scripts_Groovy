/**This script is userd to bulk update Component based on ProdCat and SubCat Category Custom Field/ 

@author Manish
Note: We need to update the Ulti user permission in JIRA.
Update Jql
**/

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
 @Grab('com.xlson.groovycsv:groovycsv:1.1')
import au.com.bytecode.opencsv.CSVReader;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.event.type.EventDispatchOption
import java.util.Date;
import com.atlassian.jira.timezone.TimeZoneManager
import java.text.SimpleDateFormat;

def mapping = [:]
List<String> excelBody = new ArrayList<String>()
List<String> category = new ArrayList<String>()
List<String> sub_cat = new ArrayList<String>()
List<String> components = new ArrayList<String>()
List<String> Failedids = new ArrayList<String>()
def ultiProjectId = 10151
def filedirectoryURL = "/jira/ProdCatLog/"

def searchProvider = ComponentAccessor.getComponentOfType(SearchProvider.class);
def searchService = ComponentAccessor.getComponentOfType(SearchService.class);
def userManager = ComponentAccessor.getUserManager();
def componentManager = ComponentAccessor.getProjectComponentManager()
def issueManager = ComponentAccessor.getIssueManager()
def appUser = userManager.getUserByKey("ulti");
def issueService = ComponentAccessor.getIssueService()
def issueInputParameters = issueService.newIssueInputParameters()
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).	getLoggedInUserTimeZone()
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

/**Initializing jqlargument**/
//def jqlargument = "project=ULTI and issuetype=\"SF Development\"  and resolution=unresolved and component in (TRADE, TET, TRADE-Customs) and key NOT IN (ULTI-379071,ULTI-368551,ULTI-367722,ULTI-364631,ULTI-361850,ULTI-289591)" //3* Update JQL
def jqlargument = "project=ULTI and issuetype=\"SF Development\" and resolution=unresolved and component in (\"zTET - DO NOT USE\", \"zTRADE - DO NOT USE\", \"zTRADE-Customs - DO NOT USE\") and key not in (ULTI-379650,ULTI-372452,ULTI-371943,ULTI-371069,ULTI-368551,ULTI-367722,ULTI-364631,ULTI-363210,ULTI-363014,ULTI-361850,ULTI-291591,ULTI-289591)"
Collection<CustomField>  customFieldCollections = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectsByName("Product/Category/Sub-Category");
CustomField customField = (CustomField)customFieldCollections.toArray()[0];

/**Getting the logged in user current time**/
def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("yyyy-MM-dd");//Getting the logged in user current time
Date now =  format.parse(userCurrentTime)


/**File Creation for logging**/
File file = new File(filedirectoryURL+'log_'+userCurrentTime+".txt");


/**Reading from CSV File**/
Reader reader = new FileReader("/jira/sharedhome/SF_Development_Mapping/SFDevelopmentMapping.csv");
CSVReader csvreader = new CSVReader(reader);
String [] nextLine;
while ((nextLine = csvreader.readNext()) != null) {
          category.add((nextLine[0]));
          sub_cat.add(nextLine[1]);
          components.add(nextLine[2]);
}

/**Creating a Dict to map Category and SubCat with Components **/
for(int i=0;i<category.size();i++){
    mapping[category[i]+sub_cat[i]] = components[i]
}

/**Executing the JQL**/
SearchService.ParseResult parseResult = searchService.parseQuery(appUser,jqlargument);
def query = parseResult.getQuery();
def results = searchProvider.search(query, appUser, PagerFilter.getUnlimitedFilter());
def fetchedIssues = results.getIssues();

/**Iterating over the feteched issues**/
for(def jqlIssues : fetchedIssues){

        def devcomp = "" 
        def parentValue = jqlIssues.getCustomFieldValue(customField).getAt(null)
        def childValue = jqlIssues.getCustomFieldValue(customField)['1']
        devcomp = mapping[parentValue.toString()+childValue.toString()]
        
        if(devcomp != null){ /**Update the component if mapping found in csv**/
            def projectComponent = componentManager.findByComponentName(ultiProjectId,devcomp.toString()) 
            
            if(projectComponent!=null){/**proceed if component is found in the jira project**/
                    def issue = issueManager.getIssueByCurrentKey(jqlIssues.getKey())
                    def workflow = ComponentAccessor.getWorkflowManager().getWorkflow(issue)
                    def actions = workflow.getLinkedStep(issue.getStatus()).getActions()
                    int actionID = 0
                    
                    for(int i = 0; actions.size()>i; i++){
                        
                        if(actions[i].getName().toString() == 'SF Updates'){
                              
                             
                              actionID = actions[i].getId()
                             
                              
                        }

                    }


                    def x = issueInputParameters.setComponentIds(projectComponent.id)
                    //def actionID = 551/**Iniating SF update Transition**/
                    def validationResult = issueService.validateTransition(appUser, issue.id, actionID, x)
                    if (validationResult.isValid()) {
                            def resultMessage = (List)issueService.transition(appUser, validationResult).getErrorCollection().getAt("errorMessages")
                            def resultTransition = resultMessage[0]
                            }
            }
            else{/**Log if the Component is not found**/
                    file.append("Component not Found: "+jqlIssues.getKey()+"\n")
       
             }
        }
    else{/**Log if the mapping is not found**/
        file.append("Mapping Not Found: "+jqlIssues.getKey()+"\n")
       
    }
}