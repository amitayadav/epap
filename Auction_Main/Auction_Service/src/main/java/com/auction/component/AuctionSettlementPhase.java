package com.auction.component;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.enums.ENUM_BidOperationCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.DailyAuctionsService;
import com.auction.service.util.AuctionOfferResult;
import com.auction.service.util.OrderExecutionLevel1Mock;

@Component
public class AuctionSettlementPhase {

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private DailyAuctionsService dailyAuctionsService;

	@Autowired
	private LogHelper logHelper;

	@Autowired
	private AuctionPolicyProcessing auctionPolicyProcessing;

	@Autowired
	private AuctionStatusHelper auctionStatusHelper;

	private AuctionLogger logger = new AuctionLogger(getClass().getName());

	public void settelOffersAndBids(Date beginDate) {
		logger.info("AuctionSettlementPhse Call settelOffersAndBids method ");
		try {
			// Retrieving Daily Auction List
			List<DailyAuctionsBean> dailyAuctionsBeansList = dailyAuctionsService.getByAuctionStatusCodesAuctionStatusCodeAndEndTime(ENUM_AuctionStatusCodes.SETTLING.getStatus(),
					beginDate);

			// Checking Daily Auction List
			if (null != dailyAuctionsBeansList && dailyAuctionsBeansList.size() > 0) {

				for (DailyAuctionsBean dailyAuctionsBean : dailyAuctionsBeansList) {
					// Retrieving Seller Auction Offers List
					List<AuctionSellersBean> auctionSellersBeanList = auctionSellersService
							.getAuctionSellersWithRemaingAvailableQuantity(ENUM_AuctionStatusCodes.SETTLING.getStatus(), dailyAuctionsBean.getDailyAuctionsId());

					// Checking Seller Auction Offers List
					if (null != auctionSellersBeanList && auctionSellersBeanList.size() > 0) {
						for (AuctionSellersBean auctionSellersBean : auctionSellersBeanList) {

							// Retrieving Buyer Auction Bids List
							Short[]  auctionBuyerBidStatus = {ENUM_AuctionBuyerBidStatusCodes.INPROGRESS.getStatus(),ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus()};
							List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService
									.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInOrderByBidPriceDescAuctionBuyersIdAsc(auctionSellersBean.getAuctionSellersId(),
											auctionBuyerBidStatus);

							// Checking Buyer Auction Bids List
							if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
								/* Order Execution Level 1 Start */
								AuctionOfferResult auctionOfferResult=OrderExecutionLevel1Mock.verifyOffer(auctionSellersBean, auctionBuyersBeanList);
								auctionPolicyProcessing.auctionOfferProcessing(auctionOfferResult.getAuctionSellersBean());
								AuctionSellersBean updatedAuctionSellersBean = auctionSellersService
										.findById(auctionSellersBean.getAuctionSellersId());
								for (AuctionBuyersBean auctionBuyersBean : auctionOfferResult.getSelectedBuyersBeans()) {
									if (ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus() == updatedAuctionSellersBean.getSellerOfferStatus()) {										 
											auctionPolicyProcessing.auctionBidProcessing(auctionBuyersBean);										 
									}
								}
								for (AuctionBuyersBean auctionBuyersBean : auctionOfferResult.getRejectedBuyersBeans()) {									 
									/** MileStone9_Comments_ـFeb11_2019 issues No#7  */
									auctionBuyersBean.setExecutedPrice(0.0f);
									auctionBuyersBean = auctionStatusHelper.endAuctionBuyerBid(auctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.EXPIRED, 0, 0F);
										logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);									 
								}
							
								List<Short> buyerBidStatusList = auctionBuyersService.getBuyerBidStatusByAuctionSellerId(auctionSellersBean.getAuctionSellersId());
								if (null != buyerBidStatusList && buyerBidStatusList.size() == 1) {
									Short buyerBidStatus = buyerBidStatusList.get(0);
									if (ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus() == buyerBidStatus) {
										auctionSellersBean.setSellerOfferStatus(ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus());
									} else if (ENUM_AuctionBuyerBidStatusCodes.EXPIRED.getStatus() == buyerBidStatus) {
										auctionSellersBean.setSellerOfferStatus(ENUM_AuctionBuyerBidStatusCodes.EXPIRED.getStatus());
									}
									auctionSellersService.save(auctionSellersBean);
								} else if (null != buyerBidStatusList && buyerBidStatusList.size() == 0) {
									auctionSellersBean.setSellerOfferStatus(ENUM_AuctionBuyerBidStatusCodes.EXPIRED.getStatus());
									auctionSellersService.save(auctionSellersBean);
								}
							} else {
								auctionStatusHelper.endAuctionSellerOffer(auctionSellersBean, ENUM_AuctionSellerOfferStatusCodes.EXPIRED);
							
							}
						}
					}
				}

			}
			logger.info("=== ending  settelOffersAndBids method === ");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
}