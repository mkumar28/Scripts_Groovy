import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.ComponentManager
import com.atlassian.jira.issue.ModifiedValue
 
Issue myIssue = issue
if ((issue.getSummary().contains("Node Down Notification: e1")) || (issue.getSummary().contains("Node Down Notification: E1")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: e2")) || (issue.getSummary().contains("Node Down Notification: E2")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: e3")) || (issue.getSummary().contains("Node Down Notification: E3")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: ez")) || (issue.getSummary().contains("Node Down Notification: EZ")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: nz")) || (issue.getSummary().contains("Node Down Notification: NZ")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: n1")) || (issue.getSummary().contains("Node Down Notification: N1")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: n2")) || (issue.getSummary().contains("Node Down Notification: N2")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: n3")) || (issue.getSummary().contains("Node Down Notification: N3")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: sc1-3")) || (issue.getSummary().contains("Node Down Notification: SC1-3")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: sc1-inet")) || (issue.getSummary().contains("Node Down Notification: SC1-INET")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Node Down Notification: sc1-mpls")) || (issue.getSummary().contains("Node Down Notification: SC1-MPLS")) ){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("UTA_")) && (issue.getSummary().contains("Scheduler")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Component")) && ((issue.getSummary().contains("UTA_")) || (issue.getSummary().contains("uta_"))) && (issue.getSummary().contains("Down")) && !(issue.getSummary().contains("WBMail"))){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("UTM")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '2' )
} 
else if (issue.getSummary().contains("Component Ultipro Timeout Expired For SITE")){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("UTA Sync")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("Component Ultimate Software UltiPro Enterprise")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("High Disk")) && (issue.getSummary().contains("99"))){
issue.setPriorityId( '2' )
} 
else if (((issue.getSummary().contains("AlertID")) ||(issue.getSummary().contains("alertid"))) && ((issue.getSummary().contains("Exit Status"))|| (issue.getSummary().contains("exit status")))){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("AlertSite")) && (issue.getSummary().contains("Alert")) && !(issue.getSummary().contains("ST=0"))){
issue.setPriorityId( '2' )
} 
else if (issue.getSummary().contains("Not Reporting Stats")){
issue.setPriorityId( '2' )
}
else if (issue.getSummary().contains("DX monitor CELERGO_FileTransferError")){
issue.setPriorityId( '2' )
} 
else if (issue.getSummary().contains("QUEUED JOBS")){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("SQL Server Response Time Check")) && (issue.getSummary().contains("Critical"))){
issue.setPriorityId( '2' )
} 
else if ((issue.getSummary().contains("[CRITICAL]")) || (issue.getSummary().contains("[Critical]")) ){
issue.setPriorityId( '2' )
}
else if ((issue.getSummary().contains("Error : Cluster")) && (issue.getSummary().contains("triggered")) ){
issue.setPriorityId( '2' )
}
else if ((issue.getSummary().contains("Virtual Memory usage on")) && (issue.getSummary().matches(/.*[aA][dD][sS][fF][sS].*/))){
issue.setPriorityId( '2' )
}
else if ((issue.getSummary().contains("[VMware vCenter - Alarm alarm.HostConnectionStateAlarm] alarm.HostConnectionStateAlarm changed status from Green to Red"))){
issue.setPriorityId( '2' )
}
else if ((issue.getSummary().contains("SSL Certificate Renewal Notice")) || (issue.getSummary().contains("Your SSL Service(s) is Due to Expire")) || (issue.getSummary().contains("SSL Certificate Expiration Notice")) || (issue.getSummary().contains("Your Secure Certificate is coming up for renewal")) || (issue.getSummary().contains("Secure Certificate Renewal Notice")) || (issue.getSummary().contains("Important notice: Expired products")) || (issue.getSummary().contains("GoDaddy Product Expiration Notice"))  ){
issue.setPriorityId( '2' )
}
else if(issue.getDescription().contains("Check: check_disk")){
issue.setPriorityId( '2' )
}
else if ((issue.getSummary().contains("ATL-wfm")) || (issue.getSummary().contains("ATL-TEST-wfm")) || (issue.getSummary().contains("PHX-wfm")) ||                                                     (issue.getSummary().contains("PHX-TEST-wfm")) || (issue.getSummary().contains("TOR-wfm")) || (issue.getSummary().contains("TOR-TEST-wfm")))                                                  {                                                                                                                                                                                             if((issue.getSummary().contains("TA-metrics-check")) || (issue.getSummary().contains("TA-rabbitmq-queue-check")) || (issue.getSummary().contains("TOA-metrics-check")) || 
             (issue.getSummary().contains("TOA-rabbitmq-queue-check")) || (issue.getSummary().contains("SCHED-metrics-check")) || (issue.getSummary().contains("SCHED-rabbitmq-queue-check"))             || (issue.getSummary().contains("RPT-metrics-check")) || (issue.getSummary().contains("RPT-rabbitmq-queue-check")))                                                                         {
   	       issue.setPriorityId( '2' )
          }
}
else if ((issue.getSummary().contains("ATL-ET")) || (issue.getSummary().contains("ATL-SALESA")) || (issue.getSummary().contains("ATL-SALESB")) || (issue.getSummary().contains("SALESC")) ||           (issue.getSummary().contains("SALESD")))                                             
       {                                                                                                                                                                                             if((issue.getSummary().contains("TA-metrics-check")) || (issue.getSummary().contains("TA-rabbitmq-queue-check")) || (issue.getSummary().contains("TOA-metrics-check")) ||
             (issue.getSummary().contains("TOA-rabbitmq-queue-check")) || (issue.getSummary().contains("SCHED-metrics-check")) || (issue.getSummary().contains("SCHED-rabbitmq-queue-check"))             || (issue.getSummary().contains("RPT-metrics-check")) || (issue.getSummary().contains("RPT-rabbitmq-queue-check")))                                                                         {
               issue.setPriorityId( '2' )
           }
}
else if ((issue.getSummary().contains("ATL-mobile")) || (issue.getSummary().contains("PHX-mobile")) || (issue.getSummary().contains("TOR-mobile")))
       {                                                                                                                                                                                             if((issue.getSummary().contains("push-receiver-health-check")) || (issue.getSummary().contains("push-api-health-check"))                                                                        || (issue.getSummary().contains("translation-service-health-check")) ||   (issue.getSummary().contains("push-rabbitmq-queue-check")))                                                       {
               issue.setPriorityId( '2' )
           }
}
else if ((issue.getSummary().contains("INFORMATICA Production App server VM Cloning")))
        {
            issue.setPriorityId('2')
}
else if ((issue.getSummary().contains("check_agent_message_count")) && (issue.getDescription().contains("Environment: spade-prod")))
 	{
	   issue.setPriorityId('2')

}
else if(issue.getSummary().contains("ALERT: teamcity-ubuntu High Load Average"))
	{
	  issue.setPriorityId('2')
}
else
{

}