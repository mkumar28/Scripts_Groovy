SELECT pj.pname  PROJECT_NAME,
       concat(pj.pkey, '-', ji.issuenum) ISSUEID,
       (SELECT pname FROM issuetype WHERE id = ji.issuetype) ISSUETYPE,
       (SELECT Stuff((SELECT ';' + co.customvalue
                      FROM customfieldvalue cv,
                           customfieldoption co
                      WHERE cv.CUSTOMFIELD = 11554
                        AND cv.issue = ji.id
                        AND cv.STRINGVALUE = co.ID FOR XML PATH ('')), 1, 1, '') [Product]
        FROM customfieldvalue cv
        WHERE cv.issue = ji.id
          AND cv.CUSTOMFIELD = 11554
        GROUP BY cv.issue, cv.CUSTOMFIELD)  CHANGE_TYPE,
       (SELECT STRINGVALUE FROM customfieldvalue cv WHERE CUSTOMFIELD = 11544 AND cv.issue = ji.id) IMPLEMENTER,
       ji.RESOLUTIONDATE ,
       (SELECT DATEVALUE FROM customfieldvalue cv WHERE CUSTOMFIELD = 11547 AND cv.issue = ji.id)   CHANGE_END_TIME,
       st.pname                                                                                     STATUS
FROM project pj,
     jiraissue ji,
     issuestatus st
WHERE pj.pkey = 'SD'
  AND ji.project = pj.ID
  AND ji.issuestatus = st.id
  AND ji.issuetype in (SELECT ID FROM issuetype WHERE pname IN ('Product release', 'Service Request'))
  AND ji.issuestatus = '6'
  AND ji.id in (SELECT ji.id FROM customfieldvalue cv  where CUSTOMFIELD = 11547 AND  ji.ID = cv.ISSUE AND RESOLUTIONDATE >cv.DATEVALUE)
  AND ji.id IN (SELECT ji.ID
                FROM customfieldvalue cv,
                     customfieldoption co
                WHERE cv.CUSTOMFIELD = 11554
                  AND cv.CUSTOMFIELD = co.CUSTOMFIELD
                  AND co.customvalue IN
                      ('Custom - Onboarding', 'Custom - eModule', 'Custom - CTK', 'Deploy - Application Upgrade (V14)',
                       'Custom - BackOffice', 'Deploy - Weekly One-off assignment', 'App owner - Deploy package/cube',
                       'Custom - Reports',
                       'Year-End Tax Update', 'Custom - REC', 'Custom - non-standard', 'Deploy - Software Patch',
                       'Deploy - Application Install',
                       'Deploy - non-standard', 'Deploy - eModule', 'Deploy - Application Upgrade',
                       'App owner - non-standard', 'Custom - Upgrade', 'Custom - Patch', 'Custom - Web',
                       'Custom - UTA', 'Custom - UPM', 'App owner - Application Maintenance',
                       'Deploy - Q4 Update', 'Deploy - Year-End Tax Update', 'Application One-Off',
                       'Application Upgrade', 'Application Install', 'App owner - Application one-off',
                       'App owner - Application install', 'App owner - Apply patch', 'App owner - Application upgrade',
                       'App owner - Configuration change', 'Ultipro - Quarterly Updates', 'Deploy - Quarterly Updates')
                  AND cv.issue = ji.id)
  AND ji.id IN (SELECT ji.id
                FROM customfieldvalue cv
                WHERE cv.CUSTOMFIELD = 11544
                  AND cv.STRINGVALUE in ('mariap', 'josem', 'darnells', 'matthewr', 'yuria', 'parthp', 'santiagoal',
                                         'santiagoa', 'matthewgo', 'anthonyma', 'christ', 'jennifergo', 'michaelka',
                                         'katherinemi', 'juang', 'aakasht')
                  AND cv.issue = ji.id)
  AND JI.RESOLUTIONDATE BETWEEN '2019-01-01 00:00:00.000' AND '2019-12-31 00:00:00.000'