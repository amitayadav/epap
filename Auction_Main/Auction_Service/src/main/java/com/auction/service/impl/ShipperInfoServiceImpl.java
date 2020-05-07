package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.ShipperInfoDao;
import com.auction.model.bean.ShipperInfoBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.ShipperInfo;
import com.auction.service.ShipperInfoService;

@Service
@Transactional
public class ShipperInfoServiceImpl implements ShipperInfoService {

	@Autowired
	ShipperInfoDao shipperInfoDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(ShipperInfoBean bean) {
		logger.info("ShipperInfoServiceImpl call save  method");
		ShipperInfo sInfo = shipperInfoDao.save(convertBeanToEntity(bean));
		return (null != sInfo.getShipperId() ? sInfo.getShipperId() : null);
	}

	@Override
	public ShipperInfoBean findById(Integer id) {
		logger.info("ShipperInfoServiceImpl call findById  method");
		return new ShipperInfoBean(shipperInfoDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("ShipperInfoServiceImpl call exists  method");
		return shipperInfoDao.exists(id);
	}

	@Override
	public List<ShipperInfoBean> findAll() {
		logger.info("ShipperInfoServiceImpl call findAll  method");
		return convertEntityListToBeanList(shipperInfoDao.findAll());
	}

	@Override
	public List<ShipperInfoBean> findAll(Iterable<Integer> ids) {
		logger.info("ShipperInfoServiceImpl call findAll  method");
		return convertEntityListToBeanList(shipperInfoDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("ShipperInfoServiceImpl call count  method");
		return shipperInfoDao.count();
	}

	@Override
	public void delete(ShipperInfoBean bean) {
		logger.info("ShipperInfoServiceImpl call delete  method");
		shipperInfoDao.delete(convertBeanToEntity(bean));

	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	private ShipperInfo convertBeanToEntity(ShipperInfoBean bean) {
		logger.info("ShipperInfoServiceImpl call convertBeanToEntity  method");
		if (null != bean) {
			ShipperInfo shipperInfo = new ShipperInfo();
			shipperInfo.setShipperId(bean.getShipperId());
			shipperInfo.setInfoLine1(bean.getInfoLine1());
			shipperInfo.setInfoLine2(bean.getInfoLine2());
			shipperInfo.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			return shipperInfo;
		}
		return null;
	}

	private List<ShipperInfoBean> convertEntityListToBeanList(List<ShipperInfo> list) {
		logger.info("ShipperInfoServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<ShipperInfoBean> shipperInfoBeanList = new ArrayList<ShipperInfoBean>(0);
			for (ShipperInfo shipperInfo : list) {
				shipperInfoBeanList.add(new ShipperInfoBean(shipperInfo));
			}
			return shipperInfoBeanList;
		}
		return null;
	}

}
