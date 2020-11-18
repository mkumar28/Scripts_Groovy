import com.atlassian.jira.component.ComponentAccessor 
import com.atlassian.jira.bc.issue.search.SearchService 
import com.atlassian.jira.user.ApplicationUser 
import com.atlassian.query.Query
import groovyx.net.http.* 
import groovyx.net.http.ContentType.* 
import groovyx.net.http.Method.* 
import net.sf.json.* 
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor 
import org.apache.http.protocol.HttpContext 
import groovy.json.JsonSlurper 
import com.atlassian.jira.timezone.TimeZoneManager 
import org.apache.log4j.Logger 
import org.apache.log4j.Level 
import org.jsoup.Jsoup; 
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element; 
import org.jsoup.select.Elements;
   
def log = Logger.getLogger("Prod-Cat-SubCat") 
log.setLevel(Level.DEBUG) 
log.debug "Hello"
//def baseUrl = "https://ultimatesoftware.aha.io" 
def confluenceURL = "https://confluence.ultimatesoftware.com/" 
def authString = "".getBytes().encodeBase64().toString() 
    

def url = new URL(confluenceURL+'rest/api/content/130600782?expand=space,body.view,version,container') 
def connection = (HttpURLConnection)url.openConnection() connection.setRequestMethod( "GET" ) 
connection.setRequestProperty( "Authorization", "Basic ${authString}" ) 
connection.doOutput = true 
connection.connect() 
   
def response = connection.getInputStream().getText() 
def jsonSlurper = new JsonSlurper() 
def jsonobject = jsonSlurper.parseText(response) 
def tables = jsonobject.body.view.value 


doc = Jsoup.parse(tables);
Elements row = doc.getElementsByTag("tr");
int rowsize = row.select("td").size() 
ArrayList<String> category = new ArrayList<String>() 
ArrayList<String> sub_cat = new ArrayList<String>() 
ArrayList<String> component = new ArrayList<String>() 

def mapping = [:]
def count =0 
for (int i =0; rowsize>i; i++) 
{ 
    category.add(row.select("td").get(i).text()) 
    sub_cat.add(row.select("td").get(i).text()) 
    component.add(row.select("td").get(i).text()) 
          
} 
} 
for(int i =0;i<category.size();i++)
{ 
    mapping[category[i]+sub_cat[i]] = component[i] 
} 
log.debug category 
log.debug sub_cat 
log.debug component 
log.debug mapping l
log.debug mapping["UltiPro Core/HR Payroll-PayrollROE(Record of Employment)"]