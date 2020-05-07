package com.auction.service;

import java.util.Date;
import java.util.List;
import com.auction.model.bean.BuyerBalanceBean;
import com.auction.model.views.AccountStatementsView;
import com.auction.model.views.PurchaseInvoicesView;
import com.auction.service.generic.GenericService;

public interface BuyerBalanceService extends GenericService<BuyerBalanceBean, Integer> {
	public List<BuyerBalanceBean> getByBalanceBetweenDate(Integer accountId, Date startDate, Date endDate);
	
	public BuyerBalanceBean getBuyerBalanceNew(Integer buyer_id);
	
	public List<AccountStatementsView> getBuyerAccountStatementBetweenDate(Integer buyerId,java.sql.Date startDate, java.sql.Date endDate);
	
	public List<PurchaseInvoicesView> getBuyerPurchaseInvoicesBetweenDate(Integer buyerId,java.sql.Date startDate, java.sql.Date endDate ,char p);
}
