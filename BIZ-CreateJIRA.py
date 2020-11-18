#!/usr/bin/python
  
import sys
import requests
import json
import datetime
import os.path

#Open the error file in read more
documents = []
currentDT = datetime.datetime.now()
print(currentDT)
usrCurrentDate=currentDT.strftime("%Y-%m-%d")
print(usrCurrentDate)
#print("/home/appadmin/JIRAAhaVersionSync/Errorlog_"+usrCurrentDate+".txt")
directoryUrl = "/jira/AhaVersionSync/logs/"
filedirectoryURL = "/jira/AhaVersionSync/"
projectid = "13690"
issueType = "123"

#Checking if  jira file  exist or not
if(os.path.isfile(directoryUrl+'Errorlog_'+usrCurrentDate+".txt")):
    f= open(directoryUrl+'Errorlog_'+usrCurrentDate+".txt","r")
    if f.mode == 'r':
        contents =f.read()
    print(contents)

    #jira_url ="https://jiratest/"
    jira_url="https://ultidev/"
    jira_login="ulti"
    jira_password="quiznos"

    #Creating Jira issue for the failed processing
    createURL =  jira_url+"rest/api/2/issue"
    data = {"fields": {"project":{"id":projectid},"issuetype":{"id":issueType},"summary":"Aha Version Sync Error","description":contents,"customfield_12924":[{"id":"122483"}],"reporter": {"name": "ulti"}}}
    headers = {'Content-type': 'application/json'}
    r = requests.post(createURL, data=json.dumps(data), headers=headers, auth=(jira_login,jira_password), verify=False)
    data= r.json()
    id = data.get('id')
    print(data)

    #Transitioning JIRA to input status
    transititionURL = jira_url+"rest/api/2/issue/"+id+"/transitions"
    #data1 = {"transition":{"id":"41"},"fields":{"customfield_11246":{"id":"120701"}}}
    data1 = {"transition":{"id":"41"}}
    r = requests.post(transititionURL, data=json.dumps(data1), headers=headers, auth=(jira_login,jira_password), verify=False)
    print(data1)
    print(r.text)


    #Adding Error log to the JIRA
    attachmentURL =  jira_url+"rest/api/2/issue/"+id+"/attachments"
    files = {'file': open(directoryUrl+'Errorlog_'+usrCurrentDate+".txt", 'rb')}
    headers = {"X-Atlassian-Token": "nocheck"}
    r = requests.post(attachmentURL, auth=(jira_login,jira_password),files=files,headers=headers,verify=False)

    #Adding main log to the JIRA
    files = {'file': open(directoryUrl+'log_'+usrCurrentDate+".txt", 'rb')}
    r = requests.post(attachmentURL, auth=(jira_login,jira_password),files=files,headers=headers,verify=False)
    #Adding Aha Release CSV to the JIRA only if file exist
    if(os.path.isfile(filedirectoryURL+'AhaReleases_'+usrCurrentDate+'.csv')):
        files = {'file': open(filedirectoryURL+'AhaReleases_'+usrCurrentDate+'.csv', 'rb')}
        r = requests.post(attachmentURL, auth=(jira_login,jira_password),files=files,headers=headers,verify=False)


