package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.ShipperBalanceId;

public class ShipperBalanceIdBean {

	private AccountProfileBean accountProfileBean;
	private Date transactionDate;

	public ShipperBalanceIdBean() {

	}

	public ShipperBalanceIdBean(ShipperBalanceId entity) {
		if (null != entity) {
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.transactionDate = entity.getTransactionDate();
		}
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

}
