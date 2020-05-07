package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AccountLocationsDao;
import com.auction.model.bean.AccountLocationsBean;
import com.auction.model.entity.AccountLocations;
import com.auction.model.entity.AccountProfile;
import com.auction.service.AccountLocationsService;

@Service
@Transactional
public class AccountLocationsServiceImpl implements AccountLocationsService {
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Autowired
	AccountLocationsDao accountLocationsDao;

	@Override
	public Integer save(AccountLocationsBean bean) {
		logger.info("AccountLocationsServiceImpl call save method ");
		AccountLocations locations = accountLocationsDao.save(convertBeanToEntity(bean));
		return (null != locations ? locations.getLocationId() : null);
	}

	@Override
	public AccountLocationsBean findById(Integer id) {
		logger.info("AccountLocationsServiceImpl call findById method ");
		return new AccountLocationsBean(accountLocationsDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("AccountLocationsServiceImpl call exists method ");
		return accountLocationsDao.exists(id);
	}

	@Override
	public List<AccountLocationsBean> findAll() {
		logger.info("AccountLocationsServiceImpl call findAll method ");
		return convertEntityListToBeanList(accountLocationsDao.findAll());
	}

	@Override
	public List<AccountLocationsBean> findAll(Iterable<Integer> ids) {
		logger.info("AccountLocationsServiceImpl call findAll method ");
		return convertEntityListToBeanList(accountLocationsDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("AccountLocationsServiceImpl call count method ");
		return accountLocationsDao.count();
	}

	@Override
	public void delete(AccountLocationsBean bean) {
		logger.info("AccountLocationsServiceImpl call delete method ");
		accountLocationsDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public List<AccountLocationsBean> findByAccountProfileAccountIdAndStatusIn(Integer accountId, Short[] status) {
		logger.info("AccountLocationsServiceImpl call findByAccountProfileAccountIdAndStatusIn method ");
		return convertEntityListToBeanList(accountLocationsDao.findByAccountProfileAccountIdAndStatusIn(accountId, status));
	}

	/**
	 * This method is used to convert AccountLocationsBean to AccountLocations
	 * 
	 * @param bean
	 * @return entity
	 */
	private AccountLocations convertBeanToEntity(AccountLocationsBean bean) {
		logger.info("AccountLocationsServiceImpl call convertBeanToEntity method ");
		if (null != bean) {
			AccountLocations entity = new AccountLocations();
			entity.setLocationId(bean.getLocationId());
			entity.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			entity.setLatitude(bean.getLatitude());
			entity.setLongitude(bean.getLongitude());
			entity.setLocationName(bean.getLocationName());
			entity.setStatus(bean.getStatus());
			return entity;
		}
		return null;
	}

	/**
	 * This method is used to convert AccountLocations List to AccountLocationsBean
	 * List
	 * 
	 * @param List<AccountLocations>
	 *            list
	 * @return List<AccountLocationsBean> list
	 */
	private List<AccountLocationsBean> convertEntityListToBeanList(List<AccountLocations> list) {
		logger.info("AccountLocationsServiceImpl call convertEntityListToBeanList method ");
		if (null != list && list.size() > 0) {

			List<AccountLocationsBean> accountLocationsBeansList = new ArrayList<AccountLocationsBean>(0);

			for (AccountLocations locations : list) {
				accountLocationsBeansList.add(new AccountLocationsBean(locations));
			}
			return accountLocationsBeansList;
		}
		return null;
	}

}