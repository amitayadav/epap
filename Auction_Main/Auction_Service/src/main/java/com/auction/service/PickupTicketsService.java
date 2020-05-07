package com.auction.service;


import java.util.Date;
import java.util.List;
import com.auction.model.bean.PickupTicketsBean;
import com.auction.service.generic.GenericService;

public interface PickupTicketsService extends GenericService<PickupTicketsBean, Integer>{
	
	public List<PickupTicketsBean> findPickupTicketsBySellerAccountId(Integer sellerId);
	public List<PickupTicketsBean> findPickupTicketsByBuyerAccountId(Integer buyerId);
	public void updatePickupTicketActionByBuyerAccountId( Integer action, Integer updatebyId, Date updatedOn, Integer buyerId, Integer sellerId, Integer dailyAuctionsid);
	public  List<PickupTicketsBean> getConfirmDeliveryByBuyerAccountIdIn(Integer buyerId) ;
	public List<PickupTicketsBean> getManageShippingPickupTickets();
}
