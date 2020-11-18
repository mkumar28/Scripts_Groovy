import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.*
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript
@Grab('com.xlson.groovycsv:groovycsv:1.1')
import static com.xlson.groovycsv.CsvParser.parseCsv
import groovyx.net.http.*
import groovyx.net.http.ContentType.*
import groovyx.net.http.Method.*
import com.xlson.groovycsv.PropertyMapper
import java.util.*
import au.com.bytecode.opencsv.CSVReader;
    

@BaseScript FieldBehaviours fieldBehaviours


def ChangeTypeCF = getFieldByName("Change Type")
def issuetype = getIssueContext().getIssueType().name

// Get pointers to the required custom field and option managers
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def optionsManager = ComponentAccessor.getOptionsManager()
def customField = customFieldManager.getCustomFieldObject(ChangeTypeCF.getFieldId())
def config = customField.getRelevantConfig(getIssueContext())
def options = optionsManager.getOptions(config)

Map mapping = [:]

List<String> excelBody = new ArrayList<String>()
List<String> changeType = new ArrayList<String>()
List<String> typeOfIssue = new ArrayList<String>()
List<String> produtRelease = new ArrayList<String>()
List<String> changeBoardSubmittal = new ArrayList<String>()
Map fieldOptions = [:]

/**for(line in parseCsv(new FileReader('/jira/sharedhome/SD_Script_CSV_Files/changetype.csv'))) {//1. Update the path for the CSV File
excelBody.add(line)}**/
Reader reader = new FileReader("/jira/sharedhome/SD_Script_CSV_Files/changetype.csv");
CSVReader csvreader = new CSVReader(reader);
String [] nextLine;
while ((nextLine = csvreader.readNext()) != null) {
          changeType.add((nextLine[0]));
          typeOfIssue.add(nextLine[1]);
}


for(int i=0; i<changeType.size();i++){

        if (typeOfIssue[i]== 'Product Release')
         {
             produtRelease.add(changeType[i].toString())
         }
         else
         {
             changeBoardSubmittal.add(changeType[i].toString())
         }
}

fieldOptions.putAll("-1":"None")
if (issuetype == "Product Release") {
   
  
    fieldOptions += options.findAll{it.value in produtRelease}.collectEntries {[(it.optionId): it.value]}
    ChangeTypeCF.setFieldOptions(fieldOptions)


}
else{
  
   fieldOptions += options.findAll{it.value in changeBoardSubmittal}.collectEntries {[(it.optionId): it.value]}
   ChangeTypeCF.setFieldOptions(fieldOptions)

}


