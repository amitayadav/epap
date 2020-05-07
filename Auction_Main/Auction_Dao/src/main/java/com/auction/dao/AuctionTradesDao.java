package com.auction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.AuctionTradeGroupView;
import com.auction.model.entity.AuctionTrades;

@Repository
public interface AuctionTradesDao extends GenericDao<AuctionTrades, Integer>{
	 
	 //@Query(value = "SELECT at FROM AuctionTrades at WHERE  at.id.logTimestamp  between  :startDate  AND  :endDate ORDER BY  at.id.logTimestamp  DESC ")
	 @Query(value = "SELECT at FROM AuctionTrades at WHERE  DATE_FORMAT(at.id.logTimestamp,'%Y-%m-%d') >= DATE_FORMAT(:startDate,'%Y-%m-%d') and DATE_FORMAT(at.id.logTimestamp,'%Y-%m-%d')  <= DATE_FORMAT(:endDate,'%Y-%m-%d')  ORDER BY  at.id.logTimestamp  DESC ")
	 public List<AuctionTrades> getAuctionTradesBetweenDate(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);

	 @Query("SELECT at FROM AuctionTrades at WHERE  at.id.logTimestamp between :startDate AND  :endDate    ORDER BY  at.id.logTimestamp  DESC")
	 public List<AuctionTrades> getAuctionTradesDate( @Param("startDate") Date startDate ,@Param("endDate")Date endDate);

	 @Query("SELECT new com.auction.model.entity.AuctionTradeGroupView(at.id.logTimestamp, at.id.productCatalog.productId, sum(at.soldQuantity), avg(at.soldPrice)) FROM AuctionTrades at GROUP BY at.id.productCatalog.productId, DATE(at.id.logTimestamp) ")
	 public List<AuctionTradeGroupView> findAllAuctionTrades();
	 
	 @Query("SELECT new com.auction.model.entity.AuctionTradeGroupView(at.id.logTimestamp, at.id.productCatalog.productId, sum(at.soldQuantity), avg(at.soldPrice)) FROM AuctionTrades at WHERE at.id.productCatalog.productId =:productId GROUP BY at.id.productCatalog.productId, DATE(at.id.logTimestamp) ")
	 public List<AuctionTradeGroupView> findAllAuctionTradesById(@Param("productId") Integer productId);
}