/**
Need to update Security  - 10540
Task Link Type - 1000
In progress Id - 11144
Analysis Complete ID - 10186
/rest/scriptrunner/latest/custom/BulkMove
**/


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
import com.atlassian.jira.project.version.Version
import com.atlassian.jira.bc.project.version.VersionService
import com.atlassian.jira.config.StatusManager
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.issue.link.IssueLink
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.timezone.TimeZoneManager
import java.text.SimpleDateFormat;

@BaseScript CustomEndpointDelegate delegate

BulkMove(httpMethod: "GET", groups: ["jira-administrators"]) { MultivaluedMap queryParams, String body,HttpServletRequest request  ->
    
  def projectManager = ComponentAccessor.getProjectManager();
def bulkkOperation = ComponentAccessor.getBulkOperationManager()
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def issuemanager = ComponentAccessor.getIssueManager()
def issueTypeSchemeManager = ComponentAccessor.getIssueTypeSchemeManager()
def subtaskManger = ComponentAccessor.getSubTaskManager()
def newIssueType 
//def MutableIssue issue = issuemanager.getIssueByCurrentKey("ULTI-385323")
def sourceProject = 'PRO'
Long securityScheme = 10641// Need to be updated in prod
def TaskLinkType = 10000 // Need to be updated in prod
def userManager = ComponentAccessor.getUserManager();
def appUser = userManager.getUserByKey("ulti");
def versionService = ComponentAccessor.getComponent(VersionService)
def securityLevelManager =ComponentAccessor.getIssueSecurityLevelManager()
def groupManager = ComponentAccessor.getGroupManager()
def userUtil = ComponentAccessor.getUserUtil()
def issuelinkManger = ComponentAccessor.getIssueLinkManager()
def statusManager = ComponentAccessor.getComponent(StatusManager)
def inProgress = "11144"
def analysisComplete = "10186"
def searchProvider = ComponentAccessor.getComponentOfType(SearchProvider.class);
def searchService = ComponentAccessor.getComponentOfType(SearchService.class);
def changeHistoryManager = ComponentAccessor.getChangeHistoryManager()
def issueService = ComponentAccessor.getIssueService()
def issueInputParameters = issueService.newIssueInputParameters()
def issueManager = ComponentAccessor.getIssueManager()
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).getLoggedInUserTimeZone()

def directoryURL ="/home/appadmin/DevMigration/"
    

def projectObj =  projectManager.getProjectObjByKeyIgnoreCase(sourceProject)
def jqlargument =  "project = ULTI AND issuetype  in (\"User Story\", Defect, \"Internal Development Story\", Epic) AND component = AuthN AND ((resolution = unresolved AND created >= 2019-01-01 AND fixVersion is EMPTY) or (fixVersion in (\"AUTHN-2019 JUL\",\"AUTHN-2019 JUN\",\"Alpha 3\",\"July 2019\",\"Milestone 2\") and status !=Canceled))" //3* Update JQL
//def jqlargument = "Key=SOFTDEMO-2823"
List<String> targetIssues = new ArrayList<String>()
List<String> sourceIssues = new ArrayList<String>()
List<String> taskIssues = new ArrayList<String>()

    
/**Getting the logged in user current time**/
def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("yyyy-MM-dd-hh:mm:SS");//Getting the logged in user current time
Date now =  format.parse(userCurrentTime)    

