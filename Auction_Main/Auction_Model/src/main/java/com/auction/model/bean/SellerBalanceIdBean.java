package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.SellerBalanceId;

public class SellerBalanceIdBean {

	private AccountProfileBean accountProfileBean;
	private Date transactionDate;

	public SellerBalanceIdBean() {

	}                     

	public SellerBalanceIdBean(SellerBalanceId sellerBalanceId) {
		this.accountProfileBean = new AccountProfileBean(sellerBalanceId.getAccountProfile());
		this.transactionDate = sellerBalanceId.getTransactionDate();
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
