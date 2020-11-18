import com.atlassian.jira.bc.filter.SearchRequestService
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.bc.JiraServiceContext
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.issue.search.SearchRequestManager;
import com.atlassian.jira.issue.search.SearchRequest;
import com.atlassian.jira.user.util.UserManager;
@Grab('com.xlson.groovycsv:groovycsv:1.1')
import static com.xlson.groovycsv.CsvParser.parseCsv
import groovyx.net.http.*
import groovyx.net.http.ContentType.*
import groovyx.net.http.Method.*
import com.xlson.groovycsv.PropertyMapper
import java.util.*


List<String> excelBody = new ArrayList<String>()
List<Long> filterId = new ArrayList<Long>();
List<Long> filterOwner = new ArrayList<Long>();
List<Long> FailedIDs = new ArrayList<Long>();

for(line in parseCsv(new FileReader('/home/appadmin/FilterOwner.csv'))) {//1. Update the path for the CSV File
excelBody.add( line)
}

SearchRequestService searchRequestService = ComponentAccessor.getComponent(SearchRequestService)
UserManager usermanager = ComponentAccessor.getComponent(UserManager)
SearchRequest sr

for(PropertyMapper excelRow : excelBody)
		{
//Use this list to add the FilterID

			filterId.add(excelRow.FilterID);
			filterOwner.add(excelRow.FilterOwner)

}



def usr
for(int i=0; i <filterId.size(); i++)
{
def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
SearchRequestManager srm = ComponentAccessor.getComponent(SearchRequestManager.class);
sr = srm.getSearchRequestById(filterId[i].toLong())//Provide the Id of the filter
 if(sr != null)
   {
	sr.getOwner()
	usr = usermanager.getUserByName(filterOwner[i].toString())//provide the userName
	sr.setOwner(usr)
	srm.update(sr)
   }
else
   {
        FailedIDs.add(filterId[i])
   }
}

FailedIDs
    
