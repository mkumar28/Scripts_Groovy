/*
 This Script is used to update the integration link on the Aha feature
1 Update a JQL
2 Update jiraurl to match with the env.
3 Update the Integration name 
You can pull the integration for a product using the following API. The below example is for HR Product
https://ultimatesoftware.aha.io/api/v1/products/HR/integrations
*/

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


def authToken = "9f129c69da987d09e3a2343fa39f86256231469df4fc595d4444ddb2d813bb2d"
def baseUrl = "https://ultimatesoftware.aha.io"
def jiraURL = "https://ultidev"
def integrationid = null
def res = null
def product = null 
def integration_name = 'PRO Jira'
def ahaid = null

//def integration_name = 'Jira Dev Test'//This need to ne updated to JIRA
def userManager = ComponentAccessor.getUserManager();
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).	getLoggedInUserTimeZone()
ApplicationUser appUser = userManager.getUserByKey("ulti");
SearchProvider searchProvider = ComponentAccessor.getComponentOfType(SearchProvider.class);
SearchService searchService = ComponentAccessor.getComponentOfType(SearchService.class);
def issueManager = ComponentAccessor.getIssueManager() 
def customFieldManager = ComponentAccessor.getCustomFieldManager() 

def customField = 	customFieldManager.getCustomFieldObject("customfield_14920")
//def jqlargument = "project = UltiPro and type = epic and \"Internal ID\" is not EMPTY and status not in (closed, Canceled)"
def jqlargument = "issuekey in (\"PRO-27260\")"
 /**Getting the logged in user current time**/
 def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("yyyy-MM-dd hh:mm:ss");//Getting the logged in user current time
 //Date now =  format.parse(userCurrentTime)


/**Creating a log file**/
File file = new File('/home/jira/Aha_log/Aha_log_'+userCurrentTime+'.txt');
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
      product = null
      integrationid = null
      

      def url = new URL(baseUrl+'/api/v1/features/'+interIDvalue)
      HttpURLConnection connection = (HttpURLConnection)url.openConnection()
      connection.setRequestMethod( "GET" )
      connection.setRequestProperty( "Authorization", "Bearer ${authToken}" )
      connection.doOutput = true
      connection.connect()   
      res = connection.getResponseCode()
    if(res == 200){
                  def response = connection.getInputStream().getText()

                  def jsonSlurper = new JsonSlurper()
                  def jsonobject = jsonSlurper.parseText(response)
                  ahaid = jsonobject.feature.id
                  product = jsonobject.feature.release.project.reference_prefix/**Getting the product detail**/
                  connection.disconnect()
              
                 

                  if(ahaid != null )
                            {
                                    /*Getting the integration linked with the product to which feature is associted with*/
                                    url = new URL(baseUrl+'/api/v1/products/'+product+'/integrations')
                                    connection = (HttpURLConnection)url.openConnection()
                                    connection.setRequestMethod( "GET" )
                                    connection.setRequestProperty( "Authorization", "Bearer ${authToken}" )
                                    connection.doOutput = true
                                    connection.connect() 
                                    response = connection.getInputStream().getText()
                                    jsonSlurper = new JsonSlurper()
                                    jsonobject = jsonSlurper.parseText(response)
                                    def integrations= jsonobject.integrations
                                    if([integrations].size() > 0){
                                      for(item in integrations){
                                          if (item.name == integration_name){
                                              integrationid = item.id

                                          }
                                      }
                                    }
                                  connection.disconnect()
                                  
                                  
                                  file.append("\n\n")
                                  file.append(interIDvalue.toString()+" "+key.toString()+" "+product.toString()+" "+integrationid.toString())

                                  def body_req = "{ \"integration_fields\": [{ \"name\": \"key\", \"value\": \"${key}\"},{\"name\":\"id\",\"value\":\"${id}\"},{\"name\": \"url\",\"value\": \"${jiraIssueURL}\"},{\"name\": \"display_short\",\"value\": \"${displayShort}\"},{\"name\": \"display_link\",\"value\": \"${jiraIssueURL}\"}]}"
                                  
                                  url =  new URL(baseUrl+'/api/v1/features/'+ahaid+'/integrations/'+integrationid+'/fields?disable_mailers=true')//Disable email
                                  connection = (HttpURLConnection)url.openConnection()
                                  connection.setRequestMethod( "POST" )
                                  connection.setRequestProperty( "Authorization", "Bearer ${authToken}" )
                                  connection.doOutput = true
                                  connection.setRequestProperty("Content-Type", "application/json")
                                  connection.getOutputStream().write(body_req.getBytes("UTF-8"))
                                  connection.connect()
                                  res = connection.getResponseCode();
                                  if(res!=200){
                                    file.append("\n\n")
                                    file.append(ahaid.toString()+" "+key.toString())     
                                  }
                                  connection.getInputStream().getText();
                                  connection.disconnect()

                            }
                      else{
                            file.append("\n\n")
                            file.append(interIDvalue.toString()+" "+key.toString()+" "+product.toString()+" "+integrationid.toString())

                      }
        }
    else{
            file.append("\n\n")
            file.append(interIDvalue.toString()+" "+key.toString()+" "+product.toString()+" "+integrationid.toString())
         
      }
}


