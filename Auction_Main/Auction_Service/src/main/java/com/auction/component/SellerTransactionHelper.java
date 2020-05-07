package com.auction.component;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.SellerTransactionsBean;
import com.auction.service.SellerTransactionsService;

@Component
public class SellerTransactionHelper {

	@Autowired
	private SellerTransactionsService sellerTransactionsService;

	@Autowired
	private EpapBalanceHelper epapBalanceHelper;

	@Autowired
	private VatBalanceHelper vatBalanceHelper;
	
	@Autowired
	private SalesNoticesHelper salesNoticesHelper;
	
	private AuctionLogger logger = new AuctionLogger(getClass().getName());
	
	public Integer createSellersTransaction(AuctionSellersBean auctionSellersBean, AuctionBuyersBean auctionBuyersBean) {
		logger.info("=========Start =======CreateS ellersTransaction===============");
		SellerTransactionsBean sellerTransactionsBean = new SellerTransactionsBean();
		float executedPrice = auctionBuyersBean.getExecutedPrice();
		logger.info("executedPrice =:"+executedPrice);
		int executedQuantity = auctionBuyersBean.getExecutedQuantity();
		logger.info("executedQuantity =:"+executedQuantity);
		BigDecimal grossSale = new BigDecimal((executedPrice * executedQuantity));
		logger.info("grossSale executedPrice * executedQuantity =:"+grossSale);
		BigDecimal netSales = grossSale;
		netSales = netSales.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal royaltyAmount = (netSales.multiply(new BigDecimal(auctionSellersBean.getRoyaltyValue())).divide(new BigDecimal(100)));
		royaltyAmount = royaltyAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal vatAmount = (netSales.multiply(new BigDecimal(auctionSellersBean.getVat())).divide(new BigDecimal(100)));
		vatAmount = vatAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		netSales = netSales.subtract(royaltyAmount);
		netSales = netSales.subtract(vatAmount);
		netSales = netSales.subtract(new BigDecimal (auctionSellersBean.getSellerShippingCharge()));
		netSales = netSales.setScale(2, BigDecimal.ROUND_HALF_UP);

		sellerTransactionsBean.setAccountProfileBean(auctionSellersBean.getAccountProfileBean());
		sellerTransactionsBean.setAuctionBuyersBean(auctionBuyersBean);
		sellerTransactionsBean.setSellQuantity(auctionBuyersBean.getExecutedQuantity());
		sellerTransactionsBean.setSellPrice(executedPrice);
		sellerTransactionsBean.setGrossSale(grossSale);
		sellerTransactionsBean.setRoyaltyPercentage(auctionSellersBean.getRoyaltyValue());
		sellerTransactionsBean.setRoyaltyAmount(royaltyAmount);
		sellerTransactionsBean.setVatPercentage(auctionSellersBean.getVat());
		sellerTransactionsBean.setVatAmount(vatAmount);
		sellerTransactionsBean.setSellerShippingCharge(auctionSellersBean.getSellerShippingCharge());
		sellerTransactionsBean.setNetSales(netSales);
		sellerTransactionsBean.setTransactionCreation(InternetTiming.getInternetDateTime());
		sellerTransactionsService.save(sellerTransactionsBean);
		logger.info("sellerTransactionsBean Executed Quantity =:"+auctionBuyersBean.getExecutedQuantity());
		logger.info("sellerTransactionsBean ExecutedPrice =:"+executedPrice);
		logger.info("sellerTransactionsBean GrossSale =:"+grossSale);
		logger.info("sellerTransactionsBean Royalty Value =:"+auctionSellersBean.getRoyaltyValue());
		logger.info("sellerTransactionsBean Royalty Amount =:"+royaltyAmount);
		logger.info("sellerTransactionsBean Vat =:"+auctionSellersBean.getVat());
		logger.info("sellerTransactionsBean Vat Amount =:"+vatAmount);
		logger.info("sellerTransactionsBean SellerShippingCharge =:"+auctionSellersBean.getSellerShippingCharge());
		logger.info("sellerTransactionsBean NetSales =:"+netSales);
		logger.info("=========End =======CreateS ellersTransaction===============");
		epapBalanceHelper.createSellerCommissionCredit(sellerTransactionsBean,royaltyAmount);
		vatBalanceHelper.createSellerVatCredit(sellerTransactionsBean, vatAmount);
		salesNoticesHelper.createSellerSalesNotices(sellerTransactionsBean);
		return sellerTransactionsBean.getTransactionId();
	}
}