package com.auction.service.impl;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AccountProfileDao;
import com.auction.dao.BankDetailsDao;
import com.auction.dao.ShippersRegistrationInfoDao;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.BankDetails;
import com.auction.model.entity.LandingPages;
import com.auction.model.entity.NotificationCodes;
import com.auction.model.entity.RoyaltyCodes;
import com.auction.model.entity.ShippersRegistrationInfo;
import com.auction.service.AccountProfileService;

@Service
@Transactional
public class AccountProfileServiceImpl implements AccountProfileService {

	@Autowired
	private AccountProfileDao accountProfileDao;

	@Autowired
	private BankDetailsDao bankDetailsDao;

	@Autowired
	private ShippersRegistrationInfoDao shippersRegistrationInfoDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(AccountProfileBean bean) {
		logger.info("AccountProfileServiceImpl call save method");
		AccountProfile entity = accountProfileDao.save(convertBeanToEntity(bean));
		bean.setAccountId(entity.getAccountId());
		return (null != entity && null != entity.getAccountId() ? entity.getAccountId() : null);
	}

	@Override
	public AccountProfileBean findById(Integer id) {
		logger.info("AccountProfileServiceImpl call findById method");
		return new AccountProfileBean(accountProfileDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("AccountProfileServiceImpl call exists method");
		return accountProfileDao.exists(id);
	}

	@Override
	public List<AccountProfileBean> findAll() {
		return null;
	}

	@Override
	public List<AccountProfileBean> findAll(Iterable<Integer> ids) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void delete(AccountProfileBean bean) {

	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public AccountProfileBean findOwnerAccountProfileByAgentAccountId(Integer accountId) {
		logger.info("AccountProfileServiceImpl call findOwnerAccountProfileByAgentAccountId method ");
		return new AccountProfileBean(accountProfileDao.findOwnerAccountProfileByAgentAccountId(accountId));
	}

	@Override
	public Integer findOwnerAccountIdByAgentAccountId(Integer accountId) {
		logger.info("AccountProfileServiceImpl call findOwnerAccountIdByAgentAccountId method ");
		return accountProfileDao.findOwnerAccountIdByAgentAccountId(accountId);
	}

	@Override
	public String findOwnerPublicNameByAgentAccountId(Integer accountId) {
		logger.info("AccountProfileServiceImpl call findOwnerPublicNameByAgentAccountId method ");
		return accountProfileDao.findOwnerPublicNameByAgentAccountId(accountId);
	}

	@Override
	public List<AccountProfileBean> findByAccountTypeCodes(Character[] accountTypeCodes) {
		logger.info("AccountProfileServiceImpl call findByAccountTypeCodes method ");
		return convertEntityListToBeanList(accountProfileDao.findByAccountTypeCodes(accountTypeCodes));
	}

	@Override
	public Blob getByEditProfileImage(String profileImage, Integer accountId) {
		logger.info("AccountProfileServiceImpl call getByEditProfileImage method ");
		Blob editProfileImage = accountProfileDao.getByEditProfileImage(profileImage, accountId);
		return editProfileImage;
	}

	@Override
	public void updateRatingByAccountProflieId(Float rating, Integer ratingTotal, Integer numOfRatings, Integer accountId) {
		logger.info("AccountProfileServiceImpl call updateRatingByAccountProflieId method ");
		accountProfileDao.updateRatingByAccountProflieId(rating, ratingTotal, numOfRatings, accountId);
	}

	@Override
	public Boolean checkValidateOtpByAccountProfiledId(Integer accountId, Integer otp) {
		logger.info("AccountProfileServiceImpl call checkValidateOtpByAccountProfiledId method ");
		return accountProfileDao.checkValidateOtpByAccountProfiledId(accountId, otp);
	}

	private AccountProfile convertBeanToEntity(AccountProfileBean bean) {
		logger.info("AccountProfileServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			AccountProfile entity = new AccountProfile();

			entity.setAccountId(bean.getAccountId());
			entity.setGovernmentId(bean.getGovernmentId());
			entity.setFName(bean.getFName());
			entity.setMName(bean.getMName());
			entity.setLName(bean.getLName());
			entity.setPublicName(bean.getPublicName());
			entity.setProfileImage(bean.getProfileImage());
			entity.setBusinessName(bean.getBusinessName());
			entity.setEmailAddress2(bean.getEmailAddress2());
			entity.setPostalAddress(bean.getPostalAddress());
			entity.setPhoneNumber1(bean.getPhoneNumber1());
			entity.setPhoneNumber2(bean.getPhoneNumber2());
			entity.setRating(bean.getRating());

			if (null != bean.getNumOfRatings())
				entity.setNumOfRatings(bean.getNumOfRatings());
			if (null != bean.getRatingTotal())
				entity.setRatingTotal(bean.getRatingTotal());

			entity.setPreferredLanguage(bean.getPreferredLanguage());

			if (null != bean.getNoOfCancellations())
				entity.setNoOfCancellations(bean.getNoOfCancellations());

			if (null != bean.getOfferOrBidCount())
				entity.setOfferOrBidCount(bean.getOfferOrBidCount());

			if (null != bean.getExecutedOfferOrBidCount())
				entity.setExecutedOfferOrBidCount(bean.getExecutedOfferOrBidCount());

			entity.setContactUs(bean.getContactUs());
			entity.setReserved1(bean.getReserved1());
			entity.setReserved2(bean.getReserved2());
			entity.setOtp(bean.getOtp());
			entity.setOtpExpiredDate(bean.getOtpExpiredDate());
			entity.setOtpCount(bean.getOtpCount());

			if (null != bean.getNotificationCodesBean() && null != bean.getNotificationCodesBean().getNotificationCode()) {
				entity.setNotificationCodes(new NotificationCodes(bean.getNotificationCodesBean().getNotificationCode()));
			}
			if (null != bean.getRoyaltyCodesBean()) {
				entity.setRoyaltyCodes(new RoyaltyCodes(bean.getRoyaltyCodesBean().getRoyaltyCode()));
			}
			if (null != bean.getLandingPagesBean()) {
				entity.setLandingPages(new LandingPages(bean.getLandingPagesBean().getLandingPageId()));
			}

			if (null != bean.getBankDetailsBean()) {
				if (null != bean.getBankDetailsBean().getBankName()) {
					BankDetails bankDetail;
					if (null != bean.getBankDetailsBean().getAccountId()) {
						bankDetail = bankDetailsDao.findOne(bean.getBankDetailsBean().getAccountId());
					} else {
						bankDetail = new BankDetails();
					}
					bankDetail.setAccountProfile(entity);
					bankDetail.setAccountId(bean.getAccountId());
					bankDetail.setBankName(bean.getBankDetailsBean().getBankName());
					bankDetail.setBankAccountNumber(bean.getBankDetailsBean().getBankAccountNumber());
					bankDetail.setIban(bean.getBankDetailsBean().getIban());
					bankDetail.setAvailableBalance(bean.getBankDetailsBean().getAvailableBalance());
					bankDetail.setBlockedAmount(bean.getBankDetailsBean().getBlockedAmount());
					bankDetail.setAdvanceBalance(bean.getBankDetailsBean().getAdvanceBalance());
					bankDetail.setCashposition(bean.getBankDetailsBean().getCashposition());
					entity.setBankDetails(bankDetail);
				}
			}

			if (null != bean.getShippersRegistrationInfoBean()) {
				if (null != bean.getShippersRegistrationInfoBean().getDriverLicenseExpirationDate()) {
					ShippersRegistrationInfo shippersRegistrationInfo;
					if (null != bean.getShippersRegistrationInfoBean().getAccountId()) {
						shippersRegistrationInfo = shippersRegistrationInfoDao.findOne(bean.getAccountId());
					} else {
						shippersRegistrationInfo = new ShippersRegistrationInfo();
					}
					entity.setShippersRegistrationInfo(shippersRegistrationInfo);
					shippersRegistrationInfo.setAccountProfile(entity);
					shippersRegistrationInfo.setAccountId(bean.getAccountId());
					shippersRegistrationInfo.setDriverLicenseExpirationDate(bean.getShippersRegistrationInfoBean().getDriverLicenseExpirationDate());
					shippersRegistrationInfo.setTruckModelYear(bean.getShippersRegistrationInfoBean().getTruckModelYear());
					shippersRegistrationInfo.setTruckType(bean.getShippersRegistrationInfoBean().getTruckType());
				}
			}
			entity.setContents(bean.getContents());
			return entity;
		}
		return null;
	}

	private List<AccountProfileBean> convertEntityListToBeanList(List<AccountProfile> accountProfileList) {
		logger.info("AccountProfileServiceImpl call convertEntityListToBeanList method");
		if (null != accountProfileList && accountProfileList.size() > 0) {
			List<AccountProfileBean> accountProfileBeanList = new ArrayList<AccountProfileBean>(0);
			for (AccountProfile accountProfile : accountProfileList) {
				accountProfileBeanList.add(new AccountProfileBean(accountProfile));
			}
			return accountProfileBeanList;
		} else {
			return null;
		}
	}

}