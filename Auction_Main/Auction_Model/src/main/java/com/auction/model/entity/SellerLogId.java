package com.auction.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class SellerLogId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private AuctionSellers auctionSellers;
	private Date logTimestamp;
	private Short offerOperation;

	public SellerLogId() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "auction_sellers_id", nullable = false, insertable =
	// false, updatable = false)
	public AuctionSellers getAuctionSellers() {
		return this.auctionSellers;
	}

	public void setAuctionSellers(AuctionSellers auctionSellers) {
		this.auctionSellers = auctionSellers;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "log_timestamp", nullable = false, length = 26)
	public Date getLogTimestamp() {
		return this.logTimestamp;
	}

	public void setLogTimestamp(Date logTimestamp) {
		this.logTimestamp = logTimestamp;
	}

	@Column(name = "offer_operation", nullable = false)
	public Short getOfferOperation() {
		return this.offerOperation;
	}

	public void setOfferOperation(Short offerOperation) {
		this.offerOperation = offerOperation;
	}

}