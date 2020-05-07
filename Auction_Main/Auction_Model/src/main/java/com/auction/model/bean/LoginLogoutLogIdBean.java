package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.LoginLogoutLogId;

public class LoginLogoutLogIdBean {

	private LoginDetailsBean loginDetailsBean;
	private Date loginTimestamp;

	public LoginLogoutLogIdBean() {

	}

	public LoginLogoutLogIdBean(LoginLogoutLogId entity) {
		this.loginDetailsBean = new LoginDetailsBean(entity.getLoginDetails());
		this.loginTimestamp = entity.getLoginTimestamp();
	}

	public LoginDetailsBean getLoginDetailsBean() {
		return loginDetailsBean;
	}

	public void setLoginDetailsBean(LoginDetailsBean loginDetailsBean) {
		this.loginDetailsBean = loginDetailsBean;
	}

	public Date getLoginTimestamp() {
		return loginTimestamp;
	}

	public void setLoginTimestamp(Date loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}

}
