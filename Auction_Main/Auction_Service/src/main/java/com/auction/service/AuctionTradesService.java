package com.auction.service;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.query.Param;

import com.auction.model.bean.AuctionTradesBean;
import com.auction.model.entity.AuctionTradeGroupView;
import com.auction.model.entity.AuctionTrades;
import com.auction.model.views.AuctionTradesView;
import com.auction.service.generic.GenericService;

public interface AuctionTradesService extends GenericService<AuctionTradesBean, Integer> {
	 
	public List<AuctionTradesView> getAuctionTradesBetweenDate(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);
	
	public List<AuctionTradesBean> getAuctionTradesBeanBetweenDate(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);
	
	public List<AuctionTradesBean> getAuctionTradesDate(Date startDate, Date endDate);
	
	public List<AuctionTradeGroupView> findAllAuctionTrades();
	
	public List<AuctionTradeGroupView> findAllAuctionTradesById(Integer productId);
	
}