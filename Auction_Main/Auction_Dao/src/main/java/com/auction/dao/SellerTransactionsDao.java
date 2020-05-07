package com.auction.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.SellerTransactions;

@Repository
public interface SellerTransactionsDao extends GenericDao<SellerTransactions, Integer> {

@Query("SELECT COUNT(st.transactionId)  > 0  FROM SellerTransactions st  WHERE st.auctionBuyers.auctionSellers.auctionSellersId =:auctionSellersId AND st.sellerShippingCharge > 0")
public Boolean countShippingChargeByAuctionSeller(@Param("auctionSellersId") Integer auctionSellersId);
	
}
