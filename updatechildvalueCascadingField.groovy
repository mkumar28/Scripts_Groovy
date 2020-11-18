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
import java.util.logging.Logger
@Grab('com.xlson.groovycsv:groovycsv:1.1')
import static com.xlson.groovycsv.CsvParser.parseCsv
import groovyx.net.http.*
import groovyx.net.http.ContentType.*
import groovyx.net.http.Method.*
import com.xlson.groovycsv.PropertyMapper
import java.util.*


List<String> excelBody = new ArrayList<String>()
List<Long> values = new ArrayList<Long>();
//List<Long> filterOwner = new ArrayList<Long>();
List<Long> childValues = new ArrayList<Long>();

for(line in parseCsv(new FileReader('/JIRA/Data/FilterOwner1.csv'))) {//1. Update the path for the CSV File
excelBody.add( line)
}



for(PropertyMapper excelRow : excelBody)
		{
//Use this list to add the FilterID

			values.add(excelRow.FilterID);
			childValues.add(excelRow.FilterOwner);
			
}


//getting CustomField
Collection<CustomField>  customFieldCollections = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectsByName("TRADE Customs Product/Category/Sub-Category");
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
                    for(int  i=0; i<l.size(); i++)
                    {
					   l_value = l.get(i).getValue()
					 
                       if(l_value.toString().equalsIgnoreCase(values.getAt(j)))
					   {  						
						 int nextSequence = l.isEmpty() ? 1 : l.getRootOptions().size() + 1;

						 newOption = optionsManager.createOption(config, l.get(i).getOptionId(), (long) nextSequence, childValues.getAt(j));
						 optionsManager.getOptions(config).sortOptionsByValue(null);
					   }
					}
				}
			}
		}
}	

