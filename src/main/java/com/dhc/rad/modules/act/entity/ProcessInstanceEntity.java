/**      
  * @文件名称: ProcessInstancEntity.java  
  * @类路径: com.dhc.rad.modules.act.entity  
  * @描述: TODO  
  * @作者：fangzr   
  * @时间：2015-11-7 上午09:12:15  
  * @版本：V1.0     
  */  
package com.dhc.rad.modules.act.entity;

/**
 * @类功能说明： 
 * @类修改者： 
 * @修改日期： 
 * @修改说明：   
 * @公司名称：DHC  
 * @作者：fangzr   
 * @创建时间：2015-11-7 上午09:12:15  
 * @版本：V1.0 
 */
public class ProcessInstanceEntity {
	private String activityId;
	private String businessKey;
	private String id;
	private String parentId;
	private String processDefinitionId;
	private String processInstanceId;
	private String tenantId;
	private boolean suspended;
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
}
