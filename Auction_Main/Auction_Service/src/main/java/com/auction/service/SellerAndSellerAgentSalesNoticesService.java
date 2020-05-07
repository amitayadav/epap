package com.auction.service;


import java.util.List;

import com.auction.model.bean.SellerSalesNoticeBean;
import com.auction.model.views.SellerSalesNoticesView;
import com.auction.service.generic.GenericService;

public interface SellerAndSellerAgentSalesNoticesService extends GenericService<SellerSalesNoticeBean, Integer>{
	
	public List<SellerSalesNoticesView> getSellersalesNoticeRepor(Integer transactionId);
}
