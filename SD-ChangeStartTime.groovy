import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.ComponentManager
import com.atlassian.jira.issue.MutableIssue
import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript
import com.atlassian.jira.timezone.TimeZoneManager
import java.util.Date.*;
import java.util.*
import java.text.SimpleDateFormat;

@BaseScript FieldBehaviours fieldBehaviours
/**getting loggedin user Timzone**/
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).getLoggedInUserTimeZone()
def usereCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("dd-MM-yyyy HH:mm:ss");

 
 /**Reading the Fields**/
 def changeStartTime =  getFieldByName("Change Start Time")
 def changeEndTime = getFieldByName("Change End Time")
 def changePriority = getFieldByName("Change Priority")
 def date = new Date().format( 'dd-MM-yyyy HH:mm:ss')

 Date changeStartTimeparsed 
 Date changeEndTimeparsed
 /**Initialising Date Field**/

 Date now =  Date.parse("dd-MM-yyyy HH:mm:ss", usereCurrentTime)
 def changeEndTimeValue = 0
 
 
 /**Checking if changeEndTime is not null and parsing it to Date Format**/
  if (changeEndTime.value != ""){
      	      def endTimeValue = (Date)changeEndTime.value
              def endTime = endTimeValue.format("dd-MM-yyyy HH:mm:ss")
              changeEndTimeparsed = Date.parse("dd-MM-yyyy HH:mm:ss",endTime)
              changeEndTimeValue = changeEndTimeparsed.time
  }
  
/**Getting the Current status of the issue**/
Object id = getFieldById("id")
def issue_Status
MutableIssue issue
if(id.value != null){
//issue = ComponentManager.getInstance().getIssueManager().getIssueObject(Long.parseLong(id.value))//JIRA 6
long value = Long.parseLong(id.value.toString());
issue = ComponentAccessor.getIssueManager().getIssueObject(value)//for JIRA 7
if(issue != null){
//issue_Status = issue.getStatusObject().getName();//JIRA 6
issue_Status = issue.getStatus().getName();//for JIRA 7
}
}
       
/**Executing Behaviour if ChangeStartTime is not null and Change Priority != Emergency **/
 if (changeStartTime.value != "" && changePriority.value != "Emergency" ){ 
     def startTimeValue = (Date) changeStartTime.value
     def startTime = startTimeValue.format("dd-MM-yyyy HH:mm:ss")
     changeStartTimeparsed = Date.parse("dd-MM-yyyy HH:mm:ss",startTime)
    
    
    def millis = changeEndTimeValue - changeStartTimeparsed.time
    long hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis))
    long min = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
    long sec = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
    
    
  if(changeStartTimeparsed < now && (issue_Status == "" ||issue_Status != "Change Approved")){
   changeStartTime.setHelpText("Please modify the Change Start Time to a future date/time.")
   if(changeEndTimeparsed < now && changeEndTimeparsed!=null){
       changeEndTime.setHelpText("Please modify the Change End Time to a future date/time.")    
    }
   else{
     changeEndTime.setHelpText("") 
   }
  }
  else if(changeStartTimeparsed > changeEndTimeparsed && changeEndTimeparsed!=null ||(changeStartTimeparsed == changeEndTimeparsed && changeEndTimeparsed!=null)){
   changeStartTime.setHelpText("Change Start Time should always be before Change End Time. Please modify accordingly.")
   if(changeEndTimeparsed>now){
      changeEndTime.setHelpText("Change End Time should always be after the Change Start Time. Please modify accordingly.")
   }
  }
  else if(hours > 10 && changeEndTime.value != "" && changeEndTimeparsed>now){     
   changeStartTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
   if(changeStartTimeparsed <  changeEndTimeparsed && changeEndTimeparsed>now){
    changeEndTime.setHelpText(" ")
   }
     }
  else if (hours == 10 && min > 0 ){
   changeStartTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
   if(changeStartTimeparsed <  changeEndTimeparsed && changeEndTimeparsed>now){
    changeEndTime.setHelpText(" ")
      }
  } 
  else if (hours == 10 && min == 0 && sec > 0  ){
   changeStartTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
  if(changeStartTimeparsed <  changeEndTimeparsed && changeEndTimeparsed>now){
    changeEndTime.setHelpText(" ")
      } 
  } 
  else if(hours <=10 && hours>=0 && min>=0 && sec >=0 ){
      changeStartTime.setHelpText(" ")
      changeEndTime.setHelpText(" ")
  }
  else{   
    changeStartTime.setHelpText(" ")     
  }
}
 /**Executing behaviour if change window gap is more then 10 hour and if Change priority = Emergency**/
 else if(changeStartTime.value != ""){
      def startTimeValue = (Date) changeStartTime.value
      def startTime = startTimeValue.format("dd-MM-yyyy HH:mm:ss")
      changeStartTimeparsed = Date.parse("dd-MM-yyyy HH:mm:ss",startTime)
      def millis = changeEndTimeValue - changeStartTimeparsed.time
      long hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis))
      long min = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
      long sec = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
     
     
  if(changeStartTimeparsed > changeEndTimeparsed && changeEndTimeparsed!=null || (changeStartTimeparsed == changeEndTimeparsed && changeEndTimeparsed!=null) ){
    changeStartTime.setHelpText("Change Start Time should always be before Change End Time. Please modify accordingly.")
     changeEndTime.setHelpText("Change End Time should always be after the Change Start Time. Please modify accordingly.")
  }  

  else if(hours > 10  && changeEndTime.value != "" && changeEndTimeparsed>now){     
   changeStartTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
   if(changeStartTimeparsed <  changeEndTimeparsed){
   changeEndTime.setHelpText(" ")
     }
  }
  else if (hours == 10 && min > 0 ){
   changeStartTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
   if(changeStartTimeparsed <  changeEndTimeparsed ){
    changeEndTime.setHelpText(" ")
         }
     } 
  else if (hours == 10 && min == 0 && sec > 0){
   changeStartTime.setHelpText("Change windows cannot exceed 10 hours. Please modify the Change Start Time and/or Change End Time accordingly.")
   if(changeStartTimeparsed <  changeEndTimeparsed  && changeEndTime.value != "" && changeEndTimeparsed>now){
     changeEndTime.setHelpText(" ")
        } 
     } 
  else if(hours <=10 && hours>=0 && min>=0 && sec>=0){
     changeStartTime.setHelpText(" ")
     changeEndTime.setHelpText(" ")     
  }
  else{    
  changeStartTime.setHelpText(" ")      
  }
}