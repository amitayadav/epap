package com.auction.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.SellerLog;

@Repository
public interface SellerLogDao extends GenericDao<SellerLog, Integer> {

	
	
	@Query("SELECT sl.id.offerOperation"
		+ " FROM SellerLog sl"
		+ " WHERE sl.id.auctionSellers.auctionSellersId = :auctionSellersId" 
		+ "	AND sl.dailyAuctions.dailyAuctionsId = :dailyAuctionsId" 
		+ " AND sl.id.offerOperation = :offerOperation")
	public Short getOfferOperation(@Param("auctionSellersId") Integer auctionSellersId, @Param("dailyAuctionsId") Integer dailyAuctionsId, @Param("offerOperation") Short offerOperation);
	
	@Query("SELECT COUNT(sl.sellerShippingCharge) > 0  FROM SellerLog sl WHERE sl.id.auctionSellers.auctionSellersId = :auctionSellersId AND  sl.id.offerOperation = 3")
	public Boolean countShippingChargeByAuctionSeller(@Param("auctionSellersId") Integer auctionSellersId);
}
