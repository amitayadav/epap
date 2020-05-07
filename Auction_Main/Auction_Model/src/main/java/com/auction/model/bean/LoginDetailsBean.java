package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.LoginDetails;

public class LoginDetailsBean {

	private String loginUserid;
	private AccountProfileBean accountProfileBean;
	private AccountStatusCodesBean accountStatusCodesBean;
	private AccountTypeCodesBean accountTypeCodesBean;
	private String ownerEmail;
	private String publicName;
	private String password;
	private String passwordSalt;
	private Boolean emailVerified;
	private Integer failedLoginCount;
	private Integer loginCount;
	private Date creationTimestamp;
	private Date loginTimestamp;
	private Integer reserved1;
	private String reserved2;
	private Integer otp;
	private Date otpExpiredDate;
	private Integer otpCount;

	public LoginDetailsBean() {
	}

	public LoginDetailsBean(LoginDetails entity) {

		this.loginUserid = entity.getLoginUserid();
		this.accountProfileBean = (null == this.accountProfileBean ? new AccountProfileBean(entity.getAccountProfile()) : this.accountProfileBean);
		this.accountStatusCodesBean = new AccountStatusCodesBean(entity.getAccountStatusCodes());
		this.accountTypeCodesBean = new AccountTypeCodesBean(entity.getAccountTypeCodes());
		/* this.ownerEmail = entity.getO; */
		this.publicName = entity.getPublicName();
		this.password = entity.getPassword();
		this.passwordSalt = entity.getPasswordSalt();
		this.emailVerified = entity.isEmailVerified();
		this.failedLoginCount = entity.getFailedLoginCount();
		this.loginCount = entity.getLoginCount();
		this.creationTimestamp = entity.getCreationTimestamp();
		this.reserved1 = entity.getReserved1();
		this.reserved2 = entity.getReserved2();
		this.otp = entity.getOtp();
		this.otpExpiredDate = entity.getOtpExpiredDate();
		this.otpCount = entity.getOtpCount();

	}

	public LoginDetailsBean(String loginUserid) {
		this.loginUserid = loginUserid;
	}

	public String getLoginUserid() {
		return loginUserid;
	}

	public void setLoginUserid(String loginUserid) {
		this.loginUserid = loginUserid;
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public AccountStatusCodesBean getAccountStatusCodesBean() {
		return accountStatusCodesBean;
	}

	public void setAccountStatusCodesBean(AccountStatusCodesBean accountStatusCodesBean) {
		this.accountStatusCodesBean = accountStatusCodesBean;
	}

	public AccountTypeCodesBean getAccountTypeCodesBean() {
		return accountTypeCodesBean;
	}

	public void setAccountTypeCodesBean(AccountTypeCodesBean accountTypeCodesBean) {
		this.accountTypeCodesBean = accountTypeCodesBean;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getPublicName() {
		return publicName;
	}

	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Date getLoginTimestamp() {
		return loginTimestamp;
	}

	public void setLoginTimestamp(Date loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}

	public Integer getReserved1() {
		return reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public Date getOtpExpiredDate() {
		return otpExpiredDate;
	}

	public void setOtpExpiredDate(Date otpExpiredDate) {
		this.otpExpiredDate = otpExpiredDate;
	}

	public Integer getOtpCount() {
		return otpCount;
	}

	public void setOtpCount(Integer otpCount) {
		this.otpCount = otpCount;
	}

	

}
