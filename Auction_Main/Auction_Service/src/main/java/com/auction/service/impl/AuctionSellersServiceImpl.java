package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AuctionSellersDao;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.entity.AccountLocations;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionSellers;
import com.auction.model.entity.DailyAuctions;
import com.auction.model.views.AuctionSellerOffersView;
import com.auction.service.AuctionSellersService;

@Service
@Transactional
public class AuctionSellersServiceImpl implements AuctionSellersService {

	@Autowired
	private AuctionSellersDao auctionSellersDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(AuctionSellersBean bean) {
		logger.info("AuctionSellersServiceImpl call save method");
		AuctionSellers auctionSellers = auctionSellersDao.save(convertBeanToEntity(bean));
		if (null == bean.getAuctionSellersId() || bean.getAuctionSellersId() == 0) {
			bean.setAuctionSellersId(auctionSellers.getAuctionSellersId());
		}
		return (null != auctionSellers ? auctionSellers.getAuctionSellersId() : null);
	}

	@Override
	public AuctionSellersBean findById(Integer id) {
		logger.info("AuctionSellersServiceImpl call findById method");
		return new AuctionSellersBean(auctionSellersDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("AuctionSellersServiceImpl call exists method");
		return auctionSellersDao.exists(id);
	}

	@Override
	public List<AuctionSellersBean> findAll() {
		logger.info("AuctionSellersServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionSellersDao.findAll());
	}

	@Override
	public List<AuctionSellersBean> findAll(Iterable<Integer> ids) {
		logger.info("AuctionSellersServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionSellersDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("AuctionSellersServiceImpl call count method");
		return auctionSellersDao.count();
	}

	@Override
	public void delete(AuctionSellersBean bean) {
		logger.info("AuctionSellersServiceImpl call delete method");
		auctionSellersDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		auctionSellersDao.refresh(new AuctionSellers(id));
		return id;
	}

	@Override
	public Integer getCountByDailyAuctionsId(Integer dailyAuctionsId) {
		logger.info("AuctionSellersServiceImpl call getCountByDailyAuctionsId method");
		return auctionSellersDao.getCountByDailyAuctionsId(dailyAuctionsId);
	}

	@Override
	public List<AuctionSellersBean> findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatusIn(Integer dailyAuctionsId, Short[] sellerOfferStatus) {
		logger.info("AuctionSellersServiceImpl call findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatusIn method");
		return convertEntityListToBeanList(auctionSellersDao.findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatusIn(dailyAuctionsId, sellerOfferStatus));
	}

	@Override
	public List<AuctionSellersBean> findByDailyAuctionsDailyAuctionsIdAndAccountProfileAccountId(Integer dailyAuctionsId, Integer accountId) {
		logger.info("AuctionSellersServiceImpl call findByDailyAuctionsDailyAuctionsIdAndAccountProfileAccountId method");
		return convertEntityListToBeanList(auctionSellersDao.findByDailyAuctionsDailyAuctionsIdAndAccountProfileAccountId(dailyAuctionsId, accountId));
	}

	@Override
	public List<AuctionSellersBean> findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatus(Integer dailyAuctionsId, Short sellerOfferStatus) {
		logger.info("AuctionSellersServiceImpl call findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatus method");
		return convertEntityListToBeanList(auctionSellersDao.findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatus(dailyAuctionsId, sellerOfferStatus));
	}

	@Override
	public void updateSellerOfferStatusByAuctionSellersId(@Param("sellerOfferStatus") Short sellerOfferStatus, @Param("auctionSellersId") Integer auctionSellersId) {
		logger.info("AuctionSellersServiceImpl call updateSellerOfferStatusByAuctionSellersId method");
		auctionSellersDao.updateSellerOfferStatusByAuctionSellersId(sellerOfferStatus, auctionSellersId);
	}
	
	@Override
	public void updateSellerOfferStatusByAuctionSellersId(Short sellerOfferStatus, Integer auctionSellersId, Date actualEndTime) {
		logger.info("AuctionSellersServiceImpl call updateSellerOfferStatusByAuctionSellersId method");
		auctionSellersDao.updateSellerOfferStatusByAuctionSellersId(sellerOfferStatus, auctionSellersId, actualEndTime);
	}

	// View classes for provide only required data on UI.
	@Override
	public AuctionSellerOffersView findViewByAuctionSellersId(Integer auctionSellersId) {
		logger.info("AuctionSellersServiceImpl call findViewByAuctionSellersId method");
		return new AuctionSellerOffersView(auctionSellersDao.findOne(auctionSellersId));
	}

	@Override
	public List<AuctionSellerOffersView> findAuctionSellerOffersViewByDailyAuctionsIddAndSellerOfferStatusIn(Integer dailyAuctionsId, Short[] sellerOfferStatus) {
		logger.info("AuctionSellersServiceImpl call findAuctionSellerOffersViewByDailyAuctionsIddAndSellerOfferStatusIn method");
		return convertEntityListToViewList(auctionSellersDao.findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatusIn(dailyAuctionsId, sellerOfferStatus));
	}

	@Override
	public List<AuctionSellersBean> getAuctionSellersWithRemaingAvailableQuantity(Short auctionStatusCode, Integer dailyAuctionsId) {
		logger.info("AuctionSellersServiceImpl call getAuctionSellersWithRemaingAvailableQuantity method");
		return convertEntityListToBeanList(auctionSellersDao.getAuctionSellersWithRemaingAvailableQuantity(auctionStatusCode, dailyAuctionsId));
	}

	@Override
	public List<Integer> getDailyAuctionIdsBySellerAccountId(Integer accountId, Date beginTime, Date endTime) {
		logger.info("AuctionSellersServiceImpl call getDailyAuctionIdsBySellerAccountId method");
		return auctionSellersDao.getDailyAuctionIdsBySellerAccountId(accountId, beginTime, endTime);
	}

	@Override
	public List<Integer> getAuctionSellerIdsByBuyerAccountId(Integer accountId, Integer dailyAuctionId) {
		logger.info("AuctionSellersServiceImpl call getAuctionSellerIdsByBuyerAccountId method");
		return auctionSellersDao.getAuctionSellerIdsByBuyerAccountId(accountId, dailyAuctionId);
	}

	@Override
	public Integer getAuctionSellersByDailyAuctionsId(Integer dailyAuctionsId, Integer sellerAccountId) {
		logger.info("AuctionSellersServiceImpl call getAuctionSellersByDailyAuctionsId method");
		return auctionSellersDao.getAuctionSellersByDailyAuctionsId(dailyAuctionsId, sellerAccountId);
	}

	@Override
	public List<Short> getSellerOfferStatusByDailyAuctionsId(Integer dailyAuctionsId) {
		logger.info("AuctionSellersServiceImpl call getSellerOfferStatusByDailyAuctionsId method");
		return auctionSellersDao.getSellerOfferStatusByDailyAuctionsId(dailyAuctionsId);
	}

	private AuctionSellers convertBeanToEntity(AuctionSellersBean bean) {
		logger.info("AuctionSellersServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			AuctionSellers entity = new AuctionSellers();
			entity.setAuctionSellersId(bean.getAuctionSellersId());
			entity.setAccountLocations(new AccountLocations(bean.getAccountLocationsBean().getLocationId()));
			entity.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			if (null != bean.getShipperAccountProfileBean() && null != bean.getShipperAccountProfileBean().getAccountId()
					&& bean.getShipperAccountProfileBean().getAccountId() > 0) {
				entity.setShipperAccountProfile(new AccountProfile(bean.getShipperAccountProfileBean().getAccountId()));
			}
			entity.setDailyAuctions(new DailyAuctions(bean.getDailyAuctionsBean().getDailyAuctionsId()));
			entity.setOfferQuantity(bean.getOfferQuantity());
			entity.setOfferPrice(bean.getOfferPrice());
			entity.setRoyaltyValue(bean.getRoyaltyValue());
			entity.setVat(bean.getVat());
			entity.setMinimumQuantity(bean.getMinimumQuantity());
			entity.setAvailableQuantity(bean.getAvailableQuantity());
			entity.setPartialAllowed(bean.getPartialAllowed());
			entity.setSellerShippingCharge((null != bean.getSellerShippingCharge() ? bean.getSellerShippingCharge() : 0F));
			entity.setSellerOfferStatus(bean.getSellerOfferStatus());
			entity.setActualStartTime(bean.getActualStartTime());
			entity.setActualEndTime(bean.getActualEndTime());
			entity.setReserved1(bean.getReserved1());
			entity.setReserved2(bean.getReserved2());
			entity.setTruckNumber(bean.getTruckNumber());
			entity.setOfferUpdatedTime(bean.getOfferUpdatedTime());
			return entity;
		}
		return null;
	}

	private List<AuctionSellersBean> convertEntityListToBeanList(List<AuctionSellers> list) {
		logger.info("AuctionSellersServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<AuctionSellersBean> auctionSellersBeansList = new ArrayList<AuctionSellersBean>(0);
			for (AuctionSellers auctionSellers : list) {
				auctionSellersBeansList.add(new AuctionSellersBean(auctionSellers));
			}
			return auctionSellersBeansList;
		}
		return null;
	}

	private List<AuctionSellerOffersView> convertEntityListToViewList(List<AuctionSellers> list) {
		logger.info("AuctionSellersServiceImpl call convertEntityListToViewList method");
		List<AuctionSellerOffersView> auctionSellerOffersViewList = new ArrayList<AuctionSellerOffersView>(0);
		if (null != list && list.size() > 0) {
			for (AuctionSellers auctionSellers : list) {
				auctionSellerOffersViewList.add(new AuctionSellerOffersView(auctionSellers));
			}
		}
		return auctionSellerOffersViewList;
	}
	
	public Integer countDailyAuctionSellerOfferStatusNotIn(Integer dailyAuctionsId, Short[] sellerOfferStatus){
		logger.info("AuctionSellersServiceImpl call countDailyAuctionSellerOfferStatusNotIn method");
		return auctionSellersDao.countDailyAuctionSellerOfferStatusNotIn(dailyAuctionsId,sellerOfferStatus);
	}
	
	public Integer countDailyAuctionSellerOfferStatusIn(Integer dailyAuctionsId, Short[] sellerOfferStatus){
		logger.info("AuctionSellersServiceImpl call countDailyAuctionSellerOfferStatusIn method");
		return auctionSellersDao.countDailyAuctionSellerOfferStatusIn(dailyAuctionsId,sellerOfferStatus);
	}

	@Override
	public List<AuctionSellersBean> getAuctionSellersWithLimitPriceByBeginTime(Short sellerOfferStatus, Short auctionStatusCode, Date beginTime) {
		logger.info("AuctionSellersServiceImpl call getAuctionSellersWithLimitPriceByBeginTime method");
		return convertEntityListToBeanList(auctionSellersDao.getAuctionSellersWithLimitPriceByBeginTime(sellerOfferStatus,auctionStatusCode,beginTime));
	
	}

	@Override
	public List<AuctionSellerOffersView> findByAuctionSellersAuctionSellersIdAndSellerOfferInAscOfferUpdatedTime(Integer dailyAuctionsId, Short[] sellerOfferStatus) {
		logger.info("AuctionBuyersServiceImpl call findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInAscBidUpdatedTime method");
	return convertEntityListToViewList(auctionSellersDao.findByDailyAuctionsDailyAuctionsIdAndSellerOfferInAscOfferUpdatedTime(dailyAuctionsId, sellerOfferStatus));
		
	}

	
}