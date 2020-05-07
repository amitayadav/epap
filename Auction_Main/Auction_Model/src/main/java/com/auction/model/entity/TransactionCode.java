package com.auction.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_code")
public class TransactionCode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Character transactionCode;
	private String transactionCodeMeaning;

	public TransactionCode() {
	}
	public TransactionCode(Character transactionCode) {
		this.transactionCode = transactionCode;
	}

	@Id
	@Column(name = "transaction_code", unique = true, nullable = false, length = 1)
	public Character getTransactionCode() {
		return this.transactionCode;
	}

	public void setTransactionCode(Character transactionCode) {
		this.transactionCode = transactionCode;
	}

	@Column(name = "transaction_code_meaning", nullable = false, length = 30)
	public String getTransactionCodeMeaning() {
		return this.transactionCodeMeaning;
	}

	public void setTransactionCodeMeaning(String transactionCodeMeaning) {
		this.transactionCodeMeaning = transactionCodeMeaning;
	}

}
