
package com.auction.component;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.BuyerTransactionsBean;
import com.auction.service.BuyerTransactionsService;

@Component
public class BuyerTransactionHelper {

	@Autowired
	private BuyerTransactionsService buyerTransactionsService;

	@Autowired
	private VatBalanceHelper vatBalanceHelper;
	
	@Autowired
	PurchaseInvoicesHelper purchaseInvoicesHelper;
	/**
	 * This method will update balance while new bid will place or bid will update
	 */
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	public Integer createBuyersTransaction(AuctionBuyersBean auctionBuyersBean) {
		logger.info("=========Start =======Create BuyersTransaction===============");
		logger.info("Auction Buyer ID"+" = :"+auctionBuyersBean.getAuctionBuyersId());
		logger.info("Buyer ID"+" = :"+auctionBuyersBean.getAccountProfileBean().getAccountId());
		logger.info("Buyer Public Name"+" = :"+auctionBuyersBean.getAccountProfileBean().getPublicName());
		// AuctionSellersBean auctionSellersBean =
		// auctionBuyersBean.getAuctionSellersBean();
		float executedPrice = auctionBuyersBean.getExecutedPrice();
		logger.info("executedPrice =: "+executedPrice);
		int executedQuantity = auctionBuyersBean.getExecutedQuantity();
		logger.info("executedQuantity =: "+executedQuantity);
		BigDecimal grossPurchase = new BigDecimal(executedPrice * executedQuantity);
		logger.info("grossPurchase =: "+grossPurchase);
		BigDecimal netPayment = grossPurchase;
		logger.info("netPayment =: "+netPayment);
		netPayment = netPayment.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal royaltyAmount = (netPayment.multiply(new BigDecimal(auctionBuyersBean.getRoyaltyValue())).divide(new BigDecimal(100)));
		BigDecimal vatAmount = (netPayment.multiply(new BigDecimal(auctionBuyersBean.getVat())).divide(new BigDecimal(100)));
		vatAmount = vatAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		netPayment = netPayment.add(royaltyAmount);		
		netPayment = netPayment.setScale(2, BigDecimal.ROUND_HALF_UP);		
		netPayment = netPayment.add(vatAmount);
		netPayment = netPayment.setScale(2, BigDecimal.ROUND_HALF_UP);
		netPayment = netPayment.add(new BigDecimal(auctionBuyersBean.getBuyerShippingCharge()));
		netPayment = netPayment.setScale(2, BigDecimal.ROUND_HALF_UP);

		BuyerTransactionsBean buyerTransactionsBean = new BuyerTransactionsBean();
		buyerTransactionsBean.setAccountProfileBean(auctionBuyersBean.getAccountProfileBean());
		buyerTransactionsBean.setAuctionBuyersBean(auctionBuyersBean);
		buyerTransactionsBean.setBuyPrice(executedPrice);
		buyerTransactionsBean.setBuyQuantity(executedQuantity);
		buyerTransactionsBean.setGrossPurchase(grossPurchase);
		buyerTransactionsBean.setRoyaltyPercentage(auctionBuyersBean.getRoyaltyValue());
		buyerTransactionsBean.setRoyaltyAmount(royaltyAmount);
		buyerTransactionsBean.setVatPercentage(auctionBuyersBean.getVat());
		buyerTransactionsBean.setVatAmount(vatAmount);
		buyerTransactionsBean.setBuyerShippingCharge(auctionBuyersBean.getBuyerShippingCharge());
		buyerTransactionsBean.setNetPayment(netPayment);
		buyerTransactionsBean.setTransactionCreation(InternetTiming.getInternetDateTime());
		buyerTransactionsService.save(buyerTransactionsBean);
		logger.info("buyerTransactionsBean executedPrice"+" = :"+executedPrice);
		logger.info("buyerTransactionsBean executedQuantity"+" = :"+executedQuantity);
		logger.info("buyerTransactionsBean grossPurchase"+" = :"+grossPurchase);
		logger.info("buyerTransactionsBean RoyaltyValue"+" = :"+auctionBuyersBean.getRoyaltyValue());
		logger.info("buyerTransactionsBean royaltyAmount"+" = :"+royaltyAmount);
		logger.info("buyerTransactionsBean Vat"+" = :"+auctionBuyersBean.getVat());
		logger.info("buyerTransactionsBean vatAmount"+" = :"+vatAmount);
		logger.info("buyerTransactionsBean Buyer Shipper Charge"+" = :"+auctionBuyersBean.getBuyerShippingCharge());
		logger.info("buyerTransactionsBeannetPayment"+" = :"+netPayment);
		logger.info("buyerTransactionsBeannet Date Time"+" = :"+InternetTiming.getInternetDateTime());
		//epapBalanceHelper.createBuyerCommissionCredit(buyerTransactionsBean, royaltyAmount);
		vatBalanceHelper.createBuyerVatCredit(buyerTransactionsBean, vatAmount);
		purchaseInvoicesHelper.createBuyerPurchaseInvoices(buyerTransactionsBean);
		logger.info("=========End =======Create BuyersTransaction======== buyerTransactionsBean.getTransactionId()=======");
		return buyerTransactionsBean.getTransactionId();

	}

}