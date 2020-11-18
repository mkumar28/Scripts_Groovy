import com.atlassian.jira.bc.filter.SearchRequestService
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.bc.JiraServiceContext
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.portal.PortalPageManager;
import com.atlassian.jira.portal.PortalPage;
import com.atlassian.jira.portal.PortalPage.Builder
import com.atlassian.jira.user.util.UserManager;
import static com.xlson.groovycsv.CsvParser.parseCsv
import groovyx.net.http.*
import groovyx.net.http.ContentType.*
import groovyx.net.http.Method.*
import com.xlson.groovycsv.PropertyMapper
import java.util.*

def userManager = ComponentAccessor.getUserManager();
ApplicationUser appUser = userManager.getUserByKey("debbies");

List<String> excelBody = new ArrayList<String>()
List<Long> dashboardId = new ArrayList<Long>();
List<Long> dashboardOwner = new ArrayList<Long>();
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

			dashboardId.add(excelRow.FilterID);
			dashboardOwner.add(excelRow.FilterOwner)

}	

def usr
for(int i=0; i <filterId.size(); i++) {
PortalPageManager portalPageManager = ComponentAccessor.getComponent(PortalPageManager)
PortalPage page = portalPageManager.getPortalPageById(24712)
PortalPage.Builder builder = page.portalPage(page)
PortalPage.Builder builderOwner = builder.owner(appUser)
portalPageManager.update(builderOwner.build())

}

