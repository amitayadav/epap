package com.auction.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.util.AuctionLogger;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.SellerSalesNoticeBean;
import com.auction.model.bean.SellerTransactionsBean;
import com.auction.service.SellerAndSellerAgentSalesNoticesService;

@Component
public class SalesNoticesHelper {

	@Autowired
	private SellerAndSellerAgentSalesNoticesService  sellerAndSellerAgentSalesNoticesService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	public void createSellerSalesNotices(SellerTransactionsBean sellerTransactionsBean) {
		logger.info("SalesNoticesHelper call createSellerSalesNotices method");
		SellerSalesNoticeBean sellerSalesNoticeBean =new SellerSalesNoticeBean();
		
		sellerSalesNoticeBean.setSellerTransactionsBean(sellerTransactionsBean);
		sellerSalesNoticeBean.setNoticeCreation(sellerTransactionsBean.getTransactionCreation());
		sellerSalesNoticeBean.setSellerPublicName(sellerTransactionsBean.getAccountProfileBean().getPublicName());
		sellerSalesNoticeBean.setBuyerPublicName(sellerTransactionsBean.getAuctionBuyersBean().getAccountProfileBean().getPublicName());
		AccountProfileBean shipperAccountProfileBean = sellerTransactionsBean. getAuctionBuyersBean().getAuctionSellersBean().getShipperAccountProfileBean();
		sellerSalesNoticeBean.setSellerShipperPublicName(null!=shipperAccountProfileBean?shipperAccountProfileBean.getPublicName():"");
		sellerAndSellerAgentSalesNoticesService.save(sellerSalesNoticeBean);
		logger.info("=== ending  createSellerSalesNotices method ===");
	}
}
