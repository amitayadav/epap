package com.auction.model.bean;

import com.auction.model.entity.CurrencyCodes;

public class CurrencyCodesBean {

	private String currencyCode;
	private String currencyName;

	public CurrencyCodesBean() {
	}

	public CurrencyCodesBean(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public CurrencyCodesBean(CurrencyCodes currencyCodes) {
		if (null != currencyCodes) {
			this.currencyCode = currencyCodes.getCurrencyCode();
			this.currencyName = currencyCodes.getCurrencyName();
		}
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

}
