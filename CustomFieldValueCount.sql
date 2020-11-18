select (select pname from project where id = ji.PROJECT) project,customvalue,cv.STRINGVALUE STRINGVALUE,  COUNT(*) AS  CountOfSelected
from
customfieldvalue  cv,
customfieldoption co,
jiraissue   ji
where co.CUSTOMFIELD = 11136//Update the customfield Id
and co.CUSTOMFIELD = cv.CUSTOMFIELD
and co.id = cv.STRINGVALUE
and co.CUSTOMFIELDCONFIG = 11434 //Update the field context if context is different
and co.disabled = 'N'
and cv.ISSUE = ji.ID
and ji.PROJECT = (select id from project where pkey = 'SD' )//Change the project key if project is different
and ji.CREATED between '2019-06-24' and '2020-06-24'
GROUP BY
  cv.STRINGVALUE,co.customvalue,project
HAVING
    COUNT(*) >=0
order by co.customvalue
