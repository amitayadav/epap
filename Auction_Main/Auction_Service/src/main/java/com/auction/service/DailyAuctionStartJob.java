package com.auction.service;

import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.component.AuctionRunningPhase;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.DailyAuctionsBean;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DailyAuctionStartJob extends QuartzJobBean {

	private DailyAuctionsService dailyAuctionsService;
	private AuctionSellersService auctionSellersService;
	private AuctionBuyersService auctionBuyersService;
	private SimpMessagingTemplate simpMessagingTemplate;
	// private AcceptBuyersBidHelper acceptBuyersBidHelper;
	private AuctionRunningPhase auctionRunningPhase;

	public void setDailyAuctionsService(DailyAuctionsService dailyAuctionsService) {
		this.dailyAuctionsService = dailyAuctionsService;
	}

	public void setAuctionSellersService(AuctionSellersService auctionSellersService) {
		this.auctionSellersService = auctionSellersService;
	}

	public void setAuctionBuyersService(AuctionBuyersService auctionBuyersService) {
		this.auctionBuyersService = auctionBuyersService;
	}

	public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	/*
	 * public void setAcceptBuyersBidHelper(AcceptBuyersBidHelper
	 * acceptBuyersBidHelper) { this.acceptBuyersBidHelper = acceptBuyersBidHelper;
	 * }
	 */

	public void setAuctionRunningPhase(AuctionRunningPhase auctionRunningPhase) {
		this.auctionRunningPhase = auctionRunningPhase;
	}

	private AuctionLogger logger = new AuctionLogger(getClass());

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			Date date = DateHelper.getAuctionDate(InternetTiming.getInternetDateTime(), true);
			List<AuctionBuyersBean> auctionBuyersBeanList = null;

			logger.trace(getClass().getSimpleName() + " for " + date + " Started... at " + new Date(System.currentTimeMillis()));
			logger.info(" DailyAuctionStartJob "+ " for " + date + " Started... at " + new Date(System.currentTimeMillis()));
			/*System.out.println("#25"+" DailyAuctionStartJob "+ " for " + date + " Started... at " + new Date(System.currentTimeMillis()));*/
			// Retrieving daily auction list
			List<DailyAuctionsBean> dailyAuctionsBeans = dailyAuctionsService.findByAuctionStatusCodesAuctionStatusCodeAndBeginTime(ENUM_AuctionStatusCodes.OPEN.getStatus(), date);
			if (null != dailyAuctionsBeans && dailyAuctionsBeans.size() > 0) {
				for (DailyAuctionsBean dailyAuctionsBean : dailyAuctionsBeans) {
					dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(ENUM_AuctionStatusCodes.RUNNING.getStatus(), dailyAuctionsBean.getDailyAuctionsId());
					logger.info(" Auction Start Job In Runing Auction");
					// Retrieving auction seller offers list
					List<AuctionSellersBean> auctionSellersBeanList  = auctionSellersService
							.findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatus(dailyAuctionsBean.getDailyAuctionsId(), ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus());

					if (null != auctionSellersBeanList && auctionSellersBeanList.size() > 0) {
						for (AuctionSellersBean auctionSellersBean : auctionSellersBeanList) {
							auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus(),
									auctionSellersBean.getAuctionSellersId());
							logger.info(" Auction Start Job In Runing Seller Offer");
							// Retrieving auction buyer bids list
							auctionBuyersBeanList = auctionBuyersService.findByAuctionSellersAuctionSellersIdAndBuyerBidStatus(auctionSellersBean.getAuctionSellersId(),
									ENUM_AuctionBuyerBidStatusCodes.OPEN.getStatus());

							if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
								for (AuctionBuyersBean auctionBuyersBean : auctionBuyersBeanList) {
									auctionBuyersService.updateBidOfferStatusByAuctionBuyersId(ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus(),
											auctionBuyersBean.getAuctionBuyersId());
									/**  issues no#106 point no3 changes  */
								/*	if(auctionSellersBean.getOfferPrice() > 0 && auctionSellersBean.getPartialAllowed() !=true) {
										if(auctionBuyersBean.getBidQuantity() > auctionSellersBean.getAvailableQuantity()) {
											Math.max(auctionSellersBean.getAvailableQuantity(),auctionBuyersBean.getMinimumQuantity());
										
										}
									}*/
									logger.info(" Auction Start Job In Runing Buyer Bid");
								}
							}
						}
						
					}
					 simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", dailyAuctionsBean.getDailyAuctionsId());
				}
			}

		//    simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionStatusUpdated");

			if (null != dailyAuctionsBeans && dailyAuctionsBeans.size() > 0) {
				logger.info(" Auction Start Job In calling method auctionOpenToRunningPhase");
				auctionRunningPhase.auctionOpenToRunningPhase(date);
			}

		//	simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionStatusUpdated");

			logger.trace(getClass().getSimpleName() + " for " + date + " Ended... at " + new Date(System.currentTimeMillis()));
			logger.info("DailyAuctionStartJob "+ " for " + date + " Ended... at " + new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
		/*	simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionStatusUpdated");*/
		}
	}
}