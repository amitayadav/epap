package com.auction.service.util;

import com.auction.model.bean.AuctionBuyersBean;

public class AuctionBidResult {
	 //code  
	AuctionBuyersBean auctionBuyersBean;
	 boolean result;
	 
		public AuctionBidResult(AuctionBuyersBean auctionBuyersBean, boolean result) {
			super();
			this.auctionBuyersBean = auctionBuyersBean;
			this.result = result;
		}
	public AuctionBuyersBean getAuctionBuyersBean() {
		return auctionBuyersBean;
	}
	public void setAuctionBuyersBean(AuctionBuyersBean auctionBuyersBean) {
		this.auctionBuyersBean = auctionBuyersBean;
	}

	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
 
	 
 
	
	
}
