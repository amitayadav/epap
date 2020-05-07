package com.auction.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_PickupTicketsAction;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.CommonsUtil;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.PickupTicketsBean;
import com.auction.service.LoginDetailsService;
import com.auction.service.PickupTicketsService;

@Component
public class PickupTicketsHelper {

	@Autowired
	private PickupTicketsService pickupTicketsService;
	
	@Autowired
	private LoginDetailsService loginDetailsService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	public void savePickupTickets(AuctionBuyersBean auctionBuyersBean) {
		logger.info("LogHelper call savePickupTickets  method");
		LoginDetailsBean loginUser = loginDetailsService
				.getLoginDetailsByAccountProfileId(auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getAccountId());
		int randomNumber =CommonsUtil.generateNumber();
		PickupTicketsBean  pickupTicketsBean = new PickupTicketsBean();
		pickupTicketsBean.setPtn(randomNumber);
		pickupTicketsBean.setTradeTime(InternetTiming.getInternetDateTime());
		pickupTicketsBean.setDailyAuctionsid(auctionBuyersBean.getDailyAuctionsBean());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER.getType()) {
	    	pickupTicketsBean.setSellerPublicName(auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getPublicName());
	    	pickupTicketsBean.setSellerAccountId(auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getAccountId());
		}else if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) { 
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService
					.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			pickupTicketsBean.setSellerPublicName(loginUser.getPublicName());
			pickupTicketsBean.setSellerAccountId(ownerLoginDetailsBean.getAccountProfileBean().getAccountId());
		}
		pickupTicketsBean.setAuctionBuyerBean(auctionBuyersBean);
		pickupTicketsBean.setSellerId(auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean());
		pickupTicketsBean.setBuyerId(auctionBuyersBean.getAccountProfileBean());
		pickupTicketsBean.setSellerTruckNumber(auctionBuyersBean.getAuctionSellersBean().getTruckNumber());
		pickupTicketsBean.setBuyerPublicName(auctionBuyersBean.getAccountProfileBean().getPublicName());
		pickupTicketsBean.setBuyerPurchasedQuantity(auctionBuyersBean.getExecutedQuantity());
		pickupTicketsBean.setSellerLocation(auctionBuyersBean.getAuctionSellersBean().getAccountLocationsBean());
		pickupTicketsBean.setBuyerLocation(auctionBuyersBean.getAccountLocationsBean());
		pickupTicketsBean.setAction(ENUM_PickupTicketsAction.Notconfirmed.getStatus());
		logger.info("=== ending  savePickupTickets  method ===");
		pickupTicketsService.save(pickupTicketsBean);
	}
}
