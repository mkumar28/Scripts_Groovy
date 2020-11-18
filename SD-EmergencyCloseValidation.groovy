/**
This Script  is used to validate Business reason field is filled or not if request is closed when change start time and End Time is greater/less 
then Current Time. 
**/

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.timezone.TimeZoneManager

/**getting loggedin user Timzone**/
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).getLoggedInUserTimeZone()
def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("dd-MM-yyyy HH:mm:ss Z");

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def changeStartTime = customFieldManager.getCustomFieldObject("customfield_11546")
def changeStartTimeValue = issue.getCustomFieldValue(changeStartTime)
def changeEndTime = customFieldManager.getCustomFieldObject("customfield_11547")
def changeEndTimeValue = issue.getCustomFieldValue(changeEndTime)
def changePriority = customFieldManager.getCustomFieldObject("customfield_11543")
String changePriorityValue = issue.getCustomFieldValue(changePriority)
def buisnessReason = customFieldManager.getCustomFieldObject("customfield_10985")
String buisnessReasonValue = issue.getCustomFieldValue(buisnessReason)
def logedInUser =ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def userManager = ComponentAccessor.getUserManager();
def appUser = userManager.getUserByKey("kelvint");

//def date = new Date().format( 'dd-MM-yyyy HH:mm:ss')
def endTimeValue = (Date)changeEndTimeValue
def endTime = endTimeValue.format("dd-MM-yyyy HH:mm:ss")
def startTimeValue = (Date) changeStartTimeValue
def startTime = startTimeValue.format("dd-MM-yyyy HH:mm:ss")
Date changeStartTimeParse = Date.parse("dd-MM-yyyy HH:mm:ss", startTime)
Date changeEndTimeParse = Date.parse("dd-MM-yyyy HH:mm:ss", endTime)
Date now =  Date.parse("dd-MM-yyyy HH:mm:ss Z", userCurrentTime)
if(appUser != logedInUser){
    
    if(changePriorityValue == "Emergency")
    {
       if((changeStartTimeParse < now && changeEndTimeParse < now) || (changeStartTimeParse > now && changeEndTimeParse> now) ) {
             if (buisnessReasonValue == null)
                 false         
             else
                true
             
    	}
    	else
          true
    }
    else 
       true
    
}
else
    true