import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.event.type.EventDispatchOption;	
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.crowd.embedded.api.User
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;




def implementationType
def issueManager = ComponentAccessor.getIssueManager()
//Issue issue  = issueManager.getIssueByCurrentKey("EMT-57573")
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def cField = customFieldManager.getCustomFieldObject("customfield_11323")
def projectStartDate = issue.getCustomFieldValue(cField)
def setSlaField = customFieldManager.getCustomFieldObject("customfield_11850")
def implementationTypeField = customFieldManager.getCustomFieldObject("customfield_11250");
def provisionType = customFieldManager.getCustomFieldObject("customfield_11671");


def crowdService = ComponentAccessor.getCrowdService();//for JIRA v6
//def userManager = ComponentAccessor.getUserManager(); //for JIRA v7
User appUser = crowdService.getUser("ulti");//for JIRA v6
//def appUser = userManager.getUserByKey("ulti");//for JIRA v7

def id=issue.getId()
def issue_id = issueManager.getIssueObject(id)
List<Option> options = (List<Option>) implementationTypeField.getValue(issue_id);

/**Add values to implentation type list**/
String[] implementaionTypefor10SLA = ["Cognos","Onboarding","Onboarding V14","Recruiting V14","Recruitment", "UET","UTA","UTB","UTM","WFM","Perception","PeopleDoc","EDBS - One Time","EDBS Onboarding","EDBS Recruitment", "EDBS UltiPro", "EDBS UPM", "EDBS UTA","EDBS UTM","SPS","UTE"]
String[] implementaionTypefor5SLA = ["Integration","Active Directory Users"]
String[] implementaionTypefor3SLA = ["UltiPro Dedicated","UltiPro Master Company","UltiPro Shared","UltiPro Toronto"]


			for (int i = 0; i < options.size(); i++) 
			{	
				
				  implementationType=options[i]	
				//Calculating DueDate when SLA=10 days				
				if(projectStartDate!= null && implementationType.toString() != null)
				{
					for(int j = 0; j<implementaionTypefor10SLA.size(); j++)
						{
							if(implementationType.toString() == implementaionTypefor10SLA[j].toString()){
						
								int sla = 10;
								int finalSla = updateIssue(projectStartDate, sla, appUser,issueManager)
								
								
							}
						}
					//Calculating DueDate when SLA=5 days					
					for(int k=0; k<implementaionTypefor5SLA.size(); k++)
						{
						 if(implementationType.toString() == implementaionTypefor5SLA[k].toString())	
							{
								def sla = 5;
								int finalSla = updateIssue(projectStartDate, sla,appUser,issueManager)
								log.debug finalSla
								
							}
						}
					//Calculating DueDate when SLA=3 days
					for(int l=0; l<implementaionTypefor3SLA.size(); l++)
						{
						 if(implementationType.toString() == implementaionTypefor3SLA[l].toString())	
							{
								def sla = 3;
								int finalSla = updateIssue(projectStartDate, sla, appUser, issueManager)
								log.debug finalSla
								
							}
						}
				}
				
			}	
			
//Updating Issue		
int updateIssue(def projectStartDate, int sla, User appUser, IssueManager issueManager){
	
	def finalSla = calculatingSla(sla, projectStartDate)

	issue.setDueDate(new Timestamp((projectStartDate + finalSla).time))
	
	issueManager.updateIssue(appUser,issue,EventDispatchOption.ISSUE_UPDATED,true)
	return finalSla
}

			
//Calculating SLA
int calculatingSla(int sla, def projectStartDate)
{
	                     def countSla = 0;
						 def finalSla = 0;
						 		
						 for (int j=1; j<=(sla+countSla); j++)
						 {
							 def dateToevaluate = new Timestamp((projectStartDate + j).time)
					
							 if(!isBusinessDay(dateToevaluate))
							 {
								 countSla++
								
							 }
							 
						 } 	
						log.debug countSla
						finalSla = sla + countSla;
					    return finalSla;
}
		
//Checking Buisness day			
boolean isBusinessDay(Date duedate)
{  

	Calendar cal = Calendar.getInstance();
	
	//logic to get the correct week for thanksgiving and black friday. if 
	Calendar cal1 = Calendar.getInstance();
	cal.setTime(duedate);
	cal1.setTime(duedate);
	def week1 = 0
	def week2 = 0
	if(cal.get(Calendar.MONTH) == Calendar.NOVEMBER){
		cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMinimum(Calendar.DAY_OF_MONTH));
		def startingDayOfMonth = cal1.getTime().day
			if( startingDayOfMonth == 5){/*If startingDayOfMonth is Friday*/
				week1 = 4
				week2 = 5
			}
			else {
				week1 = 4
				week2 = 4
				
			}
		
	}
	
	// check if weekend
	if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
		return false;
	}
	// check if New Year's Day
	if (cal.get(Calendar.MONTH) == Calendar.JANUARY
		&& cal.get(Calendar.DAY_OF_MONTH) == 1) {
		return false;
	}
	// check if Christmas
	if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
		&& cal.get(Calendar.DAY_OF_MONTH) == 25) {
		return false;
	}
	// check if 4th of July 
	if (cal.get(Calendar.MONTH) == Calendar.JULY
		&& cal.get(Calendar.DAY_OF_MONTH) == 4) {
		return false;
	}
	// check Thanksgiving (4th Thursday of November)
	if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER
		&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == week1
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
		return false;	
	}
	// check day after Thanksgiving (4th Thursday of November)
	if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER
		&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == week2
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
		return false;
	}
	// check Memorial Day (last Monday of May)
	if (cal.get(Calendar.MONTH) == Calendar.MAY
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
		&& cal.get(Calendar.DAY_OF_MONTH) > (31 - 7) ) {
		return false;
	}
	// check Labor Day (1st Monday of September)
	if (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER
		&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
		return false;
	}
	else 
		return true;
			
}
