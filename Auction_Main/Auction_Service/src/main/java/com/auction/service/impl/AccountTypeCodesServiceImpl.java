package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AccountTypeCodesDao;
import com.auction.model.bean.AccountTypeCodesBean;
import com.auction.model.entity.AccountTypeCodes;
import com.auction.service.AccountTypeCodesService;

@Service
@Transactional
public class AccountTypeCodesServiceImpl implements AccountTypeCodesService {

	@Autowired
	AccountTypeCodesDao accountTypeCodesDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	public Character save(AccountTypeCodesBean bean) {
		logger.info("AccountTypeCodesServiceImpl call save method");
		AccountTypeCodes accountTypeCodes = accountTypeCodesDao.save(convertBeanToEntity(bean));
		return (null != accountTypeCodes ? accountTypeCodes.getAccountType() : null);
	}

	public AccountTypeCodesBean findById(Character id) {
		logger.info("AccountTypeCodesServiceImpl call findById method");
		return convertEntityToBean(accountTypeCodesDao.findOne(id));
	}

	public boolean exists(Character id) {
		logger.info("AccountTypeCodesServiceImpl call exists method");
		return accountTypeCodesDao.exists(id);
	}

	public List<AccountTypeCodesBean> findAll() {
		logger.info("AccountTypeCodesServiceImpl call findAll method");
		return convertEntityListToBeanList(accountTypeCodesDao.findAll());
	}

	public List<AccountTypeCodesBean> findAll(Iterable<Character> ids) {
		logger.info("AccountTypeCodesServiceImpl call findAll method");
		return convertEntityListToBeanList(accountTypeCodesDao.findAll(ids));
	}

	public long count() {
		logger.info("AccountTypeCodesServiceImpl call count method");
		return accountTypeCodesDao.count();
	}

	public void delete(AccountTypeCodesBean bean) {
		logger.info("AccountTypeCodesServiceImpl call delete method");
		accountTypeCodesDao.delete(convertBeanToEntity(bean));
	}

	public Character refresh(Character id) {
		AccountTypeCodes accountTypeCodes = accountTypeCodesDao.findOne(id);
		return (null != accountTypeCodes ? accountTypeCodes.getAccountType() : null);
	}

	@Override
	public List<AccountTypeCodesBean> getAllAccountTypeCodeExceptAdmin(Character[] accountTypes) {
		logger.info("AccountTypeCodesServiceImpl call getAllAccountTypeCodeExceptAdmin method ");
		return convertEntityListToBeanList(accountTypeCodesDao.getAllAccountTypeCodeExceptAdmin(accountTypes));
	}

	@Override
	public AccountTypeCodesBean getAccountTypeCodeByAccountId(Integer accountId) {
		logger.info("AccountTypeCodesServiceImpl call getAccountTypeCodeByAccountId method ");
		return new AccountTypeCodesBean(accountTypeCodesDao.getAccountTypeCodeByAccountId(accountId));
	}

	private AccountTypeCodes convertBeanToEntity(AccountTypeCodesBean bean) {
		logger.info("AccountTypeCodesServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			AccountTypeCodes entity = new AccountTypeCodes();
			entity.setAccountType(bean.getAccountType());
			entity.setAccountTypeMeaning(bean.getAccountTypeMeaning());
			return entity;
		}
		return null;
	}

	private AccountTypeCodesBean convertEntityToBean(AccountTypeCodes entity) {
		logger.info("AccountTypeCodesServiceImpl call convertEntityToBean method");
		if (null != entity) {
			AccountTypeCodesBean bean = new AccountTypeCodesBean();
			bean.setAccountType(entity.getAccountType());
			bean.setAccountTypeMeaning(entity.getAccountTypeMeaning());
			return bean;
		}
		return null;
	}

	private List<AccountTypeCodesBean> convertEntityListToBeanList(List<AccountTypeCodes> accountTypeCodesList) {
		logger.info("AccountTypeCodesServiceImpl call convertEntityListToBeanList method");
		List<AccountTypeCodesBean> accountTypeCodesBeanList = null;
		if (null != accountTypeCodesList && accountTypeCodesList.size() > 0) {
			accountTypeCodesBeanList = new ArrayList<AccountTypeCodesBean>(0);
			for (AccountTypeCodes accountTypeCodes : accountTypeCodesList) {
				accountTypeCodesBeanList.add(convertEntityToBean(accountTypeCodes));
			}
		}
		return accountTypeCodesBeanList;
	}

}