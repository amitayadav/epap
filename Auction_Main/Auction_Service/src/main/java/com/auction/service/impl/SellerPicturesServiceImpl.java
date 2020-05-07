package com.auction.service.impl;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.SellerPicturesDao;
import com.auction.model.bean.SellerPicturesBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.SellerPictures;
import com.auction.service.SellerPicturesService;

@Service
@Transactional
public class SellerPicturesServiceImpl implements SellerPicturesService {

	@Autowired
	SellerPicturesDao sellerPicturesDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(SellerPicturesBean bean) {
		logger.info("SellerPicturesServiceImpl call save  method");
		SellerPictures sellerPictures = sellerPicturesDao.save(convertBeanToEntity(bean));
		return (null != sellerPictures ? sellerPictures.getPictureId() : null);
	}

	@Override
	public SellerPicturesBean findById(Integer id) {
		logger.info("SellerPicturesServiceImpl call findById  method");
		return new SellerPicturesBean(sellerPicturesDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("SellerPicturesServiceImpl call exists  method");
		return sellerPicturesDao.exists(id);
	}

	@Override
	public List<SellerPicturesBean> findAll() {
		logger.info("SellerPicturesServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerPicturesDao.findAll());
	}

	@Override
	public List<SellerPicturesBean> findAll(Iterable<Integer> ids) {
		logger.info("SellerPicturesServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerPicturesDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("SellerPicturesServiceImpl call count  method");
		return sellerPicturesDao.count();
	}

	@Override
	public void delete(SellerPicturesBean bean) {
		logger.info("SellerPicturesServiceImpl call delete  method");
		sellerPicturesDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		logger.info("SellerPicturesServiceImpl call refresh  method");
		SellerPictures sellerPictures = sellerPicturesDao.findOne(id);
		return (null != sellerPictures ? sellerPictures.getPictureId() : null);
	}
	@Override
	public Blob getBySellerInfoPictures(String pictureLocation, Integer accountId) {
		logger.info("SellerPicturesServiceImpl call getBySellerInfoPictures  method");
		Blob picturesFile =sellerPicturesDao.getBySellerInfoPictures(pictureLocation, accountId);
		return picturesFile;
	}
	
	@Override
	public List<SellerPicturesBean> getByAccountProfileId(Integer accountId) {
		logger.info("SellerPicturesServiceImpl call getByAccountProfileId  method");
		return convertEntityListToBeanList(sellerPicturesDao.getByAccountProfileId(accountId));
	}

	private SellerPictures convertBeanToEntity(SellerPicturesBean bean) {
		logger.info("SellerPicturesServiceImpl call convertBeanToEntity  method");
		if (null != bean) {
			SellerPictures entity = new SellerPictures();
			entity.setPictureId(bean.getPictureId());
			entity.setPictureLocation(bean.getPictureLocation());
			if(null != bean.getAccountProfileBean())
			entity.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			entity.setContents(bean.getContents());
			return entity;
		}
		return null;
	}

	private List<SellerPicturesBean> convertEntityListToBeanList(List<SellerPictures> list) {
		logger.info("SellerPicturesServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<SellerPicturesBean> sellerPicturesBeanList = new ArrayList<SellerPicturesBean>(0);
			for (SellerPictures sellerPictures : list) {
				sellerPicturesBeanList.add(new SellerPicturesBean(sellerPictures));
			}
			return sellerPicturesBeanList;
		}
		return null;
	}

	

}
