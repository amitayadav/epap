package com.auction.model.bean;

import com.auction.model.entity.AccountTypeCodes;

public class AccountTypeCodesBean {

	private Character accountType;
	private String accountTypeMeaning;
	
	public AccountTypeCodesBean() {
	}
	public AccountTypeCodesBean(AccountTypeCodes entity) {
		if (null != entity) {
		this.accountType = entity.getAccountType();
		this.accountTypeMeaning = entity.getAccountTypeMeaning();
		}
	}
	public AccountTypeCodesBean(Character accountType) {
		super();
		this.accountType = accountType;
	}
	public Character getAccountType() {
		return accountType;
	}
	public void setAccountType(Character accountType) {
		this.accountType = accountType;
	}
	public String getAccountTypeMeaning() {
		return accountTypeMeaning;
	}
	public void setAccountTypeMeaning(String accountTypeMeaning) {
		this.accountTypeMeaning = accountTypeMeaning;
	}
	
	
}
