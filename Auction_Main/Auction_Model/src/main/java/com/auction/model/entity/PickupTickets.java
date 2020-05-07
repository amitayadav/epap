package com.auction.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

@Entity
@Table(name = "pickup_tickets")
public class PickupTickets implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer pickupTicketsId;
	private Integer ptn;
	private Date tradeTime;
	private DailyAuctions dailyAuctionsid;
	private AccountProfile sellerId;
	private String sellerPublicName;
	private Integer sellerAccountId;
	private Integer sellerTruckNumber;
	private AccountProfile buyerId;
	private AuctionBuyers auctionBuyerId;
	private String buyerPublicName;
	private Integer buyerPurchasedQuantity;
	private AccountLocations sellerLocation;
	private AccountLocations buyerLocation;
	private Integer action;
	private Integer updatebyId;
	private Date updatedOn;
	private Integer cancelledbyId;
	private Date canceledOn;
	private Integer reserved1;
	private String reserved2;

	public PickupTickets() {

	}

	public PickupTickets(Integer pickupTicketsId) {
		this.pickupTicketsId = pickupTicketsId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "pickup_ticket_id", unique = true, nullable = false)
	public Integer getPickupTicketsId() {
		return pickupTicketsId;
	}

	public void setPickupTicketsId(Integer pickupTicketsId) {
		this.pickupTicketsId = pickupTicketsId;
	}

	@Column(name = "ptn", nullable = true)
	public Integer getPtn() {
		return ptn;
	}

	public void setPtn(Integer ptn) {
		this.ptn = ptn;
	}

	@Column(name = "trade_time", nullable = false)
	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "daily_auctions_id", nullable = false)
	public DailyAuctions getDailyAuctionsid() {
		return dailyAuctionsid;
	}

	public void setDailyAuctionsid(DailyAuctions dailyAuctionsid) {
		this.dailyAuctionsid = dailyAuctionsid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false)
	public AccountProfile getSellerId() {
		return sellerId;
	}

	public void setSellerId(AccountProfile sellerId) {
		this.sellerId = sellerId;
	}

	@Column(name = "seller_name", nullable = false)
	public String getSellerPublicName() {
		return sellerPublicName;
	}

	public void setSellerPublicName(String sellerPublicName) {
		this.sellerPublicName = sellerPublicName;
	}

	@Column(name = "seller_owner", nullable = false)
	public Integer getSellerAccountId() {
		return sellerAccountId;
	}

	public void setSellerAccountId(Integer sellerAccountId) {
		this.sellerAccountId = sellerAccountId;
	}

	@Column(name = "stn", nullable = false)
	public Integer getSellerTruckNumber() {
		return sellerTruckNumber;
	}

	public void setSellerTruckNumber(Integer sellerTruckNumber) {
		this.sellerTruckNumber = sellerTruckNumber;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", nullable = false)
	public AccountProfile getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(AccountProfile buyerId) {
		this.buyerId = buyerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_buyers_id", nullable = false)
	public AuctionBuyers getAuctionBuyerId() {
		return auctionBuyerId;
	}

	public void setAuctionBuyerId(AuctionBuyers auctionBuyerId) {
		this.auctionBuyerId = auctionBuyerId;
	}

	@Column(name = "buyer_name", nullable = false)
	public String getBuyerPublicName() {
		return buyerPublicName;
	}

	public void setBuyerPublicName(String buyerPublicName) {
		this.buyerPublicName = buyerPublicName;
	}

	@Column(name = "purchased_quantity", nullable = false)
	public Integer getBuyerPurchasedQuantity() {
		return buyerPurchasedQuantity;
	}

	public void setBuyerPurchasedQuantity(Integer buyerPurchasedQuantity) {
		this.buyerPurchasedQuantity = buyerPurchasedQuantity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_location", nullable = false)
	public AccountLocations getSellerLocation() {
		return sellerLocation;
	}

	public void setSellerLocation(AccountLocations sellerLocation) {
		this.sellerLocation = sellerLocation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_location", nullable = false)
	public AccountLocations getBuyerLocation() {
		return buyerLocation;
	}

	public void setBuyerLocation(AccountLocations buyerLocation) {
		this.buyerLocation = buyerLocation;
	}

	@Column(name = "action")
	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	@Column(name = "confirmedby_id")
	public Integer getUpdatebyId() {
		return updatebyId;
	}

	public void setUpdatebyId(Integer updatebyId) {
		this.updatebyId = updatebyId;
	}

	@Column(name = "confirmed_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "cancelledby_id")
	public Integer getCancelledbyId() {
		return cancelledbyId;
	}

	public void setCancelledbyId(Integer cancelledbyId) {
		this.cancelledbyId = cancelledbyId;
	}

	@Column(name = "canceled_on")
	public Date getCanceledOn() {
		return canceledOn;
	}

	public void setCanceledOn(Date canceledOn) {
		this.canceledOn = canceledOn;
	}

	@Column(name = "reserved1")
	public Integer getReserved1() {
		return reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	@Column(name = "reserved2")
	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

}
