public void moveIssueToAnotherProject(String newProjectName, IssueType  newIssueType, Status newStatus, MutableIssue targetIssue, String IPversionToSet) {
        try {
            log.warn("targetIssue: " + targetIssue )
            log.warn("newStatus: " + newStatus.getName() )
            
            Project projectObj=getProjectFromName(newProjectName)
            log.warn("newProjectName: " + projectObj )
            CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager()
            VersionManager versionmanger =ComponentAccessor.getVersionManager();
            CustomField cf = customFieldManager.getCustomFieldObjectByName("Version")
            log.warn("newversion: " + IPversionToSet)
            Version newversion=versionmanger.getVersion(projectObj.id, IPversionToSet)
            Collection&lt;Version&gt; newverCol=new ArrayList&lt;Version&gt;()
            newverCol.add(newversion)
            log.warn("newversionObject: " + newverCol)
//            IssueChangeHolder changeHolder = new DefaultIssueChangeHolder()
            if(cf!=null)
            {
//                Object value = cf.getValue(targetIssue);
//                ModifiedValue modifiedValue=new ModifiedValue(value, newverCol)
//                cf.updateValue(null, targetIssue, modifiedValue,changeHolder)
                targetIssue.setCustomFieldValue(cf, newverCol)
                def cfv = targetIssue.getCustomFieldValue(cf)
                log.warn("customfield value after change: " + cfv)
            }
            MoveIssueBean moveIssueBean = new MoveIssueBean(ComponentAccessor.getConstantsManager(), ComponentAccessor.getProjectManager())
            log.warn("projectObj.id: " + projectObj.id )
            moveIssueBean.getFieldValuesHolder().put(IssueFieldConstants.PROJECT, projectObj.id)
            log.warn("newIssueType.id: " + newIssueType.id )
            moveIssueBean.getFieldValuesHolder().put(IssueFieldConstants.ISSUE_TYPE, newIssueType.id)
            
    
            
            
            log.warn("curIssue.id: " + targetIssue.getId())
            moveIssueBean.setIssueId(targetIssue.getId())
            log.warn("newStatus.id : " + newStatus.id )
            moveIssueBean.setTargetStatusId(newStatus.id )
            log.warn("curIssue.key : " + targetIssue.getKey())
            moveIssueBean.setSourceIssueKey(targetIssue.getKey())
            log.warn("targetIssue: " + targetIssue)
            moveIssueBean.setUpdatedIssue(targetIssue)
            ActionContext.getSession().put(SessionKeys.MOVEISSUEBEAN, moveIssueBean)
            log.warn ("after action context move Issue Beans")
            MoveIssueUpdateFields moveIssueUpdateFields = new MoveIssueUpdateFields(
                    ComponentAccessor.getSubTaskManager(),
                    ComponentAccessor.getConstantsManager(),
                    ComponentAccessor.getWorkflowManager(),
                    ComponentAccessor.getFieldManager(),
                    ComponentAccessor.getFieldLayoutManager(),
                    ComponentAccessor.getIssueFactory(),
                    ComponentAccessor.getFieldScreenRendererFactory(),
                    ComponentAccessor.getComponentOfType(CommentService.class),
                    ComponentAccessor.getComponentOfType(IssueSecurityHelper.class),
                    ComponentAccessor.getUserUtil()
            )
            log.warn ("after moveIssueUpdateFields")
            MoveIssueConfirm moveIssueConfirm = new MoveIssueConfirm(
                    ComponentAccessor.getSubTaskManager(),
                    ComponentAccessor.getComponentOfType(AttachmentManager.class),
                    ComponentAccessor.getConstantsManager(),
                    ComponentAccessor.getWorkflowManager(),
                    ComponentAccessor.getFieldManager(),
                    ComponentAccessor.getFieldLayoutManager(),
                    ComponentAccessor.getIssueFactory(),
                    ComponentAccessor.getFieldScreenRendererFactory(),
                    ComponentAccessor.getComponentOfType(CommentService.class),
                    ComponentAccessor.getComponentOfType(IssueSecurityHelper.class),
                    ComponentAccessor.getIssueManager(),
                    ComponentAccessor.getUserUtil()
            )
            log.warn ("after moveIssueConfirm")
            JiraSystemProperties.getInstance()
            log.warn ("after JiraSystemProperties")
            moveIssueUpdateFields.setId(targetIssue.getId())
            log.warn ("after moveIssueUpdateFields.setId(targetIssue.getId())")
            Method privateUpdateFieldsDoExecute = MoveIssueUpdateFields.class.getDeclaredMethod("doExecute")
            log.warn ("after privateUpdateFieldsDoExecute")
            privateUpdateFieldsDoExecute.setAccessible(true)
            log.warn ("after setAccessible")
            privateUpdateFieldsDoExecute.invoke(moveIssueUpdateFields)
            log.warn ("after invoke")
            moveIssueConfirm.setId(targetIssue.getId())
            log.warn ("after setId")
            Method privateIssueConfirmDoExecute = MoveIssueConfirm.class.getDeclaredMethod("doExecute")
            log.warn ("after privateIssueConfirmDoExecute")
            privateIssueConfirmDoExecute.setAccessible(true)
            log.warn ("after privateIssueConfirmDoExecute.setAccessible(true)")
            privateIssueConfirmDoExecute.invoke(moveIssueConfirm)
            log.warn ("after privateIssueConfirmDoExecute.invoke(moveIssueConfirm)")
            CoreTransactionUtil.commit(true)
            log.warn ("after commit")
        }
        catch (Exception e) {
            log.error("move issue failed " + e.message)
        }
    }