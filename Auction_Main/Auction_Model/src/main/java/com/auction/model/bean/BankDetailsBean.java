package com.auction.model.bean;

import java.math.BigDecimal;

import com.auction.model.entity.BankDetails;

public class BankDetailsBean {

	private Integer accountId;
	private String bankName;
	private String bankAccountNumber;
	private String iban;
	private BigDecimal availableBalance;
	private BigDecimal blockedAmount;
	private BigDecimal advanceBalance;
	private BigDecimal cashposition;

	public BankDetailsBean() {
	}

	public BankDetailsBean(BankDetails entity) {
		this.accountId = entity.getAccountId();
		this.bankName = entity.getBankName();
		this.bankAccountNumber = entity.getBankAccountNumber();
		this.iban = entity.getIban();
		this.availableBalance = entity.getAvailableBalance();
		this.blockedAmount = entity.getBlockedAmount();
		this.advanceBalance = entity.getAdvanceBalance();
		this.cashposition = entity.getCashposition();
	}

	public BankDetailsBean(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/*
	 * public AccountProfileBean getAccountProfileBean() { return
	 * accountProfileBean; }
	 * 
	 * public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
	 * this.accountProfileBean = accountProfileBean; }
	 */

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public BigDecimal getBlockedAmount() {
		return blockedAmount;
	}

	public void setBlockedAmount(BigDecimal blockedAmount) {
		this.blockedAmount = blockedAmount;
	}

	public BigDecimal getAdvanceBalance() {
		return advanceBalance;
	}

	public void setAdvanceBalance(BigDecimal advanceBalance) {
		this.advanceBalance = advanceBalance;
	}

	public BigDecimal getCashposition() {
		return cashposition;
	}

	public void setCashposition(BigDecimal cashposition) {
		this.cashposition = cashposition;
	}

}
