/*Add the Biz/Tas number on line number 19. Make sure the ticket description content does not have anything other then the values, which need to be added to the field. For reference look at the BIZ-24087
*/

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.jira.issue.customfields.manager.OptionsManager;
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.customfields.option.Options;


//getting the issue
def issueManager = ComponentAccessor.getIssueManager();
MutableIssue issue = issueManager.getIssueByCurrentKey("BIZ-24087");//Update the Issue key


//getting the value from the description field
def description = issue.getDescription()
String[] values = description.split("\\r\\n|\\n|\\r")
value1 = values.getAt(0);
//getting CustomField
Collection<CustomField>  customFieldCollections = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectsByName("UltiPro Customers Affected");
CustomField customField = (CustomField)customFieldCollections.toArray()[0];

	Options allOptions = ComponentAccessor.getOptionsManager()
					.getOptions(customField.getConfigurationSchemes().listIterator().next().getOneAndOnlyConfig());
                    
for (int j = 0; j<values.size(); j++)
{
Option newOption = null;
		if (customField != null) {
			List<FieldConfigScheme> schemes = customField.getConfigurationSchemes();
			if (schemes != null && !schemes.isEmpty()) {
				FieldConfigScheme sc = schemes.get(0);
				Map<?, ?> configs = sc.getConfigsByConfig();
				if (configs != null && !configs.isEmpty()) {
					FieldConfig config = (FieldConfig) configs.keySet().iterator().next();
					OptionsManager optionsManager = ComponentAccessor.getOptionsManager();
					Options l = optionsManager.getOptions(config);
					int count = 1;
					for (int i = 0; i < l.size(); i++) {

						if (l.get(i).toString().equalsIgnoreCase(values.getAt(j))) {
                         			 count++;	
						}
					}
					

					
					if (count == 1) {
						int nextSequence = l.isEmpty() ? 1 : l.getRootOptions().size() + 1;
						newOption = optionsManager.createOption(config, null, (long) nextSequence, values.getAt(j));
						optionsManager.getOptions(config).sortOptionsByValue(null);
					}
				}
			}
		}
}	
