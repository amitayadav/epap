package com.auction.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.VatBalanceDao;
import com.auction.model.bean.VatBalanceBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AccountTypeCodes;
import com.auction.model.entity.TransactionCode;
import com.auction.model.entity.VatBalance;
import com.auction.service.VatService;

@Service
@Transactional
public class VatServiceImpl implements VatService {

	@Autowired
	private VatBalanceDao vatBalanceDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(VatBalanceBean bean) {
		logger.info("VatServiceImpl call save  method");
		VatBalance vatBalance = vatBalanceDao.save(convertBeanToEntity(bean));
		return (null != vatBalance ? vatBalance.getVatBalanceId(): null);
	}

	@Override
	public VatBalanceBean findById(Integer id) {
		logger.info("VatServiceImpl call findById  method");
		return new VatBalanceBean(vatBalanceDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("VatServiceImpl call exists  method");
		return vatBalanceDao.exists(id);
	}

	@Override
	public List<VatBalanceBean> findAll() {
		logger.info("VatServiceImpl call findAll  method");
		return convertEntityListToBeanList(vatBalanceDao.findAll());
	}

	@Override
	public List<VatBalanceBean> findAll(Iterable<Integer> ids) {
		logger.info("VatServiceImpl call findAll  method");
		return convertEntityListToBeanList(vatBalanceDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("VatServiceImpl call count  method");
		return vatBalanceDao.count();
	}

	@Override
	public void delete(VatBalanceBean bean) {
		logger.info("VatServiceImpl call delete  method");
		vatBalanceDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}
	private VatBalance convertBeanToEntity(VatBalanceBean bean) {
		logger.info("VatServiceImpl call convertBeanToEntity  method");
		if (null != bean) { 
			VatBalance vatBalance = new VatBalance();
			vatBalance.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			vatBalance.setAccountTypeCodes(new AccountTypeCodes(bean.getAccountTypeCodesBean().getAccountType()));
			vatBalance.setTransactionCode(new TransactionCode(bean.getTransactionCodebean().getTransactionCode()));
			vatBalance.setTransactionDate(bean.getTransactionDate());
			vatBalance.setTransactionId(bean.getTransactionId());		
			vatBalance.setBalance(bean.getBalance());
			vatBalance.setTransactionDescription(bean.getTransactionDescription());
			vatBalance.setDebitCredit(bean.getDebitCredit());
			vatBalance.setBalance(bean.getBalance());	
			vatBalance.setComments(bean.getComments());
			return vatBalance;
		}
		return null;
	}
	private List<VatBalanceBean> convertEntityListToBeanList(List<VatBalance> list) {
		logger.info("VatServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<VatBalanceBean> balanceBeansList = new ArrayList<VatBalanceBean>(0);
			for (VatBalance vatBalance : list) {
				balanceBeansList.add(new VatBalanceBean(vatBalance));			}
			return balanceBeansList;
		}
		return null;
	}
	
	public BigDecimal getLastVatBalance() {
		return vatBalanceDao.getLastVatBalance();
	}

	@Override
	public List<VatBalanceBean> getVatBalanceBeanBetweenDate(java.sql.Date startDate, java.sql.Date endDate) {
		logger.info("VatServiceImpl call getVatBalanceBeanBetweenDate  method");
		return convertEntityListToBeanList(vatBalanceDao.findByTransactionDateBetween(startDate, endDate));
		
	}
	
	 

}