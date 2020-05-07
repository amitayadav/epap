package com.auction.dao;

import java.sql.Blob;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.SellerOfferPictures;

@Repository 
public interface SellerOfferPictureDao extends GenericDao<SellerOfferPictures, Integer>{

	public List<SellerOfferPictures> findByAccountProfileAccountIdAndProductCatalogProductId(Integer accountId,
			Integer productId);
	
	@Query("SELECT s.contents FROM SellerOfferPictures s WHERE s.pictureLocation =:pictureLocation and s.accountProfile.accountId =:accountId")
	public Blob  getSellerOfferProductImg(@Param("pictureLocation") String pictureLocation ,@Param("accountId") Integer accountId);
}
