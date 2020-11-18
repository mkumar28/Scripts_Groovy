#!/usr/bin/python
  
import sys
import requests
import json
import datetime
import pycurl

#Open the error file in read more
documents = []
currentDT = datetime.datetime.now()
print(currentDT)
usrCurrentDate=currentDT.strftime("%Y-%m-%d")
#print("/home/appadmin/JIRAAhaVersionSync/Errorlog_"+usrCurrentDate+".txt")
directoryUrl = "/home/appadmin/JIRAAhaVersionSync/"
#Creating a text file
#f = open("error.txt","w+")


jira_url ="https://jirastage/rest/scriptrunner/latest/user/exec/"
payload = "/Users/manishk/Desktop/Script/StuckTask.py"
jira_login="manishk"
jira_password="sonuSHWETA@200688"
headers = {'Content-type': 'application/json'}
with open(payload) as fd: 
 r = requests.get(jira_url,data=fd.read(),headers=headers, auth=(jira_login,jira_password),verify=False)
 print(fd.read())
#data = r




