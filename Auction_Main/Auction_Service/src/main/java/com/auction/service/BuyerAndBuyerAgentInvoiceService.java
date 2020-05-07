package com.auction.service;


import java.util.List;

import com.auction.model.bean.BuyerPurchaseInvoiceBean;
import com.auction.model.views.PurchaseInvoicesView;
import com.auction.service.generic.GenericService;

public interface BuyerAndBuyerAgentInvoiceService extends GenericService<BuyerPurchaseInvoiceBean, Integer> {

	
	public List<PurchaseInvoicesView> getBuyerPurchaseInvoicesReport(Integer transactionId);
}
