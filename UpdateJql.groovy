import com.atlassian.jira.bc.filter.SearchRequestService
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.bc.JiraServiceContext
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.issue.search.SearchRequestManager;
import com.atlassian.jira.issue.search.SearchRequest;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.bc.issue.search.SearchService;
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
List<String> filterQuery = new ArrayList<String>()
List<String> filterQuery1 = new ArrayList<String>()

for(line in parseCsv(new FileReader('/JIRA/Data/FilterOwner.csv'))) {//1. Update the path for the CSV File
excelBody.add( line)
}
SearchRequestService searchRequestService = ComponentAccessor.getComponent(SearchRequestService)
SearchService searchService = ComponentAccessor.getComponent(SearchService)
UserManager usermanager = ComponentAccessor.getComponent(UserManager)
SearchRequest sr

for(PropertyMapper excelRow : excelBody)
		{
//Use this list to add the FilterID

			filterId.add(excelRow.FilterID);
			filterOwner.add(excelRow.FilterOwner)
			filterQuery.add(excelRow.Reqcontent)

}



def usr
for(int i=0; i <filterId.size(); i++)
{
def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
SearchRequestManager srm = ComponentAccessor.getComponent(SearchRequestManager.class);
sr = srm.getSearchRequestById(filterId[i].toLong())//Provide the Id of the filter
 if(sr != null)
   {
	query = filterQuery[i].toString()
	if (query.contains('GRC'))
	{
		query1 = query.replace('GRC', 'PRC')
		filterQuery1.add(query1)
	}
	def nQuery  = searchService.parseQuery(user, query1)
	sr.setQuery(nQuery.getQuery()) 
	//sr.getOwner()
	//usr = usermanager.getUserByName(filterOwner[i].toString())//provide the userName
	//sr.setOwner(usr)
	srm.update(sr)
   }
else
   {
        FailedIDs.add(filterId[i])
   }
}


    
