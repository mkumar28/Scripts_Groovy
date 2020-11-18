import com.atlassian.jira.ComponentManager
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.comments.CommentManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.util.ImportUtils
import com.atlassian.jira.user.util.DefaultUserManager
import com.atlassian.crowd.embedded.api.User
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.issue.ModifiedValue

MutableIssue issue = issue

def userUtil = ComponentAccessor.getUserUtil() //for JIRA v7

if (issue.getSummary().contains("[AMWSR]")){

		//def componentManager = ComponentManager.getInstance()//for JIRA v6
		//userUtil= componentManager.getUserUtil()//for JIRA v6
		//usera = userUtil.getUser('andreww')//for JIRA v6
		//def usera = userUtil.getUserByKey('andreww')//for JIRA v7  
		//issue.setAssignee(usera);
        def  usera = 'andreww'
        setAssignee(usera);

} 
else if ((issue.getSummary().contains("Equifax")) && (issue.getSummary().contains("Global Connectivity Issue"))){

		/*def componentManager = ComponentManager.getInstance()//for JIRA v6
		//userUtil= componentManager.getUserUtil()//for JIRA v6
		//usera = userUtil.getUser('sboc')**///for JIRA v6
		//def usera = userUtil.getUserByKey('sboc')//for JIRA v7
		//issue.setAssignee(usera);
        def  usera = 'sboc'
        setAssignee(usera);

} 
else if ((issue.getSummary().contains("FTP Error Detected")) && (issue.getSummary().contains("UCN Customer"))){

		/*def componentManager = ComponentManager.getInstance()//for JIRA v6
		//userUtil= componentManager.getUserUtil()//for JIRA v6
		//usera = userUtil.getUser('sboc')*////for JIRA v6
		//def usera = userUtil.getUserByKey('sboc')//for JIRA v7 
		//issue.setAssignee(usera);
        def  usera = 'sboc'
        setAssignee(usera);

} 
else if (issue.getSummary().contains("UCN Fatal Error Detected For Customer")){

		/**def componentManager = ComponentManager.getInstance()//for JIRA v6
		userUtil= componentManager.getUserUtil()//for JIRA v6
		usera = userUtil.getUser('sboc') *///or JIRA v6
		//def usera = userUtil.getUserByKey('sboc')//for JIRA v7
		//issue.setAssignee(usera);
        def  usera = 'sboc'
        setAssignee(usera);

}
else if ((issue.getSummary().contains("Syslog message from")) || (issue.getSummary().contains("SolarWinds NCM Job Completion Notification")) || (issue.getSummary().contains("Alert-F5 EM Rotating Archive Create Failure")) ){

		/**def componentManager = ComponentManager.getInstance()//for JIRA v6
		userUtil= componentManager.getUserUtil()//for JIRA v6
		usera = userUtil.getUser('cloudnetwork')*//////for JIRA v6
		//def usera = userUtil.getUserByKey('cloudnetwork')//for JIRA v7
		//issue.setAssignee(usera);
        def  usera = 'cloudnetwork'
        setAssignee(usera);

} 
else if ((issue.getSummary().matches(/.*[bB][rR][iI][tT].*/)) || (issue.getSummary().matches(/.*[pP][eE][rR][vV][aA][sS][iI][vV][eE].*/)) || (issue.getSummary().matches(/.*[bB][aA][rR][sS].*/))  || (issue.getSummary().matches(/.*[sS][oO][cC][mM][eE][dD].*/)) || (issue.getSummary().matches(/.*[pP][hH][bB][wW].*/))){
    if (!(issue.getSummary().contains("Web Application Unavailable: Pervasive Integration Hub main page"))) {
         /*def  componentManager = ComponentManager.getInstance()//for JIRA v6
         userUtil= componentManager.getUserUtil()//for JIRA v6
         usera = userUtil.getUser('launch')*///for JIRA v6
		 //def usera = userUtil.getUserByKey('launch')//for JIRA v7
         //issue.setAssignee(usera);
         def  usera = 'launch'
         setAssignee(usera);
}
}
else if (issue.getSummary().contains("Eventing ChangeQueue_Target Enqueue"))
{
		/*def componentManager = ComponentManager.getInstance()//for JIRA v6
		userUtil= componentManager.getUserUtil()//for JIRA v6
		usera = userUtil.getUser('cloudready')*///for JIRA v6
		//def usera = userUtil.getUserByKey('cloudready')//for JIRA v7  
		//issue.setAssignee(usera);
        def  usera = 'cloudready'
        setAssignee(usera);
}
else if ((issue.getSummary().contains("ATL-wfm")) || (issue.getSummary().contains("ATL-TEST-wfm")) || (issue.getSummary().contains("PHX-wfm")) ||(issue.getSummary().contains("PHX-TEST-wfm")) || (issue.getSummary().contains("TOR-wfm")) || (issue.getSummary().contains("TOR-TEST-wfm"))){  
        if((issue.getSummary().contains("TA-metrics-check")) || (issue.getSummary().contains("TA-rabbitmq-queue-check")) || (issue.getSummary().contains("TOA-metrics-check")) || (issue.getSummary().contains("TOA-rabbitmq-queue-check")) || (issue.getSummary().contains("SCHED-metrics-check")) || (issue.getSummary().contains("SCHED-rabbitmq-queue-check"))            
	    || (issue.getSummary().contains("RPT-metrics-check")) || (issue.getSummary().contains("RPT-rabbitmq-queue-check"))){
        /*def componentManager = ComponentManager.getInstance()//for JIRA v6
	    userUtil= componentManager.getUserUtil()//for JIRA v6
		usera = userUtil.getUser('cloudready')*////for JIRA v6
		//def usera = userUtil.getUserByKey('cloudready')//for JIRA v7
		//issue.setAssignee(usera);
        def  usera = 'cloudready'
        setAssignee(usera);
		}		
}
else if ((issue.getSummary().contains("ATL-ET")) || (issue.getSummary().contains("ATL-SALESA")) || (issue.getSummary().contains("ATL-SALESB")) || (issue.getSummary().contains("SALESC")) || (issue.getSummary().contains("SALESD")))
       { if((issue.getSummary().contains("TA-metrics-check")) || (issue.getSummary().contains("TA-rabbitmq-queue-check")) || (issue.getSummary().contains("TOA-metrics-check")) ||(issue.getSummary().contains("TOA-rabbitmq-queue-check")) ||
            (issue.getSummary().contains("SCHED-metrics-check")) || (issue.getSummary().contains("SCHED-rabbitmq-queue-check"))|| (issue.getSummary().contains("RPT-metrics-check")) || (issue.getSummary().contains("RPT-rabbitmq-queue-check"))){
                /*def componentManager = ComponentManager.getInstance()//for JIRA v6
                userUtil= componentManager.getUserUtil()//for JIRA v6
                usera = userUtil.getUser('cloud environment services')*///for JIRA v6
				//def usera = userUtil.getUserByKey('cloud environment services')///for JIRA v7
                //issue.setAssignee(usera);
                def  usera = 'cloud environment services'
                setAssignee(usera);

       }
}
else if ((issue.getSummary().contains("ATL-mobile")) || (issue.getSummary().contains("PHX-mobile")) || (issue.getSummary().contains("TOR-mobile")))
       {  if((issue.getSummary().contains("push-receiver-health-check")) || (issue.getSummary().contains("push-api-health-check"))|| (issue.getSummary().contains("translation-service-health-check")) ||(issue.getSummary().contains("push-rabbitmq-queue-check"))){
               /*def componentManager = ComponentManager.getInstance()//for JIRA v6
               userUtil= componentManager.getUserUtil()//for JIRA v6
               usera = userUtil.getUser('saassupport')*/////for JIRA v6
			   //def usera = userUtil.getUserByKey('saassupport')//for JIRA v7
               //issue.setAssignee(usera);
               def  usera = 'saassupport'
               setAssignee(usera);

           }
}
else if((issue.getSummary().contains("Daily Task: ATL DC Walk-Through")) || (issue.getSummary().contains("Daily Task: PHX DC Walk-Through")))
       {   
               /*def componentManager = ComponentManager.getInstance()//for JIRA v6
               userUtil= componentManager.getUserUtil()//for JIRA v6
               usera = userUtil.getUser('dco')*///for JIRA v6
			   //def usera = userUtil.getUserByKey('dco')//for JIRA v7
               //issue.setAssignee(usera);
               def  usera = 'dco'
               setAssignee(usera);

}
else if ((issue.getSummary().contains("INFORMATICA Production App server VM Cloning")))
        {
               /**def componentManager = ComponentManager.getInstance()//for JIRA v6
               userUtil= componentManager.getUserUtil()//for JIRA v6
               usera = userUtil.getUser('sis')*///for JIRA v6
			   //def usera = userUtil.getUserByKey('sis')//for JIRA v7
               //issue.setAssignee(usera);
               def  usera = 'sis'
               setAssignee(usera);

}
else if((issue.getSummary().contains("Drive Auto-Expand"))&& (issue.getSummary().contains("increased on"))&&(issue.getSummary().contains("Informational, Please Close Ticket")))
	{
 	       /**def componentManager = ComponentManager.getInstance()//for JIRA v6
               userUtil= componentManager.getUserUtil()//for JIRA v6
               usera = userUtil.getUser('cloudsitereliability')*///for JIRA v6
			   //def usera = userUtil.getUserByKey('cloudsitereliability')//for JIRA v7
               //issue.setAssignee(usera);
               def  usera = 'cloudsitereliability'
               setAssignee(usera);
}
else if((issue.getSummary().contains("[DEV]: Total CPU Utilization Percentage is too high")|| issue.getSummary().contains("[DEV]: Memory Pages Per Second is too High")
        || issue.getSummary().contains("[DEV]: Logical Disk Free Space is low on")||issue.getSummary().contains("[DEV]: Health Service Heartbeat Failure on")
        || issue.getSummary().contains("[DEV]: Available Megabytes of Memory is too low on") 
        || issue.getSummary().contains("[DEV]: Cluster Discovery Connect Status Unhealthy on Cluster Service" ))){
		
               
    	       def  usera = 'sis'
               setAssignee(usera);
    
     
}
else if ((issue.getSummary().contains("sps_alert_failed_event_publishing")))
        {
               /**def componentManager = ComponentManager.getInstance()//for JIRA v6
               userUtil= componentManager.getUserUtil()//for JIRA v6
               usera = userUtil.getUser('sps_alerts')*/////for JIRA v6
			   //def usera = userUtil.getUserByKey('sps_alerts')//for JIRA v7
               //issue.setAssignee(usera);
               def  usera = 'sps_alerts'
               setAssignee(usera);

}
else if(issue.getSummary().matches(/.*[Tt][Aa][Xx].*/) && issue.getSummary().matches(/.*[Ff][Ii][Ll][Ii][Nn][Gg].*/)&& issue.getSummary().matches(/.*[Ss][Ee][Rr][Vv][Ii][Cc][Ee].*/)
        && issue.getSummary().matches(/.*[Uu][Dd][Ee][Ss].*/)&& issue.getSummary().matches(/.*[Tt][Ee][Tt].*/))
	{  
	       /**def componentManager = ComponentManager.getInstance()//for JIRA v6
               userUtil= componentManager.getUserUtil()//for JIRA v6
               usera = userUtil.getUser('saassupport')*////for JIRA v6
			   //def usera = userUtil.getUserByKey('saassupport')//for JIRA v7
               //issue.setAssignee(usera);
               def  usera = 'saassupport'
               setAssignee(usera);

}

