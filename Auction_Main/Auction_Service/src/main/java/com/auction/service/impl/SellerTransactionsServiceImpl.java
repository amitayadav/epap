package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.TypeConverterUtil;
import com.auction.dao.SellerTransactionsDao;
import com.auction.model.bean.SellerTransactionsBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionBuyers;
import com.auction.model.entity.SellerTransactions;
import com.auction.model.views.SellerSalesNoticesView;
import com.auction.service.SellerTransactionsService;

@Service
@Transactional
public class SellerTransactionsServiceImpl implements SellerTransactionsService {

	@Autowired
	private SellerTransactionsDao sellerTransactionDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(SellerTransactionsBean bean) {
		logger.info("SellerTransactionsServiceImpl call save  method");
		SellerTransactions sellerTransaction = sellerTransactionDao.save(convertBeanToEntity(bean));
		if(null != sellerTransaction.getTransactionId() && sellerTransaction.getTransactionId() > 0) {
			bean.setTransactionId(sellerTransaction.getTransactionId());
		}
		return (null != sellerTransaction ? sellerTransaction.getAccountProfile().getAccountId() : null);
	}

	@Override
	public SellerTransactionsBean findById(Integer id) {
		logger.info("SellerTransactionsServiceImpl call findById  method");
		return new SellerTransactionsBean(sellerTransactionDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("SellerTransactionsServiceImpl call exists  method");
		return sellerTransactionDao.exists(id);
	}

	@Override
	public List<SellerTransactionsBean> findAll() {
		logger.info("SellerTransactionsServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerTransactionDao.findAll());
	}

	@Override
	public List<SellerTransactionsBean> findAll(Iterable<Integer> ids) {
		logger.info("SellerTransactionsServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerTransactionDao.findAll());
	}

	@Override
	public long count() {
		logger.info("SellerTransactionsServiceImpl call count  method");
		return sellerTransactionDao.count();
	}

	@Override
	public void delete(SellerTransactionsBean bean) {
		logger.info("SellerTransactionsServiceImpl call delete  method");
		sellerTransactionDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public Boolean countShippingChargeByAuctionSeller(Integer auctionSellersId) {
		logger.info("SellerTransactionsServiceImpl call countShippingChargeByAuctionSeller  method");
		return sellerTransactionDao.countShippingChargeByAuctionSeller(auctionSellersId);
	}
	
	private SellerTransactions convertBeanToEntity(SellerTransactionsBean bean) {
		logger.info("SellerTransactionsServiceImpl call convertBeanToEntity  method");
		if (null != bean) {
			SellerTransactions sellerTransactions = new SellerTransactions();
			sellerTransactions.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			sellerTransactions.setAuctionBuyers(new AuctionBuyers(bean.getAuctionBuyersBean().getAuctionBuyersId()));
			sellerTransactions.setGrossSale(bean.getGrossSale());
			sellerTransactions.setRoyaltyPercentage(bean.getRoyaltyPercentage());
			sellerTransactions.setRoyaltyAmount(bean.getRoyaltyAmount());
			sellerTransactions.setVatPercentage(bean.getVatPercentage());
			sellerTransactions.setVatAmount(bean.getVatAmount());
			sellerTransactions.setSellerShippingCharge((null != bean.getSellerShippingCharge() ? bean.getSellerShippingCharge() : 0F));
			sellerTransactions.setNetSales(bean.getNetSales());
			sellerTransactions.setReserved1(bean.getReserved1());
			sellerTransactions.setReserved2(bean.getReserved2());
			sellerTransactions.setSellPrice(bean.getSellPrice());
			sellerTransactions.setSellQuantity(bean.getSellQuantity());
			sellerTransactions.setTransactionCreation(bean.getTransactionCreation());
			sellerTransactions.setTransactionId(bean.getTransactionId());
			return sellerTransactions;
		}
		return null;
	}

	private List<SellerTransactionsBean> convertEntityListToBeanList(List<SellerTransactions> list) {
		logger.info("SellerTransactionsServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {

			List<SellerTransactionsBean> sellerTransactionBeansList = new ArrayList<SellerTransactionsBean>(0);
			for (SellerTransactions sellerTransactions : list) {
				sellerTransactionBeansList.add(new SellerTransactionsBean(sellerTransactions));
			}
			return sellerTransactionBeansList;
		}
		return null;
	}




}