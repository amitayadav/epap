package com.auction.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.util.AuctionLogger;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.BuyerPurchaseInvoiceBean;
import com.auction.model.bean.BuyerTransactionsBean;
import com.auction.service.BuyerAndBuyerAgentInvoiceService;
@Component
public class PurchaseInvoicesHelper {

	@Autowired
	private BuyerAndBuyerAgentInvoiceService buyerAndBuyerAgentInvoiceService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	public void createBuyerPurchaseInvoices(BuyerTransactionsBean buyerTransactionsBean) {
		logger.info("PurchaseInvoicesHelper Call createBuyerPurchaseInvoices metohd  ");
		BuyerPurchaseInvoiceBean buyerPurchaseInvoiceBean =new BuyerPurchaseInvoiceBean();
		buyerPurchaseInvoiceBean.setBuyerTransactionsBean(buyerTransactionsBean);
		buyerPurchaseInvoiceBean.setInvoiceCreation(buyerTransactionsBean.getTransactionCreation());
		buyerPurchaseInvoiceBean.setSellerPublicName(buyerTransactionsBean.getAuctionBuyersBean().getAuctionSellersBean().getAccountProfileBean().getPublicName());
		buyerPurchaseInvoiceBean.setBuyerPublicName(buyerTransactionsBean.getAccountProfileBean().getPublicName());
		AccountProfileBean accountProfileBean=buyerTransactionsBean.getAuctionBuyersBean().getShipperAccountProfileBean();
		buyerPurchaseInvoiceBean.setBuyerShipperPublicName(null!=accountProfileBean?accountProfileBean.getPublicName():"");
		buyerAndBuyerAgentInvoiceService.save(buyerPurchaseInvoiceBean);
		logger.info("=== ending  createBuyerPurchaseInvoices metohd  ");
	}
	
}
