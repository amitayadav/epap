package com.auction.model.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seller_log")
@AssociationOverrides({ @AssociationOverride(name = "id.auctionSellers", joinColumns = @JoinColumn(name = "auction_sellers_id")),
		@AssociationOverride(name = "id.logTimestamp", joinColumns = @JoinColumn(name = "log_timestamp")),
		@AssociationOverride(name = "id.offerOperation", joinColumns = @JoinColumn(name = "offer_operation")) })
public class SellerLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private SellerLogId id;
	private AccountProfile accountProfile;
	private AccountProfile shipperAccountProfile;
	private DailyAuctions dailyAuctions;
	private Integer offerQuantity;
	private Integer minimumQuantity;
	private Integer availableQuantity;
	private Float offerPrice;
	private Float sellerShippingCharge;
	private Integer truckNumber;


	public SellerLog() {
	}

	public SellerLog(SellerLogId id) {
		super();
		this.id = id;
	}

	@EmbeddedId
	public SellerLogId getId() {
		return this.id;
	}

	public void setId(SellerLogId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false, updatable = false)
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipper", updatable = false)
	public AccountProfile getShipperAccountProfile() {
		return this.shipperAccountProfile;
	}

	public void setShipperAccountProfile(AccountProfile shipperAccountProfile) {
		this.shipperAccountProfile = shipperAccountProfile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "daily_auctions_id", nullable = false, updatable = false)
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

	@Column(name = "seller_shipping_charge", nullable = false, precision = 6)
	public Float getSellerShippingCharge() {
		return this.sellerShippingCharge;
	}

	public void setSellerShippingCharge(Float sellerShippingCharge) {
		this.sellerShippingCharge = sellerShippingCharge;
	}
	
	@Column(name = "stn", nullable = false)
	public Integer getTruckNumber() {
		return truckNumber;
	}

	public void setTruckNumber(Integer truckNumber) {
		this.truckNumber = truckNumber;
	}

}