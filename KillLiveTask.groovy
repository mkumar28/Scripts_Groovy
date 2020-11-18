                            import com.atlassian.jira.component.ComponentAccessor
                            import com.atlassian.jira.task.TaskManagerImpl
                            
                            List<String> queue = new ArrayList<String>()
                            def TaskManager = ComponentAccessor.getComponent(TaskManagerImpl)
                            def tasks = TaskManager.getLiveTasks()

                            for(key in tasks){
                                
                                queue.add('Taskid: ' + key.getTaskId() + ' Description: ' + key.getDescription() + ' Start Time:' + key.getStartedTimestamp() + ' Username: ' + key.getUserName()+'Finished:'+ key.getResult())
                                if( key.getStartedTimestamp() == null){
                                    TaskManager.removeTask(key.getTaskId())
                                }
                                
                            }
