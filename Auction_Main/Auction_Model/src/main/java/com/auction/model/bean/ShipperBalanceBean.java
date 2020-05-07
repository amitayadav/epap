package com.auction.model.bean;

import java.math.BigDecimal;

import com.auction.model.entity.ShipperBalance;

public class ShipperBalanceBean {

	private ShipperBalanceIdBean id;
	private ShipperTransactionsBean shipperTransactionsBean;
	private String transactionDescription;
	private TransactionCodeBean transactionCodeBean;
	private BigDecimal debitCredit;
	private BigDecimal advanceBalance;
	private BigDecimal balance;
	private String comments;
	private AccountProfileBean createdAccountProfileBean;
	private AccountProfileBean updatedAccountProfileBean;
	private EpapBalanceBean epapBalanceBean;
	private Integer epapTransactionId;

	public ShipperBalanceBean() {

	}

	public ShipperBalanceBean(ShipperBalanceIdBean id) {
		this.id = id;
	}

	public ShipperBalanceBean(ShipperBalance entity) {
		if (null != entity) {
			this.id = new ShipperBalanceIdBean(entity.getId());
			this.shipperTransactionsBean = new ShipperTransactionsBean(entity.getShipperTransactions());
			this.transactionDescription = entity.getTransactionDescription();
			this.transactionCodeBean = new TransactionCodeBean(entity.getTransactionCode());
			this.debitCredit = entity.getDebitCredit();
			this.advanceBalance = entity.getAdvanceBalance();
			this.balance = entity.getBalance();
			this.comments = entity.getComments();
			this.createdAccountProfileBean = new AccountProfileBean(entity.getCreatedAccountProfile());
			this.updatedAccountProfileBean = new AccountProfileBean(entity.getUpdatedAccountProfile());
			this.epapBalanceBean = new EpapBalanceBean(entity.getEpapBalance());
			this.epapTransactionId = entity.getEpapTransactionId();
		}
	}

	public ShipperBalanceIdBean getId() {
		return id;
	}

	public void setId(ShipperBalanceIdBean id) {
		this.id = id;
	}

	public ShipperTransactionsBean getShipperTransactionsBean() {
		return shipperTransactionsBean;
	}

	public void setShipperTransactionsBean(ShipperTransactionsBean shipperTransactionsBean) {
		this.shipperTransactionsBean = shipperTransactionsBean;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public TransactionCodeBean getTransactionCodeBean() {
		return transactionCodeBean;
	}

	public void setTransactionCodeBean(TransactionCodeBean transactionCodeBean) {
		this.transactionCodeBean = transactionCodeBean;
	}

	public BigDecimal getDebitCredit() {
		return debitCredit;
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
		return balance;
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

	public AccountProfileBean getCreatedAccountProfileBean() {
		return createdAccountProfileBean;
	}

	public void setCreatedAccountProfileBean(AccountProfileBean createdAccountProfileBean) {
		this.createdAccountProfileBean = createdAccountProfileBean;
	}

	public AccountProfileBean getUpdatedAccountProfileBean() {
		return updatedAccountProfileBean;
	}

	public void setUpdatedAccountProfileBean(AccountProfileBean updatedAccountProfileBean) {
		this.updatedAccountProfileBean = updatedAccountProfileBean;
	}

	public EpapBalanceBean getEpapBalanceBean() {
		return epapBalanceBean;
	}

	public void setEpapBalanceBean(EpapBalanceBean epapBalanceBean) {
		this.epapBalanceBean = epapBalanceBean;
	}

	public Integer getEpapTransactionId() {
		return epapTransactionId;
	}

	public void setEpapTransactionId(Integer epapTransactionId) {
		this.epapTransactionId = epapTransactionId;
	}

}