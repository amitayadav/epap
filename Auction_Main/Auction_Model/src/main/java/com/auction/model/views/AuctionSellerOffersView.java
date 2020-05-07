package com.auction.model.views;

import java.util.Date;

import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.entity.AuctionSellers;

public class AuctionSellerOffersView {

	private Integer auctionSellersId;
	private Integer dailyAuctionsId;
	private Integer accountId;
	private Integer productId;
	private String sellerName;
	private String publicName;
	private Integer offerOrBidCount;
	private Integer executedOfferOrBidCount;
	private String profileImage;
	private Float rating;
	private Float cancelPercentage;
	private AccountLocationsView accountLocationsView;
	private Integer offerQuantity;
	private Integer minimumQuantity;
	private Integer availableQuantity;
	private Float offerPrice;
	private Float royaltyValue;
	private Float vat;
	private Boolean partialAllowed;
	private Short sellerOfferStatusCode;
	private String sellerOfferStatusName;
	private Date actualStartTime;
	private Date actualEndTime;
	private Boolean auctionBuyers = new Boolean(false);
	private Integer truckNumber;
	private Integer ownerAccountId;
	private Date offerUpdatedTime;
	

	public AuctionSellerOffersView() {

	}

	public AuctionSellerOffersView(AuctionSellersBean bean) {
		if (null != bean && null != bean.getAuctionSellersId()) {
			this.auctionSellersId = bean.getAuctionSellersId();
			this.dailyAuctionsId = bean.getDailyAuctionsBean().getDailyAuctionsId();
			this.accountId = bean.getAccountProfileBean().getAccountId();
			this.productId = bean.getSellerOfferInfoBean().getProductCatalogBean().getProductId();
			this.sellerName = (bean.getAccountProfileBean().getFName() + " " + bean.getAccountProfileBean().getLName());
			this.publicName = (bean.getAccountProfileBean().getPublicName());
			this.offerOrBidCount = (bean.getAccountProfileBean().getOfferOrBidCount());
			this.executedOfferOrBidCount = (bean.getAccountProfileBean().getExecutedOfferOrBidCount());
			this.profileImage = bean.getAccountProfileBean().getProfileImage();
			this.rating = bean.getAccountProfileBean().getRating();
			this.cancelPercentage = bean.getAccountProfileBean().getCancelPercentage();
			this.accountLocationsView = new AccountLocationsView(bean.getAccountLocationsBean());
			this.offerQuantity = bean.getOfferQuantity();
			this.minimumQuantity = bean.getMinimumQuantity();
			this.availableQuantity = bean.getAvailableQuantity();
			this.offerPrice = bean.getOfferPrice();
			this.royaltyValue = bean.getRoyaltyValue();
			this.vat = bean.getVat();
			this.partialAllowed = bean.getPartialAllowed();
			this.sellerOfferStatusCode = bean.getSellerOfferStatus();
			this.sellerOfferStatusName = ENUM_AuctionSellerOfferStatusCodes.getDesc(bean.getSellerOfferStatus());
			this.actualStartTime = bean.getActualStartTime();
			this.actualEndTime = bean.getActualEndTime();
			this.truckNumber =bean.getTruckNumber();
			this.offerUpdatedTime = bean.getOfferUpdatedTime();
		}
	}

	public AuctionSellerOffersView(AuctionSellers entity) {
		if (null != entity && null != entity.getAuctionSellersId()) {
			this.auctionSellersId = entity.getAuctionSellersId();
			this.dailyAuctionsId = entity.getDailyAuctions().getDailyAuctionsId();
			this.accountId = entity.getAccountProfile().getAccountId();
			this.productId = entity.getDailyAuctions().getProductCatalog().getProductId();
			this.sellerName = (entity.getAccountProfile().getFName() + " " + entity.getAccountProfile().getLName());
			this.publicName = (entity.getAccountProfile().getPublicName());
			this.offerOrBidCount = (entity.getAccountProfile().getOfferOrBidCount());
			this.executedOfferOrBidCount = (entity.getAccountProfile().getExecutedOfferOrBidCount());
			this.profileImage = entity.getAccountProfile().getProfileImage();
			this.rating = entity.getAccountProfile().getRating();
			this.cancelPercentage = new Float(
					(entity.getAccountProfile().getNoOfCancellations().floatValue() / entity.getAccountProfile().getOfferOrBidCount().floatValue()) * 100);
			this.accountLocationsView = new AccountLocationsView(entity.getAccountLocations());
			this.offerQuantity = entity.getOfferQuantity();
			this.minimumQuantity = entity.getMinimumQuantity();
			this.availableQuantity = entity.getAvailableQuantity();
			this.offerPrice = entity.getOfferPrice();
			this.royaltyValue = entity.getRoyaltyValue();
			this.vat = entity.getVat();
			this.partialAllowed = entity.getPartialAllowed();
			this.sellerOfferStatusCode = entity.getSellerOfferStatus();
			this.sellerOfferStatusName = ENUM_AuctionSellerOfferStatusCodes.getDesc(entity.getSellerOfferStatus());
			this.actualStartTime = entity.getActualStartTime();
			this.actualEndTime = entity.getActualEndTime();
			this.truckNumber =entity.getTruckNumber();
			this.offerUpdatedTime = entity.getOfferUpdatedTime();
		}
	}

