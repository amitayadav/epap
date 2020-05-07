package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "account_status_codes", uniqueConstraints = @UniqueConstraint(columnNames = "account_status_code_meaning"))
public class AccountStatusCodes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Short accountStatusCode;
	private String accountStatusCodeMeaning;

	public AccountStatusCodes() {
	}

	public AccountStatusCodes(Short accountStatusCode) {
		this.accountStatusCode = accountStatusCode;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "account_status_code", unique = true, nullable = false)
	public Short getAccountStatusCode() {
		return this.accountStatusCode;
	}

	public void setAccountStatusCode(Short accountStatusCode) {
		this.accountStatusCode = accountStatusCode;
	}

	@Column(name = "account_status_code_meaning", unique = true, nullable = false, length = 10)
	public String getAccountStatusCodeMeaning() {
		return this.accountStatusCodeMeaning;
	}

	public void setAccountStatusCodeMeaning(String accountStatusCodeMeaning) {
		this.accountStatusCodeMeaning = accountStatusCodeMeaning;
	}

}
