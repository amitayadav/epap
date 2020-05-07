package com.auction.service.util;

import com.auction.commons.util.AuctionLogger;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.ibm.icu.math.BigDecimal;

public class AuctionCalculationHelper {

	private  static AuctionLogger logger = new AuctionLogger("AuctionCalculationHelper");
	
	public static float calculateReturnBidAmountOnBidExpired(AuctionBuyersBean auctionBuyersBean) {
		logger.info("=======Start==========calculateReturnBidAmountOnBidExpired===============");
		/**  Buyer Expired Bid calculate      20-20-2019                 */
		logger.info("Return Amount Auction Buyer Id :="+"  "+auctionBuyersBean.getAuctionBuyersId()+" "+" Buyer Id:="+auctionBuyersBean.getAccountProfileBean().getAccountId()+" "+" Auction Buyer FullName:="+auctionBuyersBean.getAccountProfileBean().getFullName());
		float royalty = auctionBuyersBean.getRoyaltyValue();
		logger.info("Auction Buyer Royalty:"+"  "+royalty);
		int bidQuantity = auctionBuyersBean.getBidQuantity();
		logger.info("Auction Buyer Bid Quantity"+"  "+bidQuantity);
		float vat = auctionBuyersBean.getVat();
		logger.info("Auction Buyer Vat"+"  "+vat);
		float buyerShippingCharge = auctionBuyersBean.getBuyerShippingCharge();
		logger.info("Auction Buyer Shipping Charge"+"  "+buyerShippingCharge);
		float returnBidAmount = auctionBuyersBean.getBidPrice();
		logger.info("Auction Buyer Bid Price(returnBidAmount)"+"  "+returnBidAmount);
		/*TypeConverterUtil.scaleFloatValue(returnBidAmount = returnBidAmount * bidQuantity);
		TypeConverterUtil.scaleFloatValue(royalty = (returnBidAmount * royalty / 100));
		TypeConverterUtil.scaleFloatValue(vat = (returnBidAmount * vat / 100));
		TypeConverterUtil.scaleFloatValue(returnBidAmount += royalty + vat + buyerShippingCharge);*/
		returnBidAmount = returnBidAmount * bidQuantity;
		logger.info("ReturnBidAmount(returnBidAmount * bidQuantity):="+"  "+returnBidAmount);
		royalty = (returnBidAmount * royalty / 100);
		logger.info("Royalty(returnBidAmount * royalty / 100):="+"  "+royalty);
		vat = (returnBidAmount * vat / 100);
		logger.info("Vat(returnBidAmount * vat / 100):="+"  "+vat);
		returnBidAmount += royalty + vat + buyerShippingCharge;
		logger.info("ReturnBidAmount(returnBidAmount += royalty + vat + buyerShippingCharge;):="+"  "+returnBidAmount);
		logger.info("=======End ==========calculateReturnBidAmountOnBidExpired===============");
		return returnBidAmount;
	
	}
	
	
	public static double calculateExecutedBidAmountOnConfirmDelivery(AuctionBuyersBean auctionBuyersBean) {
		/**  Buyer accept Bid calculate      22-20-2019                 */
		logger.info("=======Start==========Calculate ExecutedBidAmount On ConfirmDelivery===============");
		logger.info("calculateExecutedBidAmountOnConfirmDelivery :="+"  "+auctionBuyersBean.getAuctionBuyersId()+" "+" Buyer Id:="+auctionBuyersBean.getAccountProfileBean().getAccountId()+" "+" Auction Buyer FullName:="+auctionBuyersBean.getAccountProfileBean().getFullName());
			BigDecimal b1 = new BigDecimal(auctionBuyersBean.getExecutedQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal b2 = new BigDecimal(auctionBuyersBean.getExecutedPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal b3 = new BigDecimal(auctionBuyersBean.getVat()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal b4 = new BigDecimal(auctionBuyersBean.getRoyaltyValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal b5 = new BigDecimal(auctionBuyersBean.getBuyerShippingCharge()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal totalCostAmount1 = new BigDecimal(b1.doubleValue() * b2.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal buyerVAT1 = new BigDecimal((totalCostAmount1.doubleValue() * b3.doubleValue()) / 100).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal buyerCom1 = new BigDecimal((totalCostAmount1.doubleValue() * b4.doubleValue()) / 100).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal calculationTotalCostAmount = new BigDecimal(totalCostAmount1.doubleValue() + buyerVAT1.doubleValue() + buyerCom1.doubleValue() + b5.doubleValue())
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			logger.info("Buyer Bid toalCostAmount1 In  bidQ * bidPrice :--##" + "   " + totalCostAmount1);
			logger.info("Buyer Bid buyerVAT = (bidQ * bidPrice* buyerVAT / 100); :--##" + "   " + buyerVAT1);
			logger.info("Buyer Bid  buyerCom = (bidQ *bidPrice*buyerCom / 100) :--##" + "   " + buyerCom1);
			logger.info("Buye bidVAT blockedAmount += (buyerCom + buyerVAT + buyerShipping) :--##" + "   " + calculationTotalCostAmount);
			logger.info("===========calculate calculationTotalCost End ===========");
			return calculationTotalCostAmount.doubleValue();
		
	
	}
	
	public static double calculateSellerOfferAmount(AuctionBuyersBean auctionBuyersBean) {
		logger.info("=======Start==========Calculate Seller Offer Amount===============");
		AuctionSellersBean auctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
		logger.info("auction Seller Id =:"+auctionSellersBean.getAuctionSellersId());
		logger.info("Daily Auction Id =:"+auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId());
		logger.info("Seller Public  Name =:"+auctionSellersBean.getAccountProfileBean().getPublicName());
		int bidQuantity = auctionBuyersBean.getExecutedQuantity();
		logger.info(" Seller bidQuantity =:"+auctionBuyersBean.getExecutedQuantity());
		float royalty = auctionSellersBean.getRoyaltyValue();
		logger.info("Seller royalty =:"+auctionBuyersBean.getExecutedQuantity());
		float vat = auctionSellersBean.getVat();
		logger.info("Seller vat =:"+auctionSellersBean.getVat());
		float sellerShippingCharge = auctionSellersBean.getSellerShippingCharge();
		logger.info("sellerShippingCharge =:"+auctionSellersBean.getSellerShippingCharge());
		 float exexutedPrice = auctionBuyersBean.getExecutedPrice(); 
		 logger.info("Seller exexutedPrice =:"+auctionBuyersBean.getExecutedPrice());
		 exexutedPrice = exexutedPrice * bidQuantity;
		royalty = (exexutedPrice * royalty / 100);
		 vat = (exexutedPrice * vat / 100);
		 exexutedPrice -= (royalty + vat);
		 /**
		  * issues no##104: Seller shipping charge issue change code 
		  */
		 if(exexutedPrice < sellerShippingCharge) {
			 sellerShippingCharge =exexutedPrice;
			 auctionBuyersBean.getAuctionSellersBean().setSellerShippingCharge(sellerShippingCharge);
			 exexutedPrice -= (sellerShippingCharge);
		 }else {
			 auctionBuyersBean.getAuctionSellersBean().setSellerShippingCharge(sellerShippingCharge);
			 exexutedPrice -= (sellerShippingCharge);
		 }
		 logger.info("Seller exexutedPrice royalty + vat + sellerShippingCharge =:"+exexutedPrice);
			logger.info("=======End==========Calculate Seller Offer Amount===============");
			BigDecimal totalSellErexexutedPrice = new BigDecimal(exexutedPrice);
			totalSellErexexutedPrice = totalSellErexexutedPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		return totalSellErexexutedPrice.doubleValue();
	}

}