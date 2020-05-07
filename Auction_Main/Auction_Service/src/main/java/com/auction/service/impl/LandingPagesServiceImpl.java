package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.LandingPagesDao;
import com.auction.model.bean.LandingPagesBean;
import com.auction.model.entity.LandingPages;
import com.auction.service.LandingPagesService;

@Service
@Transactional
public class LandingPagesServiceImpl implements LandingPagesService {

	@Autowired
	LandingPagesDao landingPagesDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Short save(LandingPagesBean bean) {
		logger.info("LandingPagesServiceImpl  call save method");
		LandingPages landingPages = landingPagesDao.save(convertBeanToEntity(bean));
		return (null != landingPages ? landingPages.getLandingPageId() : null);
	}

	@Override
	public LandingPagesBean findById(Short id) {
		logger.info("LandingPagesServiceImpl  call findById method");
		return new LandingPagesBean(landingPagesDao.findOne(id));
	}

	@Override
	public boolean exists(Short id) {
		logger.info("LandingPagesServiceImpl  call exists method");
		return landingPagesDao.exists(id);
	}

	@Override
	public List<LandingPagesBean> findAll() {
		logger.info("LandingPagesServiceImpl  call findAll method");
		return convertEntityListToBeanList(landingPagesDao.findAll());
	}

	@Override
	public List<LandingPagesBean> findAll(Iterable<Short> ids) {
		logger.info("LandingPagesServiceImpl  call findAll method");
		return convertEntityListToBeanList(landingPagesDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("LandingPagesServiceImpl  call count method");
		return landingPagesDao.count();
	}

	@Override
	public void delete(LandingPagesBean bean) {
		logger.info("LandingPagesServiceImpl  call delete method");
		landingPagesDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Short refresh(Short id) {
		return null;
	}

	private LandingPages convertBeanToEntity(LandingPagesBean bean) {
		logger.info("LandingPagesServiceImpl  call convertBeanToEntity method");
		if (null != bean) {
			LandingPages entity = new LandingPages();
			entity.setLandingPageId(bean.getLandingPageId());
			entity.setLandingPageName(bean.getLandingPageName());
			entity.setLandingPageUrl(bean.getLandingPageUrl());

			return entity;
		}
		return null;
	}

	private List<LandingPagesBean> convertEntityListToBeanList(List<LandingPages> list) {
		logger.info("LandingPagesServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {

			List<LandingPagesBean> landingPagesBeansList = new ArrayList<LandingPagesBean>(0);

			for (LandingPages pages : list) {
				landingPagesBeansList.add(new LandingPagesBean(pages));
			}
			return landingPagesBeansList;
		}
		return null;
	}

}
