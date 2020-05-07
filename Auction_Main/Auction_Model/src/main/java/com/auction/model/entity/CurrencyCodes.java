package com.auction.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency_codes")
public class CurrencyCodes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String currencyCode;
	private String currencyName;

	public CurrencyCodes() {
	}

	public CurrencyCodes(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Id
	@Column(name = "currency_code", unique = true, nullable = false, length = 3)
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "currency_name", nullable = false, length = 20)
	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

}
