package com.auction.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class BuyerBalanceId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private AccountProfile accountProfile;
	private Date transactionDate;

	public BuyerBalanceId() {

	}

	@ManyToOne(fetch = FetchType.EAGER)
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_date", nullable = false, length = 26)
	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

}
