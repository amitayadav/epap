package com.auction.model.bean;

import com.auction.model.entity.SellerInfo;

public class SellerInfoBean {

	private Integer sellerId;
	private AccountProfileBean accountProfileBean;
	private String infoLine1;
	private String infoLine2;

	public SellerInfoBean() {
	}

	public SellerInfoBean(SellerInfo entity) {
		if (null != entity) {
			this.sellerId = entity.getSellerId();
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.infoLine1 = entity.getInfoLine1();
			this.infoLine2 = entity.getInfoLine2();
		}
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public String getInfoLine1() {
		return infoLine1;
	}

	public void setInfoLine1(String infoLine1) {
		this.infoLine1 = infoLine1;
	}

	public String getInfoLine2() {
		return infoLine2;
	}

	public void setInfoLine2(String infoLine2) {
		this.infoLine2 = infoLine2;
	}

}
