package com.auction.model.bean;

import java.math.BigDecimal;

import com.auction.model.entity.BuyerBalance;

public class BuyerBalanceBean {

	private BuyerBalanceIdBean id;
	private Integer transactionId;
	private Integer epapTransactionId;
	private EpapBalanceBean epapBalanceBean;
	private String transactionDescription;
	private TransactionCodeBean transactionCodeBean;
	private BigDecimal debitCredit;
	private BigDecimal advanceBalance;
	private BigDecimal balance;
	private String comments;
	private Integer reserved1;
	private String reserved2;

	public BuyerBalanceBean() {
	}

	public BuyerBalanceBean(BuyerBalance entity) {
		if (null != entity) {
			this.id = new BuyerBalanceIdBean(entity.getId());
			this.transactionId = entity.getTransactionId();
			this.epapTransactionId= entity.getEpapTransactionId();
			this.epapBalanceBean = new EpapBalanceBean(entity.getEpapBalance());
			this.transactionCodeBean = new TransactionCodeBean(entity.getTransactionCode());
			this.transactionDescription = entity.getTransactionDescription();
			this.debitCredit = entity.getDebitCredit();
			this.advanceBalance = entity.getAdvanceBalance();
			this.balance = entity.getBalance();
			this.comments = entity.getComments();
			this.reserved1 = entity.getReserved1();
			this.reserved2 = entity.getReserved2();
		}
	}

	public BuyerBalanceIdBean getId() {
		return id;
	}

	public void setId(BuyerBalanceIdBean id) {
		this.id = id;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public EpapBalanceBean getEpapBalanceBean() {
		return epapBalanceBean;
	}

	public void setEpapBalanceBean(EpapBalanceBean epapBalanceBean) {
		this.epapBalanceBean = epapBalanceBean;
	}

	public TransactionCodeBean getTransactionCodeBean() {
		return transactionCodeBean;
	}

	public void setTransactionCodeBean(TransactionCodeBean transactionCodeBean) {
		this.transactionCodeBean = transactionCodeBean;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
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

	public Integer getReserved1() {
		return reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public Integer getEpapTransactionId() {
		return epapTransactionId;
	}

	public void setEpapTransactionId(Integer epapTransactionId) {
		this.epapTransactionId = epapTransactionId;
	}

}
