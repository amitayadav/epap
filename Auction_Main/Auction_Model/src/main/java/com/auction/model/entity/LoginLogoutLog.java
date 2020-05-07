package com.auction.model.entity;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "login_logout_log")
@AssociationOverrides({ @AssociationOverride(name = "id.loginDetails", joinColumns = @JoinColumn(name = "login_userid")),
		@AssociationOverride(name = "id.loginTimestamp", joinColumns = @JoinColumn(name = "login_timestamp")) })
public class LoginLogoutLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private LoginLogoutLogId id;
	private Date logoutTimestamp;

	public LoginLogoutLog() {
	}

	public LoginLogoutLog(LoginLogoutLogId id) {
		super();
		this.id = id;
	}

	@EmbeddedId
	public LoginLogoutLogId getId() {
		return this.id;
	}

	public void setId(LoginLogoutLogId id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logout_timestamp")
	public Date getLogoutTimestamp() {
		return this.logoutTimestamp;
	}

	public void setLogoutTimestamp(Date logoutTimestamp) {
		this.logoutTimestamp = logoutTimestamp;
	}

}
