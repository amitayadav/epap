package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.LoginDetailsDao;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AccountStatusCodesBean;
import com.auction.model.bean.AccountTypeCodesBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AccountStatusCodes;
import com.auction.model.entity.AccountTypeCodes;
import com.auction.model.entity.LoginDetails;
import com.auction.model.views.LoginDetailsViews;
import com.auction.service.LoginDetailsService;

@Service
@Transactional
public class LoginDetailsServiceImpl implements LoginDetailsService {

	@Autowired
	private LoginDetailsDao loginDetailsDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public String save(LoginDetailsBean bean) {
		logger.info("LoginDetailsServiceImpl  call save method");
		LoginDetails loginDetails = loginDetailsDao.save(convertBeanToEntity(bean));
		return (null != loginDetails ? loginDetails.getLoginUserid() : null);
	}

	@Override
	public LoginDetailsBean findById(String id) {
		logger.info("LoginDetailsServiceImpl  call findById method");
		return convertEntityToBean(loginDetailsDao.findOne(id));
	}

	@Override
	public boolean exists(String id) {
		logger.info("LoginDetailsServiceImpl  call exists method");
		return loginDetailsDao.exists(id);
	}

	@Override
	public List<LoginDetailsBean> findAll() {
		logger.info("LoginDetailsServiceImpl  call findAll method");
		return convertEntityListToBeanList(loginDetailsDao.findAll());
	}

