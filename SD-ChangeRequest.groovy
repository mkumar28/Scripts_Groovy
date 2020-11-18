/*
1 This Script used to pre pouplate values in the fields for SD project in JIRA for all the issue type which goes through CHange Review process except product releae.
2 This Script is dependent on python script /JIRA/DATA/Groovy/Python/SDChangeTemplate.py
*/


import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript
import com.atlassian.jira.ComponentManager
import com.atlassian.jira.issue.MutableIssue

@BaseScript FieldBehaviours fieldBehaviours


Long projectId = 10380



       //defining the JIRA Classes we need for the script
    def optionsManager = ComponentAccessor.getOptionsManager()
    def customFieldManager = ComponentAccessor.getCustomFieldManager()
    def versionManager = ComponentAccessor.getVersionManager()


       //Initializing the Fields 
    template =  getFieldByName("Change Template")
    summary  = getFieldById("summary")
    change_priority = getFieldByName("Change Priority")
    fixVersionsField = getFieldById("fixVersions")
    isThisEmergency = getFieldByName("Is this an emergency?")
    changeType = getFieldByName("Change Type")
    changeRisk = getFieldByName("Change Risk/Impact")
    descriptionOfImpact = getFieldByName("Description of Impact")
    implementationSteps = getFieldByName("Implementation Steps")
    testPlan = getFieldByName("Test Plan")
    rollBackPlan = getFieldByName("Roll Back Plan")
    owner = getFieldByName("Owner")
    businessUnit = getFieldByName("Business Unit")
    environment = getFieldByName("Environment")
    dataCenter = getFieldByName("Data Center")
    tester = getFieldByName("Tester")
    changeDescription = getFieldByName ("Change Description")
    implementer = getFieldByName ("Implementer")
    devicesAffected = getFieldByName ("Devices Affected")
    contingencyPlan = getFieldByName ("Contingency Plan")
    changeNotify = getFieldByName ("Change Notify")
    requestor = getFieldByName("Requestor")
    product = getFieldByName("Product")
    otherEnvironment = getFieldByName("Other Environment")

       //For Single Select Customfield Change Priority
    def changePriorityCustomField = customFieldManager.getCustomFieldObject(change_priority.getFieldId())
    def changePriorityConfig = changePriorityCustomField.getRelevantConfig(getIssueContext())
    def changePriorityOptions = optionsManager.getOptions(changePriorityConfig)
    def changePriorityOptionToSelect1 = changePriorityOptions.find { it.value == "Normal" } 
    def changePriorityOptionToSelect2 = changePriorityOptions.find { it.value == "Low" } 
    def changePriorityOptionToSelect3 = changePriorityOptions.find { it.value == "Medium" } 


       //For Single Select Customfield Is this an emergency?
    def isThisEmergencyCustomField = customFieldManager.getCustomFieldObject(isThisEmergency.getFieldId())
    def isThisEmergencyConfig = isThisEmergencyCustomField.getRelevantConfig(getIssueContext())
    def isThisEmergencyOptions = optionsManager.getOptions(isThisEmergencyConfig)
    def isThisEmergencyOptionToSelect = isThisEmergencyOptions.find { it.value == "No - not categorized with emergency priority" }


       //For Single Select Customfield Change Type
    def changeTypeCustomField = customFieldManager.getCustomFieldObject(changeType.getFieldId())
    def changeTypeConfig = changeTypeCustomField.getRelevantConfig(getIssueContext())
    def changeTypeOptions = optionsManager.getOptions(changeTypeConfig)
    def changeTypeOptionToSelect1 = changeTypeOptions.find { it.value == "Deploy - Weekly One-off assignment" }
    def changeTypeOptionToSelect2 = changeTypeOptions.find { it.value == "Activations - Core" }
    def changeTypeOptionToSelect3 = changeTypeOptions.find { it.value == "Deploy - Quarterly Updates" }
    def changeTypeOptionToSelect4 = changeTypeOptions.find { it.value == "Cloud Engine - PCF updates" }
    def changeTypeOptionToSelect5 = changeTypeOptions.find { it.value == "COS - Rack/stack/cable"}
    def changeTypeOptionToSelect6 = changeTypeOptions.find { it.value == "Server - Drive expansion"}
    def changeTypeOptionToSelect7 = changeTypeOptions.find { it.value == "Server - Add"}
    def changeTypeOptionToSelect8 = changeTypeOptions.find { it.value == "Deploy - Application Upgrade"}
    def changeTypeOptionToSelect9 = changeTypeOptions.find { it.value == "Server - Decommission"}
    def changeTypeOptionToSelect10 = changeTypeOptions.find { it.value == "COS - Apply patch"}
    def changeTypeOptionToSelect11 = changeTypeOptions.find { it.value == "Activations - TEST - Core"}
    def changeTypeOptionToSelect12 = changeTypeOptions.find { it.value == "Activations - Implementations"}
    def changeTypeOptionToSelect13 = changeTypeOptions.find { it.value == "Activations - Decomissions"}
	 def changeTypeOptionToSelect14 = changeTypeOptions.find { it.value == "Deploy - Application Upgrade (V14)"}
    def changeTypeOptionToSelect15 = changeTypeOptions.find { it.value == "App owner - Application upgrade"}
    def changeTypeOptionToSelect16 = changeTypeOptions.find { it.value == "Cloud Engine - Artifactory Update"}
    def changeTypeOptionToSelect17 = changeTypeOptions.find { it.value == "COS - HW maintenance"}

       //For Single Select Customfield Change Risk
    def changeRiskCustomField = customFieldManager.getCustomFieldObject(changeRisk.getFieldId())
    def changeRiskConfig = changeRiskCustomField.getRelevantConfig(getIssueContext())
    def changeRiskOptions = optionsManager.getOptions(changeRiskConfig)
    def changeRiskOptionToSelect1 = changeRiskOptions.find { it.value == "Medium" }
    def changeRiskOptionToSelect2 = changeRiskOptions.find { it.value == "Low" }
    def changeRiskOptionToSelect3 = changeRiskOptions.find { it.value == "High" }


       //For Multi Select Customfield Business Unit
    def businessUnitCustomField = customFieldManager.getCustomFieldObject(businessUnit.getFieldId())
    def businessUnitConfig = businessUnitCustomField.getRelevantConfig(getIssueContext())
    def businessUnitOptions = optionsManager.getOptions(businessUnitConfig)
    def businessUnitOptionToSelect1 = businessUnitOptions.find { it.value in ["Enterprise Dedicated", "Enterprise Shared", "Midmarket"] }
    def businessUnitOptionToSelect2 = businessUnitOptions.find { it.value in ["Enterprise Shared"] } 
    def businessUnitOptionToSelect3 = businessUnitOptions.find { it.value in ["Enterprise Dedicated"]}
    def businessUnitOptionToSelect4 = businessUnitOptions.find { it.value in ["Infrastructure"]}
    def businessUnitOptionToSelect5 = businessUnitOptions.find { it.value in ["UCloud"]}


       //For Multi Select Customfield Environment
    def environmentCustomField = customFieldManager.getCustomFieldObject(environment.getFieldId())
    def environmentConfig = environmentCustomField.getRelevantConfig(getIssueContext())
    def opi[04
    ] = optionsManager.getOptions(environmentConfig)
    def environmentOptionToSelect = environmentOptions.findAll { it.value in ["E11","E12","E13","E14","E15","E1D","E21","E22","E23","E24","E2D","E31","E32","E33","E34","E3D","E41","E42","E4D","ET13","ET14","ET16","ET18","ET19","ET24","ET25","ET26","EW11","EW12","EW13","EW14","EW21","EW22","EW23","EW24","EW31", "EW32", "EW33", "EW34", "EW41", "EW42", "EW43", "EW44", "EWZ11","EZ11", "EZ12", "EZ13", "EZ14", "EZ1D","EZ21","EZ22","EZ23","EZ24","EZ2D","EZ3D","EZ41","EZ42", "EZ4D", "N11","N12","N13","N14","N1D","N21","N22","N23","N24","N2D","N31","N32","N33","N34","N3D","NW11","NW12","NWZ11","NWZ12","NWZ13","NZ11","NZ12","NZ13","NZ14","NZ1D","NZ2D","NZ3D","NZ4D","T11","T12","TW11","TZ11","TZ12"] }
    def environmentOptionToSelect1 = environmentOptions.findAll { it.value in ["EREC14-TEST", "EWREC14-TEST", "NREC14-TEST", "NWREC14-TEST", "TREC14-TEST"] }
    def environmentOptionToSelect2 = environmentOptions.findAll { it.value in ["EREC14-PROD", "EWREC14-PROD", "NREC14-PROD", "TREC14-PROD"] }
    def environmentOptionToSelect3 = environmentOptions.findAll { it.value in ["E3D","NZ3D"] }
    def environmentOptionToSelect4 = environmentOptions.findAll { it.value in ["MIA Ucloud"] }
    def environmentOptionToSelect5 = environmentOptions.findAll { it.value in ["E0", "E3", "E4", "EW0", "EW1", "EW2", "EW3", "EW4", "EZ0", "EZ1", "EZ2", "EZ3", "EZ4", "N0", "N1", "N2", "N3", "NW0", "NW1", "NZ0", "NZ1", "NZ2", "NZ3", "NZ4", "T0", "T1", "TW0", "TZ0", "V0" ] }
    def environmentOptionToSelect6 = environmentOptions.findAll { it.value in ["Other"] }

 

       //For Multi Select Customfield Data Center
    def dataCenterCustomField = customFieldManager.getCustomFieldObject(dataCenter.getFieldId())
    def dataCenterConfig = dataCenterCustomField.getRelevantConfig(getIssueContext())
    def dataCenterOptions = optionsManager.getOptions(dataCenterConfig)
    def dataCenterToSelect = dataCenterOptions.findAll { it.value in ["Atlanta", "Phoenix", "Toronto"]}
    def dataCenterToSelect1 = dataCenterOptions.findAll { it.value in ["Atlanta", "Phoenix"]}
    def dataCenterToSelect2 = dataCenterOptions.findAll { it.value in ["Miami"]}
    def dataCenterToSelect3 = dataCenterOptions.findAll { it.value in ["Atlanta", "Las Vegas", "Toronto"]}

    
       //Calculating fixVersion 
    int year = Calendar.getInstance().get(Calendar.YEAR);
    fixversion = versionManager.getVersion(projectId, year.toString())

 
       //getting userid for user picker fields
    def user1 = ComponentAccessor.getCrowdService().getUser("susanl")
    def user2 = ComponentAccessor.getCrowdService().getUser("sds")
    def user3 = ComponentAccessor.getCrowdService().getUser("johannesz")
    def user4 = ComponentAccessor.getCrowdService().getUser("trippl")
    def user5 = ComponentAccessor.getCrowdService().getUser("alexu")
    def user6 = ComponentAccessor.getCrowdService().getUser("trippl")
    def user7 = ComponentAccessor.getCrowdService().getUser("jennifergo")
    def user8 = ComponentAccessor.getCrowdService().getUser("michaelde")
    def user9 = ComponentAccessor.getCrowdService().getUser("enriquer")
    def user10 = ComponentAccessor.getCrowdService().getUser("marcc")
    def user11 = ComponentAccessor.getCrowdService().getUser("cloudappultipro")
    def user12 = ComponentAccessor.getCrowdService().getUser("jarreth")
    def user13 = ComponentAccessor.getCrowdService().getUser("jaimel")
    def user14 = ComponentAccessor.getCrowdService().getUser("peterk")
    
