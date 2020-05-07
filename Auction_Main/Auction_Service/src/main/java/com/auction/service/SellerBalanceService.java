package com.auction.service;

import java.util.Date;
import java.util.List;

import com.auction.model.bean.SellerBalanceBean;
import com.auction.model.views.AccountStatementsView;
import com.auction.model.views.SellerSalesNoticesView;
import com.auction.service.generic.GenericService;

public interface SellerBalanceService extends GenericService<SellerBalanceBean, Integer> {

	public List<SellerBalanceBean> getByAccountProfile(Integer accountId);

	public List<SellerBalanceBean> getByBalanceByAccountProfile(Integer accountId);

	public List<SellerBalanceBean> getByBalanceBetweenDate(Integer accountId, Date startDate, Date endDate);

	public SellerBalanceBean getSellerBalanceNew(Integer seller_id);
	
	public List<AccountStatementsView> getSellerAccountStatementBetweenDate(Integer sellerId,java.sql.Date startDate, java.sql.Date endDate);
	
	public List<SellerSalesNoticesView> getSellerSalesNoticesBetweenDate(Integer sellerId,char transactionCode,java.sql.Date startDate, java.sql.Date endDate);
	
}