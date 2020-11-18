/*Update the Biz/Tas number on line number 19. Make sure the ticket description content does not have anything other then the values, which need to be added to the field. For reference look at the BIZ-38120
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
MutableIssue issue = issueManager.getIssueByCurrentKey("BIZ-38120");

//getting the value from the description field
def description = issue.getDescription()
String[] values = description.split("\\r\\n|\\n|\\r")

//getting CustomField
Collection<CustomField>  customFieldCollections = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectsByName("UltiPro Customer AR Numbers");
CustomField customField = (CustomField)customFieldCollections.toArray()[0];

	Options allOptions = ComponentAccessor.getOptionsManager()
					.getOptions(customField.getConfigurationSchemes().listIterator().next().getOneAndOnlyConfig());
	Options opt

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
							System.out.println("Value already exist" + count);
						}
					}

					System.out.println("Value already exist12" + count);
					if (count == 1) {

						System.out.println("Value has been added");
						int nextSequence = l.isEmpty() ? 1 : l.getRootOptions().size() + 1;

						newOption = optionsManager.createOption(config, null, (long) nextSequence, values.getAt(j));
                        if(j == values.size()-1 ) {
                        	opt = optionsManager.getOptions(config).sortOptionsByValue(null);
                        }
						
						
					}
				}
			}
		}
}


	
