package com.auction.model.views;

import java.util.Date;

import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.entity.AuctionBuyers;

public class AuctionBuyersBidsView {
	private Integer auctionBuyersId;
	private AuctionSellerOffersView auctionSellerOffersView;
	private DailyAuctionsView dailyAuctionsView;
	private Integer accountId;
	private String buyerName;
	private String publicName;
	private Integer offerOrBidCount;
	private Integer executedOfferOrBidCount;
	private String profileImage;
	private Float rating;
	private Float cancelPercentage;
	private AccountLocationsView accountLocationsView;
	private Boolean partialAllowed;
	private Integer bidQuantity;
	private Integer minimumQuantity;
	private Integer executedQuantity;
	private Float bidPrice;
	private Float executedPrice;
	private Float royaltyValue;
	private Float vat;
	private Float buyerShippingCharge;
	private Short buyerBidStatusCode;
	private String buyerBidStatusName;
	private Date actualStartTime;
	private Date actualEndTime;
	private Date bidUpdatedTime;
	public AuctionBuyersBidsView() {

	}

	public AuctionBuyersBidsView(AuctionBuyersBean bean) {
		if (null != bean && null != bean.getAuctionBuyersId()) {
			this.auctionBuyersId = bean.getAuctionBuyersId();
			this.auctionSellerOffersView = new AuctionSellerOffersView(bean.getAuctionSellersBean());
			this.dailyAuctionsView = new DailyAuctionsView(bean.getDailyAuctionsBean());
			this.accountId = (bean.getAccountProfileBean().getAccountId());
			this.buyerName = (bean.getAccountProfileBean().getFName() + " " + bean.getAccountProfileBean().getLName());
			this.publicName = (bean.getAccountProfileBean().getPublicName());
			this.offerOrBidCount = (bean.getAccountProfileBean().getOfferOrBidCount());
			this.executedOfferOrBidCount = (bean.getAccountProfileBean().getExecutedOfferOrBidCount());
			this.profileImage = bean.getAccountProfileBean().getProfileImage();
			this.rating = bean.getAccountProfileBean().getRating();
			this.cancelPercentage = bean.getAccountProfileBean().getCancelPercentage();
			this.accountLocationsView = new AccountLocationsView(bean.getAccountLocationsBean());
			this.partialAllowed = bean.getPartialAllowed();
			this.bidQuantity = bean.getBidQuantity();
			this.minimumQuantity = bean.getMinimumQuantity();
			this.executedQuantity = bean.getExecutedQuantity();
			this.bidPrice = bean.getBidPrice();
			this.executedPrice = bean.getExecutedPrice();
			this.royaltyValue = bean.getRoyaltyValue();
			this.vat = bean.getVat();
			this.buyerShippingCharge = bean.getBuyerShippingCharge();
			this.buyerBidStatusCode = bean.getBuyerBidStatus();
			this.buyerBidStatusName = ENUM_AuctionBuyerBidStatusCodes.getDesc(bean.getBuyerBidStatus());
			this.actualStartTime = bean.getActualStartTime();
			this.actualEndTime = bean.getActualEndTime();
		}
	}

	public AuctionBuyersBidsView(AuctionBuyers entity) {
		if (null != entity && null != entity.getAuctionBuyersId()) {
			this.auctionBuyersId = entity.getAuctionBuyersId();
			this.auctionSellerOffersView = new AuctionSellerOffersView(entity.getAuctionSellers());
			this.dailyAuctionsView = new DailyAuctionsView(entity.getDailyAuctions());
			this.accountId = (entity.getAccountProfile().getAccountId());
			this.buyerName = (entity.getAccountProfile().getFName() + " " + entity.getAccountProfile().getLName());
			this.publicName = (entity.getAccountProfile().getPublicName());
			this.offerOrBidCount = (entity.getAccountProfile().getOfferOrBidCount());
			this.executedOfferOrBidCount = (entity.getAccountProfile().getExecutedOfferOrBidCount());
			this.profileImage = entity.getAccountProfile().getProfileImage();
			this.rating = entity.getAccountProfile().getRating();
			this.cancelPercentage = new Float(
					(entity.getAccountProfile().getNoOfCancellations().floatValue() / entity.getAccountProfile().getOfferOrBidCount().floatValue()) * 100);
			this.accountLocationsView = new AccountLocationsView(entity.getAccountLocations());
			this.partialAllowed = entity.getPartialAllowed();
			this.bidQuantity = entity.getBidQuantity();
			this.minimumQuantity = entity.getMinimumQuantity();
			this.executedQuantity = entity.getExecutedQuantity();
			this.bidPrice = entity.getBidPrice();
			this.executedPrice = entity.getExecutedPrice();
			this.royaltyValue = entity.getRoyaltyValue();
			this.vat = entity.getVat();
			this.buyerShippingCharge = entity.getBuyerShippingCharge();
			this.buyerBidStatusCode = entity.getBuyerBidStatus();
			this.buyerBidStatusName = ENUM_AuctionBuyerBidStatusCodes.getDesc(entity.getBuyerBidStatus());
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

	public AuctionSellerOffersView getAuctionSellerOffersView() {
		return auctionSellerOffersView;
	}

	public void setAuctionSellerOffersView(AuctionSellerOffersView auctionSellerOffersView) {
		this.auctionSellerOffersView = auctionSellerOffersView;
	}

	public DailyAuctionsView getDailyAuctionsView() {
		return dailyAuctionsView;
	}

	public void setDailyAuctionsView(DailyAuctionsView dailyAuctionsView) {
		this.dailyAuctionsView = dailyAuctionsView;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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
		return minimumQuantity;
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

	public Float getBuyerShippingCharge() {
		return buyerShippingCharge;
	}

	public void setBuyerShippingCharge(Float buyerShippingCharge) {
		this.buyerShippingCharge = buyerShippingCharge;
	}

	public Short getBuyerBidStatusCode() {
		return buyerBidStatusCode;
	}

	public void setBuyerBidStatusCode(Short buyerBidStatusCode) {
		this.buyerBidStatusCode = buyerBidStatusCode;
	}

	public String getBuyerBidStatusName() {
		return buyerBidStatusName;
	}

	public void setBuyerBidStatusName(String buyerBidStatusName) {
		this.buyerBidStatusName = buyerBidStatusName;
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