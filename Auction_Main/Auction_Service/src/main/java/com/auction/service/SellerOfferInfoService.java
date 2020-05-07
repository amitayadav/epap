package com.auction.service;

import java.util.List;

import com.auction.model.bean.SellerOfferInfoBean;
import com.auction.service.generic.GenericService;

public interface SellerOfferInfoService extends GenericService<SellerOfferInfoBean, Integer> {

	public List<SellerOfferInfoBean> findByAccountProfileAccountIdAndProductCatalogProductId(Integer accountId,
			Integer productId);

}