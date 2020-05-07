package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.ProductCatalog;

@Repository
public interface ProductCatalogDao extends GenericDao<ProductCatalog, Integer> {

	public List<ProductCatalog> getByStatusIn(Short[] statusList);

	@Query("SELECT distinct(pc.productGroupName) FROM ProductCatalog pc")
	public List<String> getProductGroupName();

	@Query("SELECT distinct(pc.productName) FROM ProductCatalog pc")
	public List<String> getProductName();
	
	@Query("SELECT distinct(pc.productTypeName) FROM ProductCatalog pc")
	public List<String> getProductTypeName();

	@Query("SELECT count(pc) FROM ProductCatalog pc WHERE pc.productId !=:productId AND pc.productGroupName=:productGroupName AND pc.productName=:productName AND pc.productTypeName=:productTypeName")
	public Integer getCountByGroupNameTypeId(@Param("productId") Integer productId, @Param("productGroupName") String productGroupName, @Param("productName") String productName,
			@Param("productTypeName") String productTypeName);

	@Query("SELECT productId FROM ProductCatalog pc WHERE pc.productName=:productName AND pc.productTypeName=:productTypeName")
	public Integer getProductId(@Param("productName") String productName, @Param("productTypeName") String productTypeName); 

}