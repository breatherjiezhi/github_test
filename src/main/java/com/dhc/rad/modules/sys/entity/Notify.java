/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.entity;

import java.util.Date;

import com.dhc.rad.common.persistence.DataEntity;
import com.dhc.rad.modules.sys.entity.User;

/**
 * 通知实体类
 * @author lijunjie
 * @version 2015-11-21
 */
public class Notify extends DataEntity<Notify> {

	/**
	 * 阅读标记（0：未读；1：已读）
	 */
	public static final String READ_FLAG_YES = "1";
	public static final String READ_FLAG_NO = "0";
	
	private static final long serialVersionUID = 1L;
	private String   id;
	private String   type; //通知类型：1 系统通知 2 群发通知
	private User     receiver;//接受人
	private String   loginName;//接受人登录名
	private String   title;  //通知标题
	private String   content;//通知内容
	private String   readFlag; //是否已读
	private Date     readDate;//阅读时间
	private String   urgentFlag;//紧急标识：1 紧急，0或null 非紧急
	private User	  sender;//发送人
	private String   senderId;
//	private String   createBy;
//	private Date   createDate;
//	private String   updateBy;
//	private Date   updateDate;
//	private String   remarks;
//	private String   delFlag;

	public Notify() {
		super();
	}
	public Notify(String id) {
		super(id);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	public Date getReadDate() {
		return readDate;
	}
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	public String getUrgentFlag() {
		return urgentFlag;
	}
	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
}