	@Override
	public List<LoginDetailsBean> findAll(Iterable<String> ids) {
		logger.info("LoginDetailsServiceImpl  call findAll method");
		return convertEntityListToBeanList(loginDetailsDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("LoginDetailsServiceImpl  call count method");
		return loginDetailsDao.count();
	}

	@Override
	public void delete(LoginDetailsBean bean) {
		logger.info("LoginDetailsServiceImpl  call delete method");
		loginDetailsDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public String refresh(String id) {
		logger.info("LoginDetailsServiceImpl  call refresh method");
		LoginDetails loginDetails = loginDetailsDao.findOne(id);
		return loginDetails.getLoginUserid();
	}

	@Override
	public LoginDetailsBean getLoginDetailsByAccountProfileId(Integer accountId) {
		logger.info("LoginDetailsServiceImpl  call getLoginDetailsByAccountProfileId method");
		return convertEntityToBean(loginDetailsDao.getLoginDetailsByAccountProfileId(accountId));
	}
	@Override
	public LoginDetailsBean getLoginDetailsByAccountProfile(Character accountType) {
		logger.info("LoginDetailsServiceImpl  call getLoginDetailsByAccountProfile method");
		return convertEntityToBean(loginDetailsDao.getLoginDetailsByAccountProfile(accountType));
	}
	@Override
	public Boolean isPublicNameUnique(String publicName) {
		logger.info("LoginDetailsServiceImpl  call isPublicNameUnique method");
		Integer count = loginDetailsDao.isPublicNameUnique(publicName);
		if (null != count && count > 0) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean isPrimaryEmailUnique(String primaryEmail) {
		logger.info("LoginDetailsServiceImpl  call isPrimaryEmailUnique method");
		Integer count = loginDetailsDao.isPrimaryEmailUnique(primaryEmail);
		if (null != count && count > 0) {
			return false;
		}
		return true;
	}

	@Override
	public void updateLoginUserByAccountId(String loginUserId, Integer account_id) {
		logger.info("LoginDetailsServiceImpl  call updateLoginUserByAccountId method");
		loginDetailsDao.updateLoginUserByAccountId(loginUserId, account_id);
	}

	@Override
	public List<LoginDetailsBean> getLoginDetailsListByAccountTypes(Character[] accountTypes) {
		logger.info("LoginDetailsServiceImpl  call getLoginDetailsListByAccountTypes method");
		return convertEntityListToBeanList(loginDetailsDao.getLoginDetailsListByAccountTypes(accountTypes));
	}

	@Override
	public List<LoginDetailsBean> getLoginDetailsByRoyaltyCode(Short royaltyCode) {
		logger.info("LoginDetailsServiceImpl  call getLoginDetailsByRoyaltyCode method");
		return convertEntityListToBeanList(loginDetailsDao.getLoginDetailsByRoyaltyCode(royaltyCode));
	}

	@Override
	public List<LoginDetailsBean> findByAccountTypeCodesIn(List<Character> typeList) {
		logger.info("LoginDetailsServiceImpl  call findByAccountTypeCodesIn method");
		return convertEntityListToBeanList(loginDetailsDao.findByAccountTypeCodesIn(typeList));
	}

	@Override
	public List<LoginDetailsBean> findByAccountStatusCodesAccountStatusCodeAndLoginUseridNot(Short status, String loginUserid) {
		logger.info("LoginDetailsServiceImpl  call findByAccountStatusCodesAccountStatusCodeAndLoginUseridNot method");
		return convertEntityListToBeanList(loginDetailsDao.findByAccountStatusCodesAccountStatusCodeAndLoginUseridNot(status, loginUserid));
	}

	@Override
	public List<LoginDetailsBean> findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeInAndLoginUseridNot(Short active, Character[] accountTypes,
			String loginUserid) {
		logger.info("LoginDetailsServiceImpl  call findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeInAndLoginUseridNot method");
		return convertEntityListToBeanList(
				loginDetailsDao.findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeInAndLoginUseridNot(active, accountTypes, loginUserid));
	}

	@Override
	public List<LoginDetailsBean> findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeIn(Short status, Character[] accountTypes) {
		logger.info("LoginDetailsServiceImpl  call findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeIn method");
		return convertEntityListToBeanList(loginDetailsDao.findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeIn(status, accountTypes));
	}

	@Override
	public LoginDetailsBean findOwnerLoginDetailsByAgentLoginUserId(String loginUserid) {
		logger.info("LoginDetailsServiceImpl  call findOwnerLoginDetailsByAgentLoginUserId method");
		return new LoginDetailsBean(loginDetailsDao.findOwnerLoginDetailsByAgentLoginUserId(loginUserid));
	}

	@Override
	public List<LoginDetailsBean> findByAccountStatusCodesAccountStatusCodeInAndLoginUserid(Short[] accountStatusCodeList, String loginUserid) {
		logger.info("LoginDetailsServiceImpl  call findByAccountStatusCodesAccountStatusCodeInAndLoginUserid method");
		return convertEntityListToBeanList(loginDetailsDao.findByAccountStatusCodesAccountStatusCodeInAndLoginUserid(accountStatusCodeList, loginUserid));
	}

	@Override
	public List<LoginDetailsBean> getComplaintUsers(Short[] accountStatusCodeList, Character[] accountTypeCodeList, String loginUserid) {
		logger.info("LoginDetailsServiceImpl  call getComplaintUsers method");
		return convertEntityListToBeanList(loginDetailsDao.getComplaintUsers(accountStatusCodeList, accountTypeCodeList, loginUserid));
	}


	
	private LoginDetails convertBeanToEntity(LoginDetailsBean bean) {
		logger.info("LoginDetailsServiceImpl  call convertBeanToEntity method");
		LoginDetails entity = new LoginDetails();
		entity.setLoginUserid(bean.getLoginUserid());
		entity.setCreationTimestamp(bean.getCreationTimestamp());
		entity.setFailedLoginCount(bean.getFailedLoginCount());
		entity.setLoginCount(bean.getLoginCount());
		entity.setPassword(bean.getPassword());
		entity.setPasswordSalt(bean.getPasswordSalt());
		entity.setEmailVerified((null != bean.getEmailVerified() ? bean.getEmailVerified() : false));
		entity.setPublicName(bean.getPublicName());
		entity.setReserved1(bean.getReserved1());
		entity.setReserved2(bean.getReserved2());
		entity.setOtp(bean.getOtp());
		entity.setOtpExpiredDate(bean.getOtpExpiredDate());
		entity.setOtpCount(bean.getOtpCount());

		if (null != bean.getAccountProfileBean())
			entity.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
		entity.setAccountStatusCodes(new AccountStatusCodes(bean.getAccountStatusCodesBean().getAccountStatusCode()));
		entity.setAccountTypeCodes(new AccountTypeCodes(bean.getAccountTypeCodesBean().getAccountType()));
		/*
		 * entity.setAccountStatusCodes(new
		 * AccountStatusCodes(bean.getAccountStatusCodesBean().
		 * getAccountStatusCode())); entity.setAccountTypeCodes(new
		 * AccountTypeCodes(bean.getAccountTypeCodesBean().getAccountType()));
		 * entity.setAccountProfile(
		 * convertAccountProfileBeanToAccountProfileEntity(bean.
		 * getAccountProfileBean()));
		 */
		return entity;
	}

	private LoginDetailsBean convertEntityToBean(LoginDetails loginDetails) {
		logger.info("LoginDetailsServiceImpl  call convertEntityToBean method");
		if (null != loginDetails) {
			LoginDetailsBean bean = new LoginDetailsBean();
			bean.setLoginUserid(loginDetails.getLoginUserid());
			bean.setCreationTimestamp(loginDetails.getCreationTimestamp());
			bean.setFailedLoginCount(loginDetails.getFailedLoginCount());
			bean.setLoginCount(loginDetails.getLoginCount());
			bean.setPassword(loginDetails.getPassword());
			bean.setPasswordSalt(loginDetails.getPasswordSalt());
			bean.setEmailVerified(loginDetails.isEmailVerified());
			bean.setPublicName(loginDetails.getPublicName());
			bean.setReserved1(loginDetails.getReserved1());
			bean.setReserved2(loginDetails.getReserved2());
			bean.setOtp(loginDetails.getOtp());
			bean.setOtpExpiredDate(loginDetails.getOtpExpiredDate());
			bean.setOtpCount(loginDetails.getOtpCount());
			if (null != loginDetails.getAccountProfile()) {

				bean.setAccountProfileBean(new AccountProfileBean(loginDetails.getAccountProfile()));
			}
			AccountStatusCodesBean statusCodeBean = new AccountStatusCodesBean();
			statusCodeBean.setAccountStatusCode(loginDetails.getAccountStatusCodes().getAccountStatusCode());
			statusCodeBean.setAccountStatusCodeMeaning(loginDetails.getAccountStatusCodes().getAccountStatusCodeMeaning());
			bean.setAccountStatusCodesBean(statusCodeBean);
			AccountTypeCodesBean typeCodeBean = new AccountTypeCodesBean();
			typeCodeBean.setAccountType(loginDetails.getAccountTypeCodes().getAccountType());
			typeCodeBean.setAccountTypeMeaning(loginDetails.getAccountTypeCodes().getAccountTypeMeaning());
			bean.setAccountTypeCodesBean(typeCodeBean);
			return bean;
		}
		return null;
	}

	private List<LoginDetailsBean> convertEntityListToBeanList(List<LoginDetails> loginDetailsList) {
		logger.info("LoginDetailsServiceImpl  call convertEntityListToBeanList method");
		if (null != loginDetailsList && loginDetailsList.size() > 0) {
			List<LoginDetailsBean> loginDetailsBeanList = new ArrayList<LoginDetailsBean>(0);
			for (LoginDetails loginDetails : loginDetailsList) {
				loginDetailsBeanList.add(convertEntityToBean(loginDetails));
			}
			return loginDetailsBeanList;
		} else {
			return null;
		}
	}

private List < LoginDetailsViews>convertBeanListToViewList(List<LoginDetails> loginDetailsList){
	logger.info("LoginDetailsServiceImpl  call convertBeanListToViewList method");
	List<LoginDetailsViews> views = new ArrayList<LoginDetailsViews>();
	if (null != loginDetailsList && loginDetailsList.size() > 0) {
		for (LoginDetails loginDetails : loginDetailsList) {
			views.add(new LoginDetailsViews(loginDetails));
		}
	}
	return views;
}




}