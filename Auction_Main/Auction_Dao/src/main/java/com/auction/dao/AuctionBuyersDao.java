package com.auction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.AuctionBuyers;

@Repository
public interface AuctionBuyersDao extends GenericDao<AuctionBuyers, Integer> {

	@Query("SELECT count(abs.auctionBuyersId) FROM AuctionBuyers abs INNER JOIN abs.auctionSellers ass WHERE ass.auctionSellersId=:auctionSellersId")
	public Integer getCountByAuctionSellersId(@Param("auctionSellersId") Integer auctionSellersId);
	
	public List<AuctionBuyers> findByAuctionSellersAuctionSellersId(Integer auctionSellersId);

	public List<AuctionBuyers> findByAuctionSellersAuctionSellersIdOrderByBidPriceDescAuctionBuyersIdAsc(Integer auctionSellersId);

	public List<AuctionBuyers> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusOrderByBidPriceDescAuctionBuyersIdAsc(Integer auctionSellersId, Short buyerBidStatus);
	
	public List<AuctionBuyers> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInOrderByBidPriceDescAuctionBuyersIdAsc(Integer auctionSellersId, Short[] buyerBidStatus);

	public List<AuctionBuyers> findByAuctionSellersAuctionSellersIdAndAccountProfileAccountId(Integer auctionSellersId, Integer accountId);

	public List<AuctionBuyers> findByAuctionSellersAuctionSellersIdAndBuyerBidStatus(Integer auctionSellersId, Short buyerBidStatus);
 
