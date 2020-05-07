package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.ProductCatalogDao;
import com.auction.model.bean.ProductCatalogBean;
import com.auction.model.entity.ProductCatalog;
import com.auction.service.ProductCatalogService;

@Service
@Transactional
public class ProductCatalogServiceImpl implements ProductCatalogService {

	@Autowired
	private ProductCatalogDao productCatalogDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(ProductCatalogBean bean) {
		logger.info("ProductCatalogServiceImpl call save method");
		ProductCatalog productCatalog = productCatalogDao.save(convertBeanToEntity(bean));
		return (null != productCatalog ? productCatalog.getProductId() : null);
	}

	@Override
	public ProductCatalogBean findById(Integer id) {
		logger.info("ProductCatalogServiceImpl call findById method");
		return new ProductCatalogBean(productCatalogDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("ProductCatalogServiceImpl call exists method");
		return productCatalogDao.exists(id);
	}

	@Override
	public List<ProductCatalogBean> findAll() {
		logger.info("ProductCatalogServiceImpl call findAll method");
		return convertEntityListToBeanList(productCatalogDao.findAll());
	}

	@Override
	public List<ProductCatalogBean> findAll(Iterable<Integer> ids) {
		logger.info("ProductCatalogServiceImpl call findAll method");
		return convertEntityListToBeanList(productCatalogDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("ProductCatalogServiceImpl call count method");
		return productCatalogDao.count();
	}

	@Override
	public void delete(ProductCatalogBean bean) {
		logger.info("ProductCatalogServiceImpl call delete method");
		productCatalogDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		logger.info("ProductCatalogServiceImpl call refresh method");
		ProductCatalog productCatalog = productCatalogDao.findOne(id);
		return (null != productCatalog ? productCatalog.getProductId() : null);
	}

	@Override
	public List<ProductCatalogBean> getByStatusIn(Short[] statusList) {
		logger.info("ProductCatalogServiceImpl call getByStatusIn method");
		return convertEntityListToBeanList(productCatalogDao.getByStatusIn(statusList));
	}

	@Override
	public Integer getProductId(String productName, String productTypeName) {
		logger.info("ProductCatalogServiceImpl call getProductId method");
		return productCatalogDao.getProductId(productName, productTypeName);
	}
	
	@Override
	public List<String> getProductGroupName() {
		logger.info("ProductCatalogServiceImpl call getProductGroupName method");
		return productCatalogDao.getProductGroupName();
	}

	@Override
	public List<String> getProductName() {
		logger.info("ProductCatalogServiceImpl call getProductGroupName method");
		return productCatalogDao.getProductName();
	}
		
	@Override
	public List<String> getProductTypeName() {
		logger.info("ProductCatalogServiceImpl call getProductTypeName method");
		return productCatalogDao.getProductTypeName();
	}

	@Override
	public Integer getCountByGroupNameTypeId(Integer productId, String productGroupName, String productName, String productTypeName) {
		logger.info("ProductCatalogServiceImpl call getCountByGroupNameTypeId method");
		return productCatalogDao.getCountByGroupNameTypeId(productId,productGroupName, productName, productTypeName);
	}

	private ProductCatalog convertBeanToEntity(ProductCatalogBean bean) {
		logger.info("ProductCatalogServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			ProductCatalog entity = new ProductCatalog();
			entity.setProductId(bean.getProductId());
			entity.setProductName(bean.getProductName());
			entity.setProductDescription(bean.getProductDescription());
			entity.setProductGroupName(bean.getProductGroupName());
			entity.setProductTypeName(bean.getProductTypeName());
			entity.setContainerSpecs(bean.getContainerSpecs());
			entity.setStatus(bean.getStatus());
			return entity;
		}
		return null;
	}

	private List<ProductCatalogBean> convertEntityListToBeanList(List<ProductCatalog> list) {
		logger.info("ProductCatalogServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<ProductCatalogBean> productCatalogBeanList = new ArrayList<ProductCatalogBean>(0);
			for (ProductCatalog productCatalog : list) {
				productCatalogBeanList.add(new ProductCatalogBean(productCatalog));
			}
			return productCatalogBeanList;
		}
		return null;

	}

}