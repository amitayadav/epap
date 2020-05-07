package com.auction.model.bean;

import com.auction.model.entity.AuctionStatusCodes;

public class AuctionStatusCodesBean {

	private Short auctionStatusCode;
	private String auctionStatusCodeMeaning;

	public AuctionStatusCodesBean() {
	}

	public AuctionStatusCodesBean(Short auctionStatusCode) {
		this.auctionStatusCode = auctionStatusCode;
	}

	public AuctionStatusCodesBean(AuctionStatusCodes entity) {

		if (null != entity) {
			this.auctionStatusCode = entity.getAuctionStatusCode();
			this.auctionStatusCodeMeaning = entity.getAuctionStatusCodeMeaning();
		}
	}

	public Short getAuctionStatusCode() {
		return auctionStatusCode;
	}

	public void setAuctionStatusCode(Short auctionStatusCode) {
		this.auctionStatusCode = auctionStatusCode;
	}

	public String getAuctionStatusCodeMeaning() {
		return auctionStatusCodeMeaning;
	}

	public void setAuctionStatusCodeMeaning(String auctionStatusCodeMeaning) {
		this.auctionStatusCodeMeaning = auctionStatusCodeMeaning;
	}

}
