package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.CurrencyCodesDao;
import com.auction.model.bean.CurrencyCodesBean;
import com.auction.model.entity.CurrencyCodes;
import com.auction.service.CurrencyCodesService;

@Service
@Transactional
public class CurrencyCodesServiceImpl implements CurrencyCodesService {

	@Autowired
	CurrencyCodesDao currencyCodesDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public String save(CurrencyCodesBean bean) {
		logger.info("CurrencyCodesServiceImpl  call save method");
		CurrencyCodes currencyCodes = currencyCodesDao.save(convertBeanToEntity(bean));
		return (null != currencyCodes ? currencyCodes.getCurrencyCode() : null);
	}

	@Override
	public CurrencyCodesBean findById(String id) {
		logger.info("CurrencyCodesServiceImpl  call findById method");
		return convertEntityToBean(currencyCodesDao.findOne(id));
	}

	@Override
	public boolean exists(String id) {
		logger.info("CurrencyCodesServiceImpl  call exists method");
		return currencyCodesDao.exists(id);
	}

	@Override
	public List<CurrencyCodesBean> findAll() {
		logger.info("CurrencyCodesServiceImpl  call findAll method");
		return convertEntityListToBeanList(currencyCodesDao.findAll());
	}

	@Override
	public List<CurrencyCodesBean> findAll(Iterable<String> ids) {
		logger.info("CurrencyCodesServiceImpl  call findAll method");
		return convertEntityListToBeanList(currencyCodesDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("CurrencyCodesServiceImpl  call count method");
		return currencyCodesDao.count();
	}

	@Override
	public void delete(CurrencyCodesBean bean) {
		logger.info("CurrencyCodesServiceImpl  call delete method");
		currencyCodesDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public String refresh(String id) {
		logger.info("CurrencyCodesServiceImpl  call refresh method");
		CurrencyCodes codes = currencyCodesDao.findOne(id);
		return (null != codes ? codes.getCurrencyCode() : null);
	}

	@Override
	public List<CurrencyCodesBean> getCurrencyCodesOrderBycurrencyName() {
		logger.info("CurrencyCodesServiceImpl  call getCurrencyCodesOrderBycurrencyName method");
		return convertEntityListToBeanList(currencyCodesDao.getCurrencyCodesOrderBycurrencyName());
	}

	private CurrencyCodesBean convertEntityToBean(CurrencyCodes entity) {
		logger.info("CurrencyCodesServiceImpl  call convertEntityToBean method");
		if (null != entity) {
			CurrencyCodesBean bean = new CurrencyCodesBean();
			bean.setCurrencyCode(entity.getCurrencyCode());
			bean.setCurrencyName(entity.getCurrencyName());
			return bean;
		}
		return null;
	}

	private CurrencyCodes convertBeanToEntity(CurrencyCodesBean bean) {
		logger.info("CurrencyCodesServiceImpl  call convertBeanToEntity method");
		if (null != bean) {
			CurrencyCodes entity = new CurrencyCodes();
			entity.setCurrencyCode(bean.getCurrencyCode());
			entity.setCurrencyName(bean.getCurrencyName());
			return entity;
		}
		return null;
	}

	private List<CurrencyCodesBean> convertEntityListToBeanList(List<CurrencyCodes> list) {
		logger.info("CurrencyCodesServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {

			List<CurrencyCodesBean> currencyCodesBeansList = new ArrayList<CurrencyCodesBean>(0);
			for (CurrencyCodes currencyCodes : list) {
				currencyCodesBeansList.add(convertEntityToBean(currencyCodes));
			}
			return currencyCodesBeansList;
		}
		return null;
	}

}
