package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AuctionRequestDao;
import com.auction.model.bean.AuctionRequestBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionRequest;
import com.auction.model.entity.ProductCatalog;
import com.auction.service.AuctionRequestService;

@Service
@Transactional
public class AuctionRequestServiceImpl implements AuctionRequestService {

	@Autowired
	AuctionRequestDao auctionRequestDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(AuctionRequestBean bean) {
		logger.info("AuctionRequestServiceImpl call save method");
		AuctionRequest auctionRequest = auctionRequestDao.save(convertBeanToEntity(bean));
		return (null != auctionRequest ? auctionRequest.getRequestId() : null);
	}

	@Override
	public AuctionRequestBean findById(Integer id) {
		logger.info("AuctionRequestServiceImpl call findById method");
		return new AuctionRequestBean(auctionRequestDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("AuctionRequestServiceImpl call exists method");
		return auctionRequestDao.exists(id);
	}

	@Override
	public List<AuctionRequestBean> findAll() {
		logger.info("AuctionRequestServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionRequestDao.findAll());
	}

	@Override
	public List<AuctionRequestBean> findAll(Iterable<Integer> ids) {
		logger.info("AuctionRequestServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionRequestDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("AuctionRequestServiceImpl call count method");
		return auctionRequestDao.count();
	}

	@Override
	public void delete(AuctionRequestBean bean) {
		logger.info("AuctionRequestServiceImpl call delete method");
		auctionRequestDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	private AuctionRequest convertBeanToEntity(AuctionRequestBean bean) {
		logger.info("AuctionRequestServiceImpl call convertBeanToEntity method");
		if (null != bean) {

			AuctionRequest entity = new AuctionRequest();
			entity.setRequestId(bean.getRequestId());
			entity.setAccountProfileByCreatedBy(new AccountProfile(bean.getAccountProfileByCreatedBy().getAccountId()));
			if (null != bean.getAccountProfileByFeedbackBy())
				entity.setAccountProfileByFeedbackBy(new AccountProfile(bean.getAccountProfileByFeedbackBy().getAccountId()));
			entity.setAccountProfileBySellerId(new AccountProfile(bean.getAccountProfileBySellerId().getAccountId()));
			if (null != bean.getAccountProfileByUpdatedBy())
				entity.setAccountProfileByUpdatedBy(new AccountProfile(bean.getAccountProfileByUpdatedBy().getAccountId()));
			entity.setSellerComment(bean.getSellerComment());
			entity.setFeedback(bean.getFeedback());
			entity.setCreatedOn(bean.getCreatedOn());
			entity.setFeedbackOn(bean.getFeedbackOn());
			entity.setProductCatalog(new ProductCatalog(bean.getProductCatalogBean().getProductId()));
			entity.setStatus(bean.getStatus());
			if (null != bean.getUpdatedOn())
				entity.setUpdatedOn(bean.getUpdatedOn());

			return entity;
		}
		return null;
	}

	private List<AuctionRequestBean> convertEntityListToBeanList(List<AuctionRequest> list) {
		logger.info("AuctionRequestServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<AuctionRequestBean> requestBeansList = new ArrayList<AuctionRequestBean>(0);
			for (AuctionRequest auctionRequest : list) {
				requestBeansList.add(new AuctionRequestBean(auctionRequest));
			}
			return requestBeansList;
		}
		return null;
	}

}
