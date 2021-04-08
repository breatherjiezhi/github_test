package com.dhc.rad.modules.sys.entity;


/*
 * 
 * @author hj
 * 
 * 用于接收键值对形式参数
 */
public class CodeValue {
	
	public CodeValue(){}
	
	public CodeValue(String code, String value){
		this.code = code;
		this.value = value;
		
	}
	
	public CodeValue(String code, String value,String key){
		this.code = code;
		this.value = value;
		this.key = key;
	}
	
	private String key;
	
	private String code;
	
	private String value;
	
	private String type;
	
	private String parentCode;

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
