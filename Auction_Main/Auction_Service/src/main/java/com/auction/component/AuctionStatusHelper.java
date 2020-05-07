package com.auction.component;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.service.AccountProfileService;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.BuyerLogService;
import com.auction.service.DailyAuctionsService;



@Component
public class AuctionStatusHelper {

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private DailyAuctionsService dailyAuctionsService;

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private BuyerLogService buyerLogService;
	
	@Autowired
	private PickupTicketsHelper pickupTicketsHelper;
	
	@Autowired
	private BuyerBankDetailsHelper balanceHelper;
	
	private AuctionLogger logger = new AuctionLogger(getClass().getName());

	// Ending Auction Buyer Bid
	public AuctionBuyersBean endAuctionBuyerBid(AuctionBuyersBean auctionBuyersBean,
			ENUM_AuctionBuyerBidStatusCodes buyerBidStatus, Integer executedQuantity, Float executedPrice) {
		logger.info("AuctionSettlementPhse Call endAuctionBuyerBid method ");
		if (buyerBidStatus == ENUM_AuctionBuyerBidStatusCodes.EXPIRED) {
			logger.info(" EXPIRED Buyer(Buyer EXPIRED)  "+"  "+buyerBidStatus.getStatus());
			logger.info("Daily Auction Info "+"  "+" ProductId:="+auctionBuyersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId()+"  "+"Daily Auction Name:="+auctionBuyersBean.getDailyAuctionsBean().getProductCatalogBean().getProductName());
			logger.info("  "+" Daily Auction Id"+auctionBuyersBean.getDailyAuctionsBean().getDailyAuctionsId());
			logger.info("Seller Info In Auction Seller Id"+" "+auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
			logger.info("  "+" Auction Buyer Id "+auctionBuyersBean.getAuctionBuyersId());
			logger.info("  "+" Buyer Full Names "+auctionBuyersBean.getAccountProfileBean().getFullName());
			/* Buyer Expired  calculateBuyerBidAmount old method    */
			balanceHelper.resetBuyerUserBalance(auctionBuyersBean);
			auctionBuyersBean.setActualEndTime(InternetTiming.getInternetDateTime());
			/**
			 * this is method used MBBR
			 * buyerBankDetailsHelper.resetBuyerUserBalanceWithMBBR(auctionBuyersBean);
			 */
			
			logger.info(" End EXPIRED Buyer(Buyer Ststus)  "+" "+"Auction Buyer Bid Ststus"+auctionBuyersBean.getBuyerBidStatus()+"  "+" AuctionBuyerID : "+"  "+auctionBuyersBean.getAuctionBuyersId()+" "+" ExecutedPrice : "+"  "+auctionBuyersBean.getExecutedPrice()+" "+" Executedquantity : "+"  "+auctionBuyersBean.getExecutedQuantity()+" "+" AuctionEndTime : "+"  "+auctionBuyersBean.getActualEndTime());
		} else if (buyerBidStatus == ENUM_AuctionBuyerBidStatusCodes.SETTLING) {
			logger.info(" Executed Buyer(Buyer SETTING)  "+"  "+buyerBidStatus.getStatus());
			if (auctionBuyersBean.getAuctionSellersBean().getOfferPrice() > 0) {
				auctionBuyersBean.setExecutedPrice(auctionBuyersBean.getAuctionSellersBean().getOfferPrice());
			} else {
				auctionBuyersBean.setExecutedPrice(auctionBuyersBean.getBidPrice());
			}
			auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getExecutedQuantity());
		} else {
			auctionBuyersBean.setExecutedPrice(executedPrice);
			auctionBuyersBean.setExecutedQuantity(executedQuantity);
		}
		if (buyerBidStatus == ENUM_AuctionBuyerBidStatusCodes.FINISHED) {
			auctionBuyersBean.setActualEndTime(InternetTiming.getInternetDateTime());
		}
		auctionBuyersBean.setBuyerBidStatus(buyerBidStatus.getStatus());
		auctionBuyersService.save(auctionBuyersBean);
		if(buyerBidStatus == ENUM_AuctionBuyerBidStatusCodes.SETTLING) {
			pickupTicketsHelper.savePickupTickets(auctionBuyersBean);
		}
		logger.info(" End Executed Buyer(Buyer Ststus)  "+" "+"Auction Buyer Bid Ststus"+auctionBuyersBean.getBuyerBidStatus()+"  "+" AuctionBuyerID : "+"  "+auctionBuyersBean.getAuctionBuyersId()+" "+" ExecutedPrice : "+"  "+auctionBuyersBean.getExecutedPrice()+" "+" Executedquantity : "+"  "+auctionBuyersBean.getExecutedQuantity()+" "+" AuctionEndTime : "+"  "+auctionBuyersBean.getActualEndTime());
		/*System.out.println("#25"+" Executed Buyer(Buyer Ststus)  "+" "+"Auction Buyer Bid Ststus"+auctionBuyersBean.getBuyerBidStatus()+"  "+" AuctionBuyerID : "+"  "+auctionBuyersBean.getAuctionBuyersId()+" "+" ExecutedPrice : "+"  "+auctionBuyersBean.getExecutedPrice()+" "+" Executedquantity : "+"  "+auctionBuyersBean.getExecutedQuantity()+" "+" AuctionEndTime : "+"  "+auctionBuyersBean.getActualEndTime());*/
		if (buyerBidStatus == ENUM_AuctionBuyerBidStatusCodes.FINISHED) {
			logger.info(" Executed Buyer(Buyer FINISHED)  "+"  "+buyerBidStatus.getStatus());
			logger.info("Daily Auction Info "+"  "+" ProductId:="+auctionBuyersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId()+"  "+"Daily Auction Name:="+auctionBuyersBean.getDailyAuctionsBean().getProductCatalogBean().getProductName());
			logger.info("  "+" Daily Auction Id"+auctionBuyersBean.getDailyAuctionsBean().getDailyAuctionsId());
			logger.info("Seller Info In Auction Seller Id"+" "+auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
			logger.info("  "+" Auction Buyer Id "+auctionBuyersBean.getAuctionBuyersId());
			logger.info("  "+" Buyer Full Names "+auctionBuyersBean.getAccountProfileBean().getFullName());
			Short bidOperationStatus = buyerLogService.getBidOperation(auctionBuyersBean.getAuctionBuyersId(),
					auctionBuyersBean.getDailyAuctionsBean().getDailyAuctionsId(), buyerBidStatus.getStatus());
			if (null != bidOperationStatus && bidOperationStatus > 0) {
				AccountProfileBean accountProfileBean = accountProfileService
						.findById(auctionBuyersBean.getAccountProfileBean().getAccountId());
				accountProfileBean.setExecutedOfferOrBidCount(accountProfileBean.getExecutedOfferOrBidCount() + 1);
				accountProfileService.save(accountProfileBean);
			}
			logger.info(" ==================End=========== Executed Buyer(Buyer FINISHED)  "+"  "+buyerBidStatus.getStatus()+"=====================");
		}
		return auctionBuyersBean;
	}

	// Ending Auction Seller Offer from AuctionBuyersBean object.
	public AuctionSellersBean endAuctionSellerOffer(AuctionBuyersBean auctionBuyersBean) {
		logger.info("AuctionSettlementPhse Call endAuctionSellerOffer method ");
		AuctionSellersBean auctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
				AccountProfileBean accountProfileBean = accountProfileService
						.findById(auctionSellersBean.getAccountProfileBean().getAccountId());
				accountProfileBean.setExecutedOfferOrBidCount(accountProfileBean.getExecutedOfferOrBidCount() + 1);
				accountProfileService.save(accountProfileBean);
				logger.info("=== ending  endAuctionSellerOffer method === ");
		return auctionSellersBean;
	}
	
	// Ending Auction Seller Offer In 45 Mint 
	public void endAuctionSellerOffer(AuctionSellersBean auctionSellersBean) {
		logger.info("AuctionSettlementPhse Call endAuctionSellerOffer method ");
				AccountProfileBean accountProfileBean = accountProfileService
						.findById(auctionSellersBean.getAccountProfileBean().getAccountId());
				accountProfileBean.setExecutedOfferOrBidCount(accountProfileBean.getExecutedOfferOrBidCount() + 1);
				accountProfileService.save(accountProfileBean);
				logger.info("=== ending  endAuctionSellerOffer method === ");
	}


	// Ending Auction Seller Offer
	public AuctionSellersBean endAuctionSellerOffer(AuctionSellersBean auctionSellersBean,
			ENUM_AuctionSellerOfferStatusCodes sellerOfferStatus) {
		logger.info("AuctionSettlementPhse Call endAuctionSellerOffer method ");
		if (sellerOfferStatus == ENUM_AuctionSellerOfferStatusCodes.FINISHED) {
			if (auctionSellersBean.getOfferQuantity() == auctionSellersBean.getAvailableQuantity()) {
				sellerOfferStatus = ENUM_AuctionSellerOfferStatusCodes.EXPIRED;
			}
		}
		auctionSellersBean.setSellerOfferStatus(sellerOfferStatus.getStatus());
		auctionSellersBean.setActualEndTime(InternetTiming.getInternetDateTime());
		auctionSellersService.save(auctionSellersBean);
		logger.info("=== ending  endAuctionSellerOffer method === ");
		return auctionSellersBean;
	}

	// Ending Auction Seller
	public Boolean endOfferBySellerId(Integer auctionSellersId) {
		logger.info("AuctionSettlementPhse Call endOfferBySellerId method ");
		List<Short> buyerBidStatusList = auctionBuyersService.getBuyerBidStatusByAuctionSellerId(auctionSellersId);

		if (null != buyerBidStatusList && buyerBidStatusList.size() > 0) {
			if (!buyerBidStatusList.contains(ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus())
					&& !buyerBidStatusList.contains(ENUM_AuctionBuyerBidStatusCodes.OPEN.getStatus())
					&& !buyerBidStatusList.contains(ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus())) {

				auctionSellersService.updateSellerOfferStatusByAuctionSellersId(
						ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus(), auctionSellersId);
				return true;
			}
		}
		logger.info("=== ending  endOfferBySellerId method === ");
		return false;
	}

	// Ending Daily Auction
	public Boolean endDailyAuctionByDailyAuctionId(Integer dailyAuctionsId) {
		logger.info("AuctionSettlementPhse Call endDailyAuctionByDailyAuctionId method ");
		DailyAuctionsBean dailyAuctionsBean = dailyAuctionsService.findById(dailyAuctionsId);
		Short[] sellerStatusList = { ENUM_AuctionStatusCodes.RUNNING.getStatus(),
				ENUM_AuctionStatusCodes.OPEN.getStatus(), ENUM_AuctionStatusCodes.SETTLING.getStatus() };

		Integer count = auctionSellersService.countDailyAuctionSellerOfferStatusNotIn(dailyAuctionsId,
				sellerStatusList);
		if (null == count || count > 0) {
			Short[] sellerStatusInList = { ENUM_AuctionStatusCodes.SETTLING.getStatus() };

			Integer settlingSellerCount = auctionSellersService.countDailyAuctionSellerOfferStatusIn(dailyAuctionsId,
					sellerStatusInList);
			if (DateHelper.getRemainingTimeToEnd(dailyAuctionsBean.getEndTime()) <= 0) {
				if (null == settlingSellerCount || settlingSellerCount == 0) {
					dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(
							ENUM_AuctionStatusCodes.FINISHED.getStatus(), dailyAuctionsId);
					logger.info("=== ending  endDailyAuctionByDailyAuctionId method === ");
					return true;
				} else {
					dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(
							ENUM_AuctionStatusCodes.SETTLING.getStatus(), dailyAuctionsId);
				}
				logger.info("=== ending  endDailyAuctionByDailyAuctionId method === ");
			
			}
		}
		logger.info("=== ending  endDailyAuctionByDailyAuctionId method === ");
		return false;
	}

	public void testEndDailyAuctionByDailyAuctionId(short auctionstatus, short offerstatus) {
		logger.info("AuctionSettlementPhse Call testEndDailyAuctionByDailyAuctionId method ");
		if (ENUM_AuctionStatusCodes.RUNNING.getStatus() != offerstatus
				&& ENUM_AuctionStatusCodes.OPEN.getStatus() != offerstatus
				&& ENUM_AuctionStatusCodes.SETTLING.getStatus() != offerstatus) {

			short sellerStatusInList = ENUM_AuctionStatusCodes.SETTLING.getStatus();
			if (offerstatus == sellerStatusInList) {
				/*System.out.println("Settling Daily Auction");*/
			} else {
			/*	System.out.println("Finish Daily Auction");*/
			}
		} else {
		/*	System.out.println("No Action Required");*/
		}
		logger.info("=== ending  testEndDailyAuctionByDailyAuctionId method === ");
	}

	public static void main(String[] args) {

		BigDecimal bigDecimal1 = new BigDecimal("35.3455").setScale(2, BigDecimal.ROUND_CEILING);
		/*System.out.println(bigDecimal1.doubleValue());*/
	

	}

}