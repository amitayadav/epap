package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.BuyerLogDao;
import com.auction.model.bean.BuyerLogBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionBuyers;
import com.auction.model.entity.BuyerLog;
import com.auction.model.entity.BuyerLogId;
import com.auction.model.entity.DailyAuctions;
import com.auction.service.BuyerLogService;

@Service
@Transactional
public class BuyerLogServiceImpl implements BuyerLogService {

	@Autowired
	private BuyerLogDao BuyerLogDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(BuyerLogBean bean) {
		logger.info("BuyerLogServiceImpl  call save method");
		BuyerLog buyerLog = BuyerLogDao.save(convertBeanToEntity(bean));
		return (null != buyerLog ? buyerLog.getAccountProfile().getAccountId() : null);
	}

	@Override
	public BuyerLogBean findById(Integer id) {
		logger.info("BuyerLogServiceImpl  call findById method");
		return new BuyerLogBean(BuyerLogDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("BuyerLogServiceImpl  call exists method");
		return BuyerLogDao.exists(id);
	}

	@Override
	public List<BuyerLogBean> findAll() {
		logger.info("BuyerLogServiceImpl  call findAll method");
		return convertEntityListToBeanList(BuyerLogDao.findAll());
	}

	@Override
	public List<BuyerLogBean> findAll(Iterable<Integer> ids) {
		logger.info("BuyerLogServiceImpl  call findAll method");
		return convertEntityListToBeanList(BuyerLogDao.findAll());
	}

	@Override
	public long count() {
		logger.info("BuyerLogServiceImpl  call count method");
		return BuyerLogDao.count();
	}

	@Override
	public void delete(BuyerLogBean bean) {
		logger.info("BuyerLogServiceImpl  call delete method");
		BuyerLogDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public Short getBidOperation(Integer auctionBuyersId, Integer dailyAuctionsId, Short bidOperation) {
		logger.info("BuyerLogServiceImpl  call getBidOperation method");
		return BuyerLogDao.getBidOperation(auctionBuyersId, dailyAuctionsId, bidOperation);
	}

	@Override
	public Float getBidPriceByBuyerAccountId(Integer accountId, Integer dailyAuctionsId, Integer auctionBuyersId) {
		logger.info("BuyerLogServiceImpl  call getBidPriceByBuyerAccountId method");
		return BuyerLogDao.getBidPriceByBuyerAccountId(accountId, dailyAuctionsId, auctionBuyersId);
	}

	private BuyerLog convertBeanToEntity(BuyerLogBean buyerLogBean) {
		logger.info("BuyerLogServiceImpl  call convertBeanToEntity method");
		if (null != buyerLogBean) {
			BuyerLogId buyerLogId = new BuyerLogId();
			buyerLogId.setAuctionBuyers(new AuctionBuyers(buyerLogBean.getId().getAuctionBuyersBean().getAuctionBuyersId()));
			buyerLogId.setLogTimestamp(buyerLogBean.getId().getLogTimestamp());
			buyerLogId.setBidOperation(buyerLogBean.getId().getBidOperation());

			BuyerLog buyerLog = new BuyerLog(buyerLogId);
			buyerLog.setBidPrice(buyerLogBean.getBidPrice());
			buyerLog.setMinimumQuantity(buyerLogBean.getMinimumQuantity());
			buyerLog.setBidQuantity(buyerLogBean.getBidQuantity());
			buyerLog.setAccountProfile(new AccountProfile(buyerLogBean.getAccountProfileBean().getAccountId()));
			if (null != buyerLogBean.getShipperAccountProfileBean() && null != buyerLogBean.getShipperAccountProfileBean().getAccountId()
					&& buyerLogBean.getShipperAccountProfileBean().getAccountId() > 0) {
				buyerLog.setShipperAccountProfile(new AccountProfile(buyerLogBean.getShipperAccountProfileBean().getAccountId()));
			}
			buyerLog.setDailyAuctions(new DailyAuctions(buyerLogBean.getDailyAuctionsBean().getDailyAuctionsId()));
			buyerLog.setBuyerShippingCharge((null != buyerLogBean.getBuyerShippingCharge() ? buyerLogBean.getBuyerShippingCharge() : 0F));
			buyerLog.setExecutedPrice(buyerLogBean.getExecutedPrice());
			buyerLog.setExecutedQuantity(buyerLogBean.getExecutedQuantity());
			return buyerLog;
		}
		return null;
	}

	public List<BuyerLogBean> convertEntityListToBeanList(List<BuyerLog> buyerLogList) {
		logger.info("BuyerLogServiceImpl  call convertEntityListToBeanList method");
		if (null != buyerLogList && buyerLogList.size() > 0) {
			List<BuyerLogBean> buyerLogBeanList = new ArrayList<BuyerLogBean>();
			for (BuyerLog buyerLog : buyerLogList) {
				buyerLogBeanList.add(new BuyerLogBean(buyerLog));
			}
			return buyerLogBeanList;
		}
		return null;
	}

}