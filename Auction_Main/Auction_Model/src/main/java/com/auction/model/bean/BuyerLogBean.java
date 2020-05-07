package com.auction.model.bean;

import com.auction.model.entity.BuyerLog;

public class BuyerLogBean {

	private BuyerLogIdBean id;
	private AccountProfileBean accountProfileBean;
	private AccountProfileBean shipperAccountProfileBean;
	private DailyAuctionsBean dailyAuctionsBean;
	private Integer bidQuantity;
	private Integer minimumQuantity;
	private Float bidPrice;
	private Float buyerShippingCharge;
	private Integer executedQuantity;
	private Float executedPrice;

	public BuyerLogBean() {
	}

	public BuyerLogBean(BuyerLog entity) {
		if (null != entity) {
			this.id = new BuyerLogIdBean(entity.getId());
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.shipperAccountProfileBean = new AccountProfileBean(entity.getShipperAccountProfile());
			this.dailyAuctionsBean = new DailyAuctionsBean(entity.getDailyAuctions());
			this.bidQuantity = entity.getBidQuantity();
			this.minimumQuantity = ((entity.getMinimumQuantity() != null) ? entity.getMinimumQuantity() : 0);
			this.bidPrice = entity.getBidPrice();
			this.buyerShippingCharge = entity.getBuyerShippingCharge();
			this.executedQuantity = entity.getExecutedQuantity();
			this.executedPrice = entity.getExecutedPrice();
		}
	}

	public BuyerLogIdBean getId() {
		return this.id;
	}

	public void setId(BuyerLogIdBean id) {
		this.id = id;
	}

	public AccountProfileBean getAccountProfileBean() {
		return this.accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public AccountProfileBean getShipperAccountProfileBean() {
		return this.shipperAccountProfileBean;
	}

	public void setShipperAccountProfileBean(AccountProfileBean shipperAccountProfileBean) {
		this.shipperAccountProfileBean = shipperAccountProfileBean;
	}

	public DailyAuctionsBean getDailyAuctionsBean() {
		return this.dailyAuctionsBean;
	}

	public void setDailyAuctionsBean(DailyAuctionsBean dailyAuctionsBean) {
		this.dailyAuctionsBean = dailyAuctionsBean;
	}

	public Integer getBidQuantity() {
		return this.bidQuantity;
	}

	public void setBidQuantity(Integer bidQuantity) {
		this.bidQuantity = bidQuantity;
	}

	public Integer getMinimumQuantity() {
		return (null != this.minimumQuantity ? this.minimumQuantity : 0);
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	public Float getBidPrice() {
		return this.bidPrice;
	}

	public void setBidPrice(Float bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Float getBuyerShippingCharge() {
		return this.buyerShippingCharge;
	}

	public void setBuyerShippingCharge(Float buyerShippingCharge) {
		this.buyerShippingCharge = buyerShippingCharge;
	}

	public Integer getExecutedQuantity() {
		return executedQuantity;
	}

	public void setExecutedQuantity(Integer executedQuantity) {
		this.executedQuantity = executedQuantity;
	}

	public Float getExecutedPrice() {
		return executedPrice;
	}

	public void setExecutedPrice(Float executedPrice) {
		this.executedPrice = executedPrice;
	}

}