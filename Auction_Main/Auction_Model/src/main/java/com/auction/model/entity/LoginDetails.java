package com.auction.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "login_details")
public class LoginDetails implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String loginUserid;
	private AccountProfile accountProfile;
	private AccountStatusCodes accountStatusCodes;
	private AccountTypeCodes accountTypeCodes;
	private String publicName;
	private String password;
	private String passwordSalt;
	private Boolean emailVerified;
	private Integer failedLoginCount;
	private Integer loginCount;
	private Date creationTimestamp;
	private Integer reserved1;
	private String reserved2;
	private Integer otp;
	private Date otpExpiredDate;
	private Integer otpCount;


	public LoginDetails() {
	}

	public LoginDetails(String loginUserid) {
		this.loginUserid = loginUserid;
	}

	@Id
	@Column(name = "login_userid", unique = true, nullable = false, length = 40)
	public String getLoginUserid() {
		return this.loginUserid;
	}

	public void setLoginUserid(String loginUserid) {
		this.loginUserid = loginUserid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_status", nullable = false)
	public AccountStatusCodes getAccountStatusCodes() {
		return this.accountStatusCodes;
	}

	public void setAccountStatusCodes(AccountStatusCodes accountStatusCodes) {
		this.accountStatusCodes = accountStatusCodes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_type", nullable = false)
	public AccountTypeCodes getAccountTypeCodes() {
		return this.accountTypeCodes;
	}

	public void setAccountTypeCodes(AccountTypeCodes accountTypeCodes) {
		this.accountTypeCodes = accountTypeCodes;
	}

	@Column(name = "public_name", nullable = false, length = 20)
	public String getPublicName() {
		return this.publicName;
	}

	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "password_salt", nullable = false)
	public String getPasswordSalt() {
		return this.passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	@Column(name = "email_verified")
	public Boolean isEmailVerified() {
		return this.emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	@Column(name = "failed_login_count")
	public Integer getFailedLoginCount() {
		return this.failedLoginCount;
	}

	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	@Column(name = "login_count")
	public Integer getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_timestamp", length = 19)
	public Date getCreationTimestamp() {
		return this.creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	@Column(name = "reserved1")
	public Integer getReserved1() {
		return this.reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	@Column(name = "reserved2", length = 30)
	public String getReserved2() {
		return this.reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	@Column(name = "otp")
	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "otp_expired_date", length = 19)
	public Date getOtpExpiredDate() {
		return otpExpiredDate;
	}

	public void setOtpExpiredDate(Date otpExpiredDate) {
		this.otpExpiredDate = otpExpiredDate;
	}

	@Column(name = "otp_count")
	public Integer getOtpCount() {
		return otpCount;
	}

	public void setOtpCount(Integer otpCount) {
		this.otpCount = otpCount;
	}
}
