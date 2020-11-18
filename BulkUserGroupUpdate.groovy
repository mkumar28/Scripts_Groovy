import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.jira.issue.customfields.manager.OptionsManager;
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.customfields.option.Options;
import com.atlassian.crowd.embedded.api.Group
import java.util.logging.Logger

//getting the issue
def issueManager = ComponentAccessor.getIssueManager();
MutableIssue issue = issueManager.getIssueByCurrentKey("BIZ-24087");



//getting the value from the description field
def description = issue.getDescription()
String[] values = description.split("\\r\\n|\\n|\\r")

def userUtil = ComponentAccessor.getUserUtil()
def userManager = ComponentAccessor.getUserManager()
option = ""


for (int j = 0; j<values.size(); j++){

    Group group=(Group)options
    def user = userManager.getUserByName(userName)
    userUtil.addUserToGroup(group, user)

}