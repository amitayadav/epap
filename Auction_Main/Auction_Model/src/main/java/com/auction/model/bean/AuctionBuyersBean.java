package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.AuctionBuyers;

public class AuctionBuyersBean {

	private Integer auctionBuyersId;
	private AccountLocationsBean accountLocationsBean;
	private AccountProfileBean accountProfileBean;
	private AccountProfileBean shipperAccountProfileBean;
	private AuctionSellersBean auctionSellersBean;
	private DailyAuctionsBean dailyAuctionsBean;
	private Boolean partialAllowed;
	private Integer bidQuantity;
	private Integer minimumQuantity;
	private Integer executedQuantity;
	private Float bidPrice;
	private Float executedPrice;
	private Float royaltyValue;
	private Float vat;
	private Boolean arrangedShipping;
	private Float buyerShippingCharge;
	private Short buyerBidStatus;
	private Integer reserved1;
	private String reserved2;
	private Date actualStartTime;
	private Date actualEndTime;
	private Date bidUpdatedTime;

	public AuctionBuyersBean() {

	}

	public AuctionBuyersBean(AuctionBuyers entity) {
		if (null != entity) {
			this.auctionBuyersId = entity.getAuctionBuyersId();
			this.accountLocationsBean = new AccountLocationsBean(entity.getAccountLocations());
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.shipperAccountProfileBean = new AccountProfileBean(entity.getShipperAccountProfile());
			this.auctionSellersBean = new AuctionSellersBean(entity.getAuctionSellers());
			this.dailyAuctionsBean = new DailyAuctionsBean(entity.getDailyAuctions());
			this.partialAllowed = entity.getPartialAllowed();
			this.bidQuantity = entity.getBidQuantity();
			this.minimumQuantity = ((entity.getMinimumQuantity() != null) ? entity.getMinimumQuantity() : 0);
			this.executedQuantity = entity.getExecutedQuantity();
			this.bidPrice = entity.getBidPrice();
			this.executedPrice = entity.getExecutedPrice();
			this.royaltyValue = entity.getRoyaltyValue();
			this.vat = entity.getVat();
			this.arrangedShipping = (null != entity.getShipperAccountProfile() && null != entity.getShipperAccountProfile().getAccountId()
					&& entity.getShipperAccountProfile().getAccountId() > 0 ? true : false);
			this.buyerShippingCharge = entity.getBuyerShippingCharge();
			this.buyerBidStatus = entity.getBuyerBidStatus();
			this.reserved1 = entity.getReserved1();
			this.reserved2 = entity.getReserved2();
			this.actualStartTime = entity.getActualStartTime();
			this.actualEndTime = entity.getActualEndTime();
			this.bidUpdatedTime = entity.getBidUpdatedTime();
		}
	}

	public Integer getAuctionBuyersId() {
		return auctionBuyersId;
	}

	public void setAuctionBuyersId(Integer auctionBuyersId) {
		this.auctionBuyersId = auctionBuyersId;
	}

	public AccountLocationsBean getAccountLocationsBean() {
		return accountLocationsBean;
	}

	public void setAccountLocationsBean(AccountLocationsBean accountLocationsBean) {
		this.accountLocationsBean = accountLocationsBean;
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

	public AuctionSellersBean getAuctionSellersBean() {
		return auctionSellersBean;
	}

	public void setAuctionSellersBean(AuctionSellersBean auctionSellersBean) {
		this.auctionSellersBean = auctionSellersBean;
	}

	public DailyAuctionsBean getDailyAuctionsBean() {
		return dailyAuctionsBean;
	}

	public void setDailyAuctionsBean(DailyAuctionsBean dailyAuctionsBean) {
		this.dailyAuctionsBean = dailyAuctionsBean;
	}

	public Boolean getPartialAllowed() {
		return partialAllowed;
	}

	public void setPartialAllowed(Boolean partialAllowed) {
		this.partialAllowed = partialAllowed;
	}

	public Integer getBidQuantity() {
		return bidQuantity;
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

	public Integer getExecutedQuantity() {
		return executedQuantity;
	}

	public void setExecutedQuantity(Integer executedQuantity) {
		this.executedQuantity = executedQuantity;
	}

	public Float getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Float bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Float getExecutedPrice() {
		return executedPrice;
	}

	public void setExecutedPrice(Float executedPrice) {
		this.executedPrice = executedPrice;
	}

	public Float getRoyaltyValue() {
		return royaltyValue;
	}

	public void setRoyaltyValue(Float royaltyValue) {
		this.royaltyValue = royaltyValue;
	}

	public Float getVat() {
		return vat;
	}

	public void setVat(Float vat) {
		this.vat = vat;
	}

	public Boolean getArrangedShipping() {
		return arrangedShipping;
	}

	public void setArrangedShipping(Boolean arrangedShipping) {
		this.arrangedShipping = arrangedShipping;
	}

	public Float getBuyerShippingCharge() {
		return (null != this.buyerShippingCharge ? this.buyerShippingCharge : 0F);
	}

	public void setBuyerShippingCharge(Float buyerShippingCharge) {
		this.buyerShippingCharge = buyerShippingCharge;
	}

	public Short getBuyerBidStatus() {
		return buyerBidStatus;
	}

	public void setBuyerBidStatus(Short buyerBidStatus) {
		this.buyerBidStatus = buyerBidStatus;
	}

	public Integer getReserved1() {
		return reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	
	public Date getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public Date getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public Date getBidUpdatedTime() {
		return bidUpdatedTime;
	}

	public void setBidUpdatedTime(Date bidUpdatedTime) {
		this.bidUpdatedTime = bidUpdatedTime;
	}

	

}