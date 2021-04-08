/**      
  * @文件名称: ModelEntity.java  
  * @类路径: com.dhc.rad.modules.act.entity  
  * @描述: TODO  
  * @作者：fangzr   
  * @时间：2015-11-7 下午01:57:12  
  * @版本：V1.0     
  */  
package com.dhc.rad.modules.act.entity;

import java.util.Date;

/**
 * @类功能说明： 
 * @类修改者： 
 * @修改日期： 
 * @修改说明：   
 * @公司名称：DHC  
 * @作者：fangzr   
 * @创建时间：2015-11-7 下午01:57:12  
 * @版本：V1.0 
 */
public class ModelEntity {
	private String id;
	private String key;
	private String name;
	private Integer version;
	private Date createTime;
	private Date lastUpdateTime;
	private String processDiagramResourceName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getProcessDiagramResourceName() {
		return processDiagramResourceName;
	}
	public void setProcessDiagramResourceName(String processDiagramResourceName) {
		this.processDiagramResourceName = processDiagramResourceName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

}
