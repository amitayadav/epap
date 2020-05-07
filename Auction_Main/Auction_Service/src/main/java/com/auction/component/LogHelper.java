package com.auction.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.enums.ENUM_BidOperationCodes;
import com.auction.commons.enums.ENUM_OfferOperationCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.BuyerLogBean;
import com.auction.model.bean.BuyerLogIdBean;
import com.auction.model.bean.SellerLogBean;
import com.auction.model.bean.SellerLogIdBean;
import com.auction.service.BuyerLogService;
import com.auction.service.SellerLogService;

@Component
public class LogHelper {

	@Autowired
	private BuyerLogService buyerLogService;

	@Autowired
	private SellerLogService sellerLogService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	// This method will create log for  Buyer Executed(SETTLING) in Add  Seller Log 
		public void saveSellerLog(AuctionBuyersBean auctionBuyersBean, ENUM_OfferOperationCodes offerOperation) {
			logger.info("LogHelper call saveSellerLog method");
			SellerLogIdBean sellerLogIdBean = new SellerLogIdBean();
			sellerLogIdBean.setAuctionSellersBean(auctionBuyersBean.getAuctionSellersBean());
			sellerLogIdBean.setLogTimestamp(InternetTiming.getInternetDateTime());
			sellerLogIdBean.setOfferOperation(offerOperation.getStatus());

			SellerLogBean sellerLogBean = new SellerLogBean();
			sellerLogBean.setId(sellerLogIdBean);
			sellerLogBean.setAccountProfileBean(auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean());
			sellerLogBean.setShipperAccountProfileBean(auctionBuyersBean.getAuctionSellersBean().getShipperAccountProfileBean());
			sellerLogBean.setDailyAuctionsBean(auctionBuyersBean.getAuctionSellersBean().getDailyAuctionsBean());
			sellerLogBean.setOfferQuantity(auctionBuyersBean.getExecutedQuantity());
			sellerLogBean.setMinimumQuantity(auctionBuyersBean.getAuctionSellersBean().getMinimumQuantity());
			sellerLogBean.setAvailableQuantity(auctionBuyersBean.getAuctionSellersBean().getAvailableQuantity());
			sellerLogBean.setOfferPrice(auctionBuyersBean.getExecutedPrice());
			sellerLogBean.setSellerShippingCharge(auctionBuyersBean.getAuctionSellersBean().getSellerShippingCharge());
			sellerLogBean.setTruckNumber(auctionBuyersBean.getAuctionSellersBean().getTruckNumber());
			logger.info("=== ending  saveSellerLog method ===");
			sellerLogService.save(sellerLogBean);
		}

