import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.crowd.embedded.api.Group
import com.atlassian.jira.user.ApplicationUser;


def groupManager = ComponentAccessor.getGroupManager()
def userUtil = ComponentAccessor.getUserUtil()

List appusers = (List)groupManager.getDirectUsersInGroup(groupManager.getGroup("jira-users"))
Group gp = groupManager.getGroup("Ultimate Software")
List offShoreUser = (List)groupManager.getDirectUsersInGroup(groupManager.getGroup("Offshore"))
List ultimateSoftwareUser = (List)groupManager.getDirectUsersInGroup(groupManager.getGroup("Ultimate Software"))
def count =0;
/*Looping to users from jira-users group **/
/**for(def i=0; i<appusers.size(); i++){ 
    //if(appusers.get(i).getName() == 'manishk'){
     if(appusers.get(i)  in offShoreUser || appusers.get(i) in  ultimateSoftwareUser ){
            
             count = count +1
        }
     else
     {
          userUtil.addUserToGroup(gp, appusers.get(i))//Adding user to Ultimate software group
     }
   
  
 
}**/

count
