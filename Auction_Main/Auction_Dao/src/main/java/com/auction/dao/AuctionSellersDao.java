package com.auction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.AuctionBuyers;
import com.auction.model.entity.AuctionSellers;

@Repository
public interface AuctionSellersDao extends GenericDao<AuctionSellers, Integer> {

	@Query("SELECT count(ass.auctionSellersId) FROM AuctionSellers ass INNER JOIN ass.dailyAuctions das WHERE das.dailyAuctionsId=:dailyAuctionsId")
	public Integer getCountByDailyAuctionsId(@Param("dailyAuctionsId") Integer dailyAuctionsId);
	
	public List<AuctionSellers> findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatusIn(Integer dailyAuctionsId, Short[] sellerOfferStatus);
	
	
	@Query("SELECT abs  FROM AuctionSellers abs WHERE abs.dailyAuctions.dailyAuctionsId=:dailyAuctionsId and abs.sellerOfferStatus in :sellerOfferStatus ORDER BY (CASE WHEN abs.offerPrice = '0' THEN 1 ELSE 0 END), abs.offerPrice , abs.offerUpdatedTime ASC")
	public List<AuctionSellers> findByDailyAuctionsDailyAuctionsIdAndSellerOfferInAscOfferUpdatedTime(@Param("dailyAuctionsId")  Integer dailyAuctionsId, @Param("sellerOfferStatus")  Short[] sellerOfferStatus);
	
	
	public List<AuctionSellers> findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatus(Integer dailyAuctionsId, Short sellerOfferStatus);
	
	public List<AuctionSellers> findByDailyAuctionsDailyAuctionsIdAndAccountProfileAccountId(Integer dailyAuctionsId, Integer accountId);

	@Modifying
	@Query("UPDATE AuctionSellers SET sellerOfferStatus = :sellerOfferStatus WHERE auctionSellersId = :auctionSellersId")
	public void updateSellerOfferStatusByAuctionSellersId(@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionSellersId") Integer auctionSellersId);
	
	@Modifying
	@Query("UPDATE AuctionSellers SET sellerOfferStatus = :sellerOfferStatus, actualEndTime =:actualEndTime  WHERE auctionSellersId = :auctionSellersId")
	public void updateSellerOfferStatusByAuctionSellersId(@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionSellersId") Integer auctionSellersId,@Param("actualEndTime") Date actualEndTime);
	
	@Query("SELECT ass FROM AuctionSellers ass"  
			+ " INNER JOIN ass.dailyAuctions das" 
			+ " INNER JOIN das.auctionStatusCodes ascode"
			+ " WHERE ascode.auctionStatusCode = :auctionStatusCode"
			+ " AND das.dailyAuctionsId = :dailyAuctionsId"
			+ " AND ass.availableQuantity > 0"
			+ " ORDER BY das.beginTime, ass.offerPrice, ass.auctionSellersId")
	public List<AuctionSellers> getAuctionSellersWithRemaingAvailableQuantity(@Param("auctionStatusCode") Short auctionStatusCode, @Param("dailyAuctionsId") Integer dailyAuctionsId);
	
	@Query("SELECT das.dailyAuctionsId FROM AuctionSellers ass"  
			+ " INNER JOIN ass.dailyAuctions das"
			+ " INNER JOIN ass.accountProfile acp"
			+ " WHERE das.beginTime between :beginTime AND :endTime"
			+ " AND acp.accountId=:accountId"
			+ " ORDER BY das.beginTime, das.dailyAuctionsId")
	public List<Integer> getDailyAuctionIdsBySellerAccountId(@Param("accountId") Integer accountId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
	
	@Query("SELECT ass.auctionSellersId FROM AuctionBuyers abs"
			+ " INNER JOIN abs.auctionSellers ass"
			+ " INNER JOIN abs.dailyAuctions das"
			+ " INNER JOIN abs.accountProfile acp"
			+ " WHERE das.dailyAuctionsId=:dailyAuctionsId"
			+ " AND acp.accountId=:accountId"
			+ " ORDER BY das.beginTime, ass.auctionSellersId")
	public List<Integer> getAuctionSellerIdsByBuyerAccountId(@Param("accountId") Integer accountId, @Param("dailyAuctionsId") Integer dailyAuctionsId);
	
	@Query("SELECT count(ass.auctionSellersId) FROM AuctionSellers ass"
			+ " INNER JOIN ass.dailyAuctions das"
			+ " INNER JOIN ass.accountProfile acp"
			+ " WHERE das.dailyAuctionsId=:dailyAuctionsId"
			+ " AND acp.accountId=:sellerAccountId")
	public Integer getAuctionSellersByDailyAuctionsId(@Param("dailyAuctionsId") Integer dailyAuctionsId, @Param("sellerAccountId") Integer sellerAccountId);
	
	@Query("SELECT DISTINCT(ass.sellerOfferStatus) FROM AuctionSellers ass"
			+ " INNER JOIN ass.dailyAuctions das"
			+ " WHERE das.dailyAuctionsId=:dailyAuctionsId"
			+ " ORDER BY ass.sellerOfferStatus")
	public List<Short> getSellerOfferStatusByDailyAuctionsId(@Param("dailyAuctionsId") Integer dailyAuctionsId);
	
	@Query("SELECT count(auctionSellersId) FROM AuctionSellers WHERE dailyAuctions.dailyAuctionsId=:dailyAuctionsId and sellerOfferStatus not in :sellerOfferStatus")
	public Integer countDailyAuctionSellerOfferStatusNotIn(@Param("dailyAuctionsId") Integer dailyAuctionsId, @Param("sellerOfferStatus") Short[] sellerOfferStatus);
	
	@Query("SELECT count(auctionSellersId) FROM AuctionSellers WHERE dailyAuctions.dailyAuctionsId=:dailyAuctionsId and sellerOfferStatus in :sellerOfferStatus")
	public Integer countDailyAuctionSellerOfferStatusIn(@Param("dailyAuctionsId") Integer dailyAuctionsId, @Param("sellerOfferStatus") Short[] sellerOfferStatus);

	@Query("SELECT ass"
			+ " FROM AuctionSellers ass"
			+ " INNER JOIN ass.dailyAuctions das"
			+ " INNER JOIN das.auctionStatusCodes ascode"
			+ " WHERE ass.sellerOfferStatus = :sellerOfferStatus"
			+ " AND ascode.auctionStatusCode = :auctionStatusCode"
			+ " AND das.beginTime = :beginTime "
			+ " AND (ass.offerPrice > 0)")
	public List<AuctionSellers> getAuctionSellersWithLimitPriceByBeginTime(@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionStatusCode") Short auctionStatusCode, @Param("beginTime") Date beginTime);


}