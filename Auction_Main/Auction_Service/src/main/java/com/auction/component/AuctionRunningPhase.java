package com.auction.component;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.enums.ENUM_BidOperationCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;


@Component
public class AuctionRunningPhase {

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private AuctionSellersService auctionSellersService;
	
	@Autowired
	private AuctionOfferHelper  auctionOfferHelper;

	@Autowired
	private LogHelper logHelper;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private AuctionPolicyProcessing auctionPolicyProcessing;
	
	@Autowired
	private AuctionStatusHelper auctionStatusHelper;

	private AuctionLogger logger = new AuctionLogger(getClass().getName());

	public void auctionOpenToRunningPhase(Date beginDate) {
		try {		
			
			/* Order Execution Level 1 Start */
			/* verifyOffer changes 21-01-2019   **/
			List<AuctionSellersBean> auctionSellersBeanList =auctionSellersService. getAuctionSellersWithLimitPriceByBeginTime(ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus(), ENUM_AuctionStatusCodes.RUNNING.getStatus(),
					beginDate);
			
			if(null != auctionSellersBeanList && auctionSellersBeanList.size() > 0) {		
				for (AuctionSellersBean auctionSellersBean : auctionSellersBeanList) {
					logger.info("Auction Running Phase calling method validBuyerBidAndExecuteAuctionOffeAndExecuteBuyerBid");
					auctionOfferHelper.validBuyerBidAndExecuteAuctionOffeAndExecuteBuyerBid(auctionSellersBean);
				}
			}
		
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public void auctionRunningBidProcessPhase(AuctionBuyersBean auctionBuyersBean) {
		logger.info("Auction Running phase call auctionRunningBidProcessPhase method ");
		try {
			if (null != auctionBuyersBean) {
				AuctionSellersBean auctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
				auctionSellersBean = auctionSellersService.findById(auctionSellersService.refresh(auctionSellersService.refresh(auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId())));
				auctionBuyersBean.setAuctionSellersBean(auctionSellersBean);

				if (auctionSellersBean.getAvailableQuantity() != 0 && ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() == auctionSellersBean.getSellerOfferStatus()) {
					auctionPolicyProcessing.auctionBidProcessing(auctionBuyersBean);
				} else if (ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus() == auctionSellersBean.getSellerOfferStatus()) {
					/** MileStone9_Comments_ـFeb11_2019 issues NO#7  */
					auctionBuyersBean.setExecutedPrice(0.0f);
					auctionBuyersBean = auctionStatusHelper.endAuctionBuyerBid(auctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.EXPIRED, 0, 0F);
					logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
				}
				if (auctionSellersBean.getAvailableQuantity() == 0 && ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus() == auctionSellersBean.getSellerOfferStatus()) {
					List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.findByAuctionSellersAuctionSellersIdAndBuyerBidStatus(auctionSellersBean.getAuctionSellersId(),ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus());
					if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
						for(AuctionBuyersBean currentAuctionBuyersBean : auctionBuyersBeanList) {
							/** MileStone9_Comments_ـFeb11_2019 issues NO#7  */
							currentAuctionBuyersBean.setExecutedPrice(0.0f);
							currentAuctionBuyersBean = auctionStatusHelper.endAuctionBuyerBid(currentAuctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.EXPIRED, 0, 0F);
							logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
						}
					}
				}
			}
			simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionStatusUpdated");
			logger.info("=== ending  auctionRunningBidProcessPhase method === ");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

}