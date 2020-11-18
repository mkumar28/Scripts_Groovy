import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.query.Query
import com.atlassian.jira.issue.search.SearchResults
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.Issue
import groovyx.net.http.*
import groovyx.net.http.ContentType.*
import groovyx.net.http.Method.*
import net.sf.json.*
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext
import groovy.json.JsonSlurper
import com.atlassian.jira.timezone.TimeZoneManager

def userManager = ComponentAccessor.getUserManager();
ApplicationUser appUser = userManager.getUserByKey("ulti");
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).	getLoggedInUserTimeZone()
def authToken = "9f129c69da987d09e3a2343fa39f86256231469df4fc595d4444ddb2d813bb2d"
def baseUrl = "https://ultimatesoftware.aha.io"
def jiraURL = "https://ultidev/browse/"

SearchProvider searchProvider = ComponentAccessor.getComponentOfType(SearchProvider.class);
SearchService searchService = ComponentAccessor.getComponentOfType(SearchService.class);
def issueManager = ComponentAccessor.getIssueManager() 
def customFieldManager = ComponentAccessor.getCustomFieldManager() 

def customField = 	customFieldManager.getCustomFieldObject("customfield_14920")
//def jqlargument = "project = UltiPro and type = epic and \"Internal ID\" is not EMPTY and status not in (closed, Canceled)"
def jqlargument = "issuekey = \"ULTI-385713\""

 /**Getting the logged in user current time**/
 def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("yyyy-MM-dd hh:mm:ss");//Getting the logged in user current time
 //Date now =  format.parse(userCurrentTime)

/**Creating a log file**/
File file = new File('/home/jira/AhalogoRllBack'+userCurrentTime+'.txt');
file.write("Below is the list of Jira:")


/**Running the Jql**/
SearchService.ParseResult parseResult = searchService.parseQuery(appUser,jqlargument);
Query query = parseResult.getQuery();
SearchResults results = searchProvider.search(query, appUser, PagerFilter.getUnlimitedFilter());
def fetchedIssues= results.getIssues();

for(int i = 0; fetchedIssues.size()>i; i++){
      def issue = fetchedIssues.get(i)
      def interIDvalue = issue.getCustomFieldValue(customField) 

      
      def key = issue.getKey()
      def id = issue.getId()
      def jiraIssueURL = jiraURL+key
      def displayShort = "Epic "+key

      def url = new URL(baseUrl+'/api/v1/features?q='+interIDvalue)
      HttpURLConnection connection = (HttpURLConnection)url.openConnection()
      connection.setRequestMethod( "GET" )
      connection.setRequestProperty( "Authorization", "Bearer ${authToken}" )
      connection.doOutput = true
      connection.connect()   
      def response = connection.getInputStream().getText()
      def jsonSlurper = new JsonSlurper()
      def jsonobject = jsonSlurper.parseText(response)
      def ahaid = jsonobject.features.id[0]
      connection.disconnect()

      if(ahaid != null){
      def body_req = "{ \"feature\": { \"custom_fields\":  {\"jira_url\": \"${jiraIssueURL}\"}}}"
      body_req
      url =  new URL(baseUrl+'/api/v1/features/'+ahaid)
      connection = (HttpURLConnection)url.openConnection()
      connection.setRequestMethod( "PUT" )
      connection.setRequestProperty( "Authorization", "Bearer ${authToken}" )
      connection.doOutput = true
      connection.setRequestProperty("Content-Type", "application/json")
      connection.getOutputStream().write(body_req.getBytes("UTF-8"))
      connection.connect()
      def postRC = connection.getResponseCode();
      connection.getInputStream().getText();
      connection.disconnect()
      }
      else{
        file.append("\n\n")
        file.append(ahaid.toString()+" "+key.toString())
         
      }
}

