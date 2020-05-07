package com.auction.service;

import com.auction.model.bean.SellerTransactionsBean;
import com.auction.service.generic.GenericService;

public interface SellerTransactionsService  extends GenericService<SellerTransactionsBean, Integer> {

	public Boolean countShippingChargeByAuctionSeller(Integer auctionSellersId);
}