/***Creating a log file**/
File file = new File(directoryURL+'DetailLog_'+userCurrentTime+".txt");
file.write("Bulk Updaate Log file\n")
File file1 = new File(directoryURL+'BreifLog_'+userCurrentTime+".txt");
file1.write("Bulk Updaate Log file\n")
File file2 = new File(directoryURL+'issueTypeLog_'+userCurrentTime+".txt");

   
/**Executing the JQL**/
SearchService.ParseResult parseResult = searchService.parseQuery(appUser,jqlargument);
def query = parseResult.getQuery();
def results = searchProvider.search(query, appUser, PagerFilter.getUnlimitedFilter());
def fetchedIssues = results.getIssues();
def insideLoop = 0  
def linkSource = 0
def  taskIssueVersion
def prjvrsnString = ""
/**Iterating over the feteched issues**/
for(def jqlIssues : fetchedIssues){
        file.append("====================================\n")
        file1.append("====================================\n")
        file.append("Moving issue: "+jqlIssues.key+"\n")
        Collection<ProjectComponent> targetComponents = []
		Collection<ProjectComponent> newComponents = []
		
		Collection<Version> targetVersion = [] 
		Collection<Version> newVersions = [] 
		
    
		def taskVersion = [:]
		def taskComponent = [:]
		def taskKey = [:]
        MutableIssue issue = issuemanager.getIssueByCurrentKey(jqlIssues.key)
       
        def status = issue.getStatus()/**Setting the status to In Progress if status in soruce Project is In Testing**/
         file.append("issue status: "+status.getName()+"\n")
        if(status.getName() == "In Testing" || status.getName() == "In Testing - 1" || status.getName() == "In Testing - 2" || status.getName() == "In Testing - 3"){
           status =    statusManager.getStatus(inProgress)
         }
    	if(status.getName() == "Analysis - 1" || status.getName() == "Analysis - 2"){
        
            status =    statusManager.getStatus(analysisComplete)
    	}
        /**Getting the outward link for the source issue **/
        def outwardLinkSource = issuelinkManger.getOutwardLinks(issue.getId())
        linkSource = outwardLinkSource.size()
        
        if(outwardLinkSource.size() != 0){
            
            for( IssueLink link : outwardLinkSource){
                if(link.issueLinkType.id == 10000){
                    Collection<ProjectComponent> targetTaskComponents = []
                    Collection<Version> targetTaskVersion = [] 
                    def taskId = (Long)link.destinationId
                    
                    MutableIssue  taskIssue = issuemanager.getIssueObject(taskId)
                    file.append("Found Subtask for this issue: "+taskIssue.key+"\n")
                    taskIssues.add(taskId.toString())
                    taskIssue.getComponents().each{ component -> 
                    targetTaskComponents.add(ComponentAccessor.getProjectComponentManager().findAllForProject(projectObj.id)?.find {it.name == component.name})}

                    taskIssueVersion = ComponentAccessor.getVersionManager().getFixVersionsFor(taskIssue)
                    if(taskIssueVersion.size() !=0){//Checking if version exist or not
                    
                    taskIssueVersion.each{version ->
                        targetTaskVersion.add(ComponentAccessor.getVersionManager().getFixVersionsFor(taskIssue)?.find{it.name == version.name})}

                    }

                    taskComponent[taskId.toString()] = targetTaskComponents
                    taskVersion[taskId.toString()] = targetTaskVersion
                    file.append("Component of Subtask: \n"+taskComponent+"\n")
                    file.append("Version of Subtask: \n"+taskVersion+"\n")
                    taskKey[taskId.toString()] = taskIssue.key
                } 


            }
        }
      
       /**Preparing to move**/
        MoveIssueBean moveIssueBean = new MoveIssueBean(ComponentAccessor.getConstantsManager(), ComponentAccessor.getProjectManager())


        moveIssueBean.getFieldValuesHolder().put(IssueFieldConstants.PROJECT, projectObj.id)
        moveIssueBean.getFieldValuesHolder().put(IssueFieldConstants.ISSUE_TYPE, issue.id)
        moveIssueBean.getFieldValuesHolder().put(IssueFieldConstants.ISSUE_TYPE, issue.issueType.id.toString())
        moveIssueBean.setIssueId(issue.getId())
        moveIssueBean.setTargetStatusId(status.id)
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
    
         
       /**Processing Compoennt**/
        if(issue.getComponents().size() > 0 ){//Checking if Component field is null or not
        
        def  issueComponent = issue.getComponents()
            for(ProjectComponent issueComp : issueComponent){
                file.append("Found Component in  issue: "+issueComp.name+ "\n")
                def found = 0 
                def projectComponent = ComponentAccessor.getProjectComponentManager().findAllForProject(projectObj.id)
                for(ProjectComponent comp : projectComponent){
                    if(comp.name == issueComp.name){
                        found = found + 1
                        file.append("Component exist in target project: "+issueComp.name+"\n")
                    }
                    
               }
               if(found == 0){
                    file.append("Component does not exist in target project: "+issueComp.name+"\n")
                }
               if(found == 1)
               {
                   issue.getComponents().each{ component -> 
            	   targetComponents.add(ComponentAccessor.getProjectComponentManager().findAllForProject(projectObj.id)?.find {it.name == component.name})}
        		   moveIssueBean.getUpdatedIssue().setComponent(targetComponents)
               }
            
            }
        
 
        }
        /**Processing Version**/
        def  issueVersion = ComponentAccessor.getVersionManager().getFixVersionsFor(issue)
        if(issueVersion.size() >0){//Checking if version exist or not
           file.append("Found Versions in issue\n")
           for(Version issueVrsn : issueVersion){
                
                def found = 0
                def ProjectVersion = ComponentAccessor.getVersionManager().getVersions(projectObj)  
                    for (Version vrsn : ProjectVersion){
                        
                        if(vrsn.name == issueVrsn.name){
                            file.append("Version exist in project:"+issueVrsn.name+"\n")
                            found = found + 1

                        }
                    }
                def result
                def newVersion
                if(found == 0){
                        file.append("Creating version in porject: "+issueVrsn.name+"\n")
                        newVersion = ComponentAccessor.getVersionManager().createVersion(issueVrsn.name, null, issueVrsn.getReleaseDate() , issueVrsn.getDescription() , projectObj.id,null, false)
                        if(issueVrsn.isReleased() == true){
                           result = versionService.validateReleaseVersion(appUser, newVersion, newVersion.getReleaseDate())//If Version is releasaed in Source project, releasae on target project as well
                                                if (result.isValid()) {
                                                            versionService.releaseVersion(result)
                                                }
                        }
                }
           }
                issue.getFixVersions().each{version ->
                    targetVersion.add(ComponentAccessor.getVersionManager().getFixVersionsFor(issue)?.find{it.name == version.name})}
                moveIssueBean.getUpdatedIssue().setFixVersions(targetVersion) 
        }


        /***Chekcing for Issue Security**/
        def sourceLevelId = issue.getSecurityLevelId()
        def sourceLevel = securityLevelManager.getSecurityLevel(sourceLevelId)
        def securityLevel = securityLevelManager.getSecurityLevelByNameAndSchema(sourceLevel.getName(), securityScheme)
        moveIssueBean.getUpdatedIssue().setSecurityLevelId(securityLevel.getId())   

        /**Checking Assignee and Reporter is part of PD group or not**/
        /*def assignee = issue.getAssignee() 
        def reporter = issue.getReporter()
        def assigneeGroup = groupManager.getGroupNamesForUser(assignee.getName())
        def reporterGroup = groupManager.getGroupNamesForUser(reporter.getName())
        def group = groupManager.getGroup('Product Development')
        def assigneeGroupAdd = 0 
        def reporterGroupAdd = 0
        if(assigneeGroup.size()==0){
             groupManager.addUserToGroup(assignee, group)
             assigneeGroupAdd = assigneeGroupAdd +1



            }
        if(reporterGroup.size()==0){
             groupManager.addUserToGroup(reporter, group)
             reporterGroupAdd = reporterGroupAdd + 1

        }*/



        /**Performing the move**/
        moveIssueConfirm.setId(issue.id)
        Method privateIssueConfirmDoExecute = MoveIssueConfirm.class.getDeclaredMethod("doExecute")
        privateIssueConfirmDoExecute.setAccessible(true)
        String msg =(String)privateIssueConfirmDoExecute.invoke(moveIssueConfirm)
	    file1.append("Moved Issue "+issue.toString()+" ==> "+moveIssueBean.getUpdatedIssue().toString()+"\n")
       /**setting up Component and Fixversion for subtask**/
       
       def newOutwardLink = issuelinkManger.getOutwardLinks(moveIssueBean.getUpdatedIssue().getId())
       if(newOutwardLink.size()>0){
        
        for( IssueLink link : outwardLinkSource){
            if(link.issueLinkType.id == 10000){
                
                def taskId = (Long)link.destinationId
                MutableIssue  taskIssue = issuemanager.getIssueObject(taskId)
                def Issuetype = taskIssue.getIssueType()
                if(Issuetype.name == 'Db Change Request'){
                    file2.write("Bulk Updaate Log file\n")
                    file2.append(taskIssue.getKey()+" "+Issuetype.name+"\n")
                }
                file.append("Updating Component OR Version on Subtask: "+ taskIssue.getKey()+"\n")
                def oldTask = changeHistoryManager.issueManager.getIssue(taskIssue.getKey())
                def id  = oldTask.id
                
               
                newComponents = (Collection<ProjectComponent>)taskComponent[id.toString()]
                newVersions = (Collection<Version>)taskVersion[id.toString()]
                taskIssue.setComponent(newComponents)
                /*for(ProjectComponent comp : newComponents){
                         
                        def issueInputParametersValue = issueInputParameters.setComponentIds(comp.id)
                        def validUpdateResult = issueService.validateUpdate(appUser,taskIssue.id, issueInputParametersValue)
                        if(validUpdateResult.isValid()){
                                       issueService.update(appUser, validUpdateResult)
                        }
                       
                }*/
               file1.append("Moved SubTask "+taskKey[id.toString()]+" ==> "+taskIssue.getKey()+"\n")
               Collection<Version> targetTaskVersion = [] 
               for(Version versn : newVersions){
                        def found = 0
                        def ProjectVersion = ComponentAccessor.getVersionManager().getVersions(projectObj)  
                       
                            for (Version Prjvrsn : ProjectVersion){
                                if(Prjvrsn.name == versn.name){

                                    found = found + 1
                                    targetTaskVersion.add(Prjvrsn)
                                   

                                }
                            }
                        def result
                        def newVersion
                        if(found == 0){

                                newVersion = ComponentAccessor.getVersionManager().createVersion(versn.name, null, versn.getReleaseDate() , versn.getDescription() , projectObj.id,null, false)
                                
                                if(versn.isReleased() == true){
                                   result = versionService.validateReleaseVersion(appUser, newVersion, newVersion.getReleaseDate())//If Version is releasaed in Source project, releasae on target project as well
                                   if (result.isValid()) {
                                       versionService.releaseVersion(result)
                                  }
                                }
                               targetTaskVersion.add(newVersion)
                                
                        }   
            
                }
            file.append("Adding version at subtask:\n"+ targetTaskVersion.toString()+"\n")
            file.append("Adding Component at subtask:\n"+ newComponents.toString()+"\n")
            taskIssue.setFixVersions(targetTaskVersion)
            issueManager.updateIssue(appUser, taskIssue, EventDispatchOption.ISSUE_UPDATED, false)   
           
        }
       }    
      }
    

file.append("Move Completed:\n"+moveIssueBean.getUpdatedIssue().toString()+"\n")
targetIssues.add(issue.toString()+"==>"+moveIssueBean.getUpdatedIssue().toString())
//sourceIssues.add(issue.toString()) 

}
CoreTransactionUtil.commit(true)
    return Response.ok(new JsonBuilder([result: "${targetIssues}"] ).toString()).build();
}
