import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.component.ComponentAccessor


Issue myIssue = issue
//customFieldManager = ComponentManager.getInstance().getCustomFieldManager()//for JIRA v6
customFieldManager = ComponentAccessor.getCustomFieldManager()//for JIRA v7 

def cfLOE = customFieldManager.getCustomFieldObjectByName("Remediation")

//High Disk Space for e0medb* n0medb* t0medb*
if ((issue.getSummary().contains("High Disk Space Utilization")) && (issue.getSummary().matches(/.*[entENT][0-9][mM][eE][dD][bB].*/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=85431651")
} 
//High Disk Space for SQL servers
if ((issue.getSummary().contains("High Disk Space Utilization")) && (issue.getSummary().matches(/.*[a-zA-Z][0-9][a-zA-Z][a-zA-Z][a-zA-Z][0-9][dD][bB][0-9][0-9].*/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/SQL+Server+High+Disk+Space")
}
//High Disk Space Utilization ******DB** 
if ((issue.getSummary().contains("High Disk Space Utilization")) && (issue.getSummary().matches(/.*[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][dD][bB][a-zA-Z0-9][a-zA-Z0-9].*/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/SQL+Server+High+Disk+Space")
} 
//High Disk Space Utilization ******ST** 
if ((issue.getSummary().contains("High Disk Space Utilization")) && (issue.getSummary().matches(/.*[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][sS][tT][a-zA-Z0-9][a-zA-Z0-9].*/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/SQL+Server+High+Disk+Space")
} 
//High Disk Space Utilization ******VP** 
if ((issue.getSummary().contains("High Disk Space Utilization")) && (issue.getSummary().matches(/.*[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][vV][pP][a-zA-Z0-9][a-zA-Z0-9].*/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/SQL+Server+High+Disk+Space")
} 
// High Disk Space Utilization ***MT***** 
if ((issue.getSummary().contains("High Disk Space Utilization")) && (issue.getSummary().matches(/.*[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][mM][tT][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9].*/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Master+Tax+High+Disk+Space")
} 
//High Disk Space for Citrix servers
else if ((issue.getSummary().contains("High Disk Space Utilization")) && (issue.getSummary().matches(/.*[a-zA-Z][0-9][a-zA-Z][a-zA-Z][a-zA-Z][0-9][tT][sS][0-9][0-9].*/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Citrix+Server+High+Disk")
} 
//High Disk Space for JMP servers
else if ((issue.getSummary().contains("High Disk Space Utilization")) && (issue.getSummary().matches(/.*[jJ][mM][pP].*/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/High+disk+on+JumpPoint+Citrix+Servers")
}
//Logical Disk Free Space is low
else if  (issue.getSummary().contains("[Blocker]: Logical Disk Free Space is low")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/SQL+Server+High+Disk+Space")
}
//Audit Records are pending
else if (issue.getSummary().contains("Audit Records are pending")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82751696")
}
//Audit is Disabled and/or Paused
else if (issue.getSummary().contains("Audit is Disabled and/or Paused")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=102316947")
}
//Audit Triggers are Disabled in Databases
else if (issue.getSummary().contains("Audit Triggers are Disabled in Databases")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ulitimatesoftware.com/display/COS/Audit+Triggers+Are+Missing+OR+Disabled+In+Databases")
}
//Audit Triggers are missing in Databases
else if (issue.getSummary().contains("Audit Triggers are missing in Databases")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Audit+Triggers+Are+Missing+OR+Disabled+In+Databases")
}
//Component SMS Agent Host
else if ((issue.getSummary().contains("Component")) &&(issue.getSummary().contains("SMS Agent Host")) ){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82764095")
}
//Component PXAgent on Node
else if ((issue.getSummary().contains("Component")) &&(issue.getSummary().contains("PXAgent on Node")) ){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+SQL+PXAgent+Email+Report+is+Down")
}
//High CPU Utilization on TS
else if ((issue.getSummary().contains("High CPU Utilization on")) && (issue.getSummary().matches(/.*[a-zA-Z][0-9][a-zA-Z][a-zA-Z][a-zA-Z][0-9][tT][sS][0-9][0-9]/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Citrix+Server+High+CPU")
} 
//High CPU Utilization on TS
else if ((issue.getSummary().contains("High CPU Utilization on")) && (issue.getSummary().matches(/.*......[tT][sS]../))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Citrix+Server+High+CPU")
} 
//Component Event ID 0 SSO on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 0 SSO on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+0+SSO+on+Node+is+Down+-++Orion+is+attempting+to+restart")
}
//Component Event ID 0
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Event ID 0"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82753546")
} 
//Component Event ID - 1009 on Node
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Event ID")) && (issue.getSummary().contains("1009"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+1009+is+DOWN")
} 
//Component Event ID 1104 on Node
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Event ID")) && (issue.getSummary().contains("1104"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+1104+or+1069")
} 
//ALERT [] Disk space > 96%
else if ((issue.getSummary().contains("ALERT:")) &&(issue.getSummary().contains("Disk space > 96%")) ){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Sensu+Alerts+-+Response+Guidelines")
}
//ALERT [] HAProxy Services Down
else if ((issue.getSummary().contains("ALERT:")) &&(issue.getSummary().matches(/.*rec-prod-[a-zA-Z][a-zA-Z][a-zA-Z]-13-lb2.*/)) &&(issue.getSummary().contains("HAProxy Services Down")) ){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/ALERT%3A+%5Brec-prod-xxx-13-lb2%5D+HAProxy+Services+Down-+Priority+2")
}
//AlertID:
else if (issue.getSummary().contains("AlertID:")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/SaaS+-+Alertid+Netbackup+%28active%29+job+completed+with+exit+status+xxx")
}
//Component Event ID 10000 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 10000 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+8510+or+4359+or+10000")
}
//Component Event ID 1000
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Event ID 1000"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+1000+on+Node+%28UTA+ReportNet+Servers%29+is+Down")
}
//Virtual Memory usage on *ADSFS*
else if ((issue.getSummary().contains("Virtual Memory usage on")) && (issue.getSummary().matches(/.*[aA][dD][sS][fF][sS].*/))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/File+Server+High+Virtual+Memory")
}
//Virtual Memory usage
else if (issue.getSummary().contains("Virtual Memory usage")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82756912")
}
//Citrix MedEvac Monitoring Alerts
else if (issue.getSummary().contains("Citrix MedEvac Monitoring Alerts")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Citrix+MedEvac+Reports")
}
//SaaS SQL on Node is Unknown
else if ((issue.getSummary().contains("SaaS")) && (issue.getSummary().contains("SQL on Node")) && (issue.getSummary().contains("is Unknown"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/UltiPro+-+SQL+on+Node+*.us.saas+is+Unknown")
}
//Component  TimeAnywhere on Node
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("TimeAnywhere on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+News+App+is+DOWN+OR+Component+TimeAnywhere+is+DOWN")
}
//Component News App on Node
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("News App on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+News+App+is+DOWN+OR+Component+TimeAnywhere+is+DOWN")
}
//Component  SQL PXAgent Email Report on Node
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("SQL PXAgent Email Report on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+SQL+PXAgent+Email+Report+is+Down")
}
//NUMBER OF QUEUED JOBS REPORT on Node
else if (issue.getSummary().contains("NUMBER OF QUEUED JOBS REPORT on Node")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Number+of+Queued+Jobs+Report+on+Site+Server")
}
//Component  Event ID 5013 on Node
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Event ID 5013 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+5013")
}
//Component  Event ID 1027 on Node
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Event ID 1027 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+is+or+1027%2C+1002+or+4096")
}
//Component Ultimate Software UltiPro Enterprise Server on Node
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Ultimate Software UltiPro Enterprise Server on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Ultimate+Software+UltiPro+Enterprise+Server+on+Node")
}
//Alert from Peak 10 VNX5400
else if (issue.getSummary().contains("Alert from Peak 10 VNX5400")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82753815")
}
//Battery 0 DELL on
else if (issue.getSummary().contains("Battery 0 DELL on")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=83144517")
}
//Component  TCP Port 3389
else if (issue.getSummary().contains("Component  TCP Port 3389")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82753657")
}
//Component Active Sessions on Application
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Active Sessions on Application"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82756700")
}
//Component BIDataService on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("BIDataService on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+BI+Data+Service+alert+on+node")
}
//Component Cluster Failover Event on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Cluster Failover Event on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Cluster+Failover+Event")
}
//Component Coach App Pool
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Coach App Pool"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Coach+App+Pool+is+Down")
}
//Component COM Objects Error on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("COM Objects Error on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+COM+Objects+Error+on+Node+***+is+Down")
}
//Component Distributed Transaction Coordinator on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Distributed Transaction Coordinator on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Distributed+Transaction+Coordinator+is+attempting+to+restart")
}
//Component DPM Event Log
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("DPM Event Log"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+DPM+Event+Log+on+E1SUP4DP**+is+Down")
}
//Component Event ID - 1002 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID - 1002 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+is+or+1027%2C+1002+or+4096")
}
//Component Event ID - NovaTime Calc on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID - NovaTime Calc on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Event+ID+-+NovaTime+Calc+on+Node+is+Down")
}
//Component Event ID 1069 on node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 1069 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+1069+-+UDES+Cluster+failure")
}
//Component Event ID 1309 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 1309 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Event+ID+1309+on+Node+is+Down")
}
//Component Event ID 20 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 20 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+20")
}
//Component Event ID 30004 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 30004 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+30004+is+Down")
}
//Component Event ID 4096 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 4096 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+is+or+1027%2C+1002+or+4096")
}
//Component Event ID 4359 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 4359 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+8510+or+4359+or+10000")
}
//Component Event ID 4830 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 4830 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+4830+on+Node+is+down")
}
//Component Event ID 5002 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 5002 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82753548")
}
//Component Event ID 701 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 701 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+701+on+Node+ExSUPxDBxx")
}
//Component Event ID 8510 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID 8510 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+8510+or+4359+or+10000")
}
//Component Event ID Portsight on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Event ID Portsight on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Event+ID+Portsight+on+Node+***+is+Down")
}
//Component IIS Admin Service on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("IIS Admin Service on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+IIS+Admin+Service+on+Node+*+is+Down")
}
//Component Localhost IIS Test on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Localhost IIS Test on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+UTM+IIS+Deadlock+is+DOWN+OR+Component+Localhost+IIS+Test+is+DOWN")
}
//Component Open Locked Transactions on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Open Locked Transactions on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Open+Locked+Transactions+on+Node")
}
//Component SQL Blocked Transactions on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("SQL Blocked Transactions on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+SQL+Blocked+Transactions+on+Node")
}
//Component SQL Database Accessibility
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("SQL Database Accessibility"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+SQL+Database+Accessibility+Check+on+Node+Or+-+Response+Time+Check")
}
//Component SQL Server Response Time Check
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("SQL Server Response Time Check"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+SQL+Database+Accessibility+Check+on+Node+Or+-+Response+Time+Check")
}
//Component SSL Certificate Expiration Date on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("SSL Certificate Expiration Date on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+SSL+Certificate+Expiration+Date+is+DOWN")
}
//Component TCP Port 1433
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("TCP Port 1433"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+TCP+Port+1433+on+Node+is+Down")
}
//Component TCP Port 1494
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("TCP Port 1494"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82753657")
}
//Component Total Sessions on Application
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Total Sessions on Application"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82756700")
}
//Component Ultimate Software SSO Host Service on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Ultimate Software SSO Host Service on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+Ultimate+Software+SSO+Host+Service")
}
//Component UltimateSoftware RBSSync Service
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("UltimateSoftware RBSSync Service"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+UltimateSoftware+RBSSync+Service+on+Node+is+Down")
}
//Component Ultipro Timeout Expired For SITE Servers
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("Ultipro Timeout Expired For SITE Servers"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/UltiPro+Timeout+expired+for+site+server")
}
//Component USCorp Enterprise Ultimate Experience
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("USCorp Enterprise Ultimate Experience"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82757133")
}
//Component USCorp Workplace Ultimate Experience
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("USCorp Workplace Ultimate Experience"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82757133")
}
//Component UTA Circular Reference on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("UTA Circular Reference on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/UTA+Circular+Reference+on+Node+***+is+down")
}
//Component UTA Scheduler Error on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("UTA Scheduler Error on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+UTA+Scheduler+Error+is+DOWN")
}
//Component * UTA* Scheduler on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().matches(/.*UTA.*/)) && (issue.getSummary().contains("Scheduler on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+UTA+Scheduler+Error+is+DOWN")
}
//Component UTM IIS Deadlock
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("UTM IIS Deadlock"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+UTM+IIS+Deadlock+is+DOWN+OR+Component+Localhost+IIS+Test+is+DOWN")
}
//Device * is down
else if (issue.getSummary().contains("Device") && (issue.getSummary().contains("is down"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Down+Node+Remediation+and+Escalation+Procedures")
}
//EMCServer Celerra Notification
else if (issue.getSummary().contains("EMCServer Celerra Notification")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82753815")
}
//Excessive Bi Monitor Errors Have Occured
else if (issue.getSummary().contains("Excessive Bi Monitor Errors Have Occured")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Excessive+Bi+Monitor+Errors+Have+Occurred")
}
//Forwarded Trap Message from
else if (issue.getSummary().contains("Forwarded Trap Message from")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Forwarded+Trap+Message")
}
//High Interface Utilization above 75%)on
else if (issue.getSummary().contains("High Interface Utilization above 75%)on")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=83136445")
}
//Network Device High Memory Utilization on
else if (issue.getSummary().contains("Network Device High Memory Utilization on")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82754121")
}
//Process Audit Data Job is missing
else if (issue.getSummary().contains("Process Audit Data Job is missing")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82759450")
}
//UTM Sustained High Interface Utilization on
else if (issue.getSummary().contains("UTM Sustained High Interface Utilization on")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/UTM+Sustained+High+Interface+Utilization")
}
//Sustained High Interface Utilization on
else if (issue.getSummary().contains("Sustained High Interface Utilization on")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82754104")
}
//Usg_Watchdog_EmployeeCount
else if (issue.getSummary().contains("Usg_Watchdog_EmployeeCount")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Usg_Watchdog_EmployeeCount")
}
//Component UltiPro TouchBase URL on Node
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("UltiPro TouchBase")) && (issue.getSummary().contains("URL on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/UT+URL+UltiPro+TouchBase+URL+is+Down")
}
//SQL Server Failed and Running Maint Jobs
else if (issue.getSummary().matches(/.*SQL Server Failed and Running Maint [Jj]obs.*/)){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=83136443")
}
//Component TCP Port 9050 on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("TCP Port 9050 on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+TCP+Port+9050+on+Node+is+down")
}
//Component IBM Cognos 8 C8_* on Node * is Down
else if (issue.getSummary().matches(/.*Component  IBM Cognos 8 C8_.* on Node .* is.*Down.*/)){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+IBM+Cognos+8+or+10+C*_*%3A*%3A*+on+Node+is+Down")
}
//Component IBM Cognos C10_* on Node * is Down
else if (issue.getSummary().matches(/.*Component  IBM Cognos C10_.* on Node .* is.*Down.*/)){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+IBM+Cognos+8+or+10+C*_*%3A*%3A*+on+Node+is+Down")
}
//Component IBM Cognos on Node * is Down
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("IBM Cognos on Node")) && (issue.getSummary().contains("Down"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+IBM+Cognos+on+Node+is+Down")
}
//Component  W4 Check on Node
else if (issue.getSummary().contains("Component") && (issue.getSummary().contains("W4 Check on Node"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+W4+Check+on+node+xxxxx+is+Critical")
}
//Component SaaS HAA Web Service on Node AT2SU00WS03 is Down
else if (issue.getSummary().contains("Component") && issue.getSummary().contains("SaaS HAA Web Service on Node AT2SU00WS03")  && issue.getSummary().contains("Down")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/AT2SU00WS03")
}
//[AlertSite] Site/Device Alert (ST=2) - DED Apollo Education Group Production
else if (issue.getSummary().contains("[AlertSite] Site/Device Alert (ST=2) - DED Apollo Education Group Production")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82752127")
}
//ALERT: [ums.phx.ucloud.int] or ALERT: [ums.atl.ucloud.int] 
else if (issue.getSummary().contains("ALERT: [ums.phx.ucloud.int]") || issue.getSummary().contains("ALERT: [ums.atl.ucloud.int]")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Sensu+Alerts+-+Response+Guidelines")
}
//*esxvm*.us.saas Not Reporting Stats OR **iesxvm***.ca.saas Not Reporting Stats
else if (((issue.getSummary().matches(/.*[eE][sS][xX][vV][mM].*us.saas.*/)) || (issue.getSummary().matches(/.*[iI][eE][sS][xX][vV][mM].*ca.saas.*/)) ) && (issue.getSummary().contains("Not Reporting Stats"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Alert+for+*ESXVM.us.saas+Not+Reporting+Stats")
} 
//High Packet loss on
else if (issue.getSummary().contains("High Packet loss on")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/High+Packet+Loss+on+Node")
}
//dfm: Error event on **NETAPP**  OR dfm: Critical event on **NETAPP**
/*else if ((issue.getSummary().contains("dfm: Error event on")) || (issue.getSummary().contains("dfm: Critical event on"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/NetApp+Alerts+-+Response+Guidelines")
}*/
//dfm: **NETAPP**
else if (issue.getSummary().matches(/dfm:.*NETAPP.*/)){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/NetApp+Alerts+-+Response+Guidelines")
}
//CENG
else if (issue.getSummary().matches(/ALERT:.*CENG.*/)){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Cloud+Engine+(CENG)+Alerts")
}
//ALERT: [ 
else if (issue.getSummary().contains("ALERT: [")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Sensu+Alerts+-+Response+Guidelines")
}
//Component IDENTITY URL on Node ** is Down
else if ((issue.getSummary().contains("Component  IDENTITY URL on Node")) && (issue.getSummary().contains("Down"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Component+IDENTITY+URL+on+Node+**+is+Down")
}
//Component  nxlog on Node
else if (issue.getSummary().contains("Component  nxlog on Node")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=85450782")
}
//Tanium Notification { High Mem Threshold for Non-JMP TS }
else if (issue.getSummary().contains("Tanium Notification { High Mem Threshold for Non-JMP TS }")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=86415643")
}
//Alert: Site Connectivity Issue for DPM to fix Resolution state: New
else if (issue.getSummary().contains("Alert: Site Connectivity Issue for DPM to fix Resolution state: New")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Alert%3A+Site+Connectivity+Issue+for+DPM+to+fix+-+Resolution+State%3A+New")
}
//Warning: Cluster * triggered *  OR  Error: Cluster * triggered *   OR  Critical: Cluster * triggered * 
else if ((issue.getSummary().contains("Cluster")) && (issue.getSummary().contains("triggered"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Solid+Fire+Storage+System+Remediation")
}
//UDES: Expiring PGP Key Weekly Report 
else if (issue.getSummary().contains("UDES: Expiring PGP Key Weekly Report")){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=88874961")
}
//DX monitor CELERGO_FileTransferError
else if ((issue.getSummary().contains("CELERGO_FileTransferError")) && (issue.getSummary().contains("DX")) && (issue.getSummary().contains("monitor"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/DX+monitor+CELERGO_FileTransferError")
}
//VMware vCenter - Alarm alarm.HostConnectionStateAlarm changed status from Grenn to Red
else if ((issue.getSummary().contains("[VMware vCenter - Alarm alarm.HostConnectionStateAlarm] alarm.HostConnectionStateAlarm changed status from Green to Red"))){
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/%5BVMware+vCenter+-+Alarm+alarm.HostConnectionStateAlarm%5D+alarm.HostConnectionStateAlarm+changed+status+from+Green+to+Red")
}
//Remediation link for EMP Count Servers
//else if ((issue.getSummary().matches(/.*[entvENTV][0xX][eintEINT][eE0][imIM][epEP][dmDM][bpBP][cdnCDN][lbLB0-9][0-9][0-9].*/))){
else if ((issue.getSummary().matches(/.*[entvENTV][0xX][eintEINT][eE0][imIM][epEP][dmDM][bpBP][cdnCDN][lbLB0-9].*/))){
issue.setCustomFieldValue(cfLOE," https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=86430517")
}
//Remediation link for E3DUP0* NZ3DUP0* NZ3DTM0* servers
else if (issue.getSummary().matches(/.*[enEN][3zZ][3dD][udUD][putPUT][0pmPM][0a-zA-Z].*/))  {
issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/E3DUP0*%2C++NZ3DUP0*+and+NZ3DTM0*+Ultimate+Software+Servers")
}
//Connection Refused && EFT Celergo
else if(issue.getSummary().contains("Connection Refused") && issue.getDescription().contains("EFT Celergo")){
	issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=86434920")
}
//Fatal directory update for account ID
else if(issue.getDescription().contains("ERROR/MainProcess] Fatal directory update for account ID")){
	issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Perception+Fatal+directory+update+for+account+ID")
}
else if ((issue.getSummary().contains("Total CPU Utilization Percentage is too high on"))&& (issue.getSummary().matches(/.*[a-zA-Z][0-9][SW]TM1AP[0-9][0-9].*/))){
             issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=82753555")
}
else if ((issue.getSummary().contains("[Critical]: TransportManager failed to listen on the supplied URI using the NetTcpPortSharing service"))){
             issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=110557218")                
}
//Fatal directory update for account_id
else if (issue.getDescription().contains("ERROR/MainProcess] Fatal directory update for account_id")){
            issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?pageId=108272849")
}
//UDT Eventing.Server Has Stopped on
else if (issue.getSummary().contains("[Blocker] - UDT Eventing.Server Has Stopped on")){
           issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/pages/viewpage.action?spaceKey=COS&title=%5BBlocker%5D%3A+Scheduled+Task+failed+to+complete+successfully+on+%24Nodename")
}
else if  (issue.getSummary().contains("ALERT: teamcity-ubuntu High Load Average")){
           issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/High+Load+Validation+for+Teamcity")
}
else if (issue.getSummary().contains("dfm: Error event on devnetapp03")|| issue.getSummary().contains("dfm: Warning event on devnetapp03")){
	   issue.setCustomFieldValue(cfLOE,"https://confluence.ultimatesoftware.com/display/COS/Denver2+%28File+Server%29+Escalation+Procedure")
}
else{
}

