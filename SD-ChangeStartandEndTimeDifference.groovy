/**
This Script is used to makke sure if difference between Change Start Time and Change End Time is not more then 10 hours

**/

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.timezone.TimeZoneManager
import java.util.concurrent.TimeUnit;

/**getting loggedin user Timzone**/
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).getLoggedInUserTimeZone()
def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("dd-MM-yyyy HH:mm:ss Z");


def customFieldManager = ComponentAccessor.getCustomFieldManager()
def changeStartTime = customFieldManager.getCustomFieldObject("customfield_11546")
def changeStartTimeValue = issue.getCustomFieldValue(changeStartTime)
def changeEndTime = customFieldManager.getCustomFieldObject("customfield_11547")
def changeEndTimeValue = issue.getCustomFieldValue(changeEndTime)
def logedInUser =ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def userManager = ComponentAccessor.getUserManager();
def appUser = userManager.getUserByKey("kelvint");

//def date = new Date().format( "dd-MM-yyyy HH:mm:ss")
def endTimeValue = (Date)changeEndTimeValue
def endTime = endTimeValue.format("dd-MM-yyyy HH:mm:ss")
def startTimeValue = (Date) changeStartTimeValue
def startTime = startTimeValue.format("dd-MM-yyyy HH:mm:ss")
Date changeStartTimeParse = Date.parse("dd-MM-yyyy HH:mm:ss", startTime)
Date changeEndTimeParse = Date.parse("dd-MM-yyyy HH:mm:ss", endTime)
Date now =  Date.parse("dd-MM-yyyy HH:mm:ss Z", userCurrentTime)



def millis = changeEndTimeParse.time - changeStartTimeParse.time
  long hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis))
  long min = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
  long sec = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
i
if(appUser != logedInUser){
	if(hours > 10)
 	 false
	else if (hours == 10 && min > 0) 
         false
	else if (hours == 10 && min == 0 && sec > 0)
 	 false
	else 
 	 true
}
else
 true
