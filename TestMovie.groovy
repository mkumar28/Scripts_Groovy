import com.onresolve.scriptrunner.runner.rest.common.CustomEndpointDelegate
import groovy.json.JsonBuilder
import groovy.transform.BaseScript
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.Response
import com.atlassian.core.ofbiz.util.CoreTransactionUtil
import com.atlassian.jira.bc.issue.comment.CommentService
import com.atlassian.jira.bc.issue.IssueService
import com.atlassian.jira.event.issue.IssueEventBundleFactory
import com.atlassian.jira.event.issue.txnaware.TxnAwareEventFactory
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.config.ConstantsManager
import com.atlassian.jira.config.StatusManager
import com.atlassian.jira.config.properties.JiraSystemProperties
import com.atlassian.jira.issue.AttachmentManager
import com.atlassian.jira.issue.IssueFieldConstants
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.issuetype.IssueType
import com.atlassian.jira.issue.security.IssueSecurityHelper
import com.atlassian.jira.project.Project
import com.atlassian.jira.project.ProjectManager
import com.atlassian.jira.web.SessionKeys
import com.atlassian.jira.web.action.issue.MoveIssueConfirm
import com.atlassian.jira.web.action.issue.MoveIssueUpdateFields
import com.atlassian.jira.web.bean.MoveIssueBean
import org.apache.log4j.Category
import org.apache.log4j.Logger
import org.apache.log4j.Level
import java.lang.reflect.Method
import webwork.action.ActionContext
import com.onresolve.scriptrunner.runner.rest.common.CustomEndpointDelegate
import groovy.transform.BaseScript
import com.atlassian.jira.help.HelpUrls
import com.atlassian.jira.bc.project.component.ProjectComponent
import javax.servlet.http.HttpServletRequest
import webwork.dispatcher.GenericDispatcher

@BaseScript CustomEndpointDelegate delegate

Test(httpMethod: "GET", groups: ["jira-administrators"]) { MultivaluedMap queryParams, String body,HttpServletRequest request  ->
    
  def projectManager = ComponentAccessor.getProjectManager();
def bulkkOperation = ComponentAccessor.getBulkOperationManager()
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def issuemanager = ComponentAccessor.getIssueManager()
def issueTypeSchemeManager = ComponentAccessor.getIssueTypeSchemeManager()
def subtaskManger = ComponentAccessor.getSubTaskManager()
def newIssueType 
def MutableIssue issue = issuemanager.getIssueByCurrentKey("ULTI-385596")
def userManager = ComponentAccessor.getUserManager();
def appUser = userManager.getUserByKey("ulti");


Collection<ProjectComponent> targetComponents = []


def projectObj =  projectManager.getProjectObjByKeyIgnoreCase('MT')
 


def issueTypes = ComponentAccessor.getConstantsManager().getAllIssueTypeObjects();
for(IssueType issuetype :issueTypes) {
    if(issuetype.name == ''){
        newIssueType = issuetype
    }
}

def status = ComponentAccessor.getConstantsManager().getStatusByName('Open')
MoveIssueBean moveIssueBean = new MoveIssueBean(ComponentAccessor.getConstantsManager(), ComponentAccessor.getProjectManager())


moveIssueBean.getFieldValuesHolder().put(IssueFieldConstants.PROJECT, projectObj.id)
moveIssueBean.getFieldValuesHolder().put(IssueFieldConstants.ISSUE_TYPE, issue.id)
moveIssueBean.getFieldValuesHolder().put(IssueFieldConstants.ISSUE_TYPE, issue.issueType.id.toString())
moveIssueBean.setIssueId(issue.getId())
moveIssueBean.setTargetStatusId(status.id )
moveIssueBean.setSourceIssueKey(issue.getKey())
moveIssueBean.setUpdatedIssue(issue)

GenericDispatcher gd = new GenericDispatcher("Move")
request.setAttribute("jira.webwork.generic.dispatcher",gd)
gd.prepareContext()
  
ActionContext.getSession().put(SessionKeys.MOVEISSUEBEAN, moveIssueBean)
ActionContext.setRequest(request)



MoveIssueUpdateFields moveIssueUpdateFields = new MoveIssueUpdateFields(
         ComponentAccessor.getSubTaskManager(),
         ComponentAccessor.getConstantsManager(),
         ComponentAccessor.getWorkflowManager(),
         ComponentAccessor.getFieldManager(),
         ComponentAccessor.getFieldLayoutManager(),
         ComponentAccessor.getIssueFactory(),
         ComponentAccessor.getFieldScreenRendererFactory(),
         ComponentAccessor.getComponent(CommentService.class),
         ComponentAccessor.getComponent(IssueSecurityHelper.class),
         ComponentAccessor.getUserUtil(),
         ComponentAccessor.getComponent(HelpUrls.class)
)

MoveIssueConfirm moveIssueConfirm = new MoveIssueConfirm(
        ComponentAccessor.getSubTaskManager(),
        ComponentAccessor.getComponentOfType(AttachmentManager.class),
        ComponentAccessor.getConstantsManager(),
        ComponentAccessor.getWorkflowManager(),
        ComponentAccessor.getFieldManager(),
        ComponentAccessor.getFieldLayoutManager(),
        ComponentAccessor.getIssueFactory(),
        ComponentAccessor.getFieldScreenRendererFactory(),
        ComponentAccessor.getComponentOfType(CommentService.class),
        ComponentAccessor.getComponentOfType(IssueSecurityHelper.class),
        ComponentAccessor.getIssueManager(),
        ComponentAccessor.getUserUtil(),
        ComponentAccessor.getIssueEventManager(),
        ComponentAccessor.getComponentOfType(IssueEventBundleFactory.class),
        ComponentAccessor.getComponentOfType(TxnAwareEventFactory.class),
        ComponentAccessor.getComponent(HelpUrls.class)
)

JiraSystemProperties.getInstance()
moveIssueUpdateFields.setId(issue.id)
Method privateUpdateFieldsDoExecute = MoveIssueUpdateFields.class.getDeclaredMethod("doExecute")
privateUpdateFieldsDoExecute.setAccessible(true)
privateUpdateFieldsDoExecute.invoke(moveIssueUpdateFields)
issue.getComponents().each{ component -> 
    targetComponents.add(ComponentAccessor.getProjectComponentManager().findAllForProject(projectObj.id)?.find { it.name == "MT Test"})}

    moveIssueBean.getUpdatedIssue().setComponent(targetComponents)
moveIssueConfirm.setId(issue.id)
Method privateIssueConfirmDoExecute = MoveIssueConfirm.class.getDeclaredMethod("doExecute")
privateIssueConfirmDoExecute.setAccessible(true)
String msg =(String)privateIssueConfirmDoExecute.invoke(moveIssueConfirm)
CoreTransactionUtil.commit(true)
    return Response.ok(new JsonBuilder([result: "${moveIssueBean.getSourceIssueKey()} ==> ${moveIssueBean.getUpdatedIssue()}"]).toString()).build();
}
