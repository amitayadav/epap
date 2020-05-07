package com.auction.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.BankDetailsDao;
import com.auction.model.bean.BankDetailsBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.BankDetails;
import com.auction.service.BankDetailsService;

@Service
@Transactional
public class BankDetailsServiceImpl implements BankDetailsService {

	@Autowired
	private BankDetailsDao bankDetailsDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(BankDetailsBean bean) {
		logger.info("BankDetailsServiceImpl call  count method");
		BankDetails bankDetails = bankDetailsDao.save(convertBeanToEntity(bean));
		return (null != bankDetails ? bankDetails.getAccountId() : null);
	}

	@Override
	public BankDetailsBean findById(Integer id) {
		logger.info("BankDetailsServiceImpl  call findById method");
		return new BankDetailsBean(bankDetailsDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("BankDetailsServiceImpl  call exists method");
		return bankDetailsDao.exists(id);
	}

	@Override
	public List<BankDetailsBean> findAll() {
		logger.info("BankDetailsServiceImpl  call findAll method");
		return convertEntityListToBeanList(bankDetailsDao.findAll());
	}

	@Override
	public List<BankDetailsBean> findAll(Iterable<Integer> ids) {
		logger.info("BankDetailsServiceImpl  call findAll method");
		return convertEntityListToBeanList(bankDetailsDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("BankDetailsServiceImpl  call count method");
		return bankDetailsDao.count();
	}

	@Override
	public void delete(BankDetailsBean bean) {
		logger.info("BankDetailsServiceImpl  call delete method");
		bankDetailsDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public BigDecimal getAvailableBalanceByAccountId(Integer accountId) {
		logger.info("BankDetailsServiceImpl  call getAvailableBalanceByAccountId method");
		return bankDetailsDao.getAvailableBalanceByAccountId(accountId);
	}

	@Override
	public BigDecimal getAvailableBalanceOfOwnerByAccountId(String loginUserId) {
		logger.info("BankDetailsServiceImpl  call getAvailableBalanceOfOwnerByAccountId method");
		return bankDetailsDao.getAvailableBalanceOfOwnerByAccountId(loginUserId);
	}

	public BankDetails convertBeanToEntity(BankDetailsBean bean) {
		logger.info("BankDetailsServiceImpl  call convertBeanToEntity method");
		if (null != bean) {

			BankDetails entity = new BankDetails();
			entity.setAccountId(bean.getAccountId());
			entity.setAccountProfile(new AccountProfile(bean.getAccountId()));
			entity.setBankName(bean.getBankName());
			entity.setAvailableBalance(bean.getAvailableBalance());
			entity.setBankAccountNumber(bean.getBankAccountNumber());
			entity.setBlockedAmount(bean.getBlockedAmount());
			entity.setIban(bean.getIban());
			entity.setAdvanceBalance(bean.getAdvanceBalance());
			entity.setCashposition(bean.getCashposition());
			return entity;
		}
		return null;
	}

	public List<BankDetailsBean> convertEntityListToBeanList(List<BankDetails> list) {
		logger.info("BankDetailsServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {

			List<BankDetailsBean> bankDetailsBeansList = new ArrayList<BankDetailsBean>();
			for (BankDetails bankDetails : list) {
				bankDetailsBeansList.add(new BankDetailsBean(bankDetails));
			}
			return bankDetailsBeansList;
		}
		return null;
	}

}