	public Integer getAuctionSellersId() {
		return auctionSellersId;
	}

	public void setAuctionSellersId(Integer auctionSellersId) {
		this.auctionSellersId = auctionSellersId;
	}

	public Integer getDailyAuctionsId() {
		return dailyAuctionsId;
	}

	public void setDailyAuctionsId(Integer dailyAuctionsId) {
		this.dailyAuctionsId = dailyAuctionsId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getPublicName() {
		return publicName;
	}

	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}

	public Integer getOfferOrBidCount() {
		return offerOrBidCount;
	}

	public void setOfferOrBidCount(Integer offerOrBidCount) {
		this.offerOrBidCount = offerOrBidCount;
	}

	public Integer getExecutedOfferOrBidCount() {
		return this.executedOfferOrBidCount;
	}

	public void setExecutedOfferOrBidCount(Integer executedOfferOrBidCount) {
		this.executedOfferOrBidCount = executedOfferOrBidCount;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Float getCancelPercentage() {
		return cancelPercentage;
	}

	public void setCancelPercentage(Float cancelPercentage) {
		this.cancelPercentage = cancelPercentage;
	}

	public AccountLocationsView getAccountLocationsView() {
		return accountLocationsView;
	}

	public void setAccountLocationsView(AccountLocationsView accountLocationsView) {
		this.accountLocationsView = accountLocationsView;
	}

	public Integer getOfferQuantity() {
		return offerQuantity;
	}

	public void setOfferQuantity(Integer offerQuantity) {
		this.offerQuantity = offerQuantity;
	}

	public Integer getMinimumQuantity() {
		return minimumQuantity;
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	public Integer getAvailableQuantity() {
		return this.availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
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

	public Float getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(Float offerPrice) {
		this.offerPrice = offerPrice;
	}

	public Boolean getPartialAllowed() {
		return partialAllowed;
	}

	public void setPartialAllowed(Boolean partialAllowed) {
		this.partialAllowed = partialAllowed;
	}

	public Short getSellerOfferStatusCode() {
		return sellerOfferStatusCode;
	}

	public void setSellerOfferStatusCode(Short sellerOfferStatusCode) {
		this.sellerOfferStatusCode = sellerOfferStatusCode;
	}

	public String getSellerOfferStatusName() {
		return sellerOfferStatusName;
	}

	public void setSellerOfferStatusName(String sellerOfferStatusName) {
		this.sellerOfferStatusName = sellerOfferStatusName;
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

	public Boolean getAuctionBuyers() {
		return auctionBuyers;
	}

	public void setAuctionBuyers(Boolean auctionBuyers) {
		this.auctionBuyers = auctionBuyers;
	}
	public Integer getTruckNumber() {
		return truckNumber;
	}

	public void setTruckNumber(Integer truckNumber) {
		this.truckNumber = truckNumber;
	}

	public Integer getOwnerAccountId() {
		return ownerAccountId;
	}

	public void setOwnerAccountId(Integer ownerAccountId) {
		this.ownerAccountId = ownerAccountId;
	}

	public Date getOfferUpdatedTime() {
		return offerUpdatedTime;
	}

	public void setOfferUpdatedTime(Date offerUpdatedTime) {
		this.offerUpdatedTime = offerUpdatedTime;
	}

	
}