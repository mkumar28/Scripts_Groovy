import com.atlassian.jira.issue.Issue 
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.ComponentManager
import com.atlassian.jira.issue.ModifiedValue
 
Issue myIssue = issue
if ((issue.getSummary().contains("UTA URL")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '1' )
} 
else if ((issue.getSummary().contains("UTA URL")) && (issue.getSummary().contains("down"))){
issue.setPriorityId( '1' )
} 
else if ((issue.getSummary().contains("High Disk")) && (issue.getSummary().contains("100")) && !(issue.getSummary().contains("N1-")) && !(issue.getSummary().contains("N2-")) && !(issue.getSummary().contains("E1-")) && !(issue.getSummary().contains("E2-")) && !(issue.getSummary().contains("E0-")) && !(issue.getSummary().contains("N0-")) && !(issue.getSummary().contains("EZ-")) && !(issue.getSummary().contains("NZ-")) ){
issue.setPriorityId( '1' )
} 
else if ((issue.getSummary().contains("Ultimate Software SSO Host")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '1' )
} 
else if ((issue.getSummary().contains("Ultimate Software UltiPro Enterprise Server")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '1' )
} 
else if (issue.getSummary().contains("Benchmark UTA to ULTIPRO Restore Alert - FAILED")){
issue.setPriorityId( '1' )
} 
else if ((issue.getSummary().contains("QUEUED JOBS")) && (issue.getSummary().contains("Node"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Blocked Transactions")) && (issue.getSummary().contains("Node"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Open")) && (issue.getSummary().contains("Locked")) && (issue.getSummary().contains("Transactions"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Cluster")) && (issue.getSummary().contains("Failover")) && (issue.getSummary().contains("Event"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("Event")) && (issue.getSummary().contains("ID")) && (issue.getSummary().contains("4359"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Timeout Expired")) && (issue.getSummary().contains("For")) && (issue.getSummary().contains("SITE Servers")) && (issue.getSummary().contains("ONLY"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("SQL Server Agent (MSSQLSERVER)")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("SQL Database Accessibility Check")) && (issue.getSummary().contains("Critical"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Open Locked Transactions")) && (issue.getSummary().contains("Critical"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Event ID 8510")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Event ID 4359")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Event ID 10000")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Event ID 2004")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Device")) && (issue.getSummary().contains("is down"))){
issue.setPriorityId( '1' )
}
else if (issue.getSummary().contains("Excessive Bi Monitor Errors")){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("High Disk")) && (issue.getDescription().contains("TempDB"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Component")) && (issue.getSummary().contains("UDES/EFT File Monitor on Node")) && (issue.getSummary().contains("Down"))){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("[BLOCKER]")) || (issue.getSummary().contains("[Blocker]")) || (issue.getSummary().contains("[Blocker]:")) || (issue.getSummary().contains("[BLOCKER]:")) ){
issue.setPriorityId( '1' )
}
else if ((issue.getSummary().contains("Critical : Cluster")) && (issue.getSummary().contains("triggered")) ){
issue.setPriorityId( '1' )
}
else if (issue.getSummary().contains("Equifax - Global Connectivity Issue")){
issue.setPriorityId( '1' )
}
else if (issue.getSummary().matches(/.*ALERT:.*CENG.*/)) {
issue.setPriorityId('1')
}
else if (issue.getSummary().matches(/.*ALERT:.*uccpu.*/)) {
issue.setPriorityId('1')
}
else if (issue.getSummary().matches(/.*ALERT:.*Disk space > 96%.*/)) {
issue.setPriorityId('1')
}
else if (issue.getSummary().matches(/.*ALERT:.*keepalive.*/)) {
issue.setPriorityId('1')
}
else if (issue.getSummary().contains("dmia1ub1cpu")){
issue.setPriorityId('1') 
}
else if ((issue.getSummary().contains("ATL-wfm")) || (issue.getSummary().contains("ATL-TEST-wfm")) || (issue.getSummary().contains("ATL-ET-wfm")) ||
          (issue.getSummary().contains("ATL-SALESA-wfm")) || (issue.getSummary().contains("ATL-SALESB-wfm")) || (issue.getSummary().contains("ATL-SALESC-wfm")) ||
          (issue.getSummary().contains("ATL-SALESD-wfm"))|| (issue.getSummary().contains("PHX-wfm")) || (issue.getSummary().contains("PHX-TEST-wfm")) ||
          (issue.getSummary().contains("TOR-wfm"))||(issue.getSummary().contains("TOR-TEST-wfm"))) {
          if((issue.getSummary().contains("TA-health-check")) || (issue.getSummary().contains("TOA-health-check")) || (issue.getSummary().contains("SCHED-health-check"))|| 
             (issue.getSummary().contains("RPT-health-check"))){
              issue.setPriorityId('1')
            }
}
else if ((issue.getSummary().contains("ATL-mobile")) || (issue.getSummary().contains("PHX-mobile")) || (issue.getSummary().contains("TOR-mobile"))) {
          if((issue.getSummary().contains("gateway-health-check"))){
              issue.setPriorityId('1')
            }
}
else if((issue.getSummary().contains("Device:ucblk102-mia Disk 23"))){
          issue.setPriorityId('1') 
}
else if (issue.getSummary().contains("RabbitMQ: > 500 Messages In Queue")) {
issue.setPriorityId('1')
}
else if (issue.getDescription().contains(" EventId => Topshelf.Runtime.Windows.WindowsServiceHost.OutOfMemoryException")&& issue.getDescription().contains("ExceptionMessage => Exception of type 'System.OutOfMemoryException' was thrown")){
issue.setPriorityId('1')
}
else if((issue.getSummary().contains("ALERT:"))&&(issue.getSummary().matches(/.*[enEN][0][iI][sS][wW][fF][gG][hH][0][1-2].*/)) && (issue.getSummary().contains("Ucloud IaaS: Ingest Disk space > 70%"))){
issue.setPriorityId('1')
}
else if((issue.getSummary().contains("ALERT:"))&&(issue.getSummary().contains("One or More Cinder Services Down"))){
issue.setPriorityId('1')
}
else if(issue.getSummary().contains("ALERT: [dmia2ub1ctr0003.mia.ucloud.int] Ucloud IaaS: CPU usage > 90%")){
issue.setPriorityId('1')
}
else{
}

