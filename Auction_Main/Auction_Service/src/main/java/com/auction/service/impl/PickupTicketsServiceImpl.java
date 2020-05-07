package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.PickupTicketsDao;
import com.auction.model.bean.PickupTicketsBean;
import com.auction.model.entity.AccountLocations;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.AuctionBuyers;
import com.auction.model.entity.DailyAuctions;
import com.auction.model.entity.PickupTickets;
import com.auction.service.PickupTicketsService;

@Service
@Transactional
public class PickupTicketsServiceImpl implements PickupTicketsService{

	@Autowired
	private PickupTicketsDao pickupTicketsDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(PickupTicketsBean bean) {
		logger.info("PickupTicketsServiceImpl call save method");
		PickupTickets pickupTickets = pickupTicketsDao.save(convertBeanToEntity(bean));
		return (null != pickupTickets ? pickupTickets.getPickupTicketsId() : null);
	}

	@Override
	public PickupTicketsBean findById(Integer id) {
		logger.info("PickupTicketsServiceImpl call findById method");
		return new PickupTicketsBean(pickupTicketsDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("PickupTicketsServiceImpl call exists method");
		return pickupTicketsDao.exists(id);
	}

	@Override
	public List<PickupTicketsBean> findAll() {
		logger.info("PickupTicketsServiceImpl call findAll method");
		return convertEntityListToBeanList(pickupTicketsDao.findAll());
	}

	@Override
	public List<PickupTicketsBean> findAll(Iterable<Integer> ids) {
		logger.info("PickupTicketsServiceImpl call findAll method");
		return convertEntityListToBeanList(pickupTicketsDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("PickupTicketsServiceImpl call count method");
		return pickupTicketsDao.count();
	}

	@Override
	public void delete(PickupTicketsBean bean) {
		logger.info("PickupTicketsServiceImpl call delete method");
		pickupTicketsDao.delete(convertBeanToEntity(bean));
		
	}

	@Override
	public Integer refresh(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	/** Seller Public Name Get All Record in PitckupTickets  */
	@Override
	public List<PickupTicketsBean> findPickupTicketsBySellerAccountId(Integer sellerId) {
		logger.info("PickupTicketsServiceImpl call findPickupTicketsBySellerAccountId method");
		return convertEntityListToBeanList(pickupTicketsDao.findPickupTicketsBySellerAccountId(sellerId));
	}
	/** 
	 * Buyer Public Name Get All Record in PitckupTickets  */
	@Override
	public List<PickupTicketsBean> findPickupTicketsByBuyerAccountId(Integer buyerId) {
		logger.info("PickupTicketsServiceImpl call findPickupTicketsByBuyerAccountId method");
	return convertEntityListToBeanList(pickupTicketsDao.findPickupTicketsByBuyerAccountId(buyerId));
	}
	
	@Override
	public List<PickupTicketsBean> getConfirmDeliveryByBuyerAccountIdIn(Integer buyerId) {
		logger.info("PickupTicketsServiceImpl call getConfirmDeliveryByBuyerAccountIdIn method");
		return convertEntityListToBeanList(pickupTicketsDao.getConfirmDeliveryByBuyerAccountIdIn(buyerId));
	}
	
	@Override
	public void updatePickupTicketActionByBuyerAccountId(Integer action, Integer updatebyId, Date updatedOn, Integer buyerId, Integer sellerId, Integer dailyAuctionsid) {
		logger.info("PickupTicketsServiceImpl call updatePickupTicketActionByBuyerAccountId method");
		pickupTicketsDao.updatePickupTicketActionByBuyerAccountId(action,updatebyId,updatedOn,buyerId,sellerId,dailyAuctionsid);
		
	}

	@Override
	public List<PickupTicketsBean> getManageShippingPickupTickets() {
		logger.info("PickupTicketsServiceImpl call getManageShippingPickupTickets method");
		return convertEntityListToBeanList(pickupTicketsDao.getManageShippingPickupTickets());
	}
	
	/**
	 * This method is used to convert PickupTicketBean to PickupTicket
	 * 
	 * @param bean
	 * @return entity
	 */
	private PickupTickets convertBeanToEntity(PickupTicketsBean bean) {
		logger.info("PickupTicketsServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			PickupTickets entity = new PickupTickets();
			entity.setPickupTicketsId(bean.getPickupTicketsId());
			entity.setPtn(bean.getPtn());
			entity.setTradeTime(bean.getTradeTime());
			entity.setDailyAuctionsid(new DailyAuctions(bean.getDailyAuctionsid().getDailyAuctionsId()));
			entity.setSellerPublicName(bean.getSellerPublicName());
			entity.setSellerAccountId(bean.getSellerAccountId());
			entity.setSellerTruckNumber(bean.getSellerTruckNumber());
			entity.setBuyerPublicName(bean.getBuyerPublicName());
			entity.setBuyerPurchasedQuantity(bean.getBuyerPurchasedQuantity());
			entity.setSellerLocation(new AccountLocations(bean.getSellerLocation().getLocationId()));
			entity.setBuyerLocation(new AccountLocations(bean.getBuyerLocation().getLocationId()));
			entity.setAction(bean.getAction());
			entity.setUpdatebyId(bean.getUpdatebyId());
			entity.setUpdatedOn(bean.getUpdatedOn());
			entity.setCancelledbyId(bean.getCancelledbyId());
			entity.setCanceledOn(bean.getCanceledOn());
			entity.setReserved1(bean.getReserved1());
			entity.setReserved2(bean.getReserved2());
			entity.setSellerId(new AccountProfile(bean.getSellerId().getAccountId()));
			entity.setBuyerId(new AccountProfile(bean.getBuyerId().getAccountId()));
			entity.setAuctionBuyerId(new AuctionBuyers(bean.getAuctionBuyerBean().getAuctionBuyersId()));
			return entity;
		}
		return null;
	}

	/**
	 * This method is used to convert PickupTicke List to PickupTicketBeanBean
	 * List
	 * 
	 * @param List<PickupTicketBean>
	 *            list
	 * @return List<PickupTicketBeanBean> list
	 */
	private List<PickupTicketsBean> convertEntityListToBeanList(List<PickupTickets> list) {
		logger.info("PickupTicketsServiceImpl call convertEntityListToBeanList method");
		List<PickupTicketsBean> pickupTicketsBeansList = new ArrayList<PickupTicketsBean>(0);
		if (null != list && list.size() > 0) {
			for (PickupTickets locations : list) {
				pickupTicketsBeansList.add(new PickupTicketsBean(locations));
			}
			return pickupTicketsBeansList;
		}
		return pickupTicketsBeansList;
	}


	


	

	
	
}
