package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.SellerOfferInfoDao;
import com.auction.model.bean.SellerOfferInfoBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.ProductCatalog;
import com.auction.model.entity.SellerOfferInfo;
import com.auction.service.SellerOfferInfoService;

@Service
@Transactional
public class SellerOfferInfoServiceImpl implements SellerOfferInfoService {

	@Autowired
	SellerOfferInfoDao sellerOfferInfoDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(SellerOfferInfoBean bean) {
		logger.info("SellerOfferInfoServiceImpl call save  method");
		SellerOfferInfo entity = sellerOfferInfoDao.save(convertBeanToEntity(bean));
		return (null != entity && null != entity.getSellerOfferInfoId() ? entity.getSellerOfferInfoId() : null);
	}

	@Override
	public SellerOfferInfoBean findById(Integer id) {
		logger.info("SellerOfferInfoServiceImpl call findById  method");
		return new SellerOfferInfoBean(sellerOfferInfoDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("SellerOfferInfoServiceImpl call exists  method");
		return sellerOfferInfoDao.exists(id);
	}

	@Override
	public List<SellerOfferInfoBean> findAll() {
		logger.info("SellerOfferInfoServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerOfferInfoDao.findAll());
	}

	@Override
	public List<SellerOfferInfoBean> findAll(Iterable<Integer> ids) {
		logger.info("SellerOfferInfoServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerOfferInfoDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("SellerOfferInfoServiceImpl call count  method");
		return sellerOfferInfoDao.count();
	}

	@Override
	public void delete(SellerOfferInfoBean bean) {
		logger.info("SellerOfferInfoServiceImpl call delete  method");
		sellerOfferInfoDao.delete(bean.getSellerOfferInfoId());
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public List<SellerOfferInfoBean> findByAccountProfileAccountIdAndProductCatalogProductId(Integer accountId,
			Integer productId) {
		logger.info("SellerOfferInfoServiceImpl call findByAccountProfileAccountIdAndProductCatalogProductId  method");
		return convertEntityListToBeanList(sellerOfferInfoDao.findByAccountProfileAccountIdAndProductCatalogProductId(accountId, productId));
	}

	private SellerOfferInfo convertBeanToEntity(SellerOfferInfoBean bean) {
		logger.info("SellerOfferInfoServiceImpl call convertBeanToEntity  method");
		if (null != bean) {
			SellerOfferInfo entity = new SellerOfferInfo();

			entity.setSellerOfferInfoId(bean.getSellerOfferInfoId());
			entity.setInfoLine1(bean.getInfoLine1());
			entity.setInfoLine2(bean.getInfoLine2());
			if (null != bean.getAccountProfileBean()) {
				entity.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			}
			if (null != bean.getProductCatalogBean()) {
				entity.setProductCatalog(new ProductCatalog(bean.getProductCatalogBean().getProductId()));
			}
			return entity;
		}
		return null;
	}
	
	private List<SellerOfferInfoBean> convertEntityListToBeanList(List<SellerOfferInfo> list) {
		logger.info("SellerOfferInfoServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<SellerOfferInfoBean> sellerOfferInfoBeanList = new ArrayList<SellerOfferInfoBean>(0);
			for (SellerOfferInfo sellerOfferInfo : list) {
				sellerOfferInfoBeanList.add(new SellerOfferInfoBean(sellerOfferInfo));
			}
			return sellerOfferInfoBeanList;
		}
		return null;
	}

}
