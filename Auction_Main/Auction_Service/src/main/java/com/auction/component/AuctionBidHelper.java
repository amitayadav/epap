package com.auction.component;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.enums.ENUM_BidOperationCodes;
import com.auction.commons.enums.ENUM_OfferOperationCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.AuctionTradesBean;
import com.auction.model.bean.AuctionTradesIdBean;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.AuctionTradesService;
import com.auction.service.SellerLogService;
import com.auction.service.util.AuctionBidResult;
import com.auction.service.util.AuctionOfferResult;
import com.auction.service.util.OrderExecutionLevel1Mock;
import com.auction.service.util.OrderExecutionLevel2Mock;

@Component
public class AuctionBidHelper {

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private AuctionOfferHelper auctionOfferHelper;
	
	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private AuctionPolicyProcessing auctionPolicyProcessing;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private LogHelper logHelper;

	@Autowired
	private AuctionStatusHelper auctionStatusHelper;
	
	@Autowired
	private AuctionTradesService auctionTradesService;
	
	@Autowired
	private SellerLogService sellerLogService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/**
	 * This method will update balance while new bid will place or bid will update
	 */
	@Async("BalanceTaskExecutor")
	public Future<Boolean> updateBidUserBalanceTransaction(AuctionBuyersBean auctionBuyersBean) {
		logger.info("AuctionBidHelper Call updateBidUserBalanceTransaction method");
		try {
			AuctionSellersBean auctionSellersBeaninBuyer = auctionSellersService.findById(auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
			auctionOfferHelper.validBuyerBidAndExecuteAuctionOffeAndExecuteBuyerBid(auctionSellersBeaninBuyer);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		logger.info("End  updateBidUserBalanceTransaction method");
		return new AsyncResult<Boolean>(true);
	}
	@Async("BalanceTaskExecutor")
	public Future<Boolean> changeBidUserBalance(AuctionBuyersBean auctionBuyersBean) {
		logger.info("AuctionBidHelper Call changeBidUserBalance method");
		if (null != auctionBuyersBean && null != auctionBuyersBean.getAuctionBuyersId() && auctionBuyersBean.getAuctionBuyersId() > 0) {
			/* Order Execution Level 1 Start */
			AuctionBidResult auctionBidResult=OrderExecutionLevel1Mock.verifySingleBuyer(auctionBuyersBean);
			if(auctionBidResult.isResult()) {
			AuctionSellersBean auctionSellersBean =auctionBidResult.getAuctionBuyersBean().getAuctionSellersBean(); 
		 
				if (auctionSellersBean.getAvailableQuantity() != 0 && ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() == auctionSellersBean.getSellerOfferStatus()) {
					auctionPolicyProcessing.auctionBidProcessing(auctionBuyersBean);
					simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionBuyerBidProcessed...");
				}

				if (auctionSellersBean.getAvailableQuantity() == 0 && ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() == auctionSellersBean.getSellerOfferStatus()) {
					List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService
							.findByAuctionSellersAuctionSellersIdAndBuyerBidStatus(auctionSellersBean.getAuctionSellersId(), ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus());
					if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
						for (AuctionBuyersBean currentAuctionBuyersBean : auctionBuyersBeanList) {
							/**  MileStone9_Comments_ـFeb11_2019   issues No#7 */
							currentAuctionBuyersBean.setExecutedPrice(0.0f);
							currentAuctionBuyersBean = auctionStatusHelper.endAuctionBuyerBid(currentAuctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.EXPIRED, 0, 0F);
							logHelper.BuyerLog(currentAuctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
						}
						simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionBuyerBidProcessed...");
					}
				}
			}
		}
		logger.info("=== ending  changeBidUserBalance method ===");
		return new AsyncResult<Boolean>(true);
	}

	@Async("BalanceTaskExecutor")
	public void acceptBidProcess(Integer auctionSellersId) {
		logger.info("AuctionBidHelper Call acceptBidProcess method");
		try {
			List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.getAuctionBuyersByStatusByAuctionSellersId(ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus(),
					ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus(), ENUM_AuctionStatusCodes.RUNNING.getStatus(), auctionSellersId);
			AuctionSellersBean auctionSellersBean = auctionSellersService
					.findById(auctionSellersId);		
			if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {				
				/* Order Execution Level 1 Start */
				AuctionOfferResult auctionOfferResult=OrderExecutionLevel1Mock.verifyOffer(auctionSellersBean, auctionBuyersBeanList);
				for (AuctionBuyersBean auctionBuyersBean : auctionOfferResult.getSelectedBuyersBeans()) {
					AuctionSellersBean updatedAuctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
					if (updatedAuctionSellersBean.getAvailableQuantity() != 0 && ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() == updatedAuctionSellersBean.getSellerOfferStatus()) {
						auctionPolicyProcessing.auctionBidProcessing(auctionBuyersBean);
					} 
					
				}
				for (AuctionBuyersBean auctionBuyersBean : auctionOfferResult.getRejectedBuyersBeans()) {
					AuctionSellersBean updatedAuctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
				  if (ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus() == updatedAuctionSellersBean.getSellerOfferStatus() ||  ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() == updatedAuctionSellersBean.getSellerOfferStatus()) {
					 /** MileStone9_Comments_ـFeb11_2019 issues No#7  */
					  auctionBuyersBean.setExecutedPrice(0.0f);
					  auctionBuyersBean = auctionStatusHelper.endAuctionBuyerBid(auctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.EXPIRED, 0, 0F);
						logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
					}
				}
				auctionPolicyProcessing.auctionOfferProcessing(auctionOfferResult.getAuctionSellersBean());
				simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionStatusUpdated");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		logger.info("=== ending  acceptBidProcess method ===");
	}
	
	
	@Async("BalanceTaskExecutor")
	public void acceptTakeBidProcess(Integer auctionSellersId) {
		logger.info("AuctionBidHelper Call acceptTakeBidProcess method");
		try {
			List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.getAuctionBuyersByStatusByAuctionSellersId(ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus(),
					ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus(), ENUM_AuctionStatusCodes.RUNNING.getStatus(), auctionSellersId);
			AuctionSellersBean auctionSellersBean = auctionSellersService
					.findById(auctionSellersId);		
			if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {				
				/* Order Execution Level 1 Start */
				AuctionOfferResult auctionOfferResult=OrderExecutionLevel2Mock.verifyOffer(auctionSellersBean, auctionBuyersBeanList);
				//for (AuctionBuyersBean auctionBuyersBean : auctionOfferResult.getSelectedBuyersBeans()) {
				if(auctionOfferResult.isSellerQtyStatified() && auctionOfferResult.getSelectedBuyersBeans()!=null && auctionOfferResult.getSelectedBuyersBeans().size() > 0) {
					Float   firstBidPrice = auctionOfferResult.getSelectedBuyersBeans().get(0).getBidPrice();
					Float SellerShippingCharge =auctionOfferResult.getAuctionSellersBean().getSellerShippingCharge();
				  for (AuctionBuyersBean auctionBuyersBean : auctionOfferResult.getSelectedBuyersBeans()) {
					  int buyerBidPriceCompare = Float.compare(firstBidPrice, auctionBuyersBean.getBidPrice());
					  auctionSellersBean = auctionSellersService
								.findById(auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());	
				  if(buyerBidPriceCompare == 0) {
					  auctionBuyersBean.setAuctionSellersBean(auctionSellersBean);
				  AuctionBidResult result=OrderExecutionLevel2Mock.verifySingleBuyer(auctionBuyersBean);
				  
				  if(result.isResult()) {				  
				/*  if (auctionSellersBean.getAvailableQuantity() != 0 && ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() == auctionSellersBean.getSellerOfferStatus()) {
					  auctionPolicyProcessing.auctionBidProcessing(result.getAuctionBuyersBean());
				  } 	*/
					  auctionPolicyProcessing.auctionBidProcessing(result.getAuctionBuyersBean());
				  boolean SellerShipperCount = sellerLogService.countShippingChargeByAuctionSeller(auctionSellersBean.getAuctionSellersId());
					if(SellerShipperCount) {
						auctionBuyersBean.getAuctionSellersBean().setSellerShippingCharge(0.0f);
					}
				  logHelper.saveSellerLog(auctionBuyersBean, ENUM_OfferOperationCodes.SETTLING);
				  if(!SellerShipperCount) {
					  SellerShippingCharge=auctionBuyersBean.getAuctionSellersBean().getSellerShippingCharge();
				  }if(SellerShipperCount){
					  auctionBuyersBean.getAuctionSellersBean().setSellerShippingCharge(SellerShippingCharge);
				  }
				
				  auctionPolicyProcessing.auctionOfferProcessing(result.getAuctionBuyersBean().getAuctionSellersBean());
				  }
				}
				  }
			}
				 auctionSellersBean = auctionSellersService
							.findById(auctionSellersId);	
				if (auctionSellersBean.getAvailableQuantity() == 0 ) {
		
				  auctionBuyersBeanList = auctionBuyersService.getAuctionBuyersByStatusByAuctionSellersId(ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus(),
							ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus(), ENUM_AuctionStatusCodes.RUNNING.getStatus(), auctionSellersId);
				if(auctionBuyersBeanList !=null && auctionBuyersBeanList.size() > 0 ) {
				  for (Iterator<AuctionBuyersBean> iterator = auctionBuyersBeanList.iterator(); iterator.hasNext();) {
					  AuctionBuyersBean    auctionBuyersBean = (AuctionBuyersBean) iterator.next();	
					  /**  MileStone9_Comments_ـFeb11_2019  issues NO#7    */
					  auctionBuyersBean.setExecutedPrice(0.0f);
					  auctionBuyersBean = auctionStatusHelper.endAuctionBuyerBid(auctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.EXPIRED, 0, 0F);
						logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
				}  		
				}
				}
				/**
				 * this seller takeBid  socket used  used AuctionStatusUpdated 
				 */
				simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", auctionSellersId);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		logger.info("=== ending acceptTakeBidProcess method ===");
	}
	
public void recordAuctionTrades(AuctionBuyersBean auctionBuyersBean) {
	logger.info("AuctionBidHelper Call acceptTakeBidProcess method");
	AuctionTradesIdBean auctionTradesIdBean = new AuctionTradesIdBean();
	auctionTradesIdBean.setLogTimestamp(InternetTiming.getInternetDateTime());
	auctionTradesIdBean
			.setProductCatalogBean(auctionBuyersBean.getAuctionSellersBean().getDailyAuctionsBean().getProductCatalogBean());

	AuctionTradesBean auctionTradesBean = new AuctionTradesBean();
	auctionTradesBean.setId(auctionTradesIdBean);
	auctionTradesBean.setSoldPrice(auctionBuyersBean.getExecutedPrice());
	auctionTradesBean
			.setSoldQuantity(auctionBuyersBean.getExecutedQuantity());
	auctionTradesService.save(auctionTradesBean);
	logger.info("=== Ending acceptTakeBidProcess method ===");
}
}