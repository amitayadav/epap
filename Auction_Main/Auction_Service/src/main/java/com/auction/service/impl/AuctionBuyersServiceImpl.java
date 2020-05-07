package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AuctionBuyersDao;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.entity.AccountLocations;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionBuyers;
import com.auction.model.entity.AuctionSellers;
import com.auction.model.entity.DailyAuctions;
import com.auction.model.views.AuctionBuyersBidsView;
import com.auction.service.AuctionBuyersService;

@Service
@Transactional
public class AuctionBuyersServiceImpl implements AuctionBuyersService {

	@Autowired
	private AuctionBuyersDao auctionBuyersDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(AuctionBuyersBean bean) {
		logger.info("AuctionBuyersServiceImpl call save method");
		AuctionBuyers auctionBuyers = auctionBuyersDao.save(convertBeanToEntity(bean));
		if (null == bean.getAuctionBuyersId() || bean.getAuctionBuyersId() == 0) {
			bean.setAuctionBuyersId(auctionBuyers.getAuctionBuyersId());
		}
		return (null != auctionBuyers ? auctionBuyers.getAuctionBuyersId() : null);
	}

	@Override
	public AuctionBuyersBean findById(Integer id) {
		logger.info("AuctionBuyersServiceImpl call findById method");
		return new AuctionBuyersBean(auctionBuyersDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("AuctionBuyersServiceImpl call exists method");
		return auctionBuyersDao.exists(id);
	}

	@Override
	public List<AuctionBuyersBean> findAll() {
		logger.info("AuctionBuyersServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionBuyersDao.findAll());
	}

	@Override
	public List<AuctionBuyersBean> findAll(Iterable<Integer> ids) {
		logger.info("AuctionBuyersServiceImpl call findAll method");
		return convertEntityListToBeanList(auctionBuyersDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("AuctionBuyersServiceImpl call count method");
		return auctionBuyersDao.count();
	}

	@Override
	public void delete(AuctionBuyersBean bean) {
		logger.info("AuctionBuyersServiceImpl call delete method");
		auctionBuyersDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		auctionBuyersDao.refresh(new AuctionBuyers(id));
		return id;
	}

	@Override
	public Integer getCountByAuctionSellersId(Integer auctionSellersId) {
		logger.info("AuctionBuyersServiceImpl call getCountByAuctionSellersId method");
		return auctionBuyersDao.getCountByAuctionSellersId(auctionSellersId);
	}

	@Override
	public List<Integer> getDailyAuctionIdsByBuyerAccountId(Integer accountId, Date beginTime, Date endTime) {
		logger.info("AuctionBuyersServiceImpl call getDailyAuctionIdsByBuyerAccountId method");
		return auctionBuyersDao.getDailyAuctionIdsByBuyerAccountId(accountId, beginTime, endTime);
	}

	@Override
	public List<Short> getBuyerBidStatusByAuctionSellerId(Integer auctionSellersId) {
		logger.info("AuctionBuyersServiceImpl call getBuyerBidStatusByAuctionSellerId method");
		return auctionBuyersDao.getBuyerBidStatusByAuctionSellerId(auctionSellersId);
	}

	@Override
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersId(Integer auctionSellersId) {
		logger.info("AuctionBuyersServiceImpl call findByAuctionSellersAuctionSellersId method");
		return convertEntityListToBeanList(auctionBuyersDao.findByAuctionSellersAuctionSellersId(auctionSellersId));
	}

	@Override
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdOrderByBidPriceDescAuctionBuyersIdAsc(Integer auctionSellersId) {
		logger.info("AuctionBuyersServiceImpl call findByAuctionSellersAuctionSellersIdOrderByBidPriceDescAuctionBuyersIdAsc method");
		return convertEntityListToBeanList(auctionBuyersDao.findByAuctionSellersAuctionSellersIdOrderByBidPriceDescAuctionBuyersIdAsc(auctionSellersId));
	}

	@Override
	public List<AuctionBuyersBean> getConfirmDeliveryByBuyerAccountId(Integer accountId, Short buyerBidStatus) {
		logger.info("AuctionBuyersServiceImpl call getConfirmDeliveryByBuyerAccountId method");
		return convertEntityListToBeanList(auctionBuyersDao.getConfirmDeliveryByBuyerAccountId(accountId, buyerBidStatus));
	}

	@Override
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusOrderByBidPriceDescAuctionBuyersIdAsc(Integer auctionSellersId, Short buyerBidStatus) {
		logger.info("AuctionBuyersServiceImpl call findByAuctionSellersAuctionSellersIdAndBuyerBidStatusOrderByBidPriceDescAuctionBuyersIdAsc method");
		return convertEntityListToBeanList(
				auctionBuyersDao.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusOrderByBidPriceDescAuctionBuyersIdAsc(auctionSellersId, buyerBidStatus));
	}

	@Override
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInOrderByBidPriceDescAuctionBuyersIdAsc(Integer auctionSellersId, Short[] buyerBidStatus) {
		logger.info("AuctionBuyersServiceImpl call findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInOrderByBidPriceDescAuctionBuyersIdAsc method");
		return convertEntityListToBeanList(
				auctionBuyersDao.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInOrderByBidPriceDescAuctionBuyersIdAsc(auctionSellersId, buyerBidStatus));
	}

	@Override
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndAccountProfileAccountId(Integer auctionSellersId, Integer accountId) {
		logger.info("AuctionBuyersServiceImpl call findByAuctionSellersAuctionSellersIdAndAccountProfileAccountId method");
		return convertEntityListToBeanList(auctionBuyersDao.findByAuctionSellersAuctionSellersIdAndAccountProfileAccountId(auctionSellersId, accountId));
	}

	@Override
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndBuyerBidStatus(Integer auctionSellersId, Short buyerBidStatus) {
		logger.info("AuctionBuyersServiceImpl call findByAuctionSellersAuctionSellersIdAndBuyerBidStatus method");
		return convertEntityListToBeanList(auctionBuyersDao.findByAuctionSellersAuctionSellersIdAndBuyerBidStatus(auctionSellersId, buyerBidStatus));
	}

	@Override
	public void updateBidOfferStatusByAuctionBuyersId(Short buyerBidStatus, Integer auctionBuyersId) {
		logger.info("AuctionBuyersServiceImpl call updateBidOfferStatusByAuctionBuyersId method");
		auctionBuyersDao.updateBidOfferStatusByAuctionBuyersId(buyerBidStatus, auctionBuyersId);
	}

	@Override
	public void updateBidOfferStatusAndExecutedPriceByAuctionBuyersId(Short buyerBidStatus, Integer auctionBuyersId, Float executedPrice, Date actualEndTime) {
		logger.info("AuctionBuyersServiceImpl call updateBidOfferStatusAndExecutedPriceByAuctionBuyersId method");
		auctionBuyersDao.updateBidOfferStatusAndExecutedPriceByAuctionBuyersId(buyerBidStatus, auctionBuyersId, executedPrice, actualEndTime);
	}

	@Override
	public void updateBuyerBidQuantityByAuctionBuyersId(Integer bidQuantity, Integer auctionBuyersId) {
		logger.info("AuctionBuyersServiceImpl call updateBuyerBidQuantityByAuctionBuyersId method");
		auctionBuyersDao.updateBuyerBidQuantityByAuctionBuyersId(bidQuantity, auctionBuyersId);
	}

	@Override
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusIn(Integer auctionSellersId, Short[] buyerBidStatus) {
		logger.info("AuctionBuyersServiceImpl call findByAuctionSellersAuctionSellersIdAndBuyerBidStatusIn method");
		return convertEntityListToBeanList(auctionBuyersDao.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusIn(auctionSellersId, buyerBidStatus));
	}

	// View classes for provide only required data on UI.
	@Override
	public List<AuctionBuyersBidsView> findViewByAuctionSellersId(Integer auctionSellersId) {
		logger.info("AuctionBuyersServiceImpl call findViewByAuctionSellersId method");
		return convertEntityListToViewList(auctionBuyersDao.findByAuctionSellersAuctionSellersId(auctionSellersId));
	}

	@Override
	public AuctionBuyersBidsView findViewByAuctionBuyersId(Integer auctionBuyersId) {
		logger.info("AuctionBuyersServiceImpl call findViewByAuctionBuyersId method");
		return new AuctionBuyersBidsView(auctionBuyersDao.findOne(auctionBuyersId));
	}

	@Override
	public List<AuctionBuyersBidsView> findViewByAuctionSellersIdAndBuyerBidStatus(Integer auctionSellersId, Short[] buyerBidStatus) {
		logger.info("AuctionBuyersServiceImpl call findViewByAuctionSellersIdAndBuyerBidStatus method");
		return convertEntityListToViewList(auctionBuyersDao.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusIn(auctionSellersId, buyerBidStatus));
	}

	@Override
	public List<AuctionBuyersBean> getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice(Short buyerBidStatus, Short sellerOfferStatus,
			Short auctionStatusCode, Date beginTime) {
		logger.info("AuctionBuyersServiceImpl call getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice method");
		return convertEntityListToBeanList(auctionBuyersDao.getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice(buyerBidStatus, sellerOfferStatus,
				auctionStatusCode, beginTime));
	}

	@Override
	public List<AuctionBuyersBean> getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithMarketPrice(Short buyerBidStatus, Short sellerOfferStatus,
			Short auctionStatusCode, Date beginTime) {
		logger.info("AuctionBuyersServiceImpl call getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithMarketPrice method");
		return convertEntityListToBeanList(auctionBuyersDao.getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithMarketPrice(buyerBidStatus, sellerOfferStatus,
				auctionStatusCode, beginTime));
	}

	@Override
	public List<AuctionBuyersBean> getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPrice(Short buyerBidStatus, Short sellerOfferStatus, Short auctionStatusCode,
			Integer dailyAuctionsId, Integer auctionSellersId) {
		logger.info("AuctionBuyersServiceImpl call getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPrice method");
		return convertEntityListToBeanList(
				auctionBuyersDao.getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPrice(buyerBidStatus, sellerOfferStatus, auctionStatusCode, dailyAuctionsId, auctionSellersId));
	}

	@Override
	public List<AuctionBuyersBean> getAuctionBuyersWithEqualOrLowerBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice(Short buyerBidStatus, Short sellerOfferStatus,
			Short auctionStatusCode, Date beginTime) {
		logger.info("AuctionBuyersServiceImpl call getAuctionBuyersWithEqualOrLowerBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice method");
		return convertEntityListToBeanList(auctionBuyersDao.getAuctionBuyersWithEqualOrLowerBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice(buyerBidStatus, sellerOfferStatus,
				auctionStatusCode, beginTime));
	}

	@Override
	public List<AuctionBuyersBean> getAuctionBuyersByStatusByAuctionSellersId(Short buyerBidStatus, Short sellerOfferStatus, Short auctionStatusCode, Integer auctionSellersId) {
		logger.info("AuctionBuyersServiceImpl call getAuctionBuyersByStatusByAuctionSellersId method");
		return convertEntityListToBeanList(auctionBuyersDao.getAuctionBuyersByStatusByAuctionSellersId(buyerBidStatus, sellerOfferStatus, auctionStatusCode, auctionSellersId));
	}

	@Override
	public List<AuctionBuyersBean> getAuctionBuyersByStatusByAuctionSellersIdAscActualStartTime(Short buyerBidStatus, Short sellerOfferStatus, Short auctionStatusCode,
			Integer auctionSellersId) {
		logger.info("AuctionBuyersServiceImpl call getAuctionBuyersByStatusByAuctionSellersIdAscActualStartTime method");
		return convertEntityListToBeanList(auctionBuyersDao.getAuctionBuyersByStatusByAuctionSellersIdAscActualStartTime(buyerBidStatus, sellerOfferStatus, auctionStatusCode, auctionSellersId));
	}
	private AuctionBuyers convertBeanToEntity(AuctionBuyersBean bean) {
		logger.info("AuctionBuyersServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			AuctionBuyers entity = new AuctionBuyers();
			entity.setAuctionBuyersId(bean.getAuctionBuyersId());
			entity.setAccountLocations(new AccountLocations(bean.getAccountLocationsBean().getLocationId()));
			entity.setAccountProfile(new AccountProfile(bean.getAccountProfileBean().getAccountId()));
			if (null != bean.getShipperAccountProfileBean() && null != bean.getShipperAccountProfileBean().getAccountId()
					&& bean.getShipperAccountProfileBean().getAccountId() > 0) {
				entity.setShipperAccountProfile(new AccountProfile(bean.getShipperAccountProfileBean().getAccountId()));
			}
			entity.setAuctionSellers(new AuctionSellers(bean.getAuctionSellersBean().getAuctionSellersId()));
			entity.setDailyAuctions(new DailyAuctions(bean.getDailyAuctionsBean().getDailyAuctionsId()));
			entity.setPartialAllowed(bean.getPartialAllowed());
			entity.setBidQuantity(bean.getBidQuantity());
			entity.setMinimumQuantity(bean.getMinimumQuantity());
			entity.setExecutedQuantity(bean.getExecutedQuantity());
			entity.setBidPrice(bean.getBidPrice());
			entity.setExecutedPrice(bean.getExecutedPrice());
			entity.setRoyaltyValue(bean.getRoyaltyValue());
			entity.setVat(bean.getVat());
			entity.setBuyerShippingCharge((null != bean.getBuyerShippingCharge() ? bean.getBuyerShippingCharge() : 0F));
			entity.setBuyerBidStatus(bean.getBuyerBidStatus());
			entity.setReserved1(bean.getReserved1());
			entity.setReserved2(bean.getReserved2());
			entity.setActualStartTime(bean.getActualStartTime());
			entity.setActualEndTime(bean.getActualEndTime());
			entity.setBidUpdatedTime(bean.getBidUpdatedTime());
			return entity;
		}
		return null;
	}

	private List<AuctionBuyersBean> convertEntityListToBeanList(List<AuctionBuyers> list) {
		logger.info("AuctionBuyersServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<AuctionBuyersBean> auctionBuyersBeansList = new ArrayList<AuctionBuyersBean>(0);
			for (AuctionBuyers auctionBuyers : list) {
				auctionBuyersBeansList.add(new AuctionBuyersBean(auctionBuyers));
			}
			return auctionBuyersBeansList;
		}
		return null;
	}

	private List<AuctionBuyersBidsView> convertEntityListToViewList(List<AuctionBuyers> list) {
		logger.info("AuctionBuyersServiceImpl call convertEntityListToViewList method");
		List<AuctionBuyersBidsView> auctionBuyersBidsViewList = new ArrayList<AuctionBuyersBidsView>(0);
		if (null != list && list.size() > 0) {
			for (AuctionBuyers auctionBuyers : list) {
				auctionBuyersBidsViewList.add(new AuctionBuyersBidsView(auctionBuyers));
			}
		}
		return auctionBuyersBidsViewList;
	}

	@Override
	public Integer getAuctionBuyersByAuctionSellerId(Integer auctionSellersId, Integer accountId) {
		logger.info("AuctionBuyersServiceImpl call getAuctionBuyersByAuctionSellerId method");
		return auctionBuyersDao.getAuctionBuyersByAuctionSellerId(auctionSellersId, accountId);
	}

	@Override
	public List<AuctionBuyersBean> getManageShippingBuyerAccount(Short[] buyerBidStatus) {
		logger.info("AuctionBuyersServiceImpl call getManageShippingBuyerAccount method");
		return convertEntityListToBeanList(auctionBuyersDao.getManageShippingBuyerAccount(buyerBidStatus));
	}

	@Override
	public List<AuctionBuyersBean> getConfirmDeliveryByBuyerAccountIdIn(Integer accountId, Short[] buyerBidStatus, Date beginDate, Date endDate) {
		logger.info("AuctionBuyersServiceImpl call getConfirmDeliveryByBuyerAccountIdIn method");
		return convertEntityListToBeanList(auctionBuyersDao.getConfirmDeliveryByBuyerAccountIdIn(accountId, buyerBidStatus, beginDate, endDate));
	}

	@Override
	public Integer countAuctionBuyerByStatus(Integer auctionSellersId, Short buyerBidStatus) {
		logger.info("AuctionBuyersServiceImpl call countAuctionBuyerByStatus method");
		return auctionBuyersDao.countAuctionBuyerByStatus(auctionSellersId, buyerBidStatus);
	}

	@Override
	public List<AuctionBuyersBidsView> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInAscBidUpdatedTime(Integer auctionSellersId, Short[] buyerBidStatus) {
		logger.info("AuctionBuyersServiceImpl call findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInAscBidUpdatedTime method");
		return convertEntityListToViewList(auctionBuyersDao.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInAscBidUpdatedTime(auctionSellersId, buyerBidStatus));
	}

	
	

}