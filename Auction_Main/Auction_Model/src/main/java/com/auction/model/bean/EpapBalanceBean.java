package com.auction.model.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.auction.model.entity.EpapBalance;

public class EpapBalanceBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer epapBalanceId;
	private AccountProfileBean accountProfileBean;
	private AccountTypeCodesBean accountTypeCodesBean;
	private TransactionCodeBean transactionCodebean;
	private Date transactionDate;
	private Integer transactionId;
	private String transactionDescription;
	private BigDecimal debitCredit;
	private BigDecimal advanceBalance;
	private BigDecimal balance;
	private BigDecimal cashposition;
	private String comments;

	public EpapBalanceBean() {
	}

	public EpapBalanceBean(Integer epapBalanceId) {
		this.epapBalanceId = epapBalanceId;
	}

	public EpapBalanceBean(EpapBalance entity) {
		if (null != entity) {
			this.epapBalanceId = entity.getEpapBalanceId();
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.accountTypeCodesBean = new AccountTypeCodesBean(entity.getAccountTypeCodes());
			this.transactionCodebean = new TransactionCodeBean(entity.getTransactionCode());
			this.transactionDate = entity.getTransactionDate();
			this.transactionId = entity.getTransactionId();
			this.transactionDescription = entity.getTransactionDescription();
			this.debitCredit = entity.getDebitCredit();
			this.advanceBalance = entity.getAdvanceBalance();
			this.balance = entity.getBalance();
			this.cashposition = entity.getCashposition();
			this.comments = entity.getComments();
		}
	}

	public Integer getEpapBalanceId() {
		return this.epapBalanceId;
	}

	public void setEpapBalanceId(Integer epapBalanceId) {
		this.epapBalanceId = epapBalanceId;
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

	public BigDecimal getAdvanceBalance() {
		return advanceBalance;
	}

	public void setAdvanceBalance(BigDecimal advanceBalance) {
		this.advanceBalance = advanceBalance;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getCashposition() {
		return cashposition;
	}

	public void setCashposition(BigDecimal cashposition) {
		this.cashposition = cashposition;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}