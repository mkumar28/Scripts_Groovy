import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.user.ApplicationUser


def issueSecurityLevel = ComponentAccessor.getIssueSecurityLevelManager() 
def issueManager = ComponentAccessor.getIssueManager()
def userManager = ComponentAccessor.getUserManager(); 
def projetComponentManager = ComponentAccessor.getProjectComponentManager()
def issueService = ComponentAccessor.getIssueService()


def appUser = userManager.getUserByKey("ulti");
def projectid = 10151//Project id

//MutableIssue issue = issueManager.getIssueByCurrentKey("ULTI-385702")
def salesForceComponent = projetComponentManager.findByComponentName(projectid, 'Salesforce')
def carrierIntegrations = projetComponentManager.findByComponentName(projectid, 'Carrier Integrations')
def integration_Platform = projetComponentManager.findByComponentName(projectid, 'Integration Platform')
def issueComponent = issue.getComponents() 

/**Update Security level if Component = salesforce**/
if(issueComponent[0].name  == 'Salesforce' ){
long id = 10751
EventDispatchOption event
setSecurity(id ,issueManager,appUser, issue)
}

/**Update Security level if Component = Carrier Integrations**/
if(issueComponent[0].name  == 'Carrier Integrations' ){
long id = 10752
EventDispatchOption event
setSecurity(id ,issueManager,appUser, issue)
}

/**Update Security level if Component = Integration Platform**/
if(issueComponent[0].name  == 'Integration Platform' ){
long id = 10850
EventDispatchOption event
setSecurity(id ,issueManager,appUser, issue)
}

/**Setting security level**/
def setSecurity(long id, IssueManager issueManager, ApplicationUser appUser, MutableIssue issue){
issue.setSecurityLevelId(id)
issueManager.updateIssue(appUser,issue,EventDispatchOption.ISSUE_UPDATED,true)
}
