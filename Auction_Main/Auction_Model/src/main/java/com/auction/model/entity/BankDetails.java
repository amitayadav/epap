package com.auction.model.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "bank_details")
public class BankDetails implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer accountId;
	private AccountProfile accountProfile;
	private String bankName;
	private String bankAccountNumber;
	private String iban;
	private BigDecimal availableBalance;
	private BigDecimal blockedAmount;
	private BigDecimal advanceBalance;
	private BigDecimal cashposition;

	public BankDetails() {
	}

	public BankDetails(Integer accountId) {
		this.accountId = accountId;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "accountProfile"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "account_id", unique = true, nullable = false)
	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@Column(name = "bank_name", nullable = false, length = 30)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "bank_account_number", nullable = false, length = 16)
	public String getBankAccountNumber() {
		return this.bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	@Column(name = "iban", nullable = false, length = 24)
	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	@Column(name = "available_balance", nullable = false, precision = 10)
	public BigDecimal getAvailableBalance() {
		return this.availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	@Column(name = "blocked_amount", nullable = false, precision = 10)
	public BigDecimal getBlockedAmount() {
		return this.blockedAmount;
	}

	public void setBlockedAmount(BigDecimal blockedAmount) {
		this.blockedAmount = blockedAmount;
	}

	@Column(name = "advance_balance", nullable = false, precision = 10)
	public BigDecimal getAdvanceBalance() {
		return advanceBalance;
	}

	public void setAdvanceBalance(BigDecimal advanceBalance) {
		this.advanceBalance = advanceBalance;
	}
	@Column(name = "cash_position",precision = 10)
	public BigDecimal getCashposition() {
		return cashposition;
	}

	public void setCashposition(BigDecimal cashposition) {
		this.cashposition = cashposition;
	}

}