package com.auction.service;

import com.auction.model.bean.SellerLogBean;
import com.auction.service.generic.GenericService;

public interface SellerLogService extends GenericService<SellerLogBean, Integer> {

	public Short getOfferOperation(Integer auctionSellersId, Integer dailyAuctionsId, Short offerOperation);

	public Boolean countShippingChargeByAuctionSeller(Integer auctionSellersId);
}