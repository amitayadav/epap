package com.auction.service;

import java.sql.Blob;
import java.util.List;
import com.auction.model.bean.SellerOfferPicturesBean;
import com.auction.service.generic.GenericService;

public interface SellerOfferPicturesService extends GenericService<SellerOfferPicturesBean, Integer> {

	public List<SellerOfferPicturesBean> findByAccountProfileAccountIdAndProductCatalogProductId(Integer accountId,
			Integer productId);
	
			public Blob  getSellerOfferProductImg( String pictureLocation,Integer accountId);
}
