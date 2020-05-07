package com.auction.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.auction.model.entity.SellerOfferInfo;

@Repository
public interface SellerOfferInfoDao extends GenericDao<SellerOfferInfo, Integer> {

	public List<SellerOfferInfo> findByAccountProfileAccountIdAndProductCatalogProductId(Integer accountId,
			Integer productId);
	
}