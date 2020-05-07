package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.AuctionSellers;

public class AuctionSellersBean {

	private Integer auctionSellersId;
	private AccountLocationsBean accountLocationsBean;
	private AccountProfileBean accountProfileBean;
	private AccountProfileBean shipperAccountProfileBean;
	private DailyAuctionsBean dailyAuctionsBean;
	private SellerOfferInfoBean sellerOfferInfoBean;
	private Integer offerQuantity;
	private Integer minimumQuantity;
	private Integer availableQuantity;
	private Float offerPrice;
	private Float royaltyValue;
	private Float vat;
	private Boolean partialAllowed;
	private Boolean arrangedShipping;
	private Float sellerShippingCharge;
	private Short sellerOfferStatus;
	private Date actualStartTime;
	private Date actualEndTime;
	private Boolean allowShipperSelection = true;
	private Integer reserved1;
	private String reserved2;
	private Integer truckNumber;
	private Date offerUpdatedTime;

	public AuctionSellersBean() {
	}

	public AuctionSellersBean(Integer auctionSellersId) {
		this.auctionSellersId = auctionSellersId;
	}

	public AuctionSellersBean(AuctionSellers entity) {
		if (null != entity) {
			this.auctionSellersId = entity.getAuctionSellersId();
			this.accountLocationsBean = new AccountLocationsBean(entity.getAccountLocations());
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.shipperAccountProfileBean = new AccountProfileBean(entity.getShipperAccountProfile());
			this.arrangedShipping = (null != entity.getShipperAccountProfile() && null != entity.getShipperAccountProfile().getAccountId()
					&& entity.getShipperAccountProfile().getAccountId() > 0 ? true : false);
			this.dailyAuctionsBean = new DailyAuctionsBean(entity.getDailyAuctions());
			this.offerQuantity = entity.getOfferQuantity();
			this.minimumQuantity = ((entity.getMinimumQuantity() != null) ? entity.getMinimumQuantity() : 0);
			this.availableQuantity = entity.getAvailableQuantity();
			this.offerPrice = entity.getOfferPrice();
			this.royaltyValue = entity.getRoyaltyValue();
			this.vat = entity.getVat();
			this.partialAllowed = entity.getPartialAllowed();
			this.sellerShippingCharge = entity.getSellerShippingCharge();
			this.sellerOfferStatus = entity.getSellerOfferStatus();
			this.actualStartTime = entity.getActualStartTime();
			this.actualEndTime = entity.getActualEndTime();
			this.reserved1 = entity.getReserved1();
			this.reserved2 = entity.getReserved2();
			this.truckNumber = entity.getTruckNumber();
			this.offerUpdatedTime = entity.getOfferUpdatedTime();
		}
	}

	public Integer getAuctionSellersId() {
		return auctionSellersId;
	}

	public void setAuctionSellersId(Integer auctionSellersId) {
		this.auctionSellersId = auctionSellersId;
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

	public DailyAuctionsBean getDailyAuctionsBean() {
		return dailyAuctionsBean;
	}

	public void setDailyAuctionsBean(DailyAuctionsBean dailyAuctionsBean) {
		this.dailyAuctionsBean = dailyAuctionsBean;
	}

	public SellerOfferInfoBean getSellerOfferInfoBean() {
		return sellerOfferInfoBean;
	}

	public void setSellerOfferInfoBean(SellerOfferInfoBean sellerOfferInfoBean) {
		this.sellerOfferInfoBean = sellerOfferInfoBean;
	}

	public Integer getOfferQuantity() {
		return offerQuantity;
	}

	public void setOfferQuantity(Integer offerQuantity) {
		this.offerQuantity = offerQuantity;
	}

	public Integer getMinimumQuantity() {
		return (null != this.minimumQuantity ? this.minimumQuantity : 0);
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	public Integer getAvailableQuantity() {
		return (null != this.availableQuantity ? this.availableQuantity : 0);
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Float getOfferPrice() {
		return (null != this.offerPrice ? this.offerPrice : 0F);
	}

	public void setOfferPrice(Float offerPrice) {
		this.offerPrice = offerPrice;
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

	public Boolean getPartialAllowed() {
		return partialAllowed;
	}

	public void setPartialAllowed(Boolean partialAllowed) {
		this.partialAllowed = partialAllowed;
	}

	public Boolean getArrangedShipping() {
		return arrangedShipping;
	}

	public void setArrangedShipping(Boolean arrangedShipping) {
		this.arrangedShipping = arrangedShipping;
	}

	public Float getSellerShippingCharge() {
		// return (null != this.sellerShippingCharge ? this.sellerShippingCharge : 0F);
		return sellerShippingCharge;
	}

	public void setSellerShippingCharge(Float sellerShippingCharge) {
		this.sellerShippingCharge = sellerShippingCharge;
	}

	public Short getSellerOfferStatus() {
		return sellerOfferStatus;
	}

	public void setSellerOfferStatus(Short sellerOfferStatus) {
		this.sellerOfferStatus = sellerOfferStatus;
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

	public Boolean getAllowShipperSelection() {
		return allowShipperSelection;
	}

	public void setAllowShipperSelection(Boolean allowShipperSelection) {
		this.allowShipperSelection = allowShipperSelection;
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

	public Integer getTruckNumber() {
		return truckNumber;
	}

	public void setTruckNumber(Integer truckNumber) {
		this.truckNumber = truckNumber;
	}

	public Date getOfferUpdatedTime() {
		return offerUpdatedTime;
	}

	public void setOfferUpdatedTime(Date offerUpdatedTime) {
		this.offerUpdatedTime = offerUpdatedTime;
	}

}