//Getting the Current Jira id
Object id = getFieldById("id")
//Reading vlaue from the Teamplate field
def value = template.getValue()
//Initiating the python script to get the current value of the Change Template field
def path = '/JIRA/Data/Groovy/Python/SDChangeTemplate.py '+''+ id.value +''
def proc = path.execute()
proc.waitFor()
def changeTemplateValue = proc.text.replaceFirst(",", "").replaceAll("\\s+\$", "");

//Getting loggedin user
def 	loggedinUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser().getKey()


//Defineing behaviour of the fields based upon the selection of temlate value

if(changeTemplateValue!= value){
    
if(value == "Cloud Engine - Scale out Diego cells")
  {
     //ClearTemplate()
    //Fields being used
    summary.setFormValue("Cloud Engine - Scale out Diego cells")
    fixVersionsField.setFormValue(fixversion*.id)    
    change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
    isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
    changeType.setFormValue(changeTypeOptionToSelect4.optionId)
    changeRisk.setFormValue(changeRiskOptionToSelect2.optionId)  
    descriptionOfImpact.setFormValue("No impact expected")
    implementationSteps.setFormValue("Log into ops man, click on Elastic Runtime tile, go to resource config, increase diego cell instances, hit save, and then apply changes.")
    testPlan.setFormValue("Log into ops man, click on Elastic Runtime tile, go to resource config, verify diego cell count has increased, check ops man log to verify successful completion.")
    rollBackPlan.setFormValue("Log into ops man, click on Elastic Runtime tile, go to resource config, decrease diego cell instances, hit apply changes.")
    owner.setFormValue(user3.getName())
   

       //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
    
    
    
       //Setting the style to unselecting the fields 
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")
  }
  
  
  
/*if(value == "COS - Rack/stack/cable")
  {
     ClearTemplate()
      //Fields being used
    summary.setFormValue("COS - Rack/stack/cable")
    change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
    fixVersionsField.setFormValue(fixversion*.id)
    isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
    changeType.setFormValue(changeTypeOptionToSelect5.optionId)
    changeRisk.setFormValue(changeRiskOptionToSelect2.optionId) 
    descriptionOfImpact.setFormValue("Could cause outage if: \n1. If network cable is touched \n2. If power cable is touched \n3. If other server is touched") 
    implementationSteps.setFormValue("1. Locate server \n2. Make sure U is free \n3. Make sure we have power ports and AMP \n4. Get server \n5. Get rails \n6. Put rails in \n7. Rack server \n8. Update portal \n9. Update elevations")
    testPlan.setFormValue("1. Do not power server on \n2. No network cables \n3. This is ONLY to rack servers")
    rollBackPlan.setFormValue("There is NO POWER OR NETWORK \n1.Take servers out of rack and rails")
    owner.setFormValue(user4.getName())
   
    
       //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
    
    
       //Setting the style to unselecting the fields 
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")  
  }*/



if(value == "Server - Drive expansion")
  {
     //ClearTemplate()
      //Fields being used
    summary.setFormValue("Server - Drive expansion")
    change_priority.setFormValue(changePriorityOptionToSelect2.optionId)
    fixVersionsField.setFormValue(fixversion*.id)
    isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
    changeType.setFormValue(changeTypeOptionToSelect6.optionId)
    changeRisk.setFormValue(changeRiskOptionToSelect2.optionId) 
    implementationSteps.setFormValue("Expand drive in SolidFire and OS.")
    testPlan.setFormValue("Verify disk expansion in OS.")
    rollBackPlan.setFormValue("None possible on disk expansion.")
    owner.setFormValue(user5.getName())
    descriptionOfImpact.setFormValue("None.")
   
      
       //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")

   
    
       //Setting the style to unselecting the fields
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")
   }
   
   
   
if(value == "Server - Add")
  {
    // ClearTemplate()
     //Fields being used      
    summary.setFormValue("Server - Add")
    change_priority.setFormValue(changePriorityOptionToSelect2.optionId)
    fixVersionsField.setFormValue(fixversion*.id)
    isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
    changeType.setFormValue(changeTypeOptionToSelect7.optionId)
    changeRisk.setFormValue(changeRiskOptionToSelect2.optionId) 
    implementationSteps.setFormValue("1. Deploy servers via Standard Template in vCenter.\n2. Configure each server with appropriate server hardware specs based on server standards.\n3. Set DHCP reservation address or statically assign IP addresses based on environment config.\n4. Apply Microsoft patches that are approved by development to date.\n5. Move server to correct OU in active directory.")
    testPlan.setFormValue("Verify servers are provisioned and VMware run once scripts are executed and added to domain.")
    rollBackPlan.setFormValue("Shutdown VM\nDelete VM from disk")
    owner.setFormValue(user5.getName())
   
      
       //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")

 

    
   //Setting the style to unselecting the fields 
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #white;}</style><head></html>")
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")
  }

  
if(value == "Server - Decommission Task 1")
  {
     //ClearTemplate()
    //Fields being used   
    summary.setFormValue("Server - Decommission Task 1")
    change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
    fixVersionsField.setFormValue(fixversion*.id)
    isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
    changeType.setFormValue(changeTypeOptionToSelect9.optionId)
    changeRisk.setFormValue(changeRiskOptionToSelect2.optionId) 
    descriptionOfImpact.setFormValue("Servers will be decommissioned.") 
    implementationSteps.setFormValue("Disable computer account. \nMove to disabled computers OU. \nMark decommissioned in portal. \nRemove from backup jobs.")
    testPlan.setFormValue("Verify changes.")
    rollBackPlan.setFormValue("Enable computer account. \nMove back to original OU. \nAdd back to backup jobs.")
    owner.setFormValue(user5.getName())
   
   
       //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>")
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
   
     
       //Setting the style to unselecting the fields 
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")
  }



if(value == "COS - Apply Windows Patches")
  {
   //ClearTemplate()
    //Fields being used   
    summary.setFormValue("COS - Apply Windows Patches")
    change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
    fixVersionsField.setFormValue(fixversion*.id)
    isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
    changeType.setFormValue(changeTypeOptionToSelect10.optionId)
    changeRisk.setFormValue(changeRiskOptionToSelect1.optionId) 
    descriptionOfImpact.setFormValue("May impact accessibility to the servers being patched.") 
    implementationSteps.setFormValue("12:00am - SCCM will begin installing updates while suppressing reboots \n\n3:00am - Failover of Clusters \n\n3:15am - Manually reboot servers \n\n4:00am -Manually reboot servers SECOND TIME \n\n4:15am: Perform post patching reboots and remediation/validation procedures.\nhttps://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82754321 \n\nClose ticket by 5:00am \n\n5:30am - Perform post patching reboot validation for Clusters and rest of the servers. \nhttps://confluence.ultimatesoftware.com/display/COS/Post+Patching+Reboot+Validation")
    testPlan.setFormValue("Test accessibility for servers/affected sites & URLs.")
    rollBackPlan.setFormValue("Uninstall offending patch.")
    owner.setFormValue(user6.getName())
   
   
       //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>")
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
   
   
    
   //Setting the style to unselecting the fields 
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361],#customfield_11361{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545],#customfield_11545{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544],#customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")
    
  }

  
