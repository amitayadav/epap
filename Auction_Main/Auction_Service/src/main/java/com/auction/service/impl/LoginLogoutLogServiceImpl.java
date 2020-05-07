package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.LoginLogoutLogDao;
import com.auction.model.bean.LoginLogoutLogBean;
import com.auction.model.entity.LoginDetails;
import com.auction.model.entity.LoginLogoutLog;
import com.auction.model.entity.LoginLogoutLogId;
import com.auction.service.LoginLogoutLogService;

@Service
@Transactional
public class LoginLogoutLogServiceImpl implements LoginLogoutLogService {

	@Autowired
	LoginLogoutLogDao loginLogoutLogDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public String save(LoginLogoutLogBean bean) {
		logger.info("LoginLogoutLogServiceImpl call save method");
		LoginLogoutLog loginLogoutLog = loginLogoutLogDao.save(convertBeanToEntity(bean));
		return (null != loginLogoutLog ? loginLogoutLog.getId().getLoginDetails().getLoginUserid() : null);
	}

	@Override
	public LoginLogoutLogBean findById(String id) {
		logger.info("LoginLogoutLogServiceImpl call findById method");
		return new LoginLogoutLogBean(loginLogoutLogDao.findOne(id));
	}

	@Override
	public boolean exists(String id) {
		logger.info("LoginLogoutLogServiceImpl call exists method");
		return loginLogoutLogDao.exists(id);
	}

	@Override
	public List<LoginLogoutLogBean> findAll() {
		logger.info("LoginLogoutLogServiceImpl call findAll method");
		return convertEntityListToBeanList(loginLogoutLogDao.findAll());
	}

	@Override
	public List<LoginLogoutLogBean> findAll(Iterable<String> ids) {
		logger.info("LoginLogoutLogServiceImpl call findAll method");
		return convertEntityListToBeanList(loginLogoutLogDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("LoginLogoutLogServiceImpl call count method");
		return loginLogoutLogDao.count();
	}

	@Override
	public void delete(LoginLogoutLogBean bean) {
		logger.info("LoginLogoutLogServiceImpl call delete method");
		loginLogoutLogDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public String refresh(String id) {
		return null;
	}

	@Override
	public void updateLogoutTime(String logoutTimestamp, String loginUserid, String loginTimestamp) {
		loginLogoutLogDao.updateLogoutTime(logoutTimestamp, loginUserid, loginTimestamp);
		logger.info("LoginLogoutLogServiceImpl updateLogoutTime " + "logoutTimestamp:" + logoutTimestamp + loginUserid + "loginTimestamp:" + loginTimestamp);
	}

	@Override
	public List<LoginLogoutLogBean> getByAccountIdAndLoginTimestamp(String loginUserid, Date loginTimestamp) {
		logger.info("LoginLogoutLogServiceImpl call getByAccountIdAndLoginTimestamp method");
		return convertEntityListToBeanList(loginLogoutLogDao.getByAccountIdAndLoginTimestamp(loginUserid, loginTimestamp));
	}

	@Override
	public LoginLogoutLogBean getLogoutTimestampByAccountId(String loginUserid) {
		logger.info("LoginLogoutLogServiceImpl call getLogoutTimestampByAccountId method");
		return new LoginLogoutLogBean(loginLogoutLogDao.getLogoutTimestampByAccountId(loginUserid));

	}

	private LoginLogoutLog convertBeanToEntity(LoginLogoutLogBean bean) {
		logger.info("LoginLogoutLogServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			LoginLogoutLogId loginLogoutLogIdEntity = new LoginLogoutLogId();
			loginLogoutLogIdEntity.setLoginDetails(new LoginDetails(bean.getId().getLoginDetailsBean().getLoginUserid()));
			loginLogoutLogIdEntity.setLoginTimestamp(bean.getId().getLoginTimestamp());
			LoginLogoutLog loginLogoutLogEntity = new LoginLogoutLog(loginLogoutLogIdEntity);
			loginLogoutLogEntity.setLogoutTimestamp(bean.getLogoutTimestamp());
			return loginLogoutLogEntity;
		}
		return null;
	}

	private List<LoginLogoutLogBean> convertEntityListToBeanList(List<LoginLogoutLog> list) {
		logger.info("LoginLogoutLogServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {

			List<LoginLogoutLogBean> logoutLogBeansList = new ArrayList<LoginLogoutLogBean>(0);

			for (LoginLogoutLog log : list) {
				logoutLogBeansList.add(new LoginLogoutLogBean(log));
			}

			return logoutLogBeansList;
		}
		return null;
	}

}
