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


for(line in parseCsv(new FileReader('/home/appadmin/changetype.csv'))) {//1. Update the path for the CSV File
excelBody.add(line)
}

for(PropertyMapper excelRow : excelBody){
   //Use this list to add the FilterID
   changeType.add(excelRow.Change_Type);
   typeOfIssue.add(excelRow.Issue_Type);
  
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

if (issuetype == "Product Release") {

  ChangeTypeCF.setFieldOptions(options.findAll {it.value in produtRelease})


}
else{
   
   ChangeTypeCF.setFieldOptions(options.findAll {it.value in changeBoardSubmittal})

}


