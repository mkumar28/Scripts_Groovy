
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.crowd.embedded.api.Group
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.query.Query
import com.atlassian.jira.issue.search.SearchResults
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.Issue


def userManager = ComponentAccessor.getUserManager();
ApplicationUser appUser = userManager.getUserByKey("ulti");

SearchProvider searchProvider = ComponentAccessor.getComponentOfType(SearchProvider.class);
SearchService searchService = ComponentAccessor.getComponentOfType(SearchService.class);

def groupManager = ComponentAccessor.getGroupManager()
def level = ComponentAccessor.getIssueSecurityLevelManager() 
def issueManager = ComponentAccessor.getIssueManager() 

//For component = salesforce
def jqlargument = "project = UltiPro and component = Salesforce"
//long id = 10751//Test Id
long id = 10753
updateSecurity(id, jqlargument, searchService,searchProvider,appUser ,issueManager )

//For component = Carrier Integrations
/*jqlargument = "project = UltiPro and component = \"Carrier Integrations\""
//id = 10752/Test Id
id = 10651
updateSecurity(id, jqlargument, searchService,searchProvider,appUser ,issueManager )*/

//For component = Integration Platform
/*jqlargument = "project = UltiPro and component = \"Integration Platform\""
//id = 10752/Test Id
id = 10652
updateSecurity(id, jqlargument, searchService,searchProvider,appUser ,issueManager )*/

//For rest of the Jira issue
//def jqlargument ="project = ULTI AND level is EMPTY"
//long id = 10750
//long id = 10540
//updateSecurity(id, jqlargument, searchService,searchProvider,appUser ,issueManager )

//This function update the Security level 
def updateSecurity(long id, String jqlargument,SearchService searchService , SearchProvider searchProvider,ApplicationUser appUser, IssueManager issueManager ){
SearchService.ParseResult parseResult = searchService.parseQuery(appUser,jqlargument);
Query query = parseResult.getQuery();
SearchResults results = searchProvider.search(query, appUser, PagerFilter.getUnlimitedFilter());
def fetchedIssues= results.getIssues();

    for(int i =0; i<10000; i++){
        def issue = issueManager.getIssueObject(fetchedIssues.get(i).getKey())
        def issueType = issue.getIssueType().getName()
        if(issueType != "SalesForce Development"){
            issue.setSecurityLevelId(id)
            issueManager.updateIssue(appUser,issue,EventDispatchOption.ISSUE_UPDATED,true)
        }
    }
}