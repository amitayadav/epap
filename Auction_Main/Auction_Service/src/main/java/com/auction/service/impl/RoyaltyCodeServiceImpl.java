package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.RoyaltyCodeDao;
import com.auction.model.bean.RoyaltyCodesBean;
import com.auction.model.entity.RoyaltyCodes;
import com.auction.service.RoyaltyCodeService;

@Service
@Transactional
public class RoyaltyCodeServiceImpl implements RoyaltyCodeService {

	@Autowired
	private RoyaltyCodeDao royaltyCodeDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Short save(RoyaltyCodesBean bean) {
		logger.info("RoyaltyCodeServiceImpl call save method");
		RoyaltyCodes royaltyCodes = royaltyCodeDao.save(convertBeanToEntity(bean));
		return (null != royaltyCodes ? royaltyCodes.getRoyaltyCode() : null);
	}

	@Override
	public RoyaltyCodesBean findById(Short id) {
		logger.info("RoyaltyCodeServiceImpl call findById method");
		return convertEntityToBean(royaltyCodeDao.findOne(id));
	}

	@Override
	public boolean exists(Short id) {
		logger.info("RoyaltyCodeServiceImpl call exists method");
		return royaltyCodeDao.exists(id);
	}

	@Override
	public List<RoyaltyCodesBean> findAll() {
		logger.info("RoyaltyCodeServiceImpl call findAll method");
		return convertEntityListToBeanList(royaltyCodeDao.findAll());
	}

	@Override
	public List<RoyaltyCodesBean> findAll(Iterable<Short> ids) {
		logger.info("RoyaltyCodeServiceImpl call findAll method");
		return convertEntityListToBeanList(royaltyCodeDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("RoyaltyCodeServiceImpl call count method");
		return royaltyCodeDao.count();
	}

	@Override
	public void delete(RoyaltyCodesBean bean) {
		logger.info("RoyaltyCodeServiceImpl call delete method");
		royaltyCodeDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Short refresh(Short id) {
		logger.info("RoyaltyCodeServiceImpl call refresh method");
		RoyaltyCodes royaltyCodes = royaltyCodeDao.findOne(id);
		return (null != royaltyCodes ? royaltyCodes.getRoyaltyCode() : null);
	}

	@Override
	public RoyaltyCodesBean findByRoyaltyCodeAndRoyaltyValue(Short royaltyCode, Float royaltyValue) {
		logger.info("RoyaltyCodeServiceImpl call findByRoyaltyCodeAndRoyaltyValue method");
		return new RoyaltyCodesBean(royaltyCodeDao.findByRoyaltyCodeAndRoyaltyValue(royaltyCode, royaltyValue));
	}

	@Override
	public List<RoyaltyCodesBean> getRoyaltyCodesOrderByRoyaltyValue() {
		logger.info("RoyaltyCodeServiceImpl call getRoyaltyCodesOrderByRoyaltyValue method");
		return convertEntityListToBeanList(royaltyCodeDao.getRoyaltyCodesOrderByRoyaltyValue());
	}

	@Override
	public Float findByRoyaltyCodeByAccountProfileId(Integer accountId) {
		logger.info("RoyaltyCodeServiceImpl call findByRoyaltyCodeByAccountProfileId method");
		return royaltyCodeDao.findByRoyaltyCodeByAccountProfileId(accountId);
	}

	private RoyaltyCodes convertBeanToEntity(RoyaltyCodesBean bean) {
		logger.info("RoyaltyCodeServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			RoyaltyCodes entity = new RoyaltyCodes();
			entity.setRoyaltyCode(bean.getRoyaltyCode());
			entity.setRoyaltyValue(bean.getRoyaltyValue());
			return entity;
		}
		return null;
	}

	private List<RoyaltyCodesBean> convertEntityListToBeanList(List<RoyaltyCodes> list) {
		logger.info("RoyaltyCodeServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<RoyaltyCodesBean> royaltyCodesBeanList = new ArrayList<RoyaltyCodesBean>(0);
			for (RoyaltyCodes royaltyCodes : list) {
				royaltyCodesBeanList.add(convertEntityToBean(royaltyCodes));
			}
			return royaltyCodesBeanList;
		}
		return null;
	}

	private RoyaltyCodesBean convertEntityToBean(RoyaltyCodes entity) {
		logger.info("RoyaltyCodeServiceImpl call convertEntityToBean  method");
		if (null != entity) {
			RoyaltyCodesBean bean = new RoyaltyCodesBean();
			bean.setRoyaltyCode(entity.getRoyaltyCode());
			bean.setRoyaltyValue(entity.getRoyaltyValue());
			return bean;
		}
		return null;
	}

}