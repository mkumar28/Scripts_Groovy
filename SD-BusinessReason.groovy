import com.atlassian.jira.bc.issue.IssueService
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.ConstantsManager
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.IssueConstant
import com.atlassian.jira.issue.IssueInputParameters
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.comments.CommentManager
import com.atlassian.jira.project.Project
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.config.ResolutionManager;

	
	ApplicationUser validuser
	def resultTransition
	def jiraComponentFlag = false
	def confluenceComponentFlag = false
	def userGroups = [];
	def termination = 511;
	def confluenceTermination = 521;
	def cancel = 321;
	def closeAction = 2;
	def resolutionvalue = "Cancelled"
	List<String> groupsItem = new ArrayList<String>()
 
	
	/**Getting the user details for ulti account**/
	def userManager = ComponentAccessor.getUserManager();
	ApplicationUser appUser = userManager.getUserByKey("ulti");
	
	/**Initialising the Issuemanager,CustomFieldManager, UserUtil, GroupManager, CommentManager, Project**/
	def issueManager = ComponentAccessor.getIssueManager();
	def customFieldManager = ComponentAccessor.getCustomFieldManager()
	def userUtil = ComponentAccessor.getUserUtil()
	def groupManager = ComponentAccessor.getGroupManager()
	def resolutionManager = ComponentAccessor.getComponent(ResolutionManager)
	CommentManager commentMgr = ComponentAccessor.getCommentManager()
	ConstantsManager constantsManager = ComponentAccessor.getConstantsManager()
	MutableIssue issue = issueManager.getIssueByCurrentKey("BIZ-31815");
	Project project = issue.getProjectObject()
	
	/**Initialiazing the IssueService**/
	def issueService = ComponentAccessor.getIssueService()
	//def issueIndexMgr = ComponentAccessor.getIssueIndexManager()
	def issueInputParameters = issueService.newIssueInputParameters()
	
	/**Reading the customfield userid**/
	def userId = customFieldManager.getCustomFieldObject("customfield_14621")
	String userNameValueUpperCase = issue.getCustomFieldValue(userId)
	def userNameValue = userNameValueUpperCase.toLowerCase()
	def validAPPUser = userManager.getUserByKey(userNameValue);//Getting Application user
	
	
	/**Reading Type field**/
	def type = customFieldManager.getCustomFieldObject("customfield_11290")
	def typeValue = issue.getCustomFieldValue(type)
	
	/**Reading all the groups associated with the user**/
	if(validAPPUser != null){
		userGroups = groupManager.getGroupNamesForUser(validAPPUser);
	}
		/**chckinng if type field value is equal to Termination**/
		if(typeValue.toString() == "Termination"){
			
				
				/*Checking if any group is assigned to the  user in JIRA**/
					if(userGroups.size()>0){
						
						/*Adding Comment**/
						def body  = "Removed from these JIRA groups:"
						
						for (int i = 0; i < userGroups.size(); i++) {
							
							def group = groupManager.getGroup(userGroups[i].toString())
							userUtil.removeUserFromGroup(group,validAPPUser)
							body = body +"\n"+userGroups[i]
						}
						
						commentMgr.create(issue, appUser, body, true)
						jiraComponentFlag = true;
						
					
				}
				else{
					/*Adding comment**/
					def body  = "User does not exist in JIRA"
					commentMgr.create(issue, appUser, body, true)
				}
				
				/**Triggering the python script and readin the output**/
					def path = '/home/jira/JIRA_Termination/listConfluenceGroup.py '+''+ userNameValue +''
					def proc = path.execute()
					proc.waitFor()
					groupsItem =(List) proc.text.tokenize(',')
					int groupSize = groupsItem.size()-1
					
						/**Executing the Termination transition**/
						def actionID = termination
						workflowTransition(issue,appUser, actionID,issueService,issueInputParameters)
						
					if(groupSize > 0){
							/*Adding Comment**/
							def body  = "Removed from these Confluence groups:"
							
							/*checking if there is any confluence group assigned to the user profile**/
							for(int i=0; i<groupSize; i++){
								
							   /**Updating Add confluence  group field for every group**/
							   def issueInputParametersValue =  issueInputParameters.addCustomFieldValue("customfield_18820",groupsItem[i])
							   def validUpdateResult = issueService.validateUpdate(appUser,issue.id, issueInputParametersValue)
							   if(validUpdateResult.isValid()){
												issueService.update(appUser, validUpdateResult)
												
								}
								
								/**Executing Confluence Termination transition for every single group**/
								actionID = confluenceTermination
								workflowTransition(issue,appUser, actionID, issueService, issueInputParameters)
								
								body = body +"\n"+groupsItem[i]
									
							}
							
							/**Adding Comment**/
							 //addComment(body,issue,appUser, issueService,issueInputParameters)
							 commentMgr.create(issue, appUser, body, true)
							 confluenceComponentFlag = true;	//setting the confluence flag is groups exist for user
						}
					else {
						/**Adding comment**/
							def body = "User does not exist in Confluence"
							commentMgr.create(issue, appUser, body, true)
							//addComment(body,issue,appUser, issueService,issueInputParameters)
							 
					}
						
		}
	
	/**Initializing JIRA and Confluence Component**/
	def jiraComponent = ComponentAccessor.getProjectComponentManager().findByComponentName(project.getId(),'JIRA')
	def confluencecomponent = ComponentAccessor.getProjectComponentManager().findByComponentName(project.getId(),'Confluence')
	def collectionComponent =  ComponentAccessor.getProjectComponentManager().findComponentsByIssue(issue)
		
	/**Checking if JIRA and confluence flag is set to true, then add both the flag to the jira issue**/
	if(jiraComponentFlag == true && confluenceComponentFlag == true){
		
		collectionComponent.add(jiraComponent)
		collectionComponent.add(confluencecomponent)
		issue.setComponent(collectionComponent);
		ComponentAccessor.getIssueManager().updateIssue(appUser,issue,EventDispatchOption.ISSUE_UPDATED,true)
	
	}
	else if (jiraComponentFlag == true){
		//def collectionComponent =  ComponentAccessor.getProjectComponentManager().findComponentsByIssue(issue)
		collectionComponent.add(jiraComponent)
		issue.setComponent(collectionComponent);
		ComponentAccessor.getIssueManager().updateIssue(appUser,issue,EventDispatchOption.ISSUE_UPDATED,true)
		
		
	}
	else if (confluenceComponentFlag == true){
		//def collectionComponent =  ComponentAccessor.getProjectComponentManager().findComponentsByIssue(issue)
		collectionComponent.add(confluencecomponent)
		issue.setComponent(collectionComponent);
		ComponentAccessor.getIssueManager().updateIssue(appUser,issue,EventDispatchOption.ISSUE_UPDATED,true)
	}
		
	/**Closing Jira Incident if  profile exist else Cancelling it**/
	if(jiraComponentFlag == true || confluenceComponentFlag == true){
					def actionID = closeAction
					workflowTransition(issue,appUser, actionID, issueService,issueInputParameters)
						
	}
	else{
					//setResolution(resolutionvalue, constantsManager , issueInputParameters)
					issue.setResolution(resolutionManager.getResolutionByName("Cancelled"))
					def actionID = cancel
					workflowTransition(issue,appUser, actionID, issueService,issueInputParameters)
	}
	
	/**Implementing Comment funcntion**/
	def	addComment(String body, MutableIssue issue, ApplicationUser appUser, IssueService issueService, IssueInputParameters issueInputParameters){
												
		
							def issueInputParametersValue =  issueInputParameters.setComment(body)
							 def validUpdateResult = issueService.validateUpdate(appUser,issue.id, issueInputParametersValue)
	}
	
	
	
	/**Set Resolution Function**/
	/*def setResolution(String resoluiton,ConstantsManager constantsManager , IssueInputParameters issueInputParameters){
		IssueConstant issueResolutionConstant = constantsManager.getIssueConstantByName(ConstantsManager.RESOLUTION_CONSTANT_TYPE, resoluiton);
		issueInputParameters.setResolutionId(issueResolutionConstant.getId());
		
	}*/
	
	/**Implementing the workflow transition function**/
	def workflowTransition(MutableIssue issue, ApplicationUser appuser, int actionID, IssueService issueService, IssueInputParameters issueInputParameters){
		
		def validationResult = issueService.validateTransition(appuser, issue.id, actionID, issueInputParameters)
						
						 if (validationResult.isValid()) {
							  issueService.transition(appuser, validationResult)
							  //issueIndexMgr.reIndex(validationResult.getIssue())
							}
		
		
	}
			
		
		
		

	
		
