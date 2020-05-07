package com.auction.service;

import com.auction.model.bean.BuyerLogBean;
import com.auction.service.generic.GenericService;

public interface BuyerLogService  extends GenericService<BuyerLogBean, Integer> {

	public Short getBidOperation(Integer auctionBuyersId, Integer dailyAuctionsId, Short bidOperation);
	
	public Float getBidPriceByBuyerAccountId(Integer accountId, Integer dailyAuctionsId, Integer auctionBuyersId);
}