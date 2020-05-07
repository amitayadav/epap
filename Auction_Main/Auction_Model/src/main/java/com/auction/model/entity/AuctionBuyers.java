package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "auction_buyers")
public class AuctionBuyers implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer auctionBuyersId;
	private AccountLocations accountLocations;
	private AccountProfile accountProfile;
	private AccountProfile shipperAccountProfile;
	private AuctionSellers auctionSellers;
	private DailyAuctions dailyAuctions;
	private Boolean partialAllowed;
	private Integer bidQuantity;
	private Integer minimumQuantity;
	private Integer executedQuantity;
	private Float bidPrice;
	private Float executedPrice;
	private Float royaltyValue;
	private Float vat;
	private Float buyerShippingCharge;
	private Short buyerBidStatus;
	private Integer reserved1;
	private String reserved2;
	private Date actualStartTime;
	private Date actualEndTime;
	private Date bidUpdatedTime;

	public AuctionBuyers() {
	}

	public AuctionBuyers(Integer auctionBuyersId) {
		this.auctionBuyersId = auctionBuyersId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auction_buyers_id", unique = true, nullable = false)
	public Integer getAuctionBuyersId() {
		return this.auctionBuyersId;
	}

	public void setAuctionBuyersId(Integer auctionBuyersId) {
		this.auctionBuyersId = auctionBuyersId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_location", nullable = false)
	public AccountLocations getAccountLocations() {
		return this.accountLocations;
	}

	public void setAccountLocations(AccountLocations accountLocations) {
		this.accountLocations = accountLocations;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", nullable = false)
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipper")
	public AccountProfile getShipperAccountProfile() {
		return this.shipperAccountProfile;
	}

	public void setShipperAccountProfile(AccountProfile shipperAccountProfile) {
		this.shipperAccountProfile = shipperAccountProfile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_sellers_id", nullable = false)
	public AuctionSellers getAuctionSellers() {
		return this.auctionSellers;
	}

	public void setAuctionSellers(AuctionSellers auctionSellers) {
		this.auctionSellers = auctionSellers;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "daily_auctions_id", nullable = false)
	public DailyAuctions getDailyAuctions() {
		return this.dailyAuctions;
	}

	public void setDailyAuctions(DailyAuctions dailyAuctions) {
		this.dailyAuctions = dailyAuctions;
	}

	@Column(name = "partial_allowed", nullable = false)
	public boolean getPartialAllowed() {
		return this.partialAllowed;
	}

	public void setPartialAllowed(boolean partialAllowed) {
		this.partialAllowed = partialAllowed;
	}

	@Column(name = "bid_quantity", nullable = false)
	public Integer getBidQuantity() {
		return this.bidQuantity;
	}

	public void setBidQuantity(Integer bidQuantity) {
		this.bidQuantity = bidQuantity;
	}

	@Column(name = "minimum_quantity", nullable = false)
	public Integer getMinimumQuantity() {
		return this.minimumQuantity;
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	@Column(name = "executed_quantity", nullable = false)
	public int getExecutedQuantity() {
		return this.executedQuantity;
	}

	public void setExecutedQuantity(int executedQuantity) {
		this.executedQuantity = executedQuantity;
	}

	@Column(name = "bid_price", nullable = false, precision = 6)
	public Float getBidPrice() {
		return this.bidPrice;
	}

	public void setBidPrice(Float bidPrice) {
		this.bidPrice = bidPrice;
	}

	@Column(name = "executed_price", nullable = false, precision = 6)
	public Float getExecutedPrice() {
		return this.executedPrice;
	}

	public void setExecutedPrice(Float executedPrice) {
		this.executedPrice = executedPrice;
	}

	@Column(name = "royalty_percentage", nullable = false, precision = 4)
	public Float getRoyaltyValue() {
		return this.royaltyValue;
	}

	public void setRoyaltyValue(Float royaltyValue) {
		this.royaltyValue = royaltyValue;
	}

	@Column(name = "vat_percentage", nullable = false, precision = 4)
	public Float getVat() {
		return this.vat;
	}

	public void setVat(Float vat) {
		this.vat = vat;
	}

	@Column(name = "buyer_shipping_charge", nullable = false, precision = 6)
	public Float getBuyerShippingCharge() {
		return this.buyerShippingCharge;
	}

	public void setBuyerShippingCharge(Float buyerShippingCharge) {
		this.buyerShippingCharge = buyerShippingCharge;
	}

	@Column(name = "buyer_bid_status", nullable = false)
	public Short getBuyerBidStatus() {
		return this.buyerBidStatus;
	}

	public void setBuyerBidStatus(Short buyerBidStatus) {
		this.buyerBidStatus = buyerBidStatus;
	}

	@Column(name = "reserved1")
	public Integer getReserved1() {
		return this.reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	@Column(name = "reserved2", length = 30)
	public String getReserved2() {
		return this.reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actual_start_time", nullable = false, length = 19)
	public Date getActualStartTime() {
		return this.actualStartTime;
	}

	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actual_end_time", length = 19)
	public Date getActualEndTime() {
		return this.actualEndTime;
	}

	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bid_updated_time", length = 19)
	public Date getBidUpdatedTime() {
		return bidUpdatedTime;
	}

	public void setBidUpdatedTime(Date bidUpdatedTime) {
		this.bidUpdatedTime = bidUpdatedTime;
	}

}