if(value == "Activations - Implementations")
  {
   //ClearTemplate()
    //Fields being used  
    summary.setFormValue("Activations - Implementations")
    change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
    fixVersionsField.setFormValue(fixversion*.id)
    isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
    changeRisk.setFormValue(changeRiskOptionToSelect2.optionId)
    implementationSteps.setFormValue("Please follow the implementation steps for the application being implemented via the instructions in this link: https://confluence.ultimatesoftware.com/display/SD/Implementations")
    testPlan.setFormValue("Please follow the test plan for the application being implemented via the instructions in this link: https://confluence.ultimatesoftware.com/display/SD/Implementations")
    rollBackPlan.setFormValue("No Customer Impact.")
    owner.setFormValue(user1.getName())
    descriptionOfImpact.setFormValue("None.")
    tester.setFormValue(loggedinUser)
    implementer.setFormValue(loggedinUser)
    requestor.setFormValue(loggedinUser)


   
    //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>")        
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #d6f5d6;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #d6f5d6;}</style><head></html>")   
    
 
    
       //Setting the style to unselecting the fields
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
  }
  
  
  
if(value == "Activations - Decomissions")
  {
     //ClearTemplate()
    //Fields being used
    summary.setFormValue("Activations - Decomissions")
    change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
    fixVersionsField.setFormValue(fixversion*.id)
    isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
    changeRisk.setFormValue(changeRiskOptionToSelect2.optionId)
    implementationSteps.setFormValue("Please follow the steps for the application being decommissioned via the instructions in this link: https://confluence.ultimatesoftware.com/display/SD/Decom+and+Dismantle")
    testPlan.setFormValue("Please follow the test plan for the application being decommissioned via the instructions in this link: https://confluence.ultimatesoftware.com/display/SD/Decom+and+Dismantle")
    rollBackPlan.setFormValue("No Customer Impact.")
    owner.setFormValue(user1.getName())
    descriptionOfImpact.setFormValue("None.")
    tester.setFormValue(loggedinUser)
    implementer.setFormValue(loggedinUser)
    requestor.setFormValue(loggedinUser)
  
   
   //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>")        
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #d6f5d6;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #d6f5d6;}</style><head></html>") 
        
    
    //Setting the style to unselecting the fields
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554{background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555{background-color: #white;}</style><head></html>")
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541{background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
  }
  
if(value == "Cloud Engine - Artifactory Update"){
   //ClearTemplate()
    //Fields being used
    summary.setFormValue("Update Artifactory to")
    change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
    isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
    changeRisk.setFormValue(changeRiskOptionToSelect2.optionId)
    descriptionOfImpact.setFormValue("No Expected impact. The pipeline tests run throughout the entire process to ensure zero-downtime. During the update, the pipeline runs psr-like tests against Artifactory, attempting to download files every second and measuring for response code. We ensure zero-downtime (and therefore no impact) if all response codes were 200.")
    implementationSteps.setFormValue("Execute the deploy step in the Artifactory Deployment pipeline")
    testPlan.setFormValue("Run the smoke tests on the new version of artifactory that was just introduced into production to assert they pass https://ci.paas.mia.ulti.io:4443/teams/main/pipelines/Artifactory-Smoke ")
    rollBackPlan.setFormValue("Run upgrade process to last version")
    businessUnit.setFormValue(businessUnitOptionToSelect5.optionId)
    dataCenter.setFormValue(dataCenterToSelect2.optionId)
    environment.setFormValue(environmentOptionToSelect4.optionId)
    owner.setFormValue(user13.getName())
    changeDescription.setFormValue("Update Artifactory to")
    changeType.setFormValue(changeTypeOptionToSelect16.optionId)
    fixVersionsField.setFormValue(fixversion*.id)

    //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #d6f5d6;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541{background-color: #d6f5d6;}</style><head></html>")
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")

    //Setting the style to unselecting the fields 
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")
}

if(value == "COS - Windows Patching (INF)"){

   //ClearTemplate()
   summary.setFormValue("Windows Patching - INF - ")
   fixVersionsField.setFormValue(fixversion*.id)
   change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
   isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
   changeType.setFormValue(changeTypeOptionToSelect10.optionId)
   changeRisk.setFormValue(changeRiskOptionToSelect3.optionId)
   descriptionOfImpact.setFormValue("May Impact accessibility to the servers being patched")
   implementationSteps.setFormValue("12:00 AM - SCCM Will Begin installing updates\n\n3:00am - Failover of Clusters\n\n3:15am - Begin Reboots\n\n4:15am: Perform post patching reboots and remediation/validation procedures.\nhttps://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82754321\n\nClose ticket by 5:00am\n\n5:30am - Perform post patching reboot validation for Clusters and rest of the servers.\nhttps://confluence.ultimatesoftware.com/display/COS/Post+Patching+Reboot+Validation")
   testPlan.setFormValue("Test accessibility for servers / affected sites & URLs.")
   rollBackPlan.setFormValue("Uninstall offending patch")
   businessUnit.setFormValue(businessUnitOptionToSelect4.optionId)
   dataCenter.setFormValue(dataCenterToSelect3.optionId)
   environment.setFormValue(environmentOptionToSelect5.optionId)
   owner.setFormValue(user4.getName())
   tester.setFormValue(user10.getName())

   //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #d6f5d6;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")



    //Setting the style to unselecting the fields 
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541], #customfield_11541{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")
}
if(value == "DCOps - Add/replace memory"){

   //ClearTemplate()
   summary.setFormValue("DCOps - Add/replace memory on device - ")
   fixVersionsField.setFormValue(fixversion*.id)
   change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
   isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
   changeType.setFormValue(changeTypeOptionToSelect17.optionId)
   changeRisk.setFormValue(changeRiskOptionToSelect2.optionId)
   descriptionOfImpact.setFormValue("<TO BE FILLED OUT BY REQUESTOR>")
   implementationSteps.setFormValue("* Locate equipment and power down\n* Unrack equipment and access internals\n* Add/replace memory module as requested\n* Re-rack server and power on\n* Engage server team for testing")
   testPlan.setFormValue("* Ensure no errors upon boot\n* Engage server team for additional tests")
   rollBackPlan.setFormValue("Remove all memory modules added and reboot server")
   //environment.setFormValue(environmentOptionToSelect6.optionId)
   owner.setFormValue(user14.getName())

   //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
   



    //Setting the style to unselecting the fields 
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541], #customfield_11541{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")
    //dataCenter.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html>")
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")


}
if(value== "DCOps - Rack/stack/cable"){


    //ClearTemplate()
   summary.setFormValue("DCOps - Rack/stack/cable the following devices -")
   fixVersionsField.setFormValue(fixversion*.id)
   change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
   isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
   changeType.setFormValue(changeTypeOptionToSelect5.optionId)
   changeRisk.setFormValue(changeRiskOptionToSelect2.optionId)
   descriptionOfImpact.setFormValue("No expected impact")
   implementationSteps.setFormValue("* Locate equipment\n* Determine rack location\n* Update Elevations\n* Update Portal\n* Label all cables\n* Ensure switch ports are free\n* Cable up equipment\n* Create Labels\n* Cable DAC and RJ-45\n* Cable Power\n* Dress cables (3am-5am)\n* Plug into switches (3am-5am)\n* Rack Server (3am-5am)\n* Power on equipment\n* Test connectivity\n* Screenshot configs and attach to ticket\n* Close ticket and email group")
   testPlan.setFormValue("* Engage server team to test connectivity and troubleshoot issues")
   rollBackPlan.setFormValue("Unplug all cables, power off server")
   owner.setFormValue(user14.getName())

   //Setting the style
    summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
    change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
    isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
    changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
    descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
    testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
    rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
    owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
    changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
    implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
    fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")

    //Setting the style to unselecting the fields 
    changeDescription.setHelpText("<html><head><style>label[for=customfield_11541], #customfield_11541{background-color: #white;}</style><head></html>")
    implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
    devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
    contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
    changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
    requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>")
    //dataCenter.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
    dataCenter.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html>")
    businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
    tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
    environment.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")

}
  
  
if(value == "Clear Template")
  {
  ClearTemplate()
  }
}

