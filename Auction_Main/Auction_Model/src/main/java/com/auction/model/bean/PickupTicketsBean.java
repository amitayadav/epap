package com.auction.model.bean;

import java.util.Date;

import com.auction.commons.util.DateHelper;
import com.auction.model.entity.PickupTickets;

public class PickupTicketsBean {

	private Integer pickupTicketsId;
	private Integer ptn;
	private Date tradeTime;
	private DailyAuctionsBean dailyAuctionsid;
	private AccountProfileBean sellerId;
	private String sellerPublicName;
	private Integer sellerAccountId;
	private Integer sellerTruckNumber;
	private AccountProfileBean buyerId;
	private AuctionBuyersBean auctionBuyerBean;
	private String buyerPublicName;
	private Integer buyerPurchasedQuantity;
	private AccountLocationsBean sellerLocation;
	private AccountLocationsBean buyerLocation;
	private Integer action;
	private Integer updatebyId;
	private Date updatedOn;
	private Integer cancelledbyId;
	private Date canceledOn;
	private Integer reserved1;
	private String reserved2;
	private String tradeDateTime;

	public PickupTicketsBean() {

	}

	public PickupTicketsBean(Integer pickupTicketsId) {
		this.pickupTicketsId = pickupTicketsId;
	}

	public PickupTicketsBean(PickupTickets entity) {
		if (null != entity) {
			this.pickupTicketsId = entity.getPickupTicketsId();
			this.ptn = entity.getPtn();
			this.tradeTime = entity.getTradeTime();
			this.dailyAuctionsid = new DailyAuctionsBean(entity.getDailyAuctionsid());
			this.sellerPublicName = entity.getSellerPublicName();
			this.sellerAccountId = entity.getSellerAccountId();
			this.sellerTruckNumber = entity.getSellerTruckNumber();
			this.buyerPublicName = entity.getBuyerPublicName();
			this.buyerPurchasedQuantity = entity.getBuyerPurchasedQuantity();
			this.sellerLocation = new AccountLocationsBean(entity.getSellerLocation());
			this.buyerLocation = new AccountLocationsBean(entity.getBuyerLocation());
			this.action = entity.getAction();
			this.updatebyId = entity.getUpdatebyId();
			this.updatedOn = entity.getUpdatedOn();
			this.cancelledbyId = entity.getCancelledbyId();
			this.canceledOn = entity.getCanceledOn();
			this.reserved1 = entity.getReserved1();
			this.reserved2 = entity.getReserved2();
			this.sellerId = new AccountProfileBean(entity.getSellerId());
			this.buyerId = new AccountProfileBean(entity.getBuyerId());
			this.auctionBuyerBean = new AuctionBuyersBean(entity.getAuctionBuyerId());
			this.tradeDateTime = DateHelper.dateToStringAmPm(entity.getTradeTime());

		}
	}

	public Integer getPickupTicketsId() {
		return pickupTicketsId;
	}

	public void setPickupTicketsId(Integer pickupTicketsId) {
		this.pickupTicketsId = pickupTicketsId;
	}

	public Integer getPtn() {
		return ptn;
	}

	public void setPtn(Integer ptn) {
		this.ptn = ptn;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public DailyAuctionsBean getDailyAuctionsid() {
		return dailyAuctionsid;
	}

	public void setDailyAuctionsid(DailyAuctionsBean dailyAuctionsid) {
		this.dailyAuctionsid = dailyAuctionsid;
	}

	public String getSellerPublicName() {
		return sellerPublicName;
	}

	public void setSellerPublicName(String sellerPublicName) {
		this.sellerPublicName = sellerPublicName;
	}

	public Integer getSellerAccountId() {
		return sellerAccountId;
	}

	public void setSellerAccountId(Integer sellerAccountId) {
		this.sellerAccountId = sellerAccountId;
	}

	public Integer getSellerTruckNumber() {
		return sellerTruckNumber;
	}

	public void setSellerTruckNumber(Integer sellerTruckNumber) {
		this.sellerTruckNumber = sellerTruckNumber;
	}

	public String getBuyerPublicName() {
		return buyerPublicName;
	}

	public void setBuyerPublicName(String buyerPublicName) {
		this.buyerPublicName = buyerPublicName;
	}

	public Integer getBuyerPurchasedQuantity() {
		return buyerPurchasedQuantity;
	}

	public void setBuyerPurchasedQuantity(Integer buyerPurchasedQuantity) {
		this.buyerPurchasedQuantity = buyerPurchasedQuantity;
	}

	public AccountLocationsBean getSellerLocation() {
		return sellerLocation;
	}

	public void setSellerLocation(AccountLocationsBean sellerLocation) {
		this.sellerLocation = sellerLocation;
	}

	public AccountLocationsBean getBuyerLocation() {
		return buyerLocation;
	}

	public void setBuyerLocation(AccountLocationsBean buyerLocation) {
		this.buyerLocation = buyerLocation;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Integer getUpdatebyId() {
		return updatebyId;
	}

	public void setUpdatebyId(Integer updatebyId) {
		this.updatebyId = updatebyId;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getCancelledbyId() {
		return cancelledbyId;
	}

	public void setCancelledbyId(Integer cancelledbyId) {
		this.cancelledbyId = cancelledbyId;
	}

	public Date getCanceledOn() {
		return canceledOn;
	}

	public void setCanceledOn(Date canceledOn) {
		this.canceledOn = canceledOn;
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

	public AccountProfileBean getSellerId() {
		return sellerId;
	}

	public void setSellerId(AccountProfileBean sellerId) {
		this.sellerId = sellerId;
	}

	public AccountProfileBean getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(AccountProfileBean buyerId) {
		this.buyerId = buyerId;
	}

	public AuctionBuyersBean getAuctionBuyerBean() {
		return auctionBuyerBean;
	}

	public void setAuctionBuyerBean(AuctionBuyersBean auctionBuyerBean) {
		this.auctionBuyerBean = auctionBuyerBean;
	}

	public String getTradeDateTime() {
		return tradeDateTime;
	}

	public void setTradeDateTime(String tradeDateTime) {
		this.tradeDateTime = tradeDateTime;
	}

}
