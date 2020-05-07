package com.auction.dao;

import java.sql.Blob;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.auction.model.entity.ShipperPictures;

@Repository
public interface ShipperPicturesDao  extends GenericDao<ShipperPictures,Integer>{

	@Query("SELECT sp FROM ShipperPictures sp INNER JOIN sp.accountProfile ap WHERE ap.accountId = :accountId")
	public List<ShipperPictures> getByAccountProfileId(@Param("accountId") Integer accountId);

	@Query("SELECT s.contents FROM ShipperPictures s WHERE s.pictureLocation =:pictureLocation and s.accountProfile.accountId =:accountId")
	public Blob  getByShipperInfoPictures(@Param("pictureLocation") String pictureLocation ,@Param("accountId") Integer accountId);

}
