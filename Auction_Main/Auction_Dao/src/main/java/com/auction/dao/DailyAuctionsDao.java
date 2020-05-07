package com.auction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.DailyAuctions;

@Repository
public interface DailyAuctionsDao extends GenericDao<DailyAuctions, Integer> {

	@Modifying
	@Query("UPDATE DailyAuctions SET auctionStatusCodes.auctionStatusCode = :auctionStatusCodes WHERE beginTime = :beginDate")
	public void updateAuctionStatusBasedOnBeginTime(@Param("auctionStatusCodes") Short auctionStatusCodes, @Param("beginDate") Date beginDate);

	public List<DailyAuctions> findByAuctionStatusCodesAuctionStatusCodeAndBeginTime(Short auctionStatusCode, Date date);
	
	@Query("SELECT da FROM DailyAuctions da INNER JOIN da.auctionStatusCodes ascode WHERE ascode.auctionStatusCode = :auctionStatusCode AND da.endTime = :endDate")
	public List<DailyAuctions> getByAuctionStatusCodesAuctionStatusCodeAndEndTime(@Param("auctionStatusCode") Short auctionStatusCode, @Param("endDate") Date endDate);

	@Query("SELECT da FROM DailyAuctions da INNER JOIN da.auctionStatusCodes ascode WHERE ascode.auctionStatusCode IN (:auctionStatusCode) AND da.beginTime between :startDate AND :endDate ORDER BY  da.dailyAuctionsId  desc ")
	public List<DailyAuctions> findByAuctionStatusCodeAndTodayDate(@Param("auctionStatusCode") Short[] auctionStatusCode, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
	
	@Query("SELECT da FROM DailyAuctions da"
			+ " INNER JOIN da.auctionStatusCodes ascode"
			+ " INNER JOIN da.productCatalog prod"
			+ " WHERE da.dailyAuctionsId!=:dailyAuctionsId"
			+ " AND ascode.auctionStatusCode NOT IN (:auctionStatusCode)"
			+ " AND da.beginTime between :startDate AND :endDate"
			+ " AND prod.productGroupName=:productGroupName"
			+ " AND prod.productName=:productName"
			+ " AND prod.productTypeName=:productTypeName")
	public List<DailyAuctions> findByDuplicateAuction(@Param("dailyAuctionsId") Integer dailyAuctionsId, @Param("auctionStatusCode") Short[] auctionStatusCode, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("productGroupName") String productGroupName, @Param("productName") String productName, @Param("productTypeName") String productTypeName);

	@Modifying
	@Query("UPDATE DailyAuctions SET auctionStatusCodes.auctionStatusCode = :auctionStatusCodes WHERE dailyAuctionsId = :dailyAuctionsId")
	public void updateAuctionStatusBasedOnDailyAuctionsId(@Param("auctionStatusCodes") Short auctionStatusCodes, @Param("dailyAuctionsId") Integer dailyAuctionsId);
	
}
