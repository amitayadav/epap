package com.auction.service;

import java.sql.Blob;
import java.util.List;

import com.auction.model.bean.SellerPicturesBean;
import com.auction.service.generic.GenericService;

public interface SellerPicturesService extends GenericService<SellerPicturesBean, Integer> {

	public List<SellerPicturesBean> getByAccountProfileId(Integer accountId);
	
	public Blob  getBySellerInfoPictures(String pictureLocation , Integer accountId);
}
