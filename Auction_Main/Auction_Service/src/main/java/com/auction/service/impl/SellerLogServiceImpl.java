package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.SellerLogDao;
import com.auction.model.bean.SellerLogBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionSellers;
import com.auction.model.entity.DailyAuctions;
import com.auction.model.entity.SellerLog;
import com.auction.model.entity.SellerLogId;
import com.auction.service.SellerLogService;

@Service
@Transactional
public class SellerLogServiceImpl implements SellerLogService {

	@Autowired
	private SellerLogDao sellerLogDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(SellerLogBean bean) {
		logger.info("SellerLogServiceImpl call save  method");
		SellerLog sellerLog = sellerLogDao.save(convertBeanToEntity(bean));
		return (null != sellerLog ? sellerLog.getAccountProfile().getAccountId() : null);
	}

	@Override
	public SellerLogBean findById(Integer id) {
		logger.info("SellerLogServiceImpl call findById  method");
		return new SellerLogBean(sellerLogDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("SellerLogServiceImpl call exists  method");
		return sellerLogDao.exists(id);
	}

	@Override
	public List<SellerLogBean> findAll() {
		logger.info("SellerLogServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerLogDao.findAll());
	}

	@Override
	public List<SellerLogBean> findAll(Iterable<Integer> ids) {
		logger.info("SellerLogServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerLogDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("SellerLogServiceImpl call count  method");
		return sellerLogDao.count();
	}

	@Override
	public void delete(SellerLogBean bean) {
		logger.info("SellerLogServiceImpl call delete  method");
		sellerLogDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public Short getOfferOperation(Integer auctionSellersId, Integer dailyAuctionsId, Short offerOperation) {
		logger.info("SellerLogServiceImpl call getOfferOperation  method");
		return sellerLogDao.getOfferOperation(auctionSellersId, dailyAuctionsId, offerOperation);
	}
	@Override
	public Boolean countShippingChargeByAuctionSeller(Integer auctionSellersId) {
		logger.info("SellerLogServiceImpl call countShippingChargeByAuctionSeller  method");
		return sellerLogDao.countShippingChargeByAuctionSeller(auctionSellersId);
	}
	private SellerLog convertBeanToEntity(SellerLogBean bean) {
		logger.info("SellerLogServiceImpl call convertBeanToEntity  method");
		if (null != bean) {
			SellerLogId sellerLogId = new SellerLogId();
			sellerLogId.setLogTimestamp(bean.getId().getLogTimestamp());
			sellerLogId.setAuctionSellers(new AuctionSellers(bean.getId().getAuctionSellersBean().getAuctionSellersId()));
			sellerLogId.setOfferOperation(bean.getId().getOfferOperation());

			SellerLog sellerLog = new SellerLog(sellerLogId);
			sellerLog.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			if (null != bean.getShipperAccountProfileBean() && null != bean.getShipperAccountProfileBean().getAccountId()
					&& bean.getShipperAccountProfileBean().getAccountId() > 0) {
				sellerLog.setShipperAccountProfile(new AccountProfile(bean.getShipperAccountProfileBean().getAccountId()));
			}
			sellerLog.setDailyAuctions(new DailyAuctions(bean.getDailyAuctionsBean().getDailyAuctionsId()));
			sellerLog.setOfferPrice(bean.getOfferPrice());
			sellerLog.setOfferQuantity(bean.getOfferQuantity());
			sellerLog.setMinimumQuantity(bean.getMinimumQuantity());
			sellerLog.setAvailableQuantity(bean.getAvailableQuantity());
			sellerLog.setSellerShippingCharge((null != bean.getSellerShippingCharge() ? bean.getSellerShippingCharge() : 0F));
			sellerLog.setTruckNumber(bean.getTruckNumber());
			return sellerLog;
		}
		return null;
	}

	private List<SellerLogBean> convertEntityListToBeanList(List<SellerLog> list) {
		logger.info("SellerLogServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<SellerLogBean> sellerLogBeansList = new ArrayList<SellerLogBean>(0);
			for (SellerLog sellerLog : list) {
				sellerLogBeansList.add(new SellerLogBean(sellerLog));
			}
			return sellerLogBeansList;
		}
		return null;
	}



}