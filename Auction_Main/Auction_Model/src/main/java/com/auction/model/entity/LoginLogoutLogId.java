package com.auction.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class LoginLogoutLogId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private LoginDetails loginDetails;
	private Date loginTimestamp;

	public LoginLogoutLogId() {

	}

	@ManyToOne(fetch = FetchType.EAGER)
	public LoginDetails getLoginDetails() {
		return this.loginDetails;
	}

	public void setLoginDetails(LoginDetails loginDetails) {
		this.loginDetails = loginDetails;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_timestamp", updatable = false, nullable = false)
	public Date getLoginTimestamp() {
		return this.loginTimestamp;
	}

	public void setLoginTimestamp(Date loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}

}
