package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AuctionTradesDao;
import com.auction.model.bean.AuctionTradesBean;
import com.auction.model.entity.AuctionTradeGroupView;
import com.auction.model.entity.AuctionTrades;
import com.auction.model.entity.AuctionTradesId;
import com.auction.model.entity.ProductCatalog;
import com.auction.model.views.AuctionTradesView;
import com.auction.service.AuctionTradesService;

@Service
@Transactional
public class AuctionTradesServiceImpl implements AuctionTradesService {

	@Autowired
	private AuctionTradesDao auctionTradesDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(AuctionTradesBean bean) {
		logger.info("AuctionTradesServiceImpl call save method");
		AuctionTrades auctionTrades = auctionTradesDao.save(convertBeanToEntity(bean));
		return auctionTrades.getId().getProductCatalog().getProductId();
	}

	@Override
	public AuctionTradesBean findById(Integer id) {
		logger.info("AuctionTradesServiceImpl call findById method");
		return new AuctionTradesBean(auctionTradesDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("AuctionTradesServiceImpl call exists method");
		return auctionTradesDao.exists(id);
	}

	@Override
	public List<AuctionTradesBean> findAll() {
		logger.info("AuctionTradesServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionTradesDao.findAll());
	}
	
	@Override
	public List<AuctionTradesBean> findAll(Iterable<Integer> ids) {
		logger.info("AuctionTradesServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionTradesDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("AuctionTradesServiceImpl call count method");
		return auctionTradesDao.count();
	}

	@Override
	public void delete(AuctionTradesBean bean) {
		logger.info("AuctionTradesServiceImpl delete count method");
		auctionTradesDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	private AuctionTrades convertBeanToEntity(AuctionTradesBean bean) {
		logger.info("AuctionTradesServiceImpl convertBeanToEntity count method");
		if (null != bean) {
			AuctionTradesId auctionTradesId = new AuctionTradesId();
			auctionTradesId.setLogTimestamp(bean.getId().getLogTimestamp());
			auctionTradesId.setProductCatalog(new ProductCatalog(bean.getId().getProductCatalogBean().getProductId()));

			AuctionTrades auctionTrades = new AuctionTrades(auctionTradesId);
			auctionTrades.setSoldPrice(bean.getSoldPrice());
			auctionTrades.setSoldQuantity(bean.getSoldQuantity());
			return auctionTrades;
		}
		return null;
	}

	private List<AuctionTradesBean> convertEntityListToBeanList(List<AuctionTrades> list) {
		logger.info("AuctionTradesServiceImpl convertEntityListToBeanList count method");
		List<AuctionTradesBean> auctionTradesBeansList = new ArrayList<AuctionTradesBean>(0);
		if (null != list && list.size() > 0) {
			for (AuctionTrades auctionTrades : list) {
				auctionTradesBeansList.add(new AuctionTradesBean(auctionTrades));
			}
			return auctionTradesBeansList;
		}
		return auctionTradesBeansList;
	}

	private List<AuctionTradesView> convertEntityListToViewList(List<AuctionTrades> list) {
		logger.info("AuctionTradesServiceImpl convertEntityListToViewList count method");
		List<AuctionTradesView> views = new ArrayList<AuctionTradesView>();
		if (null != list && list.size() > 0) {
			for (AuctionTrades auctionTrade : list) {
				views.add(new AuctionTradesView(auctionTrade));
			}
		}
		return views;
	}

	public List<AuctionTradesView> getAuctionTradesBetweenDate(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate) {
		logger.info("AuctionTradesServiceImpl getAuctionTradesBetweenDate count method");
		return convertEntityListToViewList(auctionTradesDao.getAuctionTradesBetweenDate(startDate, endDate));
	}

	public List<AuctionTradesBean> getAuctionTradesBeanBetweenDate(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate) {
		logger.info("AuctionTradesServiceImpl getAuctionTradesBeanBetweenDate count method");
		return convertEntityListToBeanList(auctionTradesDao.getAuctionTradesBetweenDate(startDate, endDate));
	}

	@Override
	public List<AuctionTradesBean> getAuctionTradesDate(Date startDate,Date endDate) {
		return convertEntityListToBeanList(auctionTradesDao.getAuctionTradesDate(startDate,endDate));
	}

	@Override
	public List<AuctionTradeGroupView> findAllAuctionTrades() {
		return auctionTradesDao.findAllAuctionTrades();
	}
	
	@Override
	public List<AuctionTradeGroupView> findAllAuctionTradesById(Integer productId) {
		return auctionTradesDao.findAllAuctionTradesById(productId);
	}

	
}