package com.auction.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.BuyerLog;

@Repository
public interface BuyerLogDao extends GenericDao<BuyerLog, Integer> {

	@Query("SELECT bl.id.bidOperation"
			+ " FROM BuyerLog bl"
			+ " WHERE bl.id.auctionBuyers.auctionBuyersId = :auctionBuyersId" 
			+ "	AND bl.dailyAuctions.dailyAuctionsId = :dailyAuctionsId" 
			+ " AND bl.id.bidOperation = :bidOperation")
		public Short getBidOperation(@Param("auctionBuyersId") Integer auctionBuyersId, @Param("dailyAuctionsId") Integer dailyAuctionsId, @Param("bidOperation") Short bidOperation);
	
	@Query("SELECT Max(bidPrice) FROM BuyerLog WHERE  dailyAuctions.dailyAuctionsId = :dailyAuctionsId AND accountProfile.accountId = :accountId AND id.auctionBuyers.auctionBuyersId = :auctionBuyersId")
	public Float getBidPriceByBuyerAccountId(@Param("accountId") Integer accountId, @Param("dailyAuctionsId") Integer dailyAuctionsId,@Param("auctionBuyersId") Integer auctionBuyersId);
	
	
}