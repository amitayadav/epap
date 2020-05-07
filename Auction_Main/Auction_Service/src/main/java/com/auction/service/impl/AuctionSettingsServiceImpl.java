package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AuctionSettingsDao;
import com.auction.model.bean.AuctionSettingsBean;
import com.auction.model.bean.CurrencyCodesBean;
import com.auction.model.entity.AuctionSettings;
import com.auction.model.entity.CurrencyCodes;
import com.auction.service.AuctionSettingsService;

@Service
@Transactional
public class AuctionSettingsServiceImpl implements AuctionSettingsService {

	@Autowired
	private AuctionSettingsDao auctionSettingsDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(AuctionSettingsBean bean) {
		logger.info("AuctionSettingsServiceImpl call save method");
		AuctionSettings auctionSettings = auctionSettingsDao.save(convertBeanToEntity(bean));
		return (null != auctionSettings ? auctionSettings.getAuctionSettingsId() : null);
	}

	@Override
	public AuctionSettingsBean findById(Integer id) {
		logger.info("AuctionSettingsServiceImpl call findById method");
		return convertEntityToBean(auctionSettingsDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("AuctionSettingsServiceImpl call exists method");
		return auctionSettingsDao.exists(id);
	}

	@Override
	public List<AuctionSettingsBean> findAll() {
		logger.info("AuctionSettingsServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionSettingsDao.findAll());
	}

	@Override
	public List<AuctionSettingsBean> findAll(Iterable<Integer> ids) {
		logger.info("AuctionSettingsServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionSettingsDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("AuctionSettingsServiceImpl call count method");
		return auctionSettingsDao.count();
	}

	@Override
	public void delete(AuctionSettingsBean bean) {
		logger.info("AuctionSettingsServiceImpl call delete method");
		auctionSettingsDao.delete(convertBeanToEntity(bean));

	}

	@Override
	public Integer refresh(Integer id) {
		logger.info("AuctionSettingsServiceImpl call refresh method");
		AuctionSettings auctionSettings = auctionSettingsDao.findOne(id);
		return (null != auctionSettings ? auctionSettings.getAuctionSettingsId() : null);
	}

	private AuctionSettingsBean convertEntityToBean(AuctionSettings entity) {
		logger.info("AuctionSettingsServiceImpl call convertEntityToBean method");
		if (null != entity) {
			AuctionSettingsBean bean = new AuctionSettingsBean();
			bean.setAuctionSettingsId(entity.getAuctionSettingsId());
			bean.setCurrencyCodesBean(new CurrencyCodesBean(entity.getCurrencyCodes()));
			bean.setVatBuyers(entity.getVatBuyers());
			bean.setVatSellers(entity.getVatSellers());
			return bean;
		}
		return null;
	}

	private AuctionSettings convertBeanToEntity(AuctionSettingsBean bean) {
		logger.info("AuctionSettingsServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			AuctionSettings entity = new AuctionSettings();
			entity.setAuctionSettingsId(bean.getAuctionSettingsId());
			if (null != bean.getCurrencyCodesBean()) {
				CurrencyCodes currencyCodes = new CurrencyCodes();
				currencyCodes.setCurrencyCode(bean.getCurrencyCodesBean().getCurrencyCode());
				currencyCodes.setCurrencyName(bean.getCurrencyCodesBean().getCurrencyName());
				entity.setCurrencyCodes(currencyCodes);
			}
			entity.setVatBuyers(bean.getVatBuyers());
			entity.setVatSellers(bean.getVatSellers());
			return entity;
		}
		return null;
	}

	private List<AuctionSettingsBean> convertEntityListToBeanList(List<AuctionSettings> list) {
		logger.info("AuctionSettingsServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<AuctionSettingsBean> auctionSettingssBeansList = new ArrayList<AuctionSettingsBean>(0);
			for (AuctionSettings auctionSettings : list) {
				auctionSettingssBeansList.add(convertEntityToBean(auctionSettings));
			}
			return auctionSettingssBeansList;
		}
		return null;
	}

}