else if((issue.getSummary().matches(/.*(am|dj|redis)(-atl-triage).*/) || issue.getSummary().matches(/.*(am|dj|redis)(-cts-ds|-profile-ds|-config-ds|-ui)(-atl-triage).*/))&& 
       (issue.getSummary().matches(/.*(001376|01544|0139|001370|0130|00236).*/))&& (issue.getSummary().matches(/.*(openam-primary-3|haproxy-failover-master-2|haproxy-failover-slave-1|redis-member-1).*/)|| (issue.getSummary().matches(/.*(-openam-member-)(2|1).*/)) || (issue.getSummary().matches(/.*(-opendj-member-)(2|1).*/))|| (issue.getSummary().matches(/.*(openam-ui-ubuntu-)(3|2|1).*/))))
        {
	       /**def componentManager = ComponentManager.getInstance()//for JIRA v6
               userUtil= componentManager.getUserUtil()//for JIRA v6
               usera = userUtil.getUser('cloudready')*/////for JIRA v6
			   //def usera = userUtil.getUserByKey('cloudready')//for JIRA v7
               //issue.setAssignee(usera);
               def  usera = 'cloudready'
               setAssignee(usera);

}
else if (issue.getSummary().matches(/.*(t|T)(x|X)(m|M)(p|P)(u|U)(t|T)(e|E)([a-f]|[2-5]|[7-9]).*/)||((issue.getSummary().matches(/.*(t|T)(x|X)(m|M)(p|P)(u|U)(t|T)(e|E)[6].*/)) && (issue.getSummary().contains("SQL Database Accessibility Issue")))) {
               def  usera = 'ute_alerts'
               setAssignee(usera);
}
else if ((issue.getSummary().contains("Space Request - DEVVCDVC3 - (d:\\ add 25g, total 30g)")) || (issue.getSummary().contains("Space Request - D99DUSGUT01 (C: add 10GB Total 60GB")) || issue.getSummary().contains("Device:fl1devdc02.dev.us.corp 10.71.12.12; Severity") 
||((issue.getSummary().contains("Alarm alarm.VmCPUUsageAlarm on Virtual Machine") || issue.getSummary().contains("Alarm Guest Disk Latency on Virtual Machine") 
|| issue.getSummary().contains("Alarm Guest Snapshots on Virtual Machine") || issue.getSummary().contains("Alarm alarm.VmMemoryUsageAlarm on Virtual Machine"))
 && ((issue.getSummary().contains("DW99PADSDC02"))||(issue.getSummary().contains("DW99PADSDF01")) ||  issue.getSummary().contains("DW99PADSDF02")
 ||issue.getSummary().contains("DW99DUSGFT01") || (issue.getSummary().contains("DW99PADSVL01"))|| (issue.getSummary().contains("DW99PADSWU01")) 
 ||(issue.getSummary().contains("DW99PCMDAG01"))))) {

               def  usera = 'sis'
               setAssignee(usera);


}
else {
}

def setAssignee(String user){
    def userManager = ComponentAccessor.getUserManager()
    def usera = userManager.getUserByKey(user)//for JIRA v7
    issue.setAssignee(usera);
}
