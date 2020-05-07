package com.auction.model.entity;

import java.math.BigDecimal;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "buyer_balance")
@AssociationOverrides({ @AssociationOverride(name = "id.accountProfile", joinColumns = @JoinColumn(name = "buyer_id")),
		@AssociationOverride(name = "id.transactionDate", joinColumns = @JoinColumn(name = "transaction_date")) })
public class BuyerBalance implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BuyerBalanceId id = new BuyerBalanceId();
	private BuyerTransactions buyerTransactions;
	private Integer transactionId;
	private Integer epapTransactionId;
	private EpapBalance epapBalance;
	private String transactionDescription;
	private TransactionCode transactionCode;
	private BigDecimal debitCredit;
	private BigDecimal advanceBalance;
	private BigDecimal balance;
	private String comments;
	private Integer reserved1;
	private String reserved2;

	public BuyerBalance() {
	}

	public BuyerBalance(BuyerBalanceId id) {
		super();
		this.id = id;
	}

	@EmbeddedId
	public BuyerBalanceId getId() {
		return this.id;
	}

	public void setId(BuyerBalanceId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transaction_code", nullable = false)
	public TransactionCode getTransactionCode() {
		return this.transactionCode;
	}

	public void setTransactionCode(TransactionCode transactionCode) {
		this.transactionCode = transactionCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id", insertable = false, updatable = false)
	public BuyerTransactions getBuyerTransactions() {
		return this.buyerTransactions;
	}

	public void setBuyerTransactions(BuyerTransactions buyerTransactions) {
		this.buyerTransactions = buyerTransactions;
	}

	@Column(name = "transaction_id")
	public Integer getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "epap_id", insertable = false, updatable = false)
	public EpapBalance getEpapBalance() {
		return epapBalance;
	}

	public void setEpapBalance(EpapBalance epapBalance) {
		this.epapBalance = epapBalance;
	}
	@Column(name = "epap_id")
	public Integer getEpapTransactionId() {
		return epapTransactionId;
	}

	public void setEpapTransactionId(Integer epapTransactionId) {
		this.epapTransactionId = epapTransactionId;
	}

	@Column(name = "transaction_description", nullable = false, length = 255)
	public String getTransactionDescription() {
		return this.transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	@Column(name = "debit_credit", nullable = false, precision = 10)
	public BigDecimal getDebitCredit() {
		return this.debitCredit;
	}

	public void setDebitCredit(BigDecimal debitCredit) {
		this.debitCredit = debitCredit;
	}
	
	@Column(name = "advance_balance",nullable = false, precision = 10)
	public BigDecimal getAdvanceBalance() {
		return advanceBalance;
	}

	public void setAdvanceBalance(BigDecimal advanceBalance) {
		this.advanceBalance = advanceBalance;
	}
	
	@Column(name = "balance", nullable = false, precision = 10)
	public BigDecimal getBalance() {
		return this.balance;
	}


	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	@Column(name = "comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "reserved1")
	public Integer getReserved1() {
		return this.reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	@Column(name = "reserved2", length = 30)
	public String getReserved2() {
		return this.reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
}
