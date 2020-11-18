import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueManager


@BaseScript FieldBehaviours fieldBehaviours
IssueManager issueManager = ComponentAccessor.getIssueManager()
def customFieldManager = ComponentAccessor.getCustomFieldManager()

def component = getFieldById("components")
def escalationType = customFieldManager.getCustomFieldObject("customfield_19220")
def devComponent = getFieldById("customfield_12622")
def issueID = getUnderlyingIssue()
def issue = issueManager.getIssueByCurrentKey(issueID.toString());

List escalationTypeValue =(List)escalationType.getValue(issue)



if(escalationTypeValue[0].toString() == 'Customer Facing'){
    component.setHidden(true)
 }

