package com.auction.model.entity;
// Generated Aug 16, 2018 10:36:21 AM by Hibernate Tools 5.2.10.Final

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
@Table(name = "vat_balance")
public class VatBalance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer vatBalanceId;
	private AccountProfile accountProfile;
	private AccountTypeCodes accountTypeCodes;
	private TransactionCode transactionCode;
	private Date transactionDate;
	private Integer transactionId;
	private String transactionDescription;
	private BigDecimal debitCredit;
	private BigDecimal balance;
	private String comments;

	public VatBalance() {
	}

	/**
	 * @param vatBalanceId
	 */
	public VatBalance(Integer vatBalanceId) { 
		this.vatBalanceId = vatBalanceId;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "vat_balance_id", unique = true, nullable = false)
	public Integer getVatBalanceId() {
		return this.vatBalanceId;
	}

	public void setVatBalanceId(Integer vatBalanceId) {
		this.vatBalanceId = vatBalanceId;
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

}
