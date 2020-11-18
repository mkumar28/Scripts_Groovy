/**
This Script make sure Change End Time is greater then current time.
However not valid for Emergency request

**/

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.timezone.TimeZoneManager


/**getting loggedin user Timzone**/
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).getLoggedInUserTimeZone()
def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("dd-MM-yyyy HH:mm:ss Z");


def customFieldManager = ComponentAccessor.getCustomFieldManager()
def changeEndTime = customFieldManager.getCustomFieldObject("customfield_11547")
def changeEndTimeValue = issue.getCustomFieldValue(changeEndTime)
def changePriority = customFieldManager.getCustomFieldObject("customfield_11543")
String changePriorityValue =   issue.getCustomFieldValue(changePriority)
def logedInUser =ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def userManager = ComponentAccessor.getUserManager();
def appUser = userManager.getUserByKey("kelvint");


//def date = new Date().format( "dd-MM-yyyy HH:mm:ss")
def endTimeValue = (Date)changeEndTimeValue
def endTime = endTimeValue.format("dd-MM-yyyy HH:mm:ss")
Date changeEndTimeParse = Date.parse("dd-MM-yyyy HH:mm:ss", endTime)
Date now =  Date.parse("dd-MM-yyyy HH:mm:ss Z", userCurrentTime)
String value = changePriorityValue

if(appUser != logedInUser){
if(changePriorityValue != 'Emergency')
{
   if(  changeEndTimeParse < now )
	{
		false
	 }
   else
	true
}
else
  true
}
else
  true
