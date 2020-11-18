import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.user.ApplicationUser;
//import com.atlassian.crowd.embedded.api.User;
//import com.atlassian.crowd.embedded.api.CrowdService;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.bc.issue.IssueService
import com.atlassian.jira.bc.issue.IssueService.TransitionValidationResult
import com.atlassian.jira.bc.issue.IssueService.UpdateValidationResult
import com.atlassian.jira.issue.comments.CommentManager
import com.atlassian.jira.workflow.WorkflowTransitionUtil
import com.atlassian.jira.issue.IssueConstant
import com.atlassian.jira.issue.IssueInputParameters
import com.atlassian.jira.config.ConstantsManager
import com.atlassian.jira.config.ResolutionManager
import com.atlassian.crowd.embedded.api.Group

/**Initializing Variable**/
	ApplicationUser validuser
	def jiraflag = true
	def confluenceflag = true
	def resultTransition 
	def componentCount = 0
	def jiraIndex = 0
	def confluenceIndex = 0
	def startProgress = 341
	def allAccountsAction = 431
	def jiraAccountAction = 401
	def confluenceAccountAction = 411
	def addConfluenceGroup = 421
	def analystReviewingAction = 491
	def closeAction = 2
	
	/**Getting the ulti user account**/
	//CrowdService crowdService = ComponentAccessor.getCrowdService();
	//User user = crowdService.getUser("ulti")	
	def userManager = ComponentAccessor.getUserManager();
	ApplicationUser appUser = userManager.getUserByKey("ulti");
	CommentManager commentMgr = ComponentAccessor.getCommentManager()
    List<Group> gps = new ArrayList<Group>()
    List<String> resultMessage = new ArrayList<String>()
	
	IssueManager issueManager = ComponentAccessor.getComponentOfType(IssueManager);
	def customFieldManager = ComponentAccessor.getCustomFieldManager()
	//MutableIssue issue = issueManager.getIssueByCurrentKey("BIZ-30227");
	
		
	def type = customFieldManager.getCustomFieldObject("customfield_11290")
	def typeValue = issue.getCustomFieldValue(type)
		
    def userId = customFieldManager.getCustomFieldObject("customfield_14621")
    String userNameValueUppercase = issue.getCustomFieldValue(userId)
    def userNameValue = userNameValueUppercase.toLowerCase()
	def userName = userNameValue.split(" ")

	def fullName = customFieldManager.getCustomFieldObject("customfield_10608")
    String fullNameValue= issue.getCustomFieldValue(fullName)
		
	def groupsField = customFieldManager.getCustomFieldObject("customfield_10521")
	 gps = (List)issue.getCustomFieldValue(groupsField)
    int groupsFieldValue = gps.size()
	
	def contactEmail = customFieldManager.getCustomFieldObject("customfield_10610")
    String contactEmailFieldValues = issue.getCustomFieldValue(contactEmail)
    def validEmail = false

	if(contactEmailFieldValues.contains('@ultimatesoftware.com') ||contactEmailFieldValues.contains('@ukg.com')  ){
    
	    validEmail = true
	}
		
		
		/**Reading the Component size and checking if JIRA and COnfluence is added as Component on the rquest **/
 				    def components = issue.getComponents()
					int componentSize = components.size()
                    
					for( int j=0; j<=componentSize-1; j++){
					if(issue.getComponents()[j].getName() == "JIRA")
						{
							componentCount = componentCount + 1;
							jiraIndex = j /**Getting the JIRA index on the Component list **/
						}
					if(issue.getComponents()[j].getName()== "Confluence")
					   {
						    componentCount = componentCount + 1;
							confluenceIndex = j /**Getting the COnfluence index on the Component list**/
					   }
					}
		
        /**checking for existing user
        validuser  = userManager.getUserByKey(userNameValue);**/
		
		/**Executing the All Account Transition if component size is more then 1**/
		if((typeValue.toString() == "New Hire" || typeValue.toString() == "Account Request") && componentCount >=1){ 
            
            
                def actionID = startProgress   /**Iniating Start Progress Transition**/
                def issueService = ComponentAccessor.getIssueService()
			    def issueInputParameters = issueService.newIssueInputParameters()
				TransitionValidationResult validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
					if (validationResult.isValid()) {
								issueService.transition(appUser, validationResult)
						
							}	
			    if(fullNameValue == null || contactEmailFieldValues == "" || validEmail == false){
						jiraflag = false
						confluenceflag = false
						if(contactEmailFieldValues == null){				
							def body =  "Contact Email field is Empty"
							commentMgr.create(issue, appUser, body, true)
						}
                        if(fullNameValue == null){
                            def body =  "Contact Name field is Empty"
							commentMgr.create(issue, appUser, body, true)
                        
                        }
                       if
					}
				else{
		
						/**Execute All Account Transition if it is requested for both JIRA and Confluence**/
						if(componentCount == 2 && userName.size() == 1){
								
										def issueInputParametersValue =  issueInputParameters.addCustomFieldValue("customfield_18820",gps.name[0])
										def validUpdateResult = issueService.validateUpdate(appUser,issue.id, issueInputParametersValue)
										if(validUpdateResult.isValid()){
											issueService.update( appUser, validUpdateResult)					
										}
							actionID = allAccountsAction/**Iniating All Account Transition**/
							validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
								
							if (validationResult.isValid()) {
							
										
										resultMessage = (List)issueService.transition(appUser, validationResult).getErrorCollection().getAt("errorMessages")
										resultTransition = resultMessage[0]
										//issueIndexMgr.reIndex(validationResult.getIssue())
							}
							if(resultTransition == null){/**Checcking if All account Tranistion is succesfully executed, adding groups to Confluence account**/
							
								
								/**Adding Multiple groups for Confluence**/
					
								if(groupsFieldValue >0){
									def size = groupsFieldValue
									for(int i = 0; i<size; i++){  
										issueInputParametersValue =  issueInputParameters.addCustomFieldValue("customfield_18820",gps.name[i])
										validUpdateResult = issueService.validateUpdate(appUser,issue.id, issueInputParametersValue)
										if(validUpdateResult.isValid()){
											issueService.update( appUser, validUpdateResult)					
										}
										actionID = addConfluenceGroup/**Iniating Add Confluence Group transition**/
										issueInputParameters = issueService.newIssueInputParameters()
										validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
										
										def confluenceTransition 
										if (validationResult.isValid()) {
											resultMessage =(List) issueService.transition(appUser, validationResult).getErrorCollection().getAt("errorMessages")
											confluenceTransition = resultMessage[0]
											//issueIndexMgr.reIndex(validationResult.getIssue())
										}
										
									}
								}	
								
							}
									
							if( resultTransition == "User Profile already exists in Confluence!!"){
												
								confluenceflag = false
								def body = userNameValue+ " already exist in Confluence"
								commentMgr.create(issue, appUser, body, true)
												
								actionID = jiraAccountAction/**Iniating the JIRA transition**/
								validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
												
								
								if (validationResult.isValid()) {
									
									resultMessage = (List) issueService.transition(appUser, validationResult).getErrorCollection().getAt("errorMessages")
									def jiraTransition = resultMessage[0]
									//issueIndexMgr.reIndex(validationResult.getIssue())
								}
								
								
							}
							if(resultTransition == "User Profile already exist in JIRA!!"){
				
								def body = userNameValue+ " already exist in JIRA"
								commentMgr.create(issue, appUser, body, true)
								jiraflag = false

								actionID = confluenceAccountAction/**Iniating the confluence transition**/
								validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
								def confluenceTransition 
								if (validationResult.isValid()) {
											resultMessage = (List)issueService.transition(appUser, validationResult).getErrorCollection().getAt("errorMessages")
											confluenceTransition = resultMessage[0]
											//issueIndexMgr.reIndex(validationResult.getIssue())

								}
								if( confluenceTransition == "User Profile already exists in Confluence!!"){
												confluenceflag = false
												body = userNameValue+ " already exist in Confluence"
												commentMgr.create(issue, appUser, body, true)
								}
								
								
								if (confluenceTransition == null){
										/**Adding Multiple groups for Confluence**/
										
									
									
									if(groupsFieldValue>0){
										def size = groupsFieldValue
										for(int i = 0; i<size; i++){  
											issueInputParametersValue =  issueInputParameters.addCustomFieldValue("customfield_18820",gps.name[i])
											validUpdateResult = issueService.validateUpdate(appUser,issue.id, issueInputParametersValue)
											if(validUpdateResult.isValid()){
												issueService.update( appUser, validUpdateResult)					
											}
											actionID = addConfluenceGroup/**Iniating Add Confluence Group transition**/
											issueInputParameters = issueService.newIssueInputParameters()
											validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
											
											if (validationResult.isValid()) {
													resultMessage=(List) issueService.transition(appUser, validationResult).getErrorCollection().getAt("errorMessages")
													confluenceTransition = resultMessage[0]
													//issueIndexMgr.reIndex(validationResult.getIssue())
											}
											
										}
									}
								}
							}	
						}
					
			

					
			
						/**Exececute if component value is either JIRA or Confluence**/
							if(componentCount==1 && userName.size() == 1){		
							/**Iniating the  Jira Account transition**/
								if(issue.getComponents()[jiraIndex].name == "JIRA"){	
												actionID = jiraAccountAction
												validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)				
												def resultTransitionJIRA 
													if (validationResult.isValid()) {
												
														
														resultMessage =(List)issueService.transition(appUser, validationResult).getErrorCollection().getAt("errorMessages")
														resultTransitionJIRA = resultMessage[0]
														//issueIndexMgr.reIndex(validationResult.getIssue())
														}
									
									
										if(resultTransitionJIRA == "User Profile already exist in JIRA!!"){
											def body = userNameValue+ " already exist in JIRA"
											commentMgr.create(issue, appUser, body, true)
											jiraflag = false
										}
										
									}
								/**Iniating the confluence transition**/
								if(issue.getComponents()[confluenceIndex].name == "Confluence"){ 
									
											def issueInputParametersValue =  issueInputParameters.addCustomFieldValue("customfield_18820",gps.name[0])
											def validUpdateResult = issueService.validateUpdate(appUser,issue.id, issueInputParametersValue)
											if(validUpdateResult.isValid()){
												issueService.update( appUser, validUpdateResult)
																	
											}	
											
											actionID = confluenceAccountAction
											validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
											def resultTransitionConfluence 
											if (validationResult.isValid()) {
												resultMessage =(List)issueService.transition(appUser, validationResult).getErrorCollection().getAt("errorMessages")	
												resultTransitionConfluence = resultMessage[0]
												//issueIndexMgr.reIndex(validationResult.getIssue())
											}
												
											if( resultTransitionConfluence == "User Profile already exists in Confluence!!")
											{
												confluenceflag = false							
												def body = userNameValue+ " already exist in Confluence"
												commentMgr.create(issue, appUser, body, true)
												
											}
											
											if( resultTransitionConfluence == null) {
												/**Adding Multiple groups for Confluence**/	
											
											if(groupsFieldValue>0){
														def size = groupsFieldValue
															for(int i = 0; i<size; i++)
															{  
																issueInputParametersValue =  issueInputParameters.addCustomFieldValue("customfield_18820",gps.name[i])
																validUpdateResult = issueService.validateUpdate(appUser,issue.id, issueInputParametersValue)
																if(validUpdateResult.isValid()){
																			issueService.update(appUser, validUpdateResult)
																			
																}
																actionID = addConfluenceGroup/**Iniating Add Confluence Group transition**/
																issueInputParameters = issueService.newIssueInputParameters()
																		validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
																			def confluenceTransition 
																			if (validationResult.isValid()) {
											
																				resultMessage = (List)issueService.transition(appUser, validationResult).getErrorCollection().getAt("errorMessages")
																				confluenceTransition = resultMessage[0]
																				//issueIndexMgr.reIndex(validationResult.getIssue())
																				}
															
															}
												
												}
										
											}
								}
							}
				
					}
		    
				/**Checking Confluence and JIRA Flag**/
	         if(confluenceflag == true && jiraflag == true && userName.size() == 1){
				 
				 
				 
					actionID = closeAction /**If both the flags is set to true then close the Issue**/
					validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
						
						 if (validationResult.isValid()) {
					
							
							  issueService.transition(appUser, validationResult)
							  //issueIndexMgr.reIndex(validationResult.getIssue())
							}			
						
						

			    }
		       else{	
					actionID = analystReviewingAction/**If either of the flag is set to False then trnaistion to Analyst Reviewing Status**/
					validationResult = issueService.validateTransition(appUser, issue.id, actionID, issueInputParameters)
						
						 if (validationResult.isValid()) {						
							  issueService.transition(appUser, validationResult)
							  //issueIndexMgr.reIndex(validationResult.getIssue())						  
							}			     		
				}			
			if(userName.size() > 1){
				
				
			    def body ="There is a space in the UserName"
				commentMgr.create(issue, appUser, body, true)
			}
			
		}
				