package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.ShipperTransactionsDao;
import com.auction.model.bean.ShipperTransactionsBean;
import com.auction.model.entity.AccountLocations;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionBuyers;
import com.auction.model.entity.ShipperTransactions;
import com.auction.service.ShipperTransactionsService;

@Service
@Transactional
public class ShipperTransactionsServiceimpl implements ShipperTransactionsService {

	@Autowired
	private ShipperTransactionsDao shipperTransactionsDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(ShipperTransactionsBean bean) {
		logger.info("ShipperTransactionsServiceimpl call save  method");
		ShipperTransactions shipperTransaction = shipperTransactionsDao.save(convertBeanToEntity(bean));
		if (null != shipperTransaction.getTransactionId() && shipperTransaction.getTransactionId() > 0) {
			bean.setTransactionId(shipperTransaction.getTransactionId());
		}
		return (null != shipperTransaction ? shipperTransaction.getAccountProfile().getAccountId() : null);

	}

	@Override
	public ShipperTransactionsBean findById(Integer id) {
		logger.info("ShipperTransactionsServiceimpl call findById  method");
		return new ShipperTransactionsBean(shipperTransactionsDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("ShipperTransactionsServiceimpl call exists  method");
		return shipperTransactionsDao.exists(id);
	}

	@Override
	public List<ShipperTransactionsBean> findAll() {
		logger.info("ShipperTransactionsServiceimpl call findAll  method");
		return convertEntityListToBeanList(shipperTransactionsDao.findAll());

	}

	@Override
	public List<ShipperTransactionsBean> findAll(Iterable<Integer> ids) {
		logger.info("ShipperTransactionsServiceimpl call findAll  method");
		return convertEntityListToBeanList(shipperTransactionsDao.findAll());
	}

	@Override
	public long count() {
		logger.info("ShipperTransactionsServiceimpl call count  method");
		return shipperTransactionsDao.count();
	}

	@Override
	public void delete(ShipperTransactionsBean bean) {
		logger.info("ShipperTransactionsServiceimpl call delete  method");
		shipperTransactionsDao.delete(convertBeanToEntity(bean));

	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	private ShipperTransactions convertBeanToEntity(ShipperTransactionsBean bean) {
		logger.info("ShipperTransactionsServiceimpl call convertBeanToEntity  method");
		if (null != bean) {
			ShipperTransactions shipperTransactions = new ShipperTransactions();

			shipperTransactions.setAccountLocationsByShippingFromLocation(new AccountLocations(bean.getAccountLocationsByShippingFromLocation().getLocationId()));
			shipperTransactions.setAccountLocationsByShippingToLocation(new AccountLocations(bean.getAccountLocationsByShippingToLocation().getLocationId()));
			shipperTransactions.setAccountProfile(new AccountProfile(bean.getAccountProfile().getAccountId()));
			shipperTransactions.setAuctionBuyers(new AuctionBuyers(bean.getAuctionBuyers().getAuctionBuyersId()));
			shipperTransactions.setGrossRevenue(bean.getGrossRevenue());
			shipperTransactions.setRoyaltyPercentage(bean.getRoyaltyPercentage());
			shipperTransactions.setRoyaltyAmount(bean.getRoyaltyAmount());
			shipperTransactions.setVatPercentage(bean.getVatPercentage());
			shipperTransactions.setVatAmount(bean.getVatAmount());
			shipperTransactions.setNetRevenue(bean.getNetRevenue());
			shipperTransactions.setTransactionCreation(bean.getTransactionCreation());
			return shipperTransactions;
		}
		return null;

	}

	private List<ShipperTransactionsBean> convertEntityListToBeanList(List<ShipperTransactions> list) {
		logger.info("ShipperTransactionsServiceimpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<ShipperTransactionsBean> ShipperTransactionsBeanList = new ArrayList<ShipperTransactionsBean>(0);
			for (ShipperTransactions shipperTransactions : list) {
				ShipperTransactionsBeanList.add(new ShipperTransactionsBean(shipperTransactions));
			}
			return ShipperTransactionsBeanList;
		}
		return null;
	}
}