/**
This Script make sure Change Start Time is greater then current time.
However not valid for Emergency request
**/

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.timezone.TimeZoneManager
import java.util.Date.*;

/**getting loggedin user Timzone**/
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).getLoggedInUserTimeZone()
def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("dd-MM-yyyy HH:mm:ss Z");
def logedInUser =ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()


def customFieldManager = ComponentAccessor.getCustomFieldManager()
def changeStartTime = customFieldManager.getCustomFieldObject("customfield_11546")
def changeStartTimeValue = issue.getCustomFieldValue(changeStartTime)
def changePriority = customFieldManager.getCustomFieldObject("customfield_11543")
String changePriorityValue =   issue.getCustomFieldValue(changePriority)
/**Getting the user details for ulti account**/
def userManager = ComponentAccessor.getUserManager();
def appUser = userManager.getUserByKey("kelvint");



//def date = new Date().format( "dd-MM-yyyy HH:mm:ss")
def startTimeValue = (Date) changeStartTimeValue
def startTime = startTimeValue.format("dd-MM-yyyy HH:mm:ss")
Date changeStartTimeParse = Date.parse("dd-MM-yyyy HH:mm:ss", startTime)
Date now =  Date.parse("dd-MM-yyyy HH:mm:ss Z", userCurrentTime)
String value = changePriorityValue
if(appUser != logedInUser){
if(changePriorityValue != 'Emergency' )
{
   if( changeStartTimeParse < now )
	  false
   else
      true
     
}
else
 true
}
else
    true
