/**
Using this script to update the compoent field based on the selection of Prodcat/Subcat field 
**/

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.*
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript
import au.com.bytecode.opencsv.CSVReader;

    

@BaseScript FieldBehaviours fieldBehaviours

def componentManager = ComponentAccessor.getProjectComponentManager()

def component = getFieldById("components")
def prodCat = getFieldById("customfield_11043")
def mapping = [:]
def ultiProjectId = 10151

List<String> excelBody = new ArrayList<String>()
List<String> category = new ArrayList<String>()
List<String> sub_cat = new ArrayList<String>()
List<String> components = new ArrayList<String>()


/*Reading from csv File**/
Reader reader = new FileReader("/jira/sharedhome/SF_Development_Mapping/SFDevelopmentMapping.csv");
CSVReader csvreader = new CSVReader(reader);
String [] nextLine;
while ((nextLine = csvreader.readNext()) != null) {
          category.add((nextLine[0]));
          sub_cat.add(nextLine[1]);
          components.add(nextLine[2]);
}
/*Creating a dictionary for mapping productCat/SubCat field with Component from csv file **/
for(int i=0;i<category.size();i++){
    mapping[category[i]+sub_cat[i]] = components[i]
}

/**Reading the Prodcat/Subcat field value **/
List value = (List)prodCat.getValue()
if(value.size()>1){/**Proceed only if child value is selected for ProdCat/SubCat field**/

    def devcomp= mapping[value[0].toString()+value[1].toString()] /**Getting the component form the dic based on the value read from prodCat/Subcat Field**/
    def projectComponent = componentManager.findByComponentName(ultiProjectId,devcomp.toString()) /**Getting the component**/ 
    prodCat.setHelpText("Component will be set to "+devcomp.toString())/**Publish the helptext**/
    component.setFormValue(projectComponent.id)/**Updating the Component**/
        
}

