package com.auction.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class BuyerLogId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private AuctionBuyers auctionBuyers;
	private Date logTimestamp;
	private Short bidOperation;

	public BuyerLogId() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "auction_buyers_id", nullable = false, insertable = false,
	// updatable = false)
	public AuctionBuyers getAuctionBuyers() {
		return this.auctionBuyers;
	}

	public void setAuctionBuyers(AuctionBuyers auctionBuyers) {
		this.auctionBuyers = auctionBuyers;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "log_timestamp", nullable = false, length = 26)
	public Date getLogTimestamp() {
		return this.logTimestamp;
	}

	public void setLogTimestamp(Date logTimestamp) {
		this.logTimestamp = logTimestamp;
	}

	@Column(name = "bid_operation", nullable = false)
	public Short getBidOperation() {
		return this.bidOperation;
	}

	public void setBidOperation(Short bidOperation) {
		this.bidOperation = bidOperation;
	}
}