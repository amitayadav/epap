package com.auction.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account_type_codes")
public class AccountTypeCodes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Character accountType;
	private String accountTypeMeaning;

	public AccountTypeCodes() {
	}

	public AccountTypeCodes(Character accountType) {
		this.accountType = accountType;
	}

	@Id
	@Column(name = "account_type", unique = true, nullable = false, length = 1)
	public Character getAccountType() {
		return this.accountType;
	}

	public void setAccountType(Character accountType) {
		this.accountType = accountType;
	}

	@Column(name = "account_type_meaning", nullable = false, length = 20)
	public String getAccountTypeMeaning() {
		return this.accountTypeMeaning;
	}

	public void setAccountTypeMeaning(String accountTypeMeaning) {
		this.accountTypeMeaning = accountTypeMeaning;
	}

}