package com.auction.service;

import com.auction.model.bean.AuctionStatusCodesBean;
import com.auction.service.generic.GenericService;

public interface AuctionStatusCodesService extends GenericService<AuctionStatusCodesBean, Short> {

	public AuctionStatusCodesBean findAuctionStatuscodeByDailyAuctionId(Integer dailyAuctionsId);

}