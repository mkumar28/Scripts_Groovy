import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.crowd.embedded.api.Group
import com.atlassian.jira.user.ApplicationUser;


def groupManager = ComponentAccessor.getGroupManager()
def userUtil = ComponentAccessor.getUserUtil()

List appusers = (List)groupManager.getDirectUsersInGroup(groupManager.getGroup("jira-users"))
Group gp = groupManager.getGroup("Ultimate Software")

/*Looping to users from jira-users group **/
for(i=0; i<=500: i++){
userUtil.addUserToGroup(gp, appusers.get(i))//Adding user to Ultimate software group
}