package com.auction.service.impl;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.SellerOfferPictureDao;
import com.auction.model.bean.SellerOfferPicturesBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.ProductCatalog;
import com.auction.model.entity.SellerOfferPictures;
import com.auction.service.SellerOfferPicturesService;

@Service
@Transactional
public class SellerOfferPicturesServiceImpl implements SellerOfferPicturesService {

	@Autowired
	SellerOfferPictureDao sellerOfferPictureDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(SellerOfferPicturesBean bean) {
		logger.info("SellerOfferPicturesServiceImpl call save  method");
		SellerOfferPictures entity = sellerOfferPictureDao.save(convertBeanToEntity(bean));
		return (null != entity && null != entity.getPictureId() ? entity.getPictureId() : null);
	}

	@Override
	public SellerOfferPicturesBean findById(Integer id) {
		logger.info("SellerOfferPicturesServiceImpl call findById  method");
		return new SellerOfferPicturesBean(sellerOfferPictureDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("SellerOfferPicturesServiceImpl call exists  method");
		return sellerOfferPictureDao.exists(id);
	}

	@Override
	public List<SellerOfferPicturesBean> findAll() {
		return null;
	}

	@Override
	public List<SellerOfferPicturesBean> findAll(Iterable<Integer> ids) {
		return null;
	}

	@Override
	public long count() {
		logger.info("SellerOfferPicturesServiceImpl call count  method");
		return sellerOfferPictureDao.count();
	}

	@Override
	public void delete(SellerOfferPicturesBean bean) {
		logger.info("SellerOfferPicturesServiceImpl call delete  method");
		sellerOfferPictureDao.delete(bean.getPictureId());
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public List<SellerOfferPicturesBean> findByAccountProfileAccountIdAndProductCatalogProductId(Integer accountId, Integer productId) {
		logger.info("SellerOfferPicturesServiceImpl call findByAccountProfileAccountIdAndProductCatalogProductId  method");
		return convertEntityListToBeanList(sellerOfferPictureDao.findByAccountProfileAccountIdAndProductCatalogProductId(accountId, productId));
	}

	private SellerOfferPictures convertBeanToEntity(SellerOfferPicturesBean bean) {
		logger.info("SellerOfferPicturesServiceImpl call convertBeanToEntity  method");
		if (null != bean) {
			SellerOfferPictures entity = new SellerOfferPictures();

			entity.setPictureId(bean.getPictureId());
			entity.setPictureLocation(bean.getPictureLocation());
			if (null != bean.getAccountProfileBean()) {
				entity.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			}
			if (null != bean.getProductCatalogBean()) {
				entity.setProductCatalog(new ProductCatalog(bean.getProductCatalogBean().getProductId()));
			}
			entity.setContents(bean.getContents());
			return entity;
		}
		return null;
	}

	private List<SellerOfferPicturesBean> convertEntityListToBeanList(List<SellerOfferPictures> list) {
		logger.info("SellerOfferPicturesServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<SellerOfferPicturesBean> sellerOfferPicturesBeanList = new ArrayList<SellerOfferPicturesBean>(0);
			for (SellerOfferPictures sellerOfferPicture : list) {
				sellerOfferPicturesBeanList.add(new SellerOfferPicturesBean(sellerOfferPicture));
			}
			return sellerOfferPicturesBeanList;
		}
		return null;
	}

	@Override
	public Blob getSellerOfferProductImg(String pictureLocation,Integer accountId) {
		logger.info("SellerOfferPicturesServiceImpl call getSellerOfferProductImg  method");
		Blob file =sellerOfferPictureDao.getSellerOfferProductImg(pictureLocation,accountId);
		return file;
	}

}
