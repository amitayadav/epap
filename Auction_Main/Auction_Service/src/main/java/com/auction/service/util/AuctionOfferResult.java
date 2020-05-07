package com.auction.service.util;

import java.util.List;

import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;

public class AuctionOfferResult {
	 //code  
	  AuctionSellersBean auctionSellersBean;
	  List<AuctionBuyersBean> selectedBuyersBeans;
	  List<AuctionBuyersBean> rejectedBuyersBeans;
	  boolean isSellerQtyStatified =false;
	  
	/**
	 * @param auctionSellersBean
	 * @param auctionBuyersBeans
	 */
	/**
	 * @param auctionSellersBean
	 * @param selectedBuyersBeans
	 * @param rejectedBuyersBeans
	 */
	  public AuctionOfferResult() {
		  
	  }
	  
	public AuctionOfferResult(AuctionSellersBean auctionSellersBean, List<AuctionBuyersBean> selectedBuyersBeans, List<AuctionBuyersBean> rejectedBuyersBeans) {
		super();
		this.auctionSellersBean = auctionSellersBean;
		this.selectedBuyersBeans = selectedBuyersBeans;
		this.rejectedBuyersBeans = rejectedBuyersBeans;
	}
	public AuctionSellersBean getAuctionSellersBean() {
		return auctionSellersBean;
	}
	public void setAuctionSellersBean(AuctionSellersBean auctionSellersBean) {
		this.auctionSellersBean = auctionSellersBean;
	}
	public List<AuctionBuyersBean> getSelectedBuyersBeans() {
		return selectedBuyersBeans;
	}
	public void setSelectedBuyersBeans(List<AuctionBuyersBean> selectedBuyersBeans) {
		this.selectedBuyersBeans = selectedBuyersBeans;
	}
	public List<AuctionBuyersBean> getRejectedBuyersBeans() {
		return rejectedBuyersBeans;
	}
	public void setRejectedBuyersBeans(List<AuctionBuyersBean> rejectedBuyersBeans) {
		this.rejectedBuyersBeans = rejectedBuyersBeans;
	}
	public boolean isSellerQtyStatified() {
		return isSellerQtyStatified;
	}
	public void setSellerQtyStatified(boolean isSellerQtyStatified) {
		this.isSellerQtyStatified = isSellerQtyStatified;
	}
	
	

 
	
	
}
