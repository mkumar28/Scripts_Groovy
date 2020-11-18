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
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.issue.fields.CustomField


def implementationType
def issueManager = ComponentAccessor.getIssueManager()
//Issue issue  = issueManager.getIssueByCurrentKey("EMT-87829")
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def cField = customFieldManager.getCustomFieldObject("customfield_11323")
def projectStartDate = issue.getCustomFieldValue(cField)
//def tragetDueDate = customFieldManager.getCustomFieldObject("customfield_10280")


def userManager = ComponentAccessor.getUserManager();//for JIRA v7
ApplicationUser appUser = userManager.getUserByKey("ulti");//for JIRA v7


def id=issue.getId()
def issue_id = issueManager.getIssueObject(id)
int sla = 5;
int finalSla = updateIssue(projectStartDate, sla, appUser,issue,issueManager )
			
//Updating Issue		
int updateIssue(def projectStartDate, int sla, ApplicationUser appUser,Issue issue,IssueManager issueManager ){
	
	def finalSla = calculatingSla(sla, projectStartDate)

    
    //def  projectDate =  (Timestamp)(projectStartDate+finalSla)
    //issue.setCustomFieldValue(tragetDueDate, projectDate)
    issue.setDueDate(new Timestamp((projectStartDate + finalSla).time))
    issueManager.updateIssue(appUser, issue, EventDispatchOption.DO_NOT_DISPATCH, true)
    
   
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
