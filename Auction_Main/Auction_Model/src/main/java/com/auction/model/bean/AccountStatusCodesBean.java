package com.auction.model.bean;

import com.auction.model.entity.AccountStatusCodes;

public class AccountStatusCodesBean {

	private Short accountStatusCode;
	private String accountStatusCodeMeaning;

	public AccountStatusCodesBean() {
	}
	public AccountStatusCodesBean(AccountStatusCodes entity) {
		this.accountStatusCode = entity.getAccountStatusCode();
		this.accountStatusCodeMeaning = entity.getAccountStatusCodeMeaning();
	}
	public AccountStatusCodesBean(Short accountStatusCode) {
		this.accountStatusCode = accountStatusCode;
	}

	public Short getAccountStatusCode() {
		return accountStatusCode;
	}

	public void setAccountStatusCode(Short accountStatusCode) {
		this.accountStatusCode = accountStatusCode;
	}

	public String getAccountStatusCodeMeaning() {
		return accountStatusCodeMeaning;
	}

	public void setAccountStatusCodeMeaning(String accountStatusCodeMeaning) {
		this.accountStatusCodeMeaning = accountStatusCodeMeaning;
	}

}
