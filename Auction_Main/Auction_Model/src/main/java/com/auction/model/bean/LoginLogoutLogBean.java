package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.LoginLogoutLog;

public class LoginLogoutLogBean {

	private LoginLogoutLogIdBean id;
	private Date logoutTimestamp;

	public LoginLogoutLogBean() {

	}

	public LoginLogoutLogBean(LoginLogoutLogIdBean id) {
		this.id = id;
	}

	public LoginLogoutLogBean(LoginLogoutLog entity) {
		if (null != entity) {
			this.id = new LoginLogoutLogIdBean(entity.getId());
			this.logoutTimestamp = entity.getLogoutTimestamp();
		}
	}

	public LoginLogoutLogIdBean getId() {
		return id;
	}

	public void setId(LoginLogoutLogIdBean id) {
		this.id = id;
	}

	public Date getLogoutTimestamp() {
		return logoutTimestamp;
	}

	public void setLogoutTimestamp(Date logoutTimestamp) {
		this.logoutTimestamp = logoutTimestamp;
	}
}
