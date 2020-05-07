package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.BuyerTransactionsDao;
import com.auction.model.bean.BuyerTransactionsBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionBuyers;
import com.auction.model.entity.BuyerTransactions;
import com.auction.service.BuyerTransactionsService;

@Service
@Transactional
public class BuyerTransactionsServiceImpl implements BuyerTransactionsService {

	@Autowired
	private BuyerTransactionsDao buyerTransactionsDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(BuyerTransactionsBean bean) {
		logger.info("BuyerTransactionsServiceImpl  call save method");
		BuyerTransactions buyerTransactions = buyerTransactionsDao.save(convertBeanToEntity(bean));
		if(null != buyerTransactions.getTransactionId() && buyerTransactions.getTransactionId() > 0) {
			bean.setTransactionId(buyerTransactions.getTransactionId());
		}
		return (null != buyerTransactions ? buyerTransactions.getAccountProfile().getAccountId() : null );
	}

	@Override
	public BuyerTransactionsBean findById(Integer id) {
		logger.info("BuyerTransactionsServiceImpl  call findById method");
		return new BuyerTransactionsBean(buyerTransactionsDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("BuyerTransactionsServiceImpl  call exists method");
		return buyerTransactionsDao.exists(id);
	}

	@Override
	public List<BuyerTransactionsBean> findAll() {
		logger.info("BuyerTransactionsServiceImpl  call findAll method");
		return convertEntityListToBeanList(buyerTransactionsDao.findAll());
	}

	@Override
	public List<BuyerTransactionsBean> findAll(Iterable<Integer> ids) {
		logger.info("BuyerTransactionsServiceImpl  call findAll method");
		return convertEntityListToBeanList(buyerTransactionsDao.findAll());
	}

	@Override
	public long count() {
		logger.info("BuyerTransactionsServiceImpl  call count method");
		return buyerTransactionsDao.count();
	}

	@Override
	public void delete(BuyerTransactionsBean bean) {
		logger.info("BuyerTransactionsServiceImpl  call delete method");
		buyerTransactionsDao.delete(convertBeanToEntity(bean));

	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}
	
	private BuyerTransactions convertBeanToEntity(BuyerTransactionsBean bean) {
		logger.info("BuyerTransactionsServiceImpl  call convertBeanToEntity method");
		if(null != bean) {
			BuyerTransactions buyerTransactions = new BuyerTransactions();
			buyerTransactions.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			buyerTransactions.setAuctionBuyers(new AuctionBuyers(bean.getAuctionBuyersBean().getAuctionBuyersId()));
			buyerTransactions.setBuyPrice(bean.getBuyPrice());
			buyerTransactions.setBuyQuantity(bean.getBuyQuantity());
			buyerTransactions.setGrossPurchase(bean.getGrossPurchase());
			buyerTransactions.setRoyaltyPercentage(bean.getRoyaltyPercentage());
			buyerTransactions.setRoyaltyAmount(bean.getRoyaltyAmount());
			buyerTransactions.setVatPercentage(bean.getVatPercentage());
			buyerTransactions.setVatAmount(bean.getVatAmount());
			buyerTransactions.setNetPayment(bean.getNetPayment());
			buyerTransactions.setReserved1(bean.getReserved1());
			buyerTransactions.setReserved2(bean.getReserved2());
			buyerTransactions.setRoyaltyAmount(bean.getRoyaltyAmount());
			buyerTransactions.setBuyerShippingCharge((null != bean.getBuyerShippingCharge() ? bean.getBuyerShippingCharge() : 0F));
			buyerTransactions.setTransactionCreation(bean.getTransactionCreation());
			buyerTransactions.setTransactionId(bean.getTransactionId());
			return buyerTransactions;
		}
		return null;
	}

	public List<BuyerTransactionsBean> convertEntityListToBeanList(List<BuyerTransactions> list){
		logger.info("BuyerTransactionsServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<BuyerTransactionsBean> buyerTransactionsBeanList = new ArrayList<BuyerTransactionsBean>(); 
			for(BuyerTransactions buyerTransactions : list) {
				buyerTransactionsBeanList.add(new BuyerTransactionsBean(buyerTransactions));
			}
			return buyerTransactionsBeanList;
		}
		return null;
	}
}
