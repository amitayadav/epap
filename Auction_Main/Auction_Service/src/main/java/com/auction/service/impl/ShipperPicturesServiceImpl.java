package com.auction.service.impl;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.ShipperPicturesDao;
import com.auction.model.bean.ShipperPicturesBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.ShipperPictures;
import com.auction.service.ShipperPicturesService;

@Service
@Transactional
public class ShipperPicturesServiceImpl  implements ShipperPicturesService{

		@Autowired
		ShipperPicturesDao shipperPicturesDao;
		
		private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(ShipperPicturesBean bean) {
		logger.info("ShipperPicturesServiceImpl call save  method");
		ShipperPictures  shipperPictures = shipperPicturesDao.save(convertBeanToEntity(bean));
		return (null != shipperPictures ? shipperPictures.getPictureId() : null);
	}

	@Override
	public ShipperPicturesBean findById(Integer id) {
		logger.info("ShipperPicturesServiceImpl call findById  method");
		return new ShipperPicturesBean(shipperPicturesDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("ShipperPicturesServiceImpl call exists  method");
		return shipperPicturesDao.exists(id);
	}

	@Override
	public List<ShipperPicturesBean> findAll() {
		logger.info("ShipperPicturesServiceImpl call findAll  method");
		return convertEntityListToBeanList(shipperPicturesDao.findAll());
	}

	@Override
	public List<ShipperPicturesBean> findAll(Iterable<Integer> ids) {
		logger.info("ShipperPicturesServiceImpl call findAll  method");
		return convertEntityListToBeanList(shipperPicturesDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("ShipperPicturesServiceImpl call count  method");
		return shipperPicturesDao.count();
	}

	@Override
	public void delete(ShipperPicturesBean bean) {
		logger.info("ShipperPicturesServiceImpl call delete  method");
		shipperPicturesDao.delete(convertBeanToEntity(bean));
		
	}

	@Override
	public Integer refresh(Integer id) {
		logger.info("ShipperPicturesServiceImpl call refresh  method");
		ShipperPictures shipperPictures = shipperPicturesDao.findOne(id);
		return (null != shipperPictures ? shipperPictures.getPictureId() : null);
	}
	@Override
	public List<ShipperPicturesBean> getByAccountProfileId(Integer accountId) {
		logger.info("ShipperPicturesServiceImpl call getByAccountProfileId  method");
		return convertEntityListToBeanList(shipperPicturesDao.getByAccountProfileId(accountId));
	}
	
	@Override
	public Blob getByShipperInfoPictures(String pictureLocation, Integer accountId) {
		logger.info("ShipperPicturesServiceImpl call getByShipperInfoPictures  method");
		Blob picturesFile =shipperPicturesDao.getByShipperInfoPictures(pictureLocation, accountId);
		return picturesFile;
	}
	
	private ShipperPictures convertBeanToEntity(ShipperPicturesBean bean) {
		logger.info("ShipperPicturesServiceImpl call convertBeanToEntity  method");
		if (null != bean) {
			ShipperPictures entity = new ShipperPictures();
			entity.setPictureId(bean.getPictureId());
			entity.setPictureLocation(bean.getPictureLocation());
			if(null != bean.getAccountProfileBean())
			entity.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			entity.setContents(bean.getContents());
			return entity;
		}
		return null;
	}

	private List<ShipperPicturesBean> convertEntityListToBeanList(List<ShipperPictures> list) {
		logger.info("ShipperPicturesServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<ShipperPicturesBean> shipperPicturesBeanList = new ArrayList<ShipperPicturesBean>(0);
			for (ShipperPictures shipperPictures : list) {
				shipperPicturesBeanList.add(new ShipperPicturesBean(shipperPictures));
			}
			return shipperPicturesBeanList;
		}
		return null;
	}


}
