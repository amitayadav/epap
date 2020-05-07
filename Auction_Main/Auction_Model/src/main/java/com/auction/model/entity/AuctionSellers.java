package com.auction.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "auction_sellers")
public class AuctionSellers implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer auctionSellersId;
	private AccountLocations accountLocations;
	private AccountProfile accountProfile;
	private AccountProfile shipperAccountProfile;
	private DailyAuctions dailyAuctions;
	private Integer offerQuantity;
	private Integer minimumQuantity;
	private Integer availableQuantity;
	private Float offerPrice;
	private Float royaltyValue;
	private Float vat;
	private Boolean partialAllowed;
	private Float sellerShippingCharge;
	private Short sellerOfferStatus;
	private Date actualStartTime;
	private Date actualEndTime;
	private Integer reserved1;
	private String reserved2;
	private Integer truckNumber;
	private Date offerUpdatedTime;

	public AuctionSellers() {
	}

	public AuctionSellers(Integer auctionSellersId) {
		this.auctionSellersId = auctionSellersId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auction_sellers_id", unique = true, nullable = false)
	public Integer getAuctionSellersId() {
		return this.auctionSellersId;
	}

	public void setAuctionSellersId(Integer auctionSellersId) {
		this.auctionSellersId = auctionSellersId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_location", nullable = false)
	public AccountLocations getAccountLocations() {
		return this.accountLocations;
	}

	public void setAccountLocations(AccountLocations accountLocations) {
		this.accountLocations = accountLocations;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false)
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
	@JoinColumn(name = "daily_auctions_id", nullable = false)
	public DailyAuctions getDailyAuctions() {
		return this.dailyAuctions;
	}

	public void setDailyAuctions(DailyAuctions dailyAuctions) {
		this.dailyAuctions = dailyAuctions;
	}

	@Column(name = "offer_quantity", nullable = false)
	public Integer getOfferQuantity() {
		return this.offerQuantity;
	}

	public void setOfferQuantity(Integer offerQuantity) {
		this.offerQuantity = offerQuantity;
	}

	@Column(name = "minimum_quantity", nullable = false)
	public Integer getMinimumQuantity() {
		return this.minimumQuantity;
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
	
	@Column(name = "available_quantity", nullable = false)
	public Integer getAvailableQuantity() {
		return this.availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	@Column(name = "offer_price", nullable = false, precision = 6)
	public Float getOfferPrice() {
		return this.offerPrice;
	}

	public void setOfferPrice(Float offerPrice) {
		this.offerPrice = offerPrice;
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

	@Column(name = "partial_allowed", nullable = false)
	public Boolean getPartialAllowed() {
		return this.partialAllowed;
	}

	public void setPartialAllowed(Boolean partialAllowed) {
		this.partialAllowed = partialAllowed;
	}

	@Column(name = "seller_shipping_charge", nullable = false, precision = 6)
	public Float getSellerShippingCharge() {
		return this.sellerShippingCharge;
	}

	public void setSellerShippingCharge(Float sellerShippingCharge) {
		this.sellerShippingCharge = sellerShippingCharge;
	}

	@Column(name = "seller_offer_status", nullable = false)
	public Short getSellerOfferStatus() {
		return this.sellerOfferStatus;
	}

	public void setSellerOfferStatus(Short sellerOfferStatus) {
		this.sellerOfferStatus = sellerOfferStatus;
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
	@Column(name = "stn", nullable = false)
	public Integer getTruckNumber() {
		return truckNumber;
	}

	public void setTruckNumber(Integer truckNumber) {
		this.truckNumber = truckNumber;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "offer_updated_time", length = 19)
	public Date getOfferUpdatedTime() {
		return offerUpdatedTime;
	}

	public void setOfferUpdatedTime(Date offerUpdatedTime) {
		this.offerUpdatedTime = offerUpdatedTime;
	}
	

}