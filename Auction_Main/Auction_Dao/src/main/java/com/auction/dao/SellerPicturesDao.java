package com.auction.dao;

import java.sql.Blob;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.SellerPictures;

@Repository
public interface SellerPicturesDao extends GenericDao<SellerPictures, Integer> {

	@Query("SELECT sp FROM SellerPictures sp INNER JOIN sp.accountProfile ap WHERE ap.accountId = :accountId")
	public List<SellerPictures> getByAccountProfileId(@Param("accountId") Integer accountId);

	@Query("SELECT s.contents FROM SellerPictures s WHERE s.pictureLocation =:pictureLocation and s.accountProfile.accountId =:accountId")
	public Blob  getBySellerInfoPictures(@Param("pictureLocation") String pictureLocation ,@Param("accountId") Integer accountId);
	
}
