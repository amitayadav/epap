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
@Table(name = "shipper_balance")
@AssociationOverrides({ @AssociationOverride(name = "id.accountProfile", joinColumns = @JoinColumn(name = "shipper_id")),

		@AssociationOverride(name = "id.transactionDate", joinColumns = @JoinColumn(name = "transaction_date")) })
public class ShipperBalance implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private ShipperBalanceId id;
	private ShipperTransactions shipperTransactions;
	private String transactionDescription;
	private TransactionCode transactionCode;
	private BigDecimal debitCredit;
	private BigDecimal advanceBalance;
	private BigDecimal balance;
	private String comments;
	private AccountProfile createdAccountProfile;
	private AccountProfile updatedAccountProfile;
	private EpapBalance epapBalance;
	private Integer epapTransactionId;
	public ShipperBalance() {
	}

	public ShipperBalance(ShipperBalanceId id) {
		super();
		this.id = id;
	}

	@EmbeddedId
	public ShipperBalanceId getId() {
		return this.id;
	}

	public void setId(ShipperBalanceId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id")
	public ShipperTransactions getShipperTransactions() {
		return this.shipperTransactions;
	}

	public void setShipperTransactions(ShipperTransactions shipperTransactions) {
		this.shipperTransactions = shipperTransactions;
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

	@Column(name = "advance_balance", nullable = false, precision = 10)
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
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_code", nullable = false)
	public TransactionCode getTransactionCode() {
		return this.transactionCode;
	}

	public void setTransactionCode(TransactionCode transactionCode) {
		this.transactionCode = transactionCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdby", nullable = false)
	public AccountProfile getCreatedAccountProfile() {
		return this.createdAccountProfile;
	}

	public void setCreatedAccountProfile(AccountProfile createdAccountProfile) {
		this.createdAccountProfile = createdAccountProfile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updatedby")
	public AccountProfile getUpdatedAccountProfile() {
		return this.updatedAccountProfile;
	}

	public void setUpdatedAccountProfile(AccountProfile updatedAccountProfile) {
		this.updatedAccountProfile = updatedAccountProfile;
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

}