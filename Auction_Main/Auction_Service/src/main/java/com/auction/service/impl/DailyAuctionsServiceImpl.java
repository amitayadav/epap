package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.DailyAuctionsDao;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionSettings;
import com.auction.model.entity.AuctionStatusCodes;
import com.auction.model.entity.DailyAuctions;
import com.auction.model.entity.ProductCatalog;
import com.auction.model.views.DailyAuctionsView;
import com.auction.service.DailyAuctionsService;

@Service
@Transactional
public class DailyAuctionsServiceImpl implements DailyAuctionsService {

	@Autowired
	private DailyAuctionsDao dailyAuctionsDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(DailyAuctionsBean bean) {
		logger.info("DailyAuctionsServiceImpl  call save method");
		DailyAuctions dailyAuctions = dailyAuctionsDao.save(convertBeanToEntity(bean));
		return (null != dailyAuctions ? dailyAuctions.getDailyAuctionsId() : null);
	}

	@Override
	public DailyAuctionsBean findById(Integer id) {
		logger.info("DailyAuctionsServiceImpl  call findById method");
		return new DailyAuctionsBean(dailyAuctionsDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("DailyAuctionsServiceImpl  call exists method");
		return dailyAuctionsDao.exists(id);
	}

	@Override
	public List<DailyAuctionsBean> findAll() {
		logger.info("DailyAuctionsServiceImpl  call findAll method");
		return convertEntityListToBeanList(dailyAuctionsDao.findAll());
	}

	@Override
	public List<DailyAuctionsBean> findAll(Iterable<Integer> ids) {
		logger.info("DailyAuctionsServiceImpl  call findAll method");
		return convertEntityListToBeanList(dailyAuctionsDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("DailyAuctionsServiceImpl  call count method");
		return dailyAuctionsDao.count();
	}

	@Override
	public void delete(DailyAuctionsBean bean) {
		logger.info("DailyAuctionsServiceImpl  call delete method");
		dailyAuctionsDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public void updateAuctionStatusBasedOnBeginTime(Short auctionStatusCodes, Date beginDate) {
		logger.info("DailyAuctionsServiceImpl  call updateAuctionStatusBasedOnBeginTime method");
		dailyAuctionsDao.updateAuctionStatusBasedOnBeginTime(auctionStatusCodes, beginDate);
	}

	@Override
	public Integer refresh(Integer id) {
		logger.info("DailyAuctionsServiceImpl  call refresh method");
		dailyAuctionsDao.refresh(new DailyAuctions(id));
		return id;
	}

	@Override
	public void updateAuctionStatusBasedOnDailyAuctionsId(Short auctionStatusCodes, Integer dailyAuctionsId) {
		logger.info("DailyAuctionsServiceImpl  call updateAuctionStatusBasedOnDailyAuctionsId method");
		dailyAuctionsDao.updateAuctionStatusBasedOnDailyAuctionsId(auctionStatusCodes, dailyAuctionsId);
	}

	@Override
	public List<DailyAuctionsBean> findByAuctionStatusCodesAuctionStatusCodeAndBeginTime(Short auctionStatusCode, Date date) {
		logger.info("DailyAuctionsServiceImpl  call findByAuctionStatusCodesAuctionStatusCodeAndBeginTime method");
		return convertEntityListToBeanList(dailyAuctionsDao.findByAuctionStatusCodesAuctionStatusCodeAndBeginTime(auctionStatusCode, date));
	}

	@Override
	public List<DailyAuctionsBean> getByAuctionStatusCodesAuctionStatusCodeAndEndTime(Short auctionStatusCode, Date endDate) {
		logger.info("DailyAuctionsServiceImpl  call getByAuctionStatusCodesAuctionStatusCodeAndEndTime method");
		return convertEntityListToBeanList(dailyAuctionsDao.getByAuctionStatusCodesAuctionStatusCodeAndEndTime(auctionStatusCode, endDate));
	}

	@Override
	public List<DailyAuctionsBean> findByAuctionStatusCodeAndTodayDate(Short[] auctionStatusCode, Date startDate, Date endDate) {
		logger.info("DailyAuctionsServiceImpl  call findByAuctionStatusCodeAndTodayDate method");
		return convertEntityListToBeanList(dailyAuctionsDao.findByAuctionStatusCodeAndTodayDate(auctionStatusCode, startDate, endDate));
	}

	@Override
	public List<DailyAuctionsBean> findByDuplicateAuction(Integer dailyAuctionsId, Short[] auctionStatusCode, Date startDate, Date endDate, String productGroupName,
			String productName, String productTypeName) {
		return convertEntityListToBeanList(
				dailyAuctionsDao.findByDuplicateAuction(dailyAuctionsId, auctionStatusCode, startDate, endDate, productGroupName, productName, productTypeName));
	}

	@Override
	public List<DailyAuctionsView> findViewByAuctionStatusCodeAndTodayDate(Short[] auctionStatusCode, Date startDate, Date endDate) {
		logger.info("DailyAuctionsServiceImpl  call findViewByAuctionStatusCodeAndTodayDate method");
		return convertBeanListToViewList(dailyAuctionsDao.findByAuctionStatusCodeAndTodayDate(auctionStatusCode, startDate, endDate));
	}

	@Override
	public DailyAuctionsView findViewByAuctionId(Integer auctionId) {
		logger.info("DailyAuctionsServiceImpl  call findViewByAuctionId method");
		return new DailyAuctionsView(dailyAuctionsDao.findOne(auctionId));
	}

	private DailyAuctions convertBeanToEntity(DailyAuctionsBean bean) {
		logger.info("DailyAuctionsServiceImpl  call findViewByAuctionId method");
		if (null != bean) {
			DailyAuctions entity = new DailyAuctions();
			entity.setDailyAuctionsId(bean.getDailyAuctionsId());
			if (null != bean.getAccountProfileByCancelledbyIdBean())
				if (null != bean.getAccountProfileByCancelledbyIdBean().getAccountId())
					entity.setAccountProfileByCancelledbyId(new AccountProfile(bean.getAccountProfileByCancelledbyIdBean().getAccountId()));
			if (null != bean.getAccountProfileByCreatedbyIdBean())
				entity.setAccountProfileByCreatedbyId(new AccountProfile(bean.getAccountProfileByCreatedbyIdBean().getAccountId()));
			if (null != bean.getAccountProfileByUpdatedbyIdBean())
				entity.setAccountProfileByUpdatedbyId(new AccountProfile(bean.getAccountProfileByUpdatedbyIdBean().getAccountId()));
			if (null != bean.getAuctionSettingsBean())
				entity.setAuctionSettings(new AuctionSettings(bean.getAuctionSettingsBean().getAuctionSettingsId()));
			if (null != bean.getAuctionStatusCodesBean())
				entity.setAuctionStatusCodes(new AuctionStatusCodes(bean.getAuctionStatusCodesBean().getAuctionStatusCode()));
			if (null != bean.getProductCatalogBean())
				entity.setProductCatalog(new ProductCatalog(bean.getProductCatalogBean().getProductId()));
			entity.setBeginTime(bean.getBeginTime());
			entity.setEndTime(bean.getEndTime());
			entity.setAuctionDuration(bean.getAuctionDuration());
			entity.setCreatedOn(bean.getCreatedOn());
			entity.setUpdatedOn(bean.getUpdatedOn());
			entity.setCanceledOn(bean.getCanceledOn());
			entity.setReserved1(bean.getReserved1());
			entity.setReserved2(bean.getReserved2());
			return entity;
		}
		return null;
	}

	private List<DailyAuctionsBean> convertEntityListToBeanList(List<DailyAuctions> list) {
		logger.info("DailyAuctionsServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<DailyAuctionsBean> dailyAuctionsBeansList = new ArrayList<DailyAuctionsBean>(0);

			for (DailyAuctions dailyAuctions : list) {
				dailyAuctionsBeansList.add(new DailyAuctionsBean(dailyAuctions));
			}
			return dailyAuctionsBeansList;
		}
		return null;
	}

	private List<DailyAuctionsView> convertBeanListToViewList(List<DailyAuctions> list) {
		logger.info("DailyAuctionsServiceImpl  call convertBeanListToViewList method");
		List<DailyAuctionsView> views = new ArrayList<DailyAuctionsView>();
		if (null != list && list.size() > 0) {
			for (DailyAuctions auction : list) {
				views.add(new DailyAuctionsView(auction));
			}
		}
		return views;
	}

}