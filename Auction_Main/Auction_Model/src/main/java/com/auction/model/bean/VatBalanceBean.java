package com.auction.model.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.auction.model.entity.VatBalance;

public class VatBalanceBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer vatBalanceId;
	private AccountProfileBean accountProfileBean;
	private AccountTypeCodesBean accountTypeCodesBean;
	private TransactionCodeBean transactionCodebean;
	private Date transactionDate;
	private Integer transactionId;
	private String transactionDescription;
	private BigDecimal debitCredit;
	private BigDecimal balance;
	private String comments;

	public VatBalanceBean() {
	}

	public VatBalanceBean(Integer vatBalanceId) {
		this.vatBalanceId = vatBalanceId;
	}

	public VatBalanceBean(VatBalance entity) {
		if (null != entity) {
			this.vatBalanceId = entity.getVatBalanceId();
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.accountTypeCodesBean = new AccountTypeCodesBean(entity.getAccountTypeCodes());
			this.transactionCodebean = new TransactionCodeBean(entity.getTransactionCode());
			this.transactionDate = entity.getTransactionDate();
			this.transactionId = entity.getTransactionId();
			this.transactionDescription = entity.getTransactionDescription();
			this.debitCredit = entity.getDebitCredit();
			this.balance = entity.getBalance();
			this.comments =entity.getComments();
		}
	}

	public Integer getVatBalanceId() {
		return this.vatBalanceId;
	}

	public void setVatBalanceId(Integer vatBalanceId) {
		this.vatBalanceId = vatBalanceId;
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public AccountTypeCodesBean getAccountTypeCodesBean() {
		return accountTypeCodesBean;
	}

	public void setAccountTypeCodesBean(AccountTypeCodesBean accountTypeCodesBean) {
		this.accountTypeCodesBean = accountTypeCodesBean;
	}

	public TransactionCodeBean getTransactionCodebean() {
		return transactionCodebean;
	}

	public void setTransactionCodebean(TransactionCodeBean transactionCodebean) {
		this.transactionCodebean = transactionCodebean;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Integer getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionDescription() {
		return this.transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public BigDecimal getDebitCredit() {
		return this.debitCredit;
	}

	public void setDebitCredit(BigDecimal debitCredit) {
		this.debitCredit = debitCredit;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}