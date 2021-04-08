/**      
  * @文件名称: DeployProcess.java  
  * @类路径: com.dhc.rad.modules.act.service  
  * @描述: TODO  
  * @作者：fangzr   
  * @时间：2015-11-4 下午08:13:00  
  * @版本：V1.0     
  */  
package com.dhc.rad.modules.act.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @类功能说明： 
 * @类修改者： 
 * @修改日期： 
 * @修改说明：   
 * @公司名称：DHC  
 * @作者：fangzr   
 * @创建时间：2015-11-4 下午08:13:00  
 * @版本：V1.0 
 */
public class DeployProcess {
	
	private String processId;
	private String processKey;
	private String processName;
	private int processVersion;
	private String processResourceName;
	private String processDiagramResourceName;
	private Date deploymentDeploymentTime;
	private boolean processSuspended;
	private String processDeploymentId;
	private String processCategory;
	
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getProcessKey() {
		return processKey;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public int getProcessVersion() {
		return processVersion;
	}
	public void setProcessVersion(int processVersion) {
		this.processVersion = processVersion;
	}
	public String getProcessResourceName() {
		return processResourceName;
	}
	public void setProcessResourceName(String processResourceName) {
		this.processResourceName = processResourceName;
	}
	public String getProcessDiagramResourceName() {
		return processDiagramResourceName;
	}
	public void setProcessDiagramResourceName(String processDiagramResourceName) {
		this.processDiagramResourceName = processDiagramResourceName;
	}
	public Date getDeploymentDeploymentTime() {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		  String dateString = formatter.format(deploymentDeploymentTime);  
		  try {
			deploymentDeploymentTime = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deploymentDeploymentTime;
	}
	public void setDeploymentDeploymentTime(Date deploymentDeploymentTime) {
		this.deploymentDeploymentTime = deploymentDeploymentTime;
	}
	public boolean isProcessSuspended() {
		return processSuspended;
	}
	public void setProcessSuspended(boolean processSuspended) {
		this.processSuspended = processSuspended;
	}
	public String getProcessDeploymentId() {
		return processDeploymentId;
	}
	public void setProcessDeploymentId(String processDeploymentId) {
		this.processDeploymentId = processDeploymentId;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getProcessCategory() {
		return processCategory;
	}
	public void setProcessCategory(String processCategory) {
		this.processCategory = processCategory;
	}
	
}
