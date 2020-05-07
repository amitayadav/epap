package com.auction.service;

import java.sql.Blob;
import java.util.List;

import com.auction.model.bean.ShipperPicturesBean;
import com.auction.service.generic.GenericService;

public interface ShipperPicturesService extends GenericService<ShipperPicturesBean,Integer>{
	
	public List<ShipperPicturesBean> getByAccountProfileId(Integer accountId);
	
	public Blob getByShipperInfoPictures(String pictureLocation , Integer accountId);
}
