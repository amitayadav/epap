package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.SellerInfoDao;
import com.auction.model.bean.SellerInfoBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.SellerInfo;
import com.auction.service.SellerInfoService;

@Service
@Transactional
public class SellerInfoServiceImpl implements SellerInfoService {

	@Autowired
	SellerInfoDao sellerInfoDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(SellerInfoBean bean) {
		logger.info("SellerInfoServiceImpl call save  method");
		SellerInfo sInfo = sellerInfoDao.save(convertBeanToEntity(bean));
		return (null != sInfo.getSellerId() ? sInfo.getSellerId() : null);
	}

	@Override
	public SellerInfoBean findById(Integer id) {
		logger.info("SellerInfoServiceImpl call findById  method");
		return new SellerInfoBean(sellerInfoDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("SellerInfoServiceImpl call exists  method");
		return sellerInfoDao.exists(id);
	}

	@Override
	public List<SellerInfoBean> findAll() {
		logger.info("SellerInfoServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerInfoDao.findAll());
	}

	@Override
	public List<SellerInfoBean> findAll(Iterable<Integer> ids) {
		logger.info("SellerInfoServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerInfoDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("SellerInfoServiceImpl call count  method");
		return sellerInfoDao.count();
	}

	@Override
	public void delete(SellerInfoBean bean) {
		logger.info("SellerInfoServiceImpl call delete  method");
		sellerInfoDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	private SellerInfo convertBeanToEntity(SellerInfoBean bean) {
		logger.info("SellerInfoServiceImpl call convertBeanToEntity  method");
		if (null != bean) {
			SellerInfo sellerInfo = new SellerInfo();
			sellerInfo.setSellerId(bean.getSellerId());
			sellerInfo.setInfoLine1(bean.getInfoLine1());
			sellerInfo.setInfoLine2(bean.getInfoLine2());
			sellerInfo.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			return sellerInfo;
		}
		return null;
	}

	private List<SellerInfoBean> convertEntityListToBeanList(List<SellerInfo> list) {
		logger.info("SellerInfoServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<SellerInfoBean> sellerInfoBeanList = new ArrayList<SellerInfoBean>(0);
			for (SellerInfo sellerInfo : list) {
				sellerInfoBeanList.add(new SellerInfoBean(sellerInfo));
			}
			return sellerInfoBeanList;
		}
		return null;
	}

}
