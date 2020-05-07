package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.BuyerLogId;

public class BuyerLogIdBean {

	private AuctionBuyersBean auctionBuyersBean;
	private Date logTimestamp;
	private Short bidOperation;

	public BuyerLogIdBean() {
	}

	public BuyerLogIdBean(BuyerLogId entity) {
		if (null != entity) {
			this.auctionBuyersBean = new AuctionBuyersBean(entity.getAuctionBuyers());
			this.logTimestamp = entity.getLogTimestamp();
			this.bidOperation = entity.getBidOperation();
		}
	}

	public AuctionBuyersBean getAuctionBuyersBean() {
		return auctionBuyersBean;
	}

	public void setAuctionBuyersBean(AuctionBuyersBean auctionBuyersBean) {
		this.auctionBuyersBean = auctionBuyersBean;
	}

	public Date getLogTimestamp() {
		return logTimestamp;
	}

	public void setLogTimestamp(Date logTimestamp) {
		this.logTimestamp = logTimestamp;
	}

	public Short getBidOperation() {
		return bidOperation;
	}

	public void setBidOperation(Short bidOperation) {
		this.bidOperation = bidOperation;
	}
}