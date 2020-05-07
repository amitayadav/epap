package com.auction.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.EpapBalanceDao;
import com.auction.model.bean.EpapBalanceBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AccountTypeCodes;
import com.auction.model.entity.EpapBalance;
import com.auction.model.entity.TransactionCode;
import com.auction.service.EpapService;

@Service
@Transactional
public class EpapServiceImpl implements EpapService {

	@Autowired
	private EpapBalanceDao epapBalanceDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(EpapBalanceBean bean) {
		logger.info("EpapServiceImpl  call save method");
		EpapBalance epapBalance = epapBalanceDao.save(convertBeanToEntity(bean));
		if(null != epapBalance.getEpapBalanceId() && epapBalance.getEpapBalanceId() > 0) {
			bean.setEpapBalanceId(epapBalance.getEpapBalanceId());
		}
		return (null != epapBalance ? epapBalance.getEpapBalanceId() : null);
	}

	@Override
	public EpapBalanceBean findById(Integer id) {
		logger.info("EpapServiceImpl  call findById method");
		return new EpapBalanceBean(epapBalanceDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("EpapServiceImpl  call exists method");
		return epapBalanceDao.exists(id);
	}

	@Override
	public List<EpapBalanceBean> findAll() {
		logger.info("EpapServiceImpl  call findAll method");
		return convertEntityListToBeanList(epapBalanceDao.findAll());
	}

	@Override
	public List<EpapBalanceBean> findAll(Iterable<Integer> ids) {
		logger.info("EpapServiceImpl  call findAll method");
		return convertEntityListToBeanList(epapBalanceDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("EpapServiceImpl  call count method");
		return epapBalanceDao.count();
	}

	@Override
	public void delete(EpapBalanceBean bean) {
		logger.info("EpapServiceImpl  call delete method");
		epapBalanceDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	private EpapBalance convertBeanToEntity(EpapBalanceBean bean) {
		logger.info("EpapServiceImpl  call convertBeanToEntity method");
		if (null != bean) {
			EpapBalance epapBalance = new EpapBalance();
			epapBalance.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			epapBalance.setAccountTypeCodes(new AccountTypeCodes(bean.getAccountTypeCodesBean().getAccountType()));
			epapBalance.setTransactionCode(new TransactionCode(bean.getTransactionCodebean().getTransactionCode()));
			epapBalance.setTransactionDate(bean.getTransactionDate());
			epapBalance.setTransactionId(bean.getTransactionId());
			epapBalance.setBalance(bean.getBalance());
			epapBalance.setTransactionDescription(bean.getTransactionDescription());
			epapBalance.setDebitCredit(bean.getDebitCredit());
			epapBalance.setAdvanceBalance(bean.getAdvanceBalance());
			epapBalance.setBalance(bean.getBalance());
			epapBalance.setCashposition(bean.getCashposition());
			epapBalance.setComments(bean.getComments());
			epapBalance.setEpapBalanceId(bean.getEpapBalanceId());
			return epapBalance;
		}
		return null;
	}

	private List<EpapBalanceBean> convertEntityListToBeanList(List<EpapBalance> list) {
		logger.info("EpapServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<EpapBalanceBean> balanceBeansList = new ArrayList<EpapBalanceBean>(0);
			for (EpapBalance epapBalance : list) {
				balanceBeansList.add(new EpapBalanceBean(epapBalance));
			}
			return balanceBeansList;
		}
		return null;
	}

	public BigDecimal getLastEpapBalance() {
		logger.info("EpapServiceImpl  call getLastEpapBalance method");
		return epapBalanceDao.getLastEpapBalance();
	}

	@Override
	public List<EpapBalanceBean> getEpapBalanceBeanBetweenDate(java.sql.Date startDate, java.sql.Date endDate) {
		logger.info("EpapServiceImpl  call getEpapBalanceBeanBetweenDate method");
		return convertEntityListToBeanList(epapBalanceDao.findByTransactionDateBetween(startDate, endDate));
	}

}