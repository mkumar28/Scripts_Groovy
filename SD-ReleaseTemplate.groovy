/*
1 This Script used to pre pouplate values in the fields for SD project in JIRA for Product Release issue Type
2 This Script is also depenednt on the pyhton script /JIRA/Data/Groovy/Python/SDProductReleaseTemplate.py.
*/

import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript

@BaseScript FieldBehaviours fieldBehaviours


Long projectId = 10380


       //defining the JIRA Classes we need for the script
    def optionsManager = ComponentAccessor.getOptionsManager()
    def customFieldManager = ComponentAccessor.getCustomFieldManager()
    def versionManager = ComponentAccessor.getVersionManager()
    def componentManager = ComponentAccessor.getProjectComponentManager()



       //Initializing the Fields 
    template =  getFieldByName("Release Template")
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
    //businessUnit = getFieldByName("Business Unit")
    environment = getFieldByName("Environment")
    dataCenter = getFieldByName("Data Center")
    tester = getFieldByName("Tester")
    changeDescription = getFieldByName ("Change Description")
    implementer = getFieldByName ("Implementer")
    devicesAffected = getFieldByName ("Devices Affected")
    contingencyPlan = getFieldByName ("Contingency Plan")
    changeNotify = getFieldByName ("Change Notify")
    impactfulSalesPreSalesDemo = getFieldByName("Impactful to Sales/Pre-Sales/Demos?")
    product = getFieldByName("Product")
    requestor = getFieldByName("Requestor")
    componentField = getFieldById("components")


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
    def changeTypeOptionToSelect1 = changeTypeOptions.find { it.value == "Deploy - Software Patch" }
    def changeTypeOptionToSelect2 = changeTypeOptions.find { it.value == "Activations - Core" }
    def changeTypeOptionToSelect3 = changeTypeOptions.find { it.value == "Deploy - Quarterly Updates" }
    def changeTypeOptionToSelect4 = changeTypeOptions.find { it.value == "Cloud Engine - PCF Core Resource Updates" }
    def changeTypeOptionToSelect5 = changeTypeOptions.find { it.value == "COS - Rack/stack/cable"}
    def changeTypeOptionToSelect6 = changeTypeOptions.find { it.value == "Server - Drive expansion"}
    def changeTypeOptionToSelect7 = changeTypeOptions.find { it.value == "Server - Add"}
    def changeTypeOptionToSelect8 = changeTypeOptions.find { it.value == "Deploy - Application Upgrade"}
    def changeTypeOptionToSelect9 = changeTypeOptions.find { it.value == "Server - Decommission"}
    def changeTypeOptionToSelect10 = changeTypeOptions.find { it.value == "COS - Apply patch"}
    def changeTypeOptionToSelect11 = changeTypeOptions.find { it.value == "Activations - TEST - Core"}
    def changeTypeOptionToSelect12 = changeTypeOptions.find { it.value == "Activations - Implementations"}
    def changeTypeOptionToSelect13 = changeTypeOptions.find { it.value == "Activations - Decomissions"}
    def changeTypeOptionToSelect14 = changeTypeOptions.find { it.value == "Deploy - Application Upgrade"}
    def changeTypeOptionToSelect15 = changeTypeOptions.find { it.value == "App owner - Application upgrade"}
    
	

       //For Single Select Customfield Change Risk
    def changeRiskCustomField = customFieldManager.getCustomFieldObject(changeRisk.getFieldId())
    def changeRiskConfig = changeRiskCustomField.getRelevantConfig(getIssueContext())
    def changeRiskOptions = optionsManager.getOptions(changeRiskConfig)
    def changeRiskOptionToSelect1 = changeRiskOptions.find { it.value == "Medium" }
    def changeRiskOptionToSelect2 = changeRiskOptions.find { it.value == "Low" }


       //For Multi Select Customfield Business Unit
    /*def businessUnitCustomField = customFieldManager.getCustomFieldObject(businessUnit.getFieldId())
    def businessUnitConfig = businessUnitCustomField.getRelevantConfig(getIssueContext())
    def businessUnitOptions = optionsManager.getOptions(businessUnitConfig)
    def businessUnitOptionToSelect1 = businessUnitOptions.findAll { it.value in ["Enterprise Dedicated", "Enterprise Shared", "Midmarket"] }
    def businessUnitOptionToSelect2 = businessUnitOptions.find { it.value in ["Enterprise Shared"] }	
    def businessUnitOptionToSelect3 = businessUnitOptions.find { it.value in ["Enterprise Dedicated"]}
    def businessUnitOptionToSelect6 = businessUnitOptions.find { it.value in ["Corporate"]}
    def businessUnitOptionToSelect7 = businessUnitOptions.findAll { it.value in ["Enterprise Shared","Midmarket"]} */

       //For Multi Select Customfield Environment
    def environmentCustomField = customFieldManager.getCustomFieldObject(environment.getFieldId())
    def environmentConfig = environmentCustomField.getRelevantConfig(getIssueContext())
    def environmentOptions = optionsManager.getOptions(environmentConfig)
    def environmentOptionToSelect = environmentOptions.findAll { it.value in ["E11","E12","E13","E14","E15","E1D","E21","E22","E23","E24","E2D","E31","E32","E33","E34","E3D","E41","E42","E4D","ET13","ET14","ET16","ET18","ET19","ET24","ET25","ET26","EW11","EW12","EW13","EW14","EW21","EW22","EW23","EW24","EW31", "EW32", "EW33", "EW34", "EW41", "EW42", "EW43", "EW44", "EWZ11","EZ11", "EZ12", "EZ13", "EZ14", "EZ1D","EZ21","EZ22","EZ23","EZ24","EZ2D","EZ3D","EZ41","EZ42", "EZ4D", "N11","N12","N13","N14","N1D","N21","N22","N23","N24","N2D","N31","N32","N33","N34","N3D","NW11","NW12","NWZ11","NWZ12","NWZ13","NZ11","NZ12","NZ13","NZ14","NZ1D","NZ2D","NZ3D","NZ4D","T11","T12","TW11","TZ11","TZ12"] }
    def environmentOptionToSelect1 = environmentOptions.findAll { it.value in ["EREC14-TEST", "EWREC14-TEST", "NREC14-TEST", "NWREC14-TEST", "TREC14-TEST"] }
    def environmentOptionToSelect2 = environmentOptions.findAll { it.value in ["EREC14-PROD", "EWREC14-PROD", "NREC14-PROD", "TREC14-PROD"] }
    def environmentOptionToSelect3 = environmentOptions.findAll { it.value in ["E3D","NZ3D"] }
    def environmentOptionToSelect4 = environmentOptions.findAll { it.value in ["EMOBILE-PROD","NMOBILE-PROD","TMOBILE-PROD"] }
    def environmentOptionToSelect5 = environmentOptions.findAll { it.value in ["UTE1","UTE2","UTE3","UTE4","UTEW1","UTEW2","UTEW3","UTEW4","UTEZ1","UTEZ2"] }
	def environmentOptionToSelect6 = environmentOptions.findAll { it.value in ["UTN1","UTN2","UTN3","UTNW1","UTNZ1","UTT1","UTTW1","UTTZ1"] }
    def environmentOptionToSelect7 = environmentOptions.findAll { it.value in ["EGWO-PROD"] }
    def environmentOptionToSelect8 = environmentOptions.findAll { it.value in ["EWFM14-PROD","NWFM14-PROD","TWFM14-PROD"] }
    def environmentOptionToSelect9 = environmentOptions.findAll { it.value in ["EWFM14-TEST","NWFM14-TEST","TWFM14-TEST"] }
    def environmentOptionToSelect10 = environmentOptions.findAll { it.value in ["ATL PCF"]}
    def environmentOptionToSelect11 =  environmentOptions.findAll{ it.value in ["E11","E12","E13","E14","E15","E1D","E21","E22","E23","E24","E2D","E31","E32","E33","E34","E3D","E41","E42","E43","E44","E4D","ET16","ET18","ET19","ET28","ET31","EW11","EW12","EW13","EW14","EW15","EW21","EW22","EW23","EW24","EW31", "EW32", "EW33", "EW34", "EW41", "EW42", "EW43","EW44","EW45","EW46","EWZ11","EZ11", "EZ12", "EZ13", "EZ14","EZ15","EZ16","EZ17","EZ1D","EZ21","EZ22","EZ23","EZ24","EZ2D","EZ3D","EZ41","EZ42", "EZ4D", "N11","N12","N13","N14","N1D","N21","N22","N23","N24","N2D","N31","N32","N33","N34","N35","N3D","NW11","NW12","NW13","NW14","NW15","NWZ11","NWZ12","NWZ13","NWZ14","NWZ21","NZ11","NZ12","NZ13","NZ14","NZ15","NZ16","NZ17","NZ1D","NZ2D","NZ3D","NZ4D","T11","T12","TW11","TW12","TZ11","TZ12"]}	

       //For Multi Select Customfield Data Center
    def dataCenterCustomField = customFieldManager.getCustomFieldObject(dataCenter.getFieldId())
    def dataCenterConfig = dataCenterCustomField.getRelevantConfig(getIssueContext())
    def dataCenterOptions = optionsManager.getOptions(dataCenterConfig)
    def dataCenterToSelect = dataCenterOptions.findAll {it.value in ["Atlanta", "Phoenix", "Toronto"]}
    def dataCenterToSelect1 = dataCenterOptions.findAll {it.value in ["Atlanta", "Phoenix"]}
    def dataCenterToSelect2 = dataCenterOptions.findAll {it.value in ["Atlanta", "Las Vegas", "Toronto"]}
    def dataCenterToSelect3 = dataCenterOptions.findAll {it.value in ["Atlanta"]}
    def dataCenterToSelect4 = dataCenterOptions.findAll {it.value in ["Las Vegas", "Toronto"]}
    def dataCenterToSelect5 = dataCenterOptions.findAll {it.value in ["Atlanta", "Las Vegas", "Toronto"]}
	//For Single Select Customfield Impactful to Sales/Pre-Sales/Demos
    def impactfulSalesPreSalesDemoCustomField = customFieldManager.getCustomFieldObject(impactfulSalesPreSalesDemo.getFieldId())
    def impactfulSalesConfig = impactfulSalesPreSalesDemoCustomField.getRelevantConfig(getIssueContext())
    def impactfulSalesOptions = optionsManager.getOptions(impactfulSalesConfig)
    def impactfulSalesOptionsToSelect1 = impactfulSalesOptions.find { it.value == "Yes" }
    def impactfulSalesOptionsToSelect2 = impactfulSalesOptions.find { it.value == "No" }
    
      //For Multi Select Customfield Product
    def productCustomField = customFieldManager.getCustomFieldObject(product.getFieldId())
    def productConfig = productCustomField.getRelevantConfig(getIssueContext())
    def productOptions = optionsManager.getOptions(productConfig)
    def productToSelect1 = productOptions.findAll { it.value in ["Experience Ecosystem"]}
    def productToSelect2 = productOptions.findAll { it.value in ["Time"]}
    def productToSelect3 = productOptions.findAll { it.value in ["Recruiting"]}
    def productToSelect4 = productOptions.findAll { it.value in ["USMP"]}
    //def productToSelect5 = productOptions.findAll { it.value in ["Workforce Management"]}
    def productToSelect6 = productOptions.findAll { it.value in ["Experience Enabler"]}
       //Calculating fixVersion 
    int year = Calendar.getInstance().get(Calendar.YEAR);
    fixversion = versionManager.getVersion(projectId, year.toString())

    //calculating Component
    //def ProjectComponentManager = componentManager.getProjectComponentManager();
    def projectComponent = componentManager.findByComponentName(10380,"Mobile Platform")
    def projectComponent1 = componentManager.findByComponentName(10380,"UltiPro Time Collection")
    def projectComponent2 = componentManager.findByComponentName(10380,"Recruiting")
    def projectComponent3 = componentManager.findByComponentName(10380,"USMP")
    def projectComponent4 = componentManager.findByComponentName(10380,"WFM UPT")
    def projectComponent5 = componentManager.findByComponentName(10380,"Cloud Deployment")  
  
	
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
    def user14 = ComponentAccessor.getCrowdService().getUser("cloudappultipro")
    def user15 = ComponentAccessor.getCrowdService().getUser("emanuelv")
    def user16 = ComponentAccessor.getCrowdService().getUser("jessicat")
    def user17 = ComponentAccessor.getCrowdService().getUser("Recruiting App Owners")
    def user18 = ComponentAccessor.getCrowdService().getUser("cloudready")
    def user19 = ComponentAccessor.getCrowdService().getUser("stevens")
    
	
//Reading vlaue from the Teamplate field
def value = template.getValue()
//Getting the current issue id
Object id = getFieldById("id")
//Initiating the python script to get the current value of the Release Template field
def path = '/JIRA/Data/Groovy/Python/SDProductReleaseTemplate.py '+''+ id.value +''
def proc = path.execute()
proc.waitFor()
def rleaseTemplateValue = proc.text.replaceFirst(",", "").replaceAll("\\s+\$", "");
	
      

if(rleaseTemplateValue!= value){
//defineing behaviour of the fields based upon the selection of temlate value
    if(value == "Weekly Patch Assignment")
      {
         //ClearTemplate()
            //Fields being used
        //summary.setFormValue("Weekly Patch Assignment")
        change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
        isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
        //changeType.setFormValue(changeTypeOptionToSelect1.optionId)
        changeRisk.setFormValue(changeRiskOptionToSelect1.optionId)
        fixVersionsField.setFormValue(fixversion*.id)
        descriptionOfImpact.setFormValue("Possible intermittent access to Ultipro Web during the maintenance window.")
        implementationSteps.setFormValue("Start Test environments at 12AM \nStart Prod environments at 3AM \nRun MDUU on Site(MDUU) Servers \nRun FRED on Client,Int,Auth,SQL,Web,DPM Servers")
        testPlan.setFormValue("Review oneoff logs to confirm deployment was successful")
        rollBackPlan.setFormValue("Work with Dev On-call to resolve any issues and finish deployment. No actual rollback planned.")
        owner.setFormValue(user8.getName())
        tester.setFormValue(user2.getName())
        //businessUnit.setFormValue(businessUnitOptionToSelect1.optionId)
        environment.setFormValue(environmentOptionToSelect11*.optionId)
        dataCenter.setFormValue(dataCenterToSelect5*.optionId)
        componentField.setFormValue(projectComponent5.id)
        changeType.setFormValue(changeTypeOptionToSelect1.optionId)


        //Setting the style
        change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
        fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
        isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
        changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
        changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>")
        descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
        implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
        testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
        rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
        owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
        tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
        environment.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #d6f5d6;}</style><head></html>")
        dataCenter.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #d6f5d6;}</style><head></html>")
        componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #d6f5d6;}</style><head></html>")
         

        //Setting the style to unselecting the fields
        summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #white;}</style><head></html>")
        implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11554 {background-color: #white;}</style><head></html>")	
        devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
        changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
        contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
        changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
        //componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #white;}</style><head></html>")
        product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #white;}</style><head></html>")
        impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")
        requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")
      }



    if(value == "Shared Ultipro Quarterly Updates")
      {
         //ClearTemplate()
           //Fields being used
        //summary.setFormValue("Shared UltiPro Quarterly Updates")
        change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
        fixVersionsField.setFormValue(fixversion*.id)
        isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
        changeType.setFormValue(changeTypeOptionToSelect3.optionId)
        changeRisk.setFormValue(changeRiskOptionToSelect1.optionId)
        implementationSteps.setFormValue("https://confluence.ultimatesoftware.com/display/SD/Upgrade+Checklist")
        testPlan.setFormValue("Test BO,PAC,SMC,Web,web reports,3rd party.")
        rollBackPlan.setFormValue("Restore backup")
        owner.setFormValue(user1.getName())
        tester.setFormValue(user2.getName())
        descriptionOfImpact.setFormValue("None.")


           //Setting the style
        
        change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
        fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
        isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
        changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
        changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>")        
        implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
        testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
        rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
        owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
        tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")
        descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")


           //Setting the style to unselecting the fields
        summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #white;}</style><head></html>")
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
        environment.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
        dataCenter.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
        implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
        devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
        changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
        contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
        changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
        componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #white;}</style><head></html>")
        product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #white;}</style><head></html>")
        impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")
        requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")
      }


    if(value == "Weekly Corp Patch")
      {
         //ClearTemplate()
           //Fields being used  
        //summary.setFormValue("Weekly USG Patch")
        change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
        fixVersionsField.setFormValue(fixversion*.id)
        isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
        changeType.setFormValue(changeTypeOptionToSelect1.optionId)
        changeRisk.setFormValue(changeRiskOptionToSelect1.optionId) 
        descriptionOfImpact.setFormValue("Possible intermittent access to Ultipro Web during the maintenance window.") 
        implementationSteps.setFormValue("Run MDUU on Site(MDUU) Servers \nRUN FRED on Client,Int,Auth,Web,DPM Servers")
        testPlan.setFormValue("Review one-off logs to confirm deployment was successful.")
        rollBackPlan.setFormValue("Work with Dev on-call to resolve any issues and finish deployment. No actual rollback planned")
        owner.setFormValue(user1.getName())
        //businessUnit.setFormValue(businessUnitOptionToSelect3.optionId)
        dataCenter.setFormValue(dataCenterToSelect1.optionId)
        environment.setFormValue(environmentOptionToSelect3.optionId)


           //Setting the style
        //summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
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
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
        environment.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #d6f5d6;}</style><head></html")
        dataCenter.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #d6f5d6;}</style><head></html>")


           //Setting the style to unselecting the fields 
        summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #white;}</style><head></html>")
        tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
        implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
        devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
        changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
        contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
        changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
        componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #white;}</style><head></html>")
        product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #white;}</style><head></html>")
        impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")
        requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")
      }



    if(value == "Dedicated Ultipro Quarterly Updates")
      {
        // ClearTemplate()
           //Fields being used   
        //summary.setFormValue("Dedicated Ultipro Quarterly Updates")
        change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
        fixVersionsField.setFormValue(fixversion*.id)
        isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
        changeType.setFormValue(changeTypeOptionToSelect8.optionId)
        changeRisk.setFormValue(changeRiskOptionToSelect1.optionId) 
        descriptionOfImpact.setFormValue("Ultipro services will be down during deployment.") 
        implementationSteps.setFormValue("https://confluence.ultimatesoftware.com/display/SD/Upgrade+Checklist")
        testPlan.setFormValue("Test BO, PAC, SMC, Web, Web reports, 3rd party.")
        rollBackPlan.setFormValue("Restore backup")
        owner.setFormValue(user1.getName())


           //Setting the style
        
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
        summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #white;}</style><head></html>")
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
        environment.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #white;}</style><head></html")
        dataCenter.setHelpText("<html><head><style>label[for=customfield_11136],#customfield_11136{background-color: #white;}</style><head></html>")
        tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
        implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")
        devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
        changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #white;}</style><head></html>")
        contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
        changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
        componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #white;}</style><head></html>")
        product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #white;}</style><head></html>")
        impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")
        requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")
      }


    if(value == "Recruiting TEST")
      { 
         //ClearTemplate()
           //Fields being used
        //summary.setFormValue("Recruiting - ATL/PHX/TOR Test - Cluster Management - App Only")
        change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
        isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
        changeDescription.setFormValue("Push Release in ATL/PHX/TOR Test New code in this release: https://ultidev/issues/?jql=component%20%3D%20Recruiting%20AND%20status%20%3D%20closed%20AND%20resolved%20%3E%3D2018-03-19%20and%20resolved%20%3C%3D2018-03-30")
        changeType.setFormValue(changeTypeOptionToSelect8.optionId)
        descriptionOfImpact.setFormValue("None.")
        implementationSteps.setFormValue("Automated process through TeamCity. Details steps are included in the comments below.")
        testPlan.setFormValue("Tests run as part of the upgrade in the pipeline, and the deployment engineer performs a manual SSO test once the upgrade is complete.")
        owner.setFormValue(user8.getName())
        tester.setFormValue(user11.getName())
        implementer.setFormValue(user11.getName())
        //businessUnit.setFormValue(businessUnitOptionToSelect2.optionId)
        environment.setFormValue(environmentOptionToSelect1.optionId)
        dataCenter.setFormValue(dataCenterToSelect*.optionId)
        devicesAffected.setFormValue("Rec14")
        rollBackPlan.setFormValue("Blue/Green process, rollback to orignial environment")
        changeNotify.setFormValue(user12.getName())
        fixVersionsField.setFormValue(fixversion*.id)


           //Setting the style
        
        change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
        isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
        implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
        testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
        owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
        descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
        devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #d6f5d6;}</style><head></html>")
        implementer.setHelpText("<html><head><style>label[for=customfield_11544],#customfield_11544 {background-color: #d6f5d6;}</style><head></html>")
        changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #d6f5d6;}</style><head></html>")
        changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554{background-color: #d6f5d6;}</style><head></html>")
        tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
        environment.setHelpText("<html><head><style>label[for=customfield_11362], #customfield_11362{background-color: #d6f5d6;}</style><head></html")
        dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #d6f5d6;}</style><head></html>")
        rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
        changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #d6f5d6;}</style><head></html>")
        fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")
        



           //Setting the style to unselecting the fields
        summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #white;}</style><head></html>")
        changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556{background-color: #white;}</style><head></html>")
        contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
        componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #white;}</style><head></html>")
        product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #white;}</style><head></html>")
        impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")
        requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")
      }



    if(value == "Recruiting PROD")
      {
         //ClearTemplate()
           //Fields being used
        //summary.setFormValue("Recruiting - ATL/PHX/TOR Prod - Cluster Management - App Only")
        change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
        isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
        changeDescription.setFormValue("Push Release in ATL/LAS/TOR Prod\n New code in this release: https://ultidev/issues/?jql=component%20%3D%20Recruiting%20AND%20status%20%3D%20closed%20AND%20resolved%20%3E%3DXXXX-XX-XX%20and%20resolved%20%3C%3DXXXX-XX-XX")
        changeType.setFormValue(changeTypeOptionToSelect14.optionId)
        descriptionOfImpact.setFormValue("None.")
        implementationSteps.setFormValue("Automated process through TeamCity. Details steps are included in the comments below.")
        testPlan.setFormValue("Tests run as part of the upgrade in the pipeline, and the deployment engineer performs a manual SSO test once the upgrade is complete.")
        owner.setFormValue(user8.getName())
        tester.setFormValue(user11.getName())
        implementer.setFormValue(user11.getName())
        //businessUnit.setFormValue(businessUnitOptionToSelect2.optionId)
        environment.setFormValue(environmentOptionToSelect2.optionId)
        dataCenter.setFormValue(dataCenterToSelect2*.optionId)
        devicesAffected.setFormValue("Rec14")
        rollBackPlan.setFormValue("Blue/Green process, rollback to orignial environment")
        changeNotify.setFormValue(user12.getName())
        fixVersionsField.setFormValue(fixversion*.id)
        impactfulSalesPreSalesDemo.setFormValue(impactfulSalesOptionsToSelect2.optionId)
        componentField.setFormValue(projectComponent2.id)
        product.setFormValue(productToSelect3.optionId)
        requestor.setFormValue(user16.getName())
        changeRisk.setFormValue(changeRiskOptionToSelect2.optionId)


        


         //Setting the style
        //summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
        change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
        isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
        implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
        testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
        owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
        descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
        devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #d6f5d6;}</style><head></html>")
        implementer.setHelpText("<html><head><style>label[for=customfield_11544],#customfield_11544 {background-color: #d6f5d6;}</style><head></html>")
        changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #d6f5d6;}</style><head></html>")
        changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554{background-color: #d6f5d6;}</style><head></html>")
        tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
        environment.setHelpText("<html><head><style>label[for=customfield_11362], #customfield_11362{background-color: #d6f5d6;}</style><head></html")
        dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #d6f5d6;}</style><head></html>")
        rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
        changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #d6f5d6;}</style><head></html>")
        fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")	
        componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #d6f5d6;}</style><head></html>")
        product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #d6f5d6;}</style><head></html>")
        impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #d6f5d6;}</style><head></html>")
        requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #d6f5d6;}</style><head></html>")
        changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556{background-color: #d6f5d6;}</style><head></html>")



         //Setting the style to unselecting the fields
        summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #white;}</style><head></html>")
        contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
        //componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #white;}</style><head></html>")
        //product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #white;}</style><head></html>")
        //impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")
        //requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")
      }



    if(value == "Deploy - WFM PROD")
      {
        //ClearTemplate()
           //Fields being used
        summary.setFormValue("WFM PROD: x.y.z Release | Patch")
        change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
        isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
        changeDescription.setFormValue("Modules to Deploy - TA - Backend tag: x.y.z - Frontend tag: x.y.z - TOA - Backend tag: x.y.z - Frontend tag: x.y.z - Scheduling - Backend tag: x.y.z - Frontend tag: x.y.z - Reporting - Backend tag: x.y.z - Frontend tag: x.y.z Environments to Update - ATL Prod - PHX Prod - TOR Prod  Deployment Steps 1. No special steps")
        changeType.setFormValue(changeTypeOptionToSelect8.optionId)
        descriptionOfImpact.setFormValue("Module will be unavailable during upgrade process.")
        implementationSteps.setFormValue("Take backups with the following pipelines: \n\nATL: https://ci.mia.ucloud.int/viewType.html?buildTypeId=NWFM_Beta_Atl_Tasks_MassMySQLFullBackups \nPLAS1: https://ci.mia.ucloud.int/viewType.html?buildTypeId=NWFM_Beta_Plas1_Tasks_MassMySQLFullBackups \nTOR: https://ci.mia.ucloud.int/viewType.html?buildTypeId=NWFM_Beta_Tor_Tasks_MassMySQLFullBackups \nUse one-click pipelines: https://ci.mia.ucloud.int/project.html?projectId=NWFM_Beta_OneClickDeployments&tab=projectOverview \n\nCheck AlertSite Synthetic SSO tests for any failures, post deployment.")
        testPlan.setFormValue("Test via synthetics")
        owner.setFormValue(user8.getName())
        //businessUnit.setFormValue(businessUnitOptionToSelect1.optionId)
        dataCenter.setFormValue(dataCenterToSelect2.optionId)
        devicesAffected.setFormValue("WFM E0, WFM N0, WFM T0, app resides in PCF.")
        rollBackPlan.setFormValue("Contact WFM On-Call. \nIf there were database changes, use backups taken prior to updates to roll back the database. \n\nFor DB schema changes: restoring from backup = ~15-20 minutes \nFor Gateway changes: < 5 minutes \nFor TOA: DB restore  = ~20-25 minutes")
        fixVersionsField.setFormValue(fixversion*.id)
        environment.setFormValue(environmentOptionToSelect8.optionId)
        product.setFormValue(productToSelect2.optionId)
        componentField.setFormValue(projectComponent4.id)
        implementer.setFormValue(user14.getName())
        tester.setFormValue(user14.getName())
        requestor.setFormValue(user14.getName())



        //Setting the style	
        changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #d6f5d6;}</style><head></html>")
        changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554{background-color: #d6f5d6;}</style><head></html>")
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
        dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #d6f5d6;}</style><head></html>")
        change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
        isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
        implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
        testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
        owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
        descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
        devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #d6f5d6;}</style><head></html>")
        fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")    
        rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
        environment.setHelpText("<html><head><style>label[for=customfield_11362], #customfield_11362{background-color: #d6f5d6;}</style><head></html")
        product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #d6f5d6;}</style><head></html>")
        componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #d6f5d6;}</style><head></html>")
        implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #d6f5d6;}</style><head></html>")
        tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545 {background-color: #d6f5d6;}</style><head></html>")
        requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502 {background-color: #d6f5d6;}</style><head></html>")
        summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")



        //Setting the style to unselecting the fields
        changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556{background-color: #white;}</style><head></html>")
        contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
        changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
        impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")
        
      }

    if(value == "Deploy - WFM TEST")
      {
        //ClearTemplate()
           //Fields being used
        summary.setFormValue("WFM TEST: x.y.z Release | Patch")
        change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
        isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
        changeDescription.setFormValue("Modules to Deploy - TA - Backend tag: x.y.z - Frontend tag: x.y.z - TOA - Backend tag: x.y.z - Frontend tag: x.y.z - Scheduling - Backend tag: x.y.z - Frontend tag: x.y.z - Reporting - Backend tag: x.y.z - Frontend tag: x.y.z Environments to Update - ATL Prod - PHX Prod - TOR Prod  Deployment Steps 1. No special steps")
        changeType.setFormValue(changeTypeOptionToSelect8.optionId)
        descriptionOfImpact.setFormValue("Module will be unavailable during upgrade process.")
        implementationSteps.setFormValue("Take backups with the following pipelines: \n\nATL: https://ci.mia.ucloud.int/viewType.html?buildTypeId=NWFM_Beta_Atl_Tasks_MassMySQLFullBackups \nPLAS1: https://ci.mia.ucloud.int/viewType.html?buildTypeId=NWFM_Beta_Plas1_Tasks_MassMySQLFullBackups \nTOR: https://ci.mia.ucloud.int/viewType.html?buildTypeId=NWFM_Beta_Tor_Tasks_MassMySQLFullBackups \nUse one-click pipelines: https://ci.mia.ucloud.int/project.html?projectId=NWFM_Beta_OneClickDeployments&tab=projectOverview \n\nCheck AlertSite Synthetic SSO tests for any failures, post deployment.")
        testPlan.setFormValue("Test via synthetics")
        owner.setFormValue(user8.getName())
        //businessUnit.setFormValue(businessUnitOptionToSelect1.optionId)
        dataCenter.setFormValue(dataCenterToSelect2.optionId)
        devicesAffected.setFormValue("WFM E0, WFM N0, WFM T0, app resides in PCF.")
        rollBackPlan.setFormValue("Contact WFM On-Call. \nIf there were database changes, use backups taken prior to updates to roll back the database. \n\nFor DB schema changes: restoring from backup = ~15-20 minutes \nFor Gateway changes: < 5 minutes \nFor TOA: DB restore  = ~20-25 minutes")
        fixVersionsField.setFormValue(fixversion*.id)
        environment.setFormValue(environmentOptionToSelect9.optionId)
        product.setFormValue(productToSelect2.optionId)
        componentField.setFormValue(projectComponent4.id)
        implementer.setFormValue(user14.getName())
        tester.setFormValue(user14.getName())
        requestor.setFormValue(user14.getName())



        //Setting the style 
        changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #d6f5d6;}</style><head></html>")
        changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554{background-color: #d6f5d6;}</style><head></html>")
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
        dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #d6f5d6;}</style><head></html>")
        change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
        isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
        implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
        testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
        owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
        descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
        devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #d6f5d6;}</style><head></html>")
        fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")    
        rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
        environment.setHelpText("<html><head><style>label[for=customfield_11362], #customfield_11362{background-color: #d6f5d6;}</style><head></html")
        product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #d6f5d6;}</style><head></html>")
        componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #d6f5d6;}</style><head></html>")
        implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #d6f5d6;}</style><head></html>")
        tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")
        requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #d6f5d6;}</style><head></html>")
        summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")



        //Setting the style to unselecting the fields
        changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556{background-color: #white;}</style><head></html>")
        contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
        changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
        impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")
        
      }


  if(value == "Deploy - Mobile PROD")
      {
      //ClearTemplate()
      summary.setFormValue("Mobile Service - xxx Release (ATL/PLAS/TOR PROD)")
      componentField.setFormValue(projectComponent.id)
      change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
      isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
      impactfulSalesPreSalesDemo.setFormValue(impactfulSalesOptionsToSelect2.optionId)
      changeDescription.setFormValue("Mobile enhancement release\n1. Gateway **except SALES**\n2. Translation **except SALES**\n3. MPS **except SALES**")
      product.setFormValue(productToSelect1.optionId)
      changeType.setFormValue(changeTypeOptionToSelect8.optionId)
      changeRisk.setFormValue(changeRiskOptionToSelect2.optionId) 
      descriptionOfImpact.setFormValue("The PCF services has 0 downtime due to Blue/Green deployment. Also can be reverted if issue noticed")
      implementationSteps.setFormValue("Please follow the instructions from this confluence page\n\n1. GW: https://confluence.ultimatesoftware.com/pages/viewpage.action?spaceKey=SD&title=Deploying+the+Mobile+Gateway \n2. MPS: https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=107677899 \n3. Translations:\nhttps://confluence.ultimatesoftware.com/display/SD/Deploying+the+Mobile+Translation+Service")
      testPlan.setFormValue("[Mobile App / Service Test] Test Step with UltiPro instance credentials for all DC:\nhttps://docs.google.com/document/d/1NE2gwEkPmFntzFHn4VtN0lKy9nqHbxfvXCB1kfVbuws/edit?usp=sharing")   
      rollBackPlan.setFormValue("Revert Blue / Green swap.")
      //businessUnit.setFormValue(businessUnitOptionToSelect1.optionId)
      dataCenter.setFormValue(dataCenterToSelect2*.optionId)
      environment.setFormValue(environmentOptionToSelect4.optionId)
      implementer.setFormValue(user14.getName())
      owner.setFormValue(user8.getName())
      tester.setFormValue(user14.getName())
      requestor.setFormValue(user15.getName())

      //Setting the style
      summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
      componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #d6f5d6;}</style><head></html>")
      change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
      isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
      changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
      changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
      descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
      implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
      testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
      rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
      owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
      //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
      dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #d6f5d6;}</style><head></html>")
      environment.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #d6f5d6;}</style><head></html")
      tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")
      requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #d6f5d6;}</style><head></html>")
      changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #d6f5d6;}</style><head></html>")
      implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #d6f5d6;}</style><head></html>")
      product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #d6f5d6;}</style><head></html>")
      impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #d6f5d6;}</style><head></html>")

      //Setting the style to unselecting the fields
      //implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11554 {background-color: #white;}</style><head></html>")	
      devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #white;}</style><head></html>")
      contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
      changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
      fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions {background-color: #d6f5d6;}</style><head></html>")

      }

   if(value == "Deploy - UTC ATL/MM")
      {
         //ClearTemplate()
         summary.setFormValue("Upgrade ATL/MM TouchBase Host Servers to x.y.z")
         changeDescription.setFormValue("Upgrade ATL/MM Touchbase clients to x.y.z")
         change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
         product.setFormValue(productToSelect2.optionId)
         componentField.setFormValue(projectComponent1.id)
         changeRisk.setFormValue(changeRiskOptionToSelect1.optionId) 
         isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
         changeType.setFormValue(changeTypeOptionToSelect8.optionId)
         descriptionOfImpact.setFormValue("Possible intermittent access to UTC during the maintenance window")
         devicesAffected.setFormValue("https://ultiworld.ultimatesoftware.com/SharedServices/tierIII/Secured%20Documents/Touchbase%20Tracking/Employtouch%20Customers.xlsx ")
         implementationSteps.setFormValue("To be provided as a Child page to this site:\n https://confluence.ultimatesoftware.com/pages/viewpage.action?spaceKey=SD&title=UTC")
         testPlan.setFormValue("Validate login and validate application is up")
         rollBackPlan.setFormValue("Reinstall Application and Restore DB. Failing that, Restore War file and restore DB")
         //businessUnit.setFormValue(businessUnitOptionToSelect7.optionId)
         owner.setFormValue(user8.getName())
         dataCenter.setFormValue(dataCenterToSelect3*.optionId)
         environment.setFormValue(environmentOptionToSelect5.optionId)

         //Setting the style
         summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
         componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #d6f5d6;}</style><head></html>")
         change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
         isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
         changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
         changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
         descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
         implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
         testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
         rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
         owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>") 
         dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #d6f5d6;}</style><head></html>")
         environment.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #d6f5d6;}</style><head></html")
         product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #d6f5d6;}</style><head></html>")
         devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #d6f5d6;}</style><head></html>")
         changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #d6f5d6;}</style><head></html>")
         //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")

         //Setting the style to unselecting the fields
         implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11554 {background-color: #white;}</style><head></html>")	
         contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
         changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
         fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions {background-color: #d6f5d6;}</style><head></html>")
         tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
         requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")
         impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")

   }  

   if(value == "Deploy - UTC LAS/TOR"){

         //ClearTemplate()
         summary.setFormValue("Upgrade LAS/TOR TouchBase Host Servers to x.y.z")
         changeDescription.setFormValue("Upgrade LAS/TOR Touchbase clients to x.y.z")
         change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
         product.setFormValue(productToSelect2.optionId)
         componentField.setFormValue(projectComponent1.id)
         changeRisk.setFormValue(changeRiskOptionToSelect1.optionId) 
         isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
         changeType.setFormValue(changeTypeOptionToSelect8.optionId)
         descriptionOfImpact.setFormValue("Possible intermittent access to UTC during the maintenance window")
         devicesAffected.setFormValue("https://ultiworld.ultimatesoftware.com/SharedServices/tierIII/Secured%20Documents/Touchbase%20Tracking/Employtouch%20Customers.xlsx ")
         implementationSteps.setFormValue("To be provided as a Child page to this site:\n https://confluence.ultimatesoftware.com/pages/viewpage.action?spaceKey=SD&title=UTC")
         testPlan.setFormValue("Validate login and validate application is up")
         rollBackPlan.setFormValue("Reinstall Application and Restore DB. Failing that, Restore War file and restore DB")
         //businessUnit.setFormValue(businessUnitOptionToSelect7.optionId)
         owner.setFormValue(user8.getName())
         dataCenter.setFormValue(dataCenterToSelect4*.optionId)
         environment.setFormValue(environmentOptionToSelect6.optionId)

         //Setting the style
         summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
         componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #d6f5d6;}</style><head></html>")
         change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
         isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")        
         changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
         changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>") 
         descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
         implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
         testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
         rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
         owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>") 
         dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #d6f5d6;}</style><head></html>")
         environment.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #d6f5d6;}</style><head></html")
         product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #d6f5d6;}</style><head></html>")
         devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #d6f5d6;}</style><head></html>")
         changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #d6f5d6;}</style><head></html>")
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")

         //Setting the style to unselecting the fields
         implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11554 {background-color: #white;}</style><head></html>")	
         contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
         changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
         fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions {background-color: #d6f5d6;}</style><head></html>")
         tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
         requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")
         impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")

         
         

   }
   if(value == "Deploy - GWO PROD"){

          //ClearTemplate()
          summary.setFormValue("GWO - Deployment")
          change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
          isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
          impactfulSalesPreSalesDemo.setFormValue(impactfulSalesOptionsToSelect2.optionId)
          changeDescription.setFormValue("Deploy changes to the GWO application")
          componentField.setFormValue(projectComponent3.id)
          product.setFormValue(productToSelect4.optionId)
          owner.setFormValue(user8.getName())
          changeType.setFormValue(changeTypeOptionToSelect8.optionId)
          descriptionOfImpact.setFormValue("App will temporarily be unavailable while deployment is in progress; employees will be unable to access GWO. Not customer-facing")
          implementationSteps.setFormValue("https://confluence.ultimatesoftware.com/display/SD/GWO+-+Deployment")
          devicesAffected.setFormValue("GWO in Atlanta")
          testPlan.setFormValue("https://confluence.ultimatesoftware.com/display/SD/GWO+-+Deployment")
          rollBackPlan.setFormValue("Run Rollback pipeline")
          //businessUnit.setFormValue(businessUnitOptionToSelect6.optionId)
          dataCenter.setFormValue(dataCenterToSelect3*.optionId)
          environment.setFormValue(environmentOptionToSelect7.optionId)
          fixVersionsField.setFormValue(fixversion*.id)

          //Setting the style
          summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
          componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #d6f5d6;}</style><head></html>")
          change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
          changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
          descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
          implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
          rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
          owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
          //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
          dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #d6f5d6;}</style><head></html>")
          environment.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #d6f5d6;}</style><head></html")
          changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #d6f5d6;}</style><head></html>")
          product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #d6f5d6;}</style><head></html>")
          impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #d6f5d6;}</style><head></html>")
          devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #d6f5d6;}</style><head></html>")
          testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
          isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
          fixVersionsField.setHelpText("<html><head><style>label[for=fixVersions], #fixVersions-textarea {background-color: #d6f5d6;}</style><head></html>")

          //Setting the style to unselecting the fields
          contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
          changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
          changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #white;}</style><head></html>")
          tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #white;}</style><head></html>")
          requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")
          implementer.setHelpText("<html><head><style>label[for=customfield_11544], #customfield_11544 {background-color: #white;}</style><head></html>")

    }
   if(value == "Ultipro Learning Mobile Release"){
           summary.setFormValue("Ultipro Learning Mobile Release to App Stores (Google Play & Apple)")
           change_priority.setFormValue(changePriorityOptionToSelect1.optionId)
           isThisEmergency.setFormValue(isThisEmergencyOptionToSelect.optionId)
           changeDescription.setFormValue("Push Ultipro Learning Mobile app to Google Play and Apple Store")
           product.setFormValue(productToSelect6.optionId)
           implementer.setFormValue(user17.getName())
           owner.setFormValue(user18.getName())
           tester.setFormValue(user17.getName())   
           requestor.setFormValue(user19.getName())
           changeType.setFormValue(changeTypeOptionToSelect14.optionId)
           changeRisk.setFormValue(changeRiskOptionToSelect2.optionId)
           descriptionOfImpact.setFormValue("Users choose when to update their device")
           devicesAffected.setFormValue("App Stores")
           implementationSteps.setFormValue("Follow Confluence instructions for the Ultipro Learning app instead of Ultipro: https://confluence.ultimatesoftware.com/display/SD/Mobile+-+Releasing+Mobile+App+to+iTunes+and+Play+Stores")
           testPlan.setFormValue("Successful login to Ultipro Learning Mobile App (credentials to be provided to the implementer)")
           rollBackPlan.setFormValue("Revert to the previous version")
           //businessUnit.setFormValue(businessUnitOptionToSelect6.optionId)
           dataCenter.setFormValue(dataCenterToSelect2.optionId)
           environment.setFormValue(environmentOptionToSelect10.optionId)
            
           summary.setHelpText("<html><head><style>label[for=summary], #summary {background-color: #d6f5d6;}</style><head></html>")
           change_priority.setHelpText("<html><head><style>label[for=customfield_11543], #customfield_11543 {background-color: #d6f5d6;}</style><head></html>")
           isThisEmergency.setHelpText("<html><head><style>label[for=customfield_14121],#customfield_14121 {background-color: #d6f5d6;}</style><head></html>")
           changeDescription.setHelpText("<html><head><style>label[for=customfield_11541],#customfield_11541 {background-color: #d6f5d6;}</style><head></html>")
           product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #d6f5d6;}</style><head></html>")
           implementer.setHelpText("<html><head><style>label[for=customfield_11544],#customfield_11544 {background-color: #d6f5d6;}</style><head></html>")
           owner.setHelpText("<html><head><style>label[for=customfield_10756], #customfield_10756{background-color: #d6f5d6;}</style><head></html>")
           tester.setHelpText("<html><head><style>label[for=customfield_11545], #customfield_11545{background-color: #d6f5d6;}</style><head></html>")
           requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #d6f5d6;}</style><head></html>")
           changeType.setHelpText("<html><head><style>label[for=customfield_11554], #customfield_11554 {background-color: #d6f5d6;}</style><head></html>")
           changeRisk.setHelpText("<html><head><style>label[for=customfield_11556], #customfield_11556 {background-color: #d6f5d6;}</style><head></html>")
           descriptionOfImpact.setHelpText("<html><head><style>label[for=customfield_18320],#customfield_18320 {background-color: #d6f5d6;}</style><head></html>")
           devicesAffected.setHelpText("<html><head><style>label[for=customfield_11555],#customfield_11555 {background-color: #d6f5d6;}</style><head></html>")
           implementationSteps.setHelpText("<html><head><style>label[for=customfield_11561],#customfield_11561{background-color: #d6f5d6;}</style><head></html>")
           testPlan.setHelpText("<html><head><style>label[for=customfield_11562], #customfield_11562{background-color: #d6f5d6;}</style><head></html>")
           rollBackPlan.setHelpText("<html><head><style>label[for=customfield_11563], #customfield_11563{background-color: #d6f5d6;}</style><head></html>")
           //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #d6f5d6;}</style><head></html>")
           dataCenter.setHelpText("<html><head><style>label[for=customfield_11136], #customfield_11136{background-color: #d6f5d6;}</style><head></html>")
           environment.setHelpText("<html><head><style>label[for=customfield_11362],#customfield_11362{background-color: #d6f5d6;}</style><head></html")
           
           contingencyPlan.setHelpText("<html><head><style>label[for=customfield_11564], #customfield_11564{background-color: #white;}</style><head></html>")
           changeNotify.setHelpText("<html><head><style>label[for=customfield_11565], #customfield_11565{background-color: #white;}</style><head></html>")
           componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #white;}</style><head></html>")
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
        //businessUnit.setFormValue("")
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
        impactfulSalesPreSalesDemo.setFormValue("")
        componentField.setFormValue("")
        product.setFormValue("")
        requestor.setFormValue("")

           //Setting the style to unselecting the fields 
        //businessUnit.setHelpText("<html><head><style>label[for=customfield_11361], #customfield_11361{background-color: #white;}</style><head></html>")
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
        componentField.setHelpText("<html><head><style>label[for=components], #components-textarea {background-color: #white;}</style><head></html>")
        product.setHelpText("<html><head><style>label[for=customfield_11752], #customfield_11752 {background-color: #white;}</style><head></html>")
        impactfulSalesPreSalesDemo.setHelpText("<html><head><style>label[for=customfield_19421], #customfield_19421 {background-color: #white;}</style><head></html>")
        requestor.setHelpText("<html><head><style>label[for=customfield_10502], #customfield_10502{background-color: #white;}</style><head></html>")


}