	// This method will create log for seller
	public void sellerLog(AuctionSellersBean auctionSellersBean, ENUM_OfferOperationCodes offerOperation) {
		logger.info("LogHelper call sellerLog method");
		SellerLogIdBean sellerLogIdBean = new SellerLogIdBean();
		sellerLogIdBean.setAuctionSellersBean(auctionSellersBean);
		sellerLogIdBean.setLogTimestamp(InternetTiming.getInternetDateTime());
		sellerLogIdBean.setOfferOperation(offerOperation.getStatus());

		SellerLogBean sellerLogBean = new SellerLogBean();
		sellerLogBean.setId(sellerLogIdBean);
		sellerLogBean.setAccountProfileBean(auctionSellersBean.getAccountProfileBean());
		sellerLogBean.setShipperAccountProfileBean(auctionSellersBean.getShipperAccountProfileBean());
		sellerLogBean.setDailyAuctionsBean(auctionSellersBean.getDailyAuctionsBean());
		sellerLogBean.setOfferQuantity(auctionSellersBean.getOfferQuantity());
		sellerLogBean.setMinimumQuantity(auctionSellersBean.getMinimumQuantity());
		sellerLogBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity());
		sellerLogBean.setOfferPrice(auctionSellersBean.getOfferPrice());
		sellerLogBean.setSellerShippingCharge(auctionSellersBean.getSellerShippingCharge());
		sellerLogBean.setTruckNumber(auctionSellersBean.getTruckNumber());
		sellerLogService.save(sellerLogBean);
		logger.info("=== ending  sellerLog method ===");
	}

	// This method will create log for seller for REMAINING QUANTITY
	public void SellerLogForRemainingQuantity(AuctionSellersBean auctionSellersBean, int remainingQuantity) {
		logger.info("LogHelper call SellerLogForRemainingQuantity  method");
		SellerLogIdBean sellerLogIdBean = new SellerLogIdBean();
		sellerLogIdBean.setAuctionSellersBean(auctionSellersBean);
		sellerLogIdBean.setLogTimestamp(InternetTiming.getInternetDateTime());
		sellerLogIdBean.setOfferOperation(ENUM_OfferOperationCodes.REMAINED_QTY.getStatus());

		SellerLogBean sellerLogBean = new SellerLogBean();
		sellerLogBean.setId(sellerLogIdBean);
		sellerLogBean.setAccountProfileBean(auctionSellersBean.getAccountProfileBean());
		sellerLogBean.setShipperAccountProfileBean(auctionSellersBean.getShipperAccountProfileBean());
		sellerLogBean.setDailyAuctionsBean(auctionSellersBean.getDailyAuctionsBean());
		sellerLogBean.setOfferQuantity(auctionSellersBean.getOfferQuantity());
		sellerLogBean.setMinimumQuantity(auctionSellersBean.getMinimumQuantity());
		sellerLogBean.setAvailableQuantity(remainingQuantity);
		sellerLogBean.setOfferPrice(auctionSellersBean.getOfferPrice());
		sellerLogBean.setSellerShippingCharge(auctionSellersBean.getSellerShippingCharge());
		sellerLogBean.setTruckNumber(auctionSellersBean.getTruckNumber());
		sellerLogService.save(sellerLogBean);
		logger.info("=== ending  SellerLogForRemainingQuantity  method ===");
	}

	// This method will create log for buyers
	public void BuyerLog(AuctionBuyersBean auctionBuyersBean, ENUM_BidOperationCodes bidOperation) {
		logger.info("LogHelper call BuyerLog  method");
		BuyerLogIdBean buyerLogIdBean = new BuyerLogIdBean();
		buyerLogIdBean.setLogTimestamp(InternetTiming.getInternetDateTime());
		buyerLogIdBean.setAuctionBuyersBean(auctionBuyersBean);
		buyerLogIdBean.setBidOperation(bidOperation.getStatus());

		BuyerLogBean buyerLogBean = new BuyerLogBean();
		buyerLogBean.setId(buyerLogIdBean);
		buyerLogBean.setAccountProfileBean(auctionBuyersBean.getAccountProfileBean());
		buyerLogBean.setShipperAccountProfileBean(auctionBuyersBean.getShipperAccountProfileBean());
		buyerLogBean.setDailyAuctionsBean(auctionBuyersBean.getDailyAuctionsBean());
		buyerLogBean.setBidQuantity(auctionBuyersBean.getBidQuantity());
		buyerLogBean.setMinimumQuantity(auctionBuyersBean.getMinimumQuantity());
		buyerLogBean.setBidPrice(auctionBuyersBean.getBidPrice());
		buyerLogBean.setBuyerShippingCharge(buyerLogBean.getBuyerShippingCharge());
		buyerLogBean.setExecutedPrice(auctionBuyersBean.getExecutedPrice());
		buyerLogBean.setExecutedQuantity(auctionBuyersBean.getExecutedQuantity());
		buyerLogService.save(buyerLogBean);
		logger.info("=== ending  BuyerLog  method ===");
	}

	// This method will create log for buyers for REMAINING QUANTITY
	public void BuyerLogForRemainingQuantity(AuctionBuyersBean auctionBuyersBean, int remainingQuantity) {
		logger.info("LogHelper call BuyerLog  method");
		BuyerLogIdBean buyerLogIdBean = new BuyerLogIdBean();
		buyerLogIdBean.setLogTimestamp(InternetTiming.getInternetDateTime());
		buyerLogIdBean.setAuctionBuyersBean(auctionBuyersBean);
		buyerLogIdBean.setBidOperation(ENUM_BidOperationCodes.REMAINED_QTY.getStatus());

		BuyerLogBean buyerLogBean = new BuyerLogBean();
		buyerLogBean.setId(buyerLogIdBean);
		buyerLogBean.setAccountProfileBean(auctionBuyersBean.getAccountProfileBean());
		buyerLogBean.setShipperAccountProfileBean(auctionBuyersBean.getShipperAccountProfileBean());
		buyerLogBean.setDailyAuctionsBean(auctionBuyersBean.getDailyAuctionsBean());
		buyerLogBean.setBidQuantity(remainingQuantity);
		buyerLogBean.setMinimumQuantity(auctionBuyersBean.getMinimumQuantity());
		buyerLogBean.setBidPrice(auctionBuyersBean.getBidPrice());
		buyerLogBean.setBuyerShippingCharge(buyerLogBean.getBuyerShippingCharge());
		buyerLogBean.setExecutedPrice(auctionBuyersBean.getExecutedPrice());
		buyerLogBean.setExecutedQuantity(auctionBuyersBean.getExecutedQuantity());
		buyerLogService.save(buyerLogBean);
		logger.info("=== ending  BuyerLog  method ===");
	}

}