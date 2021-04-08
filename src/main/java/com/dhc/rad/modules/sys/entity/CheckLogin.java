package com.dhc.rad.modules.sys.entity;

import java.util.List;

public class CheckLogin {
	private String id;
	private String password;
	private String result;
	private String msg;
	private List<User> userList;
	private String strOldPassword;
	private String strNewPassword;

	public String getStrOldPassword() {
		return strOldPassword;
	}

	public void setStrOldPassword(String strOldPassword) {
		this.strOldPassword = strOldPassword;
	}

	public String getStrNewPassword() {
		return strNewPassword;
	}

	public void setStrNewPassword(String strNewPassword) {
		this.strNewPassword = strNewPassword;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "DemoEntity [id=" + id + ", password=" + password + ", result=" + result + ", userList=" + userList
				+ "]";
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
