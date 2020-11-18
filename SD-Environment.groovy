import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript


@BaseScript FieldBehaviours fieldBehaviours
def environment =  getFieldByName("Environment")
def otherEnvironment = getFieldByName("Other Environment")

otherEnvironment.setHidden(true)
def envValue = (List) environment.value

if(envValue != null)
{
 def eValue = envValue.get(0)

 if(eValue == "Other"){
       otherEnvironment.setHidden(false)
       otherEnvironment.setRequired(true)
       }
     else{
       otherEnvironment.setHidden(true)
       otherEnvironment.setRequired(false)
       }
}

