package com.auction.service;

import java.util.List;

import com.auction.model.bean.ProductCatalogBean;
import com.auction.service.generic.GenericService;

public interface ProductCatalogService extends GenericService<ProductCatalogBean, Integer> {

	public List<ProductCatalogBean> getByStatusIn(Short[] statusList);

	public List<String> getProductGroupName();
	
	public List<String> getProductName();
	
	public List<String> getProductTypeName();
	
	public Integer getProductId(String productName, String productTypeName);

	public Integer getCountByGroupNameTypeId(Integer productId, String productGroupName, String productName, String productTypeName);

}
