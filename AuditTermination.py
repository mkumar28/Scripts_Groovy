#!/usr/bin/python3w
  
import sys
#import requests
import urllib
import json
import datetime
import os.path

jira_login="ulti"
jira_password="quiznos"
#jira_url="https://ultidev/rest/api/latest/search?jql=project+%3D+BIZ+AND+summary+%7E+Termination+AND+issuetype+%3D+%22Account+Request%22+AND+resolved+%3E%3D+2020-02-01+AND+resolved+%3C%3D+2020-07-18+ORDER+BY+created+DESC
#"

jql = "project = BIZ AND summary ~ Termination AND issuetype = \"Account Request\" AND resolved >= 2020-02-01 AND resolved <= 2020-07-18 ORDER BY created DESC"
#jqlUrl = urllib.quote_plus(jql)
#searchURL= jira_url+jqlUrl
'''headers = {'Content-type': 'application/json'}
r = requests.get(searchURL, headers=headers, auth=(jira_login,jira_password), verify=False)
data= r.json()
id = data.get('id')'''

txt =  "Termination Information\r\nCompany\tUSG - The Ultimate Software Group, Inc.\r\nFull Name\tJennifer Heffernan\r\nEmployee Number\t18617\r\nManager Name\tKevin Florez\r\nDepartment\t617 - Cloud Ops\r\nDivision\tTECH - Technology\r\nJob Code\tDVUS0901\r\nEmployee Type\tRegular - Part Time\r\nTermination Date\tMarch 06, 2020\r\nLast Day Worked\tMarch 06, 2020\r\nTime Zone\tAmerica/New_York\r\nDisable System Access Date\tMarch 06, 2020\r\nDisable System Access Local Time\t5:30 PM\r\nTermination\tEOD\r\nBldg Location\tVIRTUA\r\nAddress 1\t7619 Helen White Lane\r\nCity\tLand O Lakes\r\nState/Province\tFL\r\nZip/Postal Code\t34637\r\n\r\n\r\n"

x = txt.split("\r\n")

for item in x:
 if 'Termination Date' in item:
     print item