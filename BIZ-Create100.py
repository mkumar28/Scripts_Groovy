#!/usr/bin/python
  
import sys
import requests
import json
import datetime

#Open the error file in read more
'''currentDT = datetime.datetime.now()
print(currentDT)
usrCurrentDate=currentDT.strftime("%Y-%m-%d")
#print("/home/appadmin/JIRAAhaVersionSync/Errorlog_"+usrCurrentDate+".txt")
#directoryUrl = "/home/appadmin/JIRAAhaVersionSync/"
f= open(directoryUrl+'Errorlog_'+usrCurrentDate+".txt","r")
if f.mode == 'r':
    contents =f.read()
print(contents)'''
jira_url ="https://fl1cpjira03"
#jira_url ="https://fl1cdjira05/"
jira_login="ulti"
jira_password="quiznos"

#Creating Jira issue for the failed processing
strings = []
strings.append("/rest/api/2/issue/ULTI-441450?expand=changelog")
strings.append("/rest/api/2/issue/ULTI-443477?expand=changelog")
strings.append('/rest/api/2/issue/ULTI-445864?expand=changelog')
strings.append('/rest/api/2/issue/ULTI-448113?expand=changelog')
strings.append('/rest/api/2/issue/ULTI-448215?expand=changelog')
strings.append('/rest/api/2/issue/ULTI-448623?expand=changelog')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3d%22Build+and+Deployment%22+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3d%22Business+Intelligence%22+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3d%22Data+Platform%22+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3d%22Data+Warehouse%22+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3d%22NU+Pay%22+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3d%22Talent+Management%22+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project+%3d+ULTI+AND+component+%3d+%22Tax+Data+Store%22+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project+%3d+ULTI+AND+component+%3d+%22Tax+Data+Store%22++and+Updated+%3e%3d+%272020-7-9+9%3a57%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3d%22TC+Customs%22+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project+%3d+ULTI+AND+component+%3d+%27Tax+Services%27+and+Updated+%3e%3d+%272020-7-9+9%3a57%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3dAuthN+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3dLaunch++and+Updated+%3e%3d+%272020-7-9+9%3a57%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project+%3d+ULTI+AND+Component+%3d+Notifications++and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3dPayroll+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3dRecruiting+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project+%3d+ULTI+and+component+%3d+USMP++and+Updated+%3e%3d+%272020-7-9+9%3a57%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project%3dULTI+and+component%3dUTA+and+Updated+%3e%3d+%272020-7-9+9%3a54%27+&maxResults=50')
strings.append('/rest/api/2/search?startAt=0&jql=project+%3d+ULTI+AND+component+in+(%22WFM+Platform%22%2c+%22WFM+Nucleus%22%2c+%22WFM+Reporting%22%2c+%22WFM+UPS%22%2c+%22WFM+UPT%22%2c+%22WFM+TOA%22%2c+%22WFM+AM%22%2c+%22WFM+Mobile%22)+and+Updated+%3e%3d+%272020-7-9+9%3a57%27+&maxResults=50')
#createURL =  jira_url+"rest/api/2/issue"
'''for x in range(100):
    data = {"fields": {"project":{"id":"10890"},"issuetype":{"id":"178"},"summary":"Create Issue "+str(x),"description":"Test","customfield_12924":[{"id":"106006"}],"customfield_10502": {"name": "ulti"},"reporter": {"name": "ulti"}}}
    headers = {'Content-type': 'application/json'}
    r = requests.post(createURL, data=json.dumps(data), headers=headers, auth=(jira_login,jira_password), verify=False)
    data= r.json()
    id = data.get('id')
    print(data)'''
for x in strings:
    createURL =  jira_url+x
    headers = {'Content-type': 'application/json'}
    r = requests.get(createURL, headers=headers, auth=(jira_login,jira_password), verify=False)
    data= r.json()
    id = data.get('id')
    print(data)


