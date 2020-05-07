package com.auction.model.bean;

import com.auction.model.entity.TransactionCode;

public class TransactionCodeBean {

	private Character transactionCode;
	private String transactionCodeMeaning;

	public TransactionCodeBean() {
	}

	public TransactionCodeBean(Character transactionCode) {
		this.transactionCode = transactionCode;
	}

	public TransactionCodeBean(TransactionCode entity) {

		if (null != entity) {
			this.transactionCode = entity.getTransactionCode();
			this.transactionCodeMeaning = entity.getTransactionCodeMeaning();
		}
	}

	public Character getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(Character transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionCodeMeaning() {
		return transactionCodeMeaning;
	}

	public void setTransactionCodeMeaning(String transactionCodeMeaning) {
		this.transactionCodeMeaning = transactionCodeMeaning;
	}

}
