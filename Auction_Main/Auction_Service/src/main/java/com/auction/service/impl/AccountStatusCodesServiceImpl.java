package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AccountStatusCodesDao;
import com.auction.model.bean.AccountStatusCodesBean;
import com.auction.model.entity.AccountStatusCodes;
import com.auction.service.AccountStatusCodesService;

@Service
@Transactional
public class AccountStatusCodesServiceImpl implements AccountStatusCodesService {

	@Autowired
	private AccountStatusCodesDao accountStatusCodesDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Short save(AccountStatusCodesBean bean) {
		logger.info("AccountStatusCodesServiceImpl call save method");
		AccountStatusCodes code = accountStatusCodesDao.save(convertBeanToEntity(bean));
		return (null != code ? code.getAccountStatusCode() : null);
	}

	@Override
	public AccountStatusCodesBean findById(Short id) {
		logger.info("AccountStatusCodesServiceImpl call findById method");
		return new AccountStatusCodesBean(accountStatusCodesDao.findOne(id));
	}

	@Override
	public boolean exists(Short id) {
		logger.info("AccountStatusCodesServiceImpl call exists method");
		return accountStatusCodesDao.exists(id);
	}

	@Override
	public List<AccountStatusCodesBean> findAll() {
		logger.info("AccountStatusCodesServiceImpl call findAll method");
		return convertEntityListToBeanList(accountStatusCodesDao.findAll());
	}

	@Override
	public List<AccountStatusCodesBean> findAll(Iterable<Short> ids) {
		logger.info("AccountStatusCodesServiceImpl call findAll method");
		return convertEntityListToBeanList(accountStatusCodesDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("AccountStatusCodesServiceImpl call count method");
		return accountStatusCodesDao.count();
	}

	@Override
	public void delete(AccountStatusCodesBean bean) {

	}

	@Override
	public Short refresh(Short id) {
		return null;
	}

	@Override
	public List<AccountStatusCodesBean> findByAccountStatusCodes(Short[] codeList) {
		logger.info("AccountStatusCodesServiceImpl call findByAccountStatusCodes method ");
		return convertEntityListToBeanList(accountStatusCodesDao.findByAccountStatusCodes(codeList));
	}

	@Override
	public List<AccountStatusCodesBean> findByNotInAccountStatusCodes(Short[] codeList) {
		logger.info("AccountStatusCodesServiceImpl call findByNotInAccountStatusCodes method ");
		return convertEntityListToBeanList(accountStatusCodesDao.findByNotInAccountStatusCodes(codeList));
	}

	@Override
	public List<AccountStatusCodesBean> findAllByOrderByAccountStatusCodeAsc() {
		logger.info("AccountStatusCodesServiceImpl call findAllByOrderByAccountStatusCodeAsc method ");
		return convertEntityListToBeanList(accountStatusCodesDao.findAllByOrderByAccountStatusCodeAsc());
	}

	private AccountStatusCodes convertBeanToEntity(AccountStatusCodesBean bean) {
		logger.info("AccountStatusCodesServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			AccountStatusCodes entity = new AccountStatusCodes();
			entity.setAccountStatusCode(bean.getAccountStatusCode());
			entity.setAccountStatusCodeMeaning(bean.getAccountStatusCodeMeaning());
			return entity;
		}
		return null;
	}

	private List<AccountStatusCodesBean> convertEntityListToBeanList(List<AccountStatusCodes> list) {
		logger.info("AccountStatusCodesServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<AccountStatusCodesBean> beansList = new ArrayList<AccountStatusCodesBean>(0);
			for (AccountStatusCodes code : list) {
				beansList.add(new AccountStatusCodesBean(code));
			}
			return beansList;
		}
		return null;
	}

}