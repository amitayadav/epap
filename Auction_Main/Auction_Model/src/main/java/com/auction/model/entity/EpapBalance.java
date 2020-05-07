package com.auction.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "epap_balance")
public class EpapBalance implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer epapBalanceId;
	private AccountProfile accountProfile;
	private AccountTypeCodes accountTypeCodes;
	private TransactionCode transactionCode;
	private Date transactionDate;
	private Integer transactionId;
	private String transactionDescription;
	private BigDecimal debitCredit;
	private BigDecimal advanceBalance;
	private BigDecimal balance;
	private BigDecimal cashposition;
	private String comments;

	public EpapBalance() {
	}

	public EpapBalance(Integer epapBalanceId) {
		this.epapBalanceId = epapBalanceId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "epap_balance_id", unique = true, nullable = false)
	public Integer getEpapBalanceId() {
		return this.epapBalanceId;
	}

	public void setEpapBalanceId(Integer epapBalanceId) {
		this.epapBalanceId = epapBalanceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_type", nullable = false)
	public AccountTypeCodes getAccountTypeCodes() {
		return this.accountTypeCodes;
	}

	public void setAccountTypeCodes(AccountTypeCodes accountTypeCodes) {
		this.accountTypeCodes = accountTypeCodes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_code", nullable = false)
	public TransactionCode getTransactionCode() {
		return this.transactionCode;
	}

	public void setTransactionCode(TransactionCode transactionCode) {
		this.transactionCode = transactionCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_date", nullable = false, length = 19)
	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Column(name = "transaction_id")
	public Integer getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	@Column(name = "transaction_description", nullable = false, length = 100)
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
	@Column(name = "cash_position", nullable = false, precision = 10)
	public BigDecimal getCashposition() {
		return cashposition;
	}

	public void setCashposition(BigDecimal cashposition) {
		this.cashposition = cashposition;
	}

	@Column(name = "comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}