	public List<AuctionBuyers> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusIn(Integer auctionSellersId, Short[] buyerBidStatus);
	
	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " WHERE abs.auctionSellers.auctionSellersId=:auctionSellersId and abs.buyerBidStatus in :buyerBidStatus  ORDER BY abs.bidPrice DESC, abs.bidUpdatedTime ASC")
	public List<AuctionBuyers> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInAscBidUpdatedTime(@Param("auctionSellersId")  Integer auctionSellersId, @Param("buyerBidStatus")  Short[] buyerBidStatus);
	
	@Modifying
	@Query("UPDATE AuctionBuyers SET buyerBidStatus = :buyerBidStatus WHERE auctionBuyersId = :auctionBuyersId")
	public void updateBidOfferStatusByAuctionBuyersId(@Param("buyerBidStatus") Short buyerBidStatus, @Param("auctionBuyersId") Integer auctionBuyersId);

	
	@Modifying
	@Query("UPDATE AuctionBuyers SET buyerBidStatus = :buyerBidStatus , executedPrice = :executedPrice , actualEndTime = :actualEndTime  WHERE auctionBuyersId = :auctionBuyersId")
	public void updateBidOfferStatusAndExecutedPriceByAuctionBuyersId(@Param("buyerBidStatus") Short buyerBidStatus, @Param("auctionBuyersId") Integer auctionBuyersId,@Param("executedPrice") Float executedPrice,@Param("actualEndTime") Date actualEndTime);
	
	@Modifying
	@Query("UPDATE AuctionBuyers SET bidQuantity = :bidQuantity WHERE auctionBuyersId = :auctionBuyersId")
	public void updateBuyerBidQuantityByAuctionBuyersId(@Param("bidQuantity") Integer bidQuantity,@Param("auctionBuyersId") Integer auctionBuyersId);
	
	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " INNER JOIN abs.auctionSellers ass"
			+ " INNER JOIN abs.dailyAuctions das"
			+ " INNER JOIN das.auctionStatusCodes ascode"
			+ " WHERE abs.buyerBidStatus = :buyerBidStatus"
			+ " AND ass.sellerOfferStatus = :sellerOfferStatus"
			+ " AND ascode.auctionStatusCode = :auctionStatusCode"
			+ " AND das.beginTime = :beginTime "
			+ " AND (ass.offerPrice > 0)"
			+ " AND (abs.bidPrice = ass.offerPrice OR abs.bidPrice > ass.offerPrice)"
			+ " ORDER BY abs.bidPrice DESC, abs.auctionBuyersId ASC")
	public List<AuctionBuyers> getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice(@Param("buyerBidStatus") Short buyerBidStatus,
			@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionStatusCode") Short auctionStatusCode, @Param("beginTime") Date beginTime);
	
	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " INNER JOIN abs.auctionSellers ass"
			+ " INNER JOIN abs.dailyAuctions das"
			+ " INNER JOIN das.auctionStatusCodes ascode"
			+ " WHERE abs.buyerBidStatus = :buyerBidStatus"
			+ " AND ass.sellerOfferStatus = :sellerOfferStatus"
			+ " AND ascode.auctionStatusCode = :auctionStatusCode"
			+ " AND das.beginTime = :beginTime "
			+ " AND (abs.bidPrice = ass.offerPrice OR abs.bidPrice > ass.offerPrice)"
			+ " ORDER BY abs.bidPrice DESC, abs.auctionBuyersId ASC")
	public List<AuctionBuyers> getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithMarketPrice(@Param("buyerBidStatus") Short buyerBidStatus,
			@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionStatusCode") Short auctionStatusCode, @Param("beginTime") Date beginTime);

	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " INNER JOIN abs.auctionSellers ass"
			+ " INNER JOIN abs.dailyAuctions das"
			+ " INNER JOIN das.auctionStatusCodes ascode"
			+ " WHERE abs.buyerBidStatus = :buyerBidStatus"
			+ " AND ass.sellerOfferStatus = :sellerOfferStatus"
			+ " AND ascode.auctionStatusCode = :auctionStatusCode"
			+ " AND das.beginTime = :beginTime "
			+ " AND (ass.offerPrice > 0)"
			+ " ORDER BY abs.bidPrice DESC, abs.auctionBuyersId ASC")
	public List<AuctionBuyers> getAuctionBuyersWithEqualOrLowerBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice(@Param("buyerBidStatus") Short buyerBidStatus,
			@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionStatusCode") Short auctionStatusCode, @Param("beginTime") Date beginTime);	
	
	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " INNER JOIN abs.auctionSellers ass"
			+ " INNER JOIN abs.dailyAuctions das"
			+ " INNER JOIN das.auctionStatusCodes ascode"
			+ " WHERE abs.buyerBidStatus = :buyerBidStatus"
			+ " AND ass.sellerOfferStatus = :sellerOfferStatus"
			+ " AND ascode.auctionStatusCode = :auctionStatusCode"
			+ " AND das.dailyAuctionsId = :dailyAuctionsId"
			+ " AND ass.auctionSellersId = :auctionSellersId"
			+ " AND (abs.bidPrice = ass.offerPrice OR abs.bidPrice > ass.offerPrice)"
			+ " ORDER BY abs.bidPrice DESC, abs.auctionBuyersId ASC")
	public List<AuctionBuyers> getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPrice(@Param("buyerBidStatus") Short buyerBidStatus,
			@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionStatusCode") Short auctionStatusCode, @Param("dailyAuctionsId") Integer dailyAuctionsId, @Param("auctionSellersId") Integer auctionSellersId);
	
	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " INNER JOIN abs.auctionSellers ass"
			+ " INNER JOIN abs.dailyAuctions das"
			+ " INNER JOIN das.auctionStatusCodes ascode"
			+ " WHERE abs.buyerBidStatus = :buyerBidStatus"
			+ " AND ass.sellerOfferStatus = :sellerOfferStatus"
			+ " AND ascode.auctionStatusCode = :auctionStatusCode"
			+ " AND ass.auctionSellersId = :auctionSellersId"
			+ " ORDER BY abs.bidPrice DESC, abs.bidUpdatedTime ASC")
	public List<AuctionBuyers> getAuctionBuyersByStatusByAuctionSellersId(@Param("buyerBidStatus") Short buyerBidStatus,
			@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionStatusCode") Short auctionStatusCode, @Param("auctionSellersId") Integer auctionSellersId);

	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " INNER JOIN abs.auctionSellers ass"
			+ " INNER JOIN abs.dailyAuctions das"
			+ " INNER JOIN das.auctionStatusCodes ascode"
			+ " WHERE abs.buyerBidStatus = :buyerBidStatus"
			+ " AND ass.sellerOfferStatus = :sellerOfferStatus"
			+ " AND ascode.auctionStatusCode = :auctionStatusCode"
			+ " AND ass.auctionSellersId = :auctionSellersId"
			+ " ORDER BY abs.actualStartTime ASC, abs.auctionBuyersId ASC")
	public List<AuctionBuyers> getAuctionBuyersByStatusByAuctionSellersIdAscActualStartTime(@Param("buyerBidStatus") Short buyerBidStatus,
			@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionStatusCode") Short auctionStatusCode, @Param("auctionSellersId") Integer auctionSellersId);
	
	
	@Query("SELECT das.dailyAuctionsId FROM AuctionBuyers abs"  
			+ " INNER JOIN abs.dailyAuctions das"
			+ " INNER JOIN abs.accountProfile acp"
			+ " WHERE das.beginTime between :beginTime AND :endTime"
			+ " AND acp.accountId=:accountId"
			+ " ORDER BY das.beginTime, das.dailyAuctionsId")
	public List<Integer> getDailyAuctionIdsByBuyerAccountId(@Param("accountId")
			Integer accountId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
	
	@Query("SELECT count(abs.auctionBuyersId) FROM AuctionBuyers abs"
			+ " INNER JOIN abs.auctionSellers ass"
			+ " INNER JOIN abs.accountProfile acp"
			+ " WHERE ass.auctionSellersId=:auctionSellersId"
			+ " AND acp.accountId=:accountId")
	public Integer getAuctionBuyersByAuctionSellerId(@Param("auctionSellersId") Integer auctionSellersId, @Param("accountId") Integer accountId);
	
	@Query("SELECT DISTINCT(abs.buyerBidStatus) FROM AuctionBuyers abs"
			+ " INNER JOIN abs.auctionSellers ass"
			+ " WHERE ass.auctionSellersId=:auctionSellersId"
			+ " ORDER BY abs.buyerBidStatus")
	public List<Short> getBuyerBidStatusByAuctionSellerId(@Param("auctionSellersId") Integer auctionSellersId);

	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " INNER JOIN abs.accountProfile acp"
			+ " WHERE abs.buyerBidStatus in :buyerBidStatus"
			+ " AND acp.accountId = :accountId  AND abs.actualStartTime between :beginDate AND :endDate "
			+ "order by abs.auctionBuyersId desc")
		public List<AuctionBuyers> getConfirmDeliveryByBuyerAccountIdIn(@Param("accountId") Integer accountId,@Param("buyerBidStatus") Short[] buyerBidStatus,@Param("beginDate") Date beginDate,@Param("endDate") Date endDate);

	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " INNER JOIN abs.accountProfile acp"
			+ " WHERE abs.buyerBidStatus = :buyerBidStatus"
			+ " AND acp.accountId = :accountId ")
		public List<AuctionBuyers> getConfirmDeliveryByBuyerAccountId(@Param("accountId") Integer accountId,@Param("buyerBidStatus") Short buyerBidStatus);
	
	

	@Query("SELECT abs"
			+ " FROM AuctionBuyers abs"
			+ " WHERE abs.buyerBidStatus in :buyerBidStatus")
		public List<AuctionBuyers> getManageShippingBuyerAccount(@Param("buyerBidStatus") Short[] buyerBidStatus);
	
	@Query("SELECT count(auctionBuyersId) FROM AuctionBuyers WHERE auctionSellers.auctionSellersId=:auctionSellersId and buyerBidStatus = :buyerBidStatus")
	public Integer  countAuctionBuyerByStatus(@Param("auctionSellersId")  Integer auctionSellersId, @Param("buyerBidStatus")  Short buyerBidStatus);
}