def ClearTemplate(){
    //Fields being used
 fixVersionsField.setFormValue(fixversion*.id)

 
    //Setting the style
 fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
 
 //Unselecting the field
 businessUnit.setFormValue("")
 environment.setFormValue("")
 dataCenter.setFormValue("")
 tester.setFormValue("")
 owner.setFormValue("")
 rollBackPlan.setFormValue("")
 testPlan.setFormValue("")
 implementationSteps.setFormValue("")
 descriptionOfImpact.setFormValue("")
 changeRisk.setFormValue("")
 changeType.setFormValue("")
 change_priority.setFormValue("")
 changeDescription.setFormValue("")
 isThisEmergency.setFormValue("")
 summary.setFormValue("")
 implementer.setFormValue("")
 devicesAffected.setFormValue("")
 contingencyPlan.setFormValue("")
 changeNotify.setFormValue("")
 requestor.setFormValue("")
      
    
 //Setting the style to unselecting the fields 
 businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
 environment.setHelpText("<html><head><style>label[for=customfield_11362], #customfield_11362{background-color: #white;}</style><head></html")
 dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #white;}</style><head></html>")
 tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
 owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #white;}</style><head></html>")
 rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #white;}</style><head></html>")
 testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #white;}</style><head></html>")
 implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561], #customfield_11561{background-color: #white;}</style><head></html>")
 descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320], #customfield_18320{background-color: #white;}</style><head></html>")
 changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556{background-color: #white;}</style><head></html>")
 changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554{background-color: #white;}</style><head></html>")
 isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121], #customfield_14121{background-color: #white;}</style><head></html>")
 change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543{background-color: #white;}</style><head></html>")
 summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #white;}</style><head></html>")
 implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
 devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
 changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
 contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
 changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
 requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #white;}</style><head></html>") 

}

/**This will check if other value is selected within the environment field or not**/
if(environment.value != null){
   if( environment.value[0] == "Other"){

      otherEnvironment.setHidden(false)
      otherEnvironment.setRequired(true)
   }
   else{
         otherEnvironment.setHidden(true)
         otherEnvironment.setRequired(false)
         }
   }
else{
       otherEnvironment.setHidden(true)
       otherEnvironment.setRequired(false)
       }
