package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.SellerLogId;

public class SellerLogIdBean {

	private AuctionSellersBean auctionSellersBean;
	private Date logTimestamp;
	private Short offerOperation;

	public SellerLogIdBean() {
	}

	public SellerLogIdBean(SellerLogId entity) {

		if (null != entity) {
			this.auctionSellersBean = new AuctionSellersBean(entity.getAuctionSellers());
			this.logTimestamp = entity.getLogTimestamp();
			this.offerOperation = entity.getOfferOperation();
		}
	}

	public AuctionSellersBean getAuctionSellersBean() {
		return auctionSellersBean;
	}

	public void setAuctionSellersBean(AuctionSellersBean auctionSellersBean) {
		this.auctionSellersBean = auctionSellersBean;
	}

	public Date getLogTimestamp() {
		return logTimestamp;
	}

	public void setLogTimestamp(Date logTimestamp) {
		this.logTimestamp = logTimestamp;
	}

	public Short getOfferOperation() {
		return offerOperation;
	}

	public void setOfferOperation(Short offerOperation) {
		this.offerOperation = offerOperation;
	}

}