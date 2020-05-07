package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AuctionStatusCodesDao;
import com.auction.model.bean.AuctionStatusCodesBean;
import com.auction.model.entity.AuctionStatusCodes;
import com.auction.service.AuctionStatusCodesService;

@Service
@Transactional
public class AuctionStatusCodesServiceImpl implements AuctionStatusCodesService {

	@Autowired
	private AuctionStatusCodesDao auctionStatusCodesDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Short save(AuctionStatusCodesBean bean) {
		logger.info("AuctionStatusCodesServiceImpl call save method");
		AuctionStatusCodes auctionStatusCodes = auctionStatusCodesDao.save(convertBeanToEntity(bean));
		return (null != auctionStatusCodes ? auctionStatusCodes.getAuctionStatusCode() : null);
	}

	@Override
	public AuctionStatusCodesBean findById(Short id) {
		logger.info("AuctionStatusCodesServiceImpl call findById method");
		return new AuctionStatusCodesBean(auctionStatusCodesDao.findOne(id));
	}

	@Override
	public boolean exists(Short id) {
		logger.info("AuctionStatusCodesServiceImpl call exists method");
		return auctionStatusCodesDao.exists(id);
	}

	@Override
	public List<AuctionStatusCodesBean> findAll() {
		logger.info("AuctionStatusCodesServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionStatusCodesDao.findAll());
	}

	@Override
	public List<AuctionStatusCodesBean> findAll(Iterable<Short> ids) {
		logger.info("AuctionStatusCodesServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionStatusCodesDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("AuctionStatusCodesServiceImpl call count method");
		return auctionStatusCodesDao.count();
	}

	@Override
	public void delete(AuctionStatusCodesBean bean) {
		logger.info("AuctionStatusCodesServiceImpl call delete method");
		auctionStatusCodesDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Short refresh(Short id) {
		return null;
	}

	@Override
	public AuctionStatusCodesBean findAuctionStatuscodeByDailyAuctionId(Integer dailyAuctionsId) {
		logger.info("AuctionStatusCodesServiceImpl call findAuctionStatuscodeByDailyAuctionId method");
		return new AuctionStatusCodesBean(auctionStatusCodesDao.findAuctionStatuscodeByDailyAuctionId(dailyAuctionsId));
	}

	private AuctionStatusCodes convertBeanToEntity(AuctionStatusCodesBean bean) {
		logger.info("AuctionStatusCodesServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			AuctionStatusCodes entity = new AuctionStatusCodes();
			entity.setAuctionStatusCode(bean.getAuctionStatusCode());
			entity.setAuctionStatusCodeMeaning(bean.getAuctionStatusCodeMeaning());
			return entity;
		}
		return null;
	}

	private List<AuctionStatusCodesBean> convertEntityListToBeanList(List<AuctionStatusCodes> list) {
		logger.info("AuctionStatusCodesServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<AuctionStatusCodesBean> auctionStatusCodesBeansList = new ArrayList<AuctionStatusCodesBean>(0);
			for (AuctionStatusCodes auctionStatusCodes : list) {
				auctionStatusCodesBeansList.add(new AuctionStatusCodesBean(auctionStatusCodes));
			}
			return auctionStatusCodesBeansList;
		}
		return null;
	}

}