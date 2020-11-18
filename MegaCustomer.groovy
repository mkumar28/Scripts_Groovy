import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.component.ComponentAccessor

def issueManager = ComponentAccessor.getIssueManager()
Issue myIssue = issueManager.getIssueByCurrentKey("EMT-57573")

customFieldManager = ComponentAccessor.getCustomFieldManager()//for JIRA v7

CompanyName = customFieldManager.getCustomFieldObjectByName("Company Name")

PleaseNote = customFieldManager.getCustomFieldObjectByName("Please Note")

company = issue.getCustomFieldValue(CompanyName).value


if ((company.toString().matches(/CheckPoint HR, LLC (f\/k\/a The Wilshire Group, LLC)/)) || 
(company.toString().matches(/Community Health Systems Professional Services Corporation/)) ||
(company.toString().matches(/Genesco Inc./)) || 
(company.toString().matches(/Tesla, Inc./)) || 
(company.toString().matches(/Google LLC/)) || 
(company.toString().matches(/Infosync Services, LLC/)) || 
(company.toString().matches(/Alphabet, Inc. (Google)/)) || 
(company.toString().matches(/OSI Restaurant Partners, LLC/)) || 
(company.toString().matches(/Aramark Services, Inc./)) || 
(company.toString().matches(/Laureate Education, Inc./)) || 
(company.toString().matches(/Texas Roadhouse Holdings LLC/)) || 
(company.toString().matches(/The Cheesecake Factory Incorporated/)) || 
((company.toString().matches(/Aetna Life Insurance Company/)))
//(company.toString().matches(/The Pep Boys - Manny, Moe & Jack/)) || 
//(company.toString().matches(/Winners Merchants International L.P./)) || 
//(company.toString().matches(/Aetna Life Insurance Company/)) || 
//(company.toString().matches(/LVMH Moet Hennessy Louis Vuitton, Inc./)) || 
//(company.toString().matches(/Pacific Dental Services, LLC/)) || 
//(company.toString().matches(/Fiesta Restaurant Group, Inc./)) || 
//(company.toString().matches(/Space Exploration Technologies Corp/))
) {

myIssue.setCustomFieldValue(PleaseNote , "{color:blue}*THIS IS A MEGA CUSTOMER*{color}" )

}
else {
}