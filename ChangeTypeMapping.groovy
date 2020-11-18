import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.user.ApplicationUser;


IssueManager issueManager = ComponentAccessor.getComponentOfType(IssueManager);
def customFieldManager = ComponentAccessor.getCustomFieldManager()
//MutableIssue issue = issueManager.getIssueByCurrentKey("SD-4311818")
def type = customFieldManager.getCustomFieldObject("customfield_11554")
String typeValue = issue.getCustomFieldValue(type)
ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()

if(typeValue.startsWith("Network") && currentUser.getUsername() == 'kelvint'){
    true
}
if(typeValue.startsWith("Network") && currentUser.getUsername() == 'kelvint'){
    true
}
else
{
    false
}

