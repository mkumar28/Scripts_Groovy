import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.ComponentManager
import com.onresolve.jira.groovy.user.FieldBehaviours
import com.atlassian.jira.issue.MutableIssue
import groovy.transform.BaseScript
import com.atlassian.jira.timezone.TimeZoneManager
import java.util.Date;
import java.util.concurrent.TimeUnit

@BaseScript FieldBehaviours fieldBehaviours

/**getting loggedin user Timzone**/
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager). getLoggedInUserTimeZone()
def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("dd-MM-yyyy HH:mm:ss");

/**Reading fields**/
def changeEndTime =  getFieldByName("Change End Time")
def changeStartTime = getFieldByName("Change Start Time")
def changePriority = getFieldByName("Change Priority")
def date = new Date().format( 'dd-MM-yyyy HH:mm:ss')
Date changeEndTimeparsed
Date changeStartTimeparsed
Date now =  Date.parse("dd-MM-yyyy HH:mm:ss", userCurrentTime)

/**Getting the Current status of the issue**/
Object id = getFieldById("id")
def issue_Status
MutableIssue issue
if(id.value != null){
//issue = ComponentManager.getInstance().getIssueManager().getIssueObject(Long.parseLong(id.value))//JIRA 6
long value = Long.parseLong(id.value.toString());
issue = ComponentAccessor.getIssueManager().getIssueObject(value)//for JIRA 7
if(issue != null){
//issue_Status = issue.getStatusObject().getName();//for JIRA 6
issue_Status = issue.getStatus().getName();//for JIRA 7
}
}

/**Reading Change Start Time Field and pasing through it Date format**/
 if( changeStartTime.value != ""){
     def startTimeValue = (Date)changeStartTime.value
     def startTime = startTimeValue.format('dd-MM-yyyy HH:mm:ss')
     changeStartTimeparsed = Date.parse("dd-MM-yyyy HH:mm:ss", startTime)
 }


/**Executing Behaviours when Change Time Field is not null and Change Prirority != null**/
if (changeEndTime.value != "" && changePriority.value != "Emergency"){
   def endTimeValue = (Date)changeEndTime.value
   def endTime = endTimeValue.format('dd-MM-yyyy HH:mm:ss')
   changeEndTimeparsed= Date.parse("dd-MM-yyyy HH:mm:ss", endTime)
   def millis = changeEndTimeparsed.time - changeStartTimeparsed.time  
   long hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis))
   long min = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
   long sec = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        
   if(changeEndTimeparsed < now) {
    changeEndTime.setHelpText("Please modify the Change End Time to a future date/time.")
    if(changeStartTimeparsed < now && (issue_Status == "" ||issue_Status != "Change Approved")){
     changeStartTime.setHelpText("Please modify the Change Start Time to a future date/time.")  
    }
    else{
     changeStartTime.setHelpText("") 
    }
   }    
   else if(changeEndTimeparsed < changeStartTimeparsed || (changeEndTimeparsed == changeStartTimeparsed && changeStartTimeparsed != null)){
    changeStartTime.setHelpText("Change Start Time should always be before Change End Time. Please modify accordingly.")
    changeEndTime.setHelpText("Change End Time should always be after the Change Start Time. Please modify accordingly.")
   }
   else if(hours > 10){   
    changeEndTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
    if(changeEndTimeparsed >  changeStartTimeparsed && changeStartTimeparsed>now){
     changeStartTime.setHelpText("")     
    }
   }
   else if (hours== 10 && min > 0){
    changeEndTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
    if(changeEndTimeparsed >  changeStartTimeparsed && changeStartTimeparsed>now){
     changeStartTime.setHelpText(" ")     
    } 
   } 
   else if (hours == 10 && min== 0 && sec > 0) {
    changeEndTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
    if(changeEndTimeparsed >  changeStartTimeparsed && changeStartTimeparsed>now){
    changeStartTime.setHelpText(" ")     
    }
   }   
   else if (hours <=10 && min>=0 && sec>=0){
    changeEndTime.setHelpText(" ")
    if(changeStartTimeparsed>now){
     changeStartTime.setHelpText(" ")
    }
   }
   else{                 
    changeEndTime.setHelpText(" ")                                  
   }
}
/**Executing Behaviours when Change End Time is not null and Change Prority = Emergency **/

else if(changeEndTime.value != ""){
   def endTimeValue = (Date)changeEndTime.value
   def endTime = endTimeValue.format('dd-MM-yyyy HH:mm:ss')
   changeEndTimeparsed= Date.parse("dd-MM-yyyy HH:mm:ss", endTime)
   def millis = changeEndTimeparsed.time - changeStartTimeparsed.time
   long hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis))
   long min = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
   long sec = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
 
   if(changeEndTimeparsed < changeStartTimeparsed || (changeEndTimeparsed == changeStartTimeparsed && changeStartTimeparsed != null)){
    changeEndTime.setHelpText("Change End Time should always be after the Change Start Time. Please modify accordingly.")
    changeStartTime.setHelpText("Change Start Time should always be before Change End Time. Please modify accordingly.")
   }
   else if(hours > 10){
    changeEndTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
    if(changeEndTimeparsed >  changeStartTimeparsed ){
    changeStartTime.setHelpText(" ")     
    }
   }
   else if (hours== 10 && min > 0){
    changeEndTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
    if(changeEndTimeparsed >  changeStartTimeparsed){
     changeStartTime.setHelpText(" ")     
    } 
   } 
   else if (hours == 10 && min== 0 && sec > 0){
    changeEndTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
    if(changeEndTimeparsed >  changeStartTimeparsed ){
     changeStartTime.setHelpText(" ")     
    }
   }   
   else if (hours<=10 && min>=0 && sec>=0){
    changeEndTime.setHelpText(" ")
    changeStartTime.setHelpText(" ")      
   }
   else{ 
    changeEndTime.setHelpText(" ")         
   }
}