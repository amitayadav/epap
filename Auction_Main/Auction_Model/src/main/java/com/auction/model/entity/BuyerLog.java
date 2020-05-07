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
@Table(name = "buyer_log")
@AssociationOverrides({ @AssociationOverride(name = "id.auctionBuyers", joinColumns = @JoinColumn(name = "auction_buyers_id")),
		@AssociationOverride(name = "id.logTimestamp", joinColumns = @JoinColumn(name = "log_timestamp")),
		@AssociationOverride(name = "id.bidOperation", joinColumns = @JoinColumn(name = "bid_operation")) })
public class BuyerLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BuyerLogId id;
	private AccountProfile accountProfile;
	private AccountProfile shipperAccountProfile;
	private DailyAuctions dailyAuctions;
	private Integer bidQuantity;
	private Integer minimumQuantity;
	private Float bidPrice;
	private Float buyerShippingCharge;
	private Integer executedQuantity;
	private Float executedPrice;


	public BuyerLog() {
	}
	
	public BuyerLog(BuyerLogId id) {
		this.id = id;
	}

	@EmbeddedId
	public BuyerLogId getId() {
		return this.id;
	}

	public void setId(BuyerLogId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", nullable = false, updatable = false)
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
	@JoinColumn(name = "daily_auction_id", nullable = false, updatable = false)
	public DailyAuctions getDailyAuctions() {
		return this.dailyAuctions;
	}

	public void setDailyAuctions(DailyAuctions dailyAuctions) {
		this.dailyAuctions = dailyAuctions;
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
	
	@Column(name = "bid_price", nullable = false, precision = 6)
	public Float getBidPrice() {
		return this.bidPrice;
	}

	public void setBidPrice(Float bidPrice) {
		this.bidPrice = bidPrice;
	}

	@Column(name = "buyer_shipping_charge", nullable = false, precision = 6)
	public Float getBuyerShippingCharge() {
		return this.buyerShippingCharge;
	}

	public void setBuyerShippingCharge(Float buyerShippingCharge) {
		this.buyerShippingCharge = buyerShippingCharge;
	}
	@Column(name = "executed_quantity", nullable = false)
	public Integer getExecutedQuantity() {
		return executedQuantity;
	}

	public void setExecutedQuantity(Integer executedQuantity) {
		this.executedQuantity = executedQuantity;
	}
	@Column(name = "executed_price", nullable = false, precision = 6)
	public Float getExecutedPrice() {
		return executedPrice;
	}

	public void setExecutedPrice(Float executedPrice) {
		this.executedPrice = executedPrice;
	}
}