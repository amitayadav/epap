package com.auction.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.AuctionStatusCodes;

@Repository
public interface AuctionStatusCodesDao  extends GenericDao<AuctionStatusCodes, Short>{

	@Query("SELECT ac FROM DailyAuctions da INNER JOIN da.auctionStatusCodes ac WHERE da.dailyAuctionsId=:dailyAuctionsId")
	public AuctionStatusCodes findAuctionStatuscodeByDailyAuctionId(@Param("dailyAuctionsId") Integer dailyAuctionsId);
	
}