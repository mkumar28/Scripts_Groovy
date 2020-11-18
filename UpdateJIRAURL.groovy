@Grab('com.xlson.groovycsv:groovycsv:1.1')
import au.com.bytecode.opencsv.CSVReader;
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.timezone.TimeZoneManager
import java.text.SimpleDateFormat;


List<String> ahaID = new ArrayList<String>()
List<String> jiraURL = new ArrayList<String>()

def authToken = "9f129c69da987d09e3a2343fa39f86256231469df4fc595d4444ddb2d813bb2d"
def baseUrl = "https://ultimatesoftware.aha.io"


//def integration_name = 'Jira Dev Test'//This need to ne updated to JIRA
def userManager = ComponentAccessor.getUserManager();
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).	getLoggedInUserTimeZone()
def appUser = userManager.getUserByKey("ulti");
SearchProvider searchProvider = ComponentAccessor.getComponentOfType(SearchProvider.class);
SearchService searchService = ComponentAccessor.getComponentOfType(SearchService.class);
def issueManager = ComponentAccessor.getIssueManager() 
def customFieldManager = ComponentAccessor.getCustomFieldManager()
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

/**Getting the logged in user current time**/
def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("yyyy-MM-dd-hh:mm:SS");//Getting the logged in user current time
Date now =  format.parse(userCurrentTime) 

/**Creating a log file**/
File file = new File('/home/jira/Aha_log/Aha_log_JiraUrl'+userCurrentTime+'.txt');
file.write("Below is the list of Jira:")


/**Reading from CSV File**/
Reader reader = new FileReader("/home/appadmin/promigration-jiraurlupdate1.csv");
CSVReader csvreader = new CSVReader(reader);
String [] nextLine;
while ((nextLine = csvreader.readNext()) != null) {
          ahaID.add((nextLine[1]));
          jiraURL.add(nextLine[2]);
         
}

for(int i=1;i<ahaID.size();i++){
   
   def url = new URL(baseUrl+'/api/v1/features/'+ahaID[i]+'/fields?disable_mailers=true')

   def body_req = "{\"custom_fields\" : [ { \"key\": \"jira_url\",\"value\" : \"${jiraURL[i]}\"} ]}"
   HttpURLConnection connection = (HttpURLConnection)url.openConnection()
   connection.setRequestMethod( "PUT" )
   connection.setRequestProperty( "Authorization", "Bearer ${authToken}" )
   connection.doOutput = true
   connection.setRequestProperty("Content-Type", "application/json")
   connection.getOutputStream().write(body_req.getBytes("UTF-8"))
   connection.connect()
   def res = connection.getResponseCode();
   connection.getInputStream().getText();
   connection.disconnect()
   if(res!= 200){
      file.append("\n\n")
      file.append(ahaID[i].toString()+" "+jiraURL[i].toString())  
   }

}
