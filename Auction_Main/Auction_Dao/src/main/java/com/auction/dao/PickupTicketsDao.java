package com.auction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.PickupTickets;

@Repository
public interface PickupTicketsDao extends GenericDao<PickupTickets, Integer>{

	@Query("FROM PickupTickets WHERE sellerId.accountId =:sellerId")
	public List<PickupTickets> findPickupTicketsBySellerAccountId(@Param("sellerId") Integer sellerId);

	@Query("FROM PickupTickets WHERE buyerId.accountId =:buyerId  order by auctionBuyerId.auctionBuyersId desc")
	public List<PickupTickets> findPickupTicketsByBuyerAccountId(@Param("buyerId") Integer buyerId);

	@Modifying
	@Query("UPDATE PickupTickets SET action = :action , updatebyId = :updatebyId , updatedOn = :updatedOn  WHERE buyerId.accountId = :buyerId AND sellerId.accountId =:sellerId AND dailyAuctionsid.dailyAuctionsId =:dailyAuctionsid")
	public void updatePickupTicketActionByBuyerAccountId(@Param("action") Integer action,@Param("updatebyId") Integer updatebyId,@Param("updatedOn") Date updatedOn,@Param("buyerId") Integer buyerId,@Param("sellerId") Integer sellerId,@Param("dailyAuctionsid") Integer dailyAuctionsid);

	
	@Query("FROM PickupTickets WHERE buyerId.accountId =:buyerId and ptn IS NOT NULL order by auctionBuyerId.auctionBuyersId desc")
	public List<PickupTickets>getConfirmDeliveryByBuyerAccountIdIn(@Param("buyerId") Integer buyerId);
	
	@Query("FROM PickupTickets WHERE  ptn IS NOT NULL order by tradeTime desc")
	public List<PickupTickets> getManageShippingPickupTickets();
}
