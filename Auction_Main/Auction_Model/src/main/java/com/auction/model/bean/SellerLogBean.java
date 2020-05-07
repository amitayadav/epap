package com.auction.model.bean;

import com.auction.model.entity.SellerLog;

public class SellerLogBean {

	private SellerLogIdBean id;
	private AccountProfileBean accountProfileBean;
	private AccountProfileBean shipperAccountProfileBean;
	private DailyAuctionsBean dailyAuctionsBean;
	private Integer offerQuantity;
	private Integer minimumQuantity;
	private Integer availableQuantity;
	private Float offerPrice;
	private Float sellerShippingCharge;
	private Integer truckNumber;


	public SellerLogBean() {

	}

	public SellerLogBean(SellerLog entity) {

		if (null != entity) {
			this.id = new SellerLogIdBean(entity.getId());
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.shipperAccountProfileBean = new AccountProfileBean(entity.getShipperAccountProfile());
			this.dailyAuctionsBean = new DailyAuctionsBean(entity.getDailyAuctions());
			this.offerQuantity = entity.getOfferQuantity();
			this.minimumQuantity = ((entity.getMinimumQuantity() != null) ? entity.getMinimumQuantity() : 0);
			this.availableQuantity = entity.getAvailableQuantity();
			this.offerPrice = entity.getOfferPrice();
			this.sellerShippingCharge = entity.getSellerShippingCharge();
			this.truckNumber=entity.getTruckNumber();
		}
	}

	public SellerLogIdBean getId() {
		return id;
	}

	public void setId(SellerLogIdBean id) {
		this.id = id;
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public AccountProfileBean getShipperAccountProfileBean() {
		return shipperAccountProfileBean;
	}

	public void setShipperAccountProfileBean(AccountProfileBean shipperAccountProfileBean) {
		this.shipperAccountProfileBean = shipperAccountProfileBean;
	}

	public DailyAuctionsBean getDailyAuctionsBean() {
		return dailyAuctionsBean;
	}

	public void setDailyAuctionsBean(DailyAuctionsBean dailyAuctionsBean) {
		this.dailyAuctionsBean = dailyAuctionsBean;
	}

	public Integer getOfferQuantity() {
		return offerQuantity;
	}

	public void setOfferQuantity(Integer offerQuantity) {
		this.offerQuantity = offerQuantity;
	}

	public Integer getMinimumQuantity() {
		return this.minimumQuantity;
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
	
	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Float getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(Float offerPrice) {
		this.offerPrice = offerPrice;
	}

	public Float getSellerShippingCharge() {
		return sellerShippingCharge;
	}

	public void setSellerShippingCharge(Float sellerShippingCharge) {
		this.sellerShippingCharge = sellerShippingCharge;
	}
	
	public Integer getTruckNumber() {
		return truckNumber;
	}

	public void setTruckNumber(Integer truckNumber) {
		this.truckNumber = truckNumber;
	}

}