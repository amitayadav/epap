package com.auction.service;

import java.util.Date;
import java.util.List;

import com.auction.model.bean.ShipperBalanceBean;
import com.auction.model.views.AccountStatementsView;
import com.auction.service.generic.GenericService;

public interface ShipperBalanceService extends GenericService<ShipperBalanceBean, Integer> {

	public List<ShipperBalanceBean> getByBalanceBetweenDate(Integer accountId, Date startDate, Date endDate);

	public ShipperBalanceBean getShipperBalanceNew(Integer shipper_id);
	
	public List<AccountStatementsView> getShipperAccountStatementBetweenDate(Integer shipperId,java.sql.Date startDate, java.sql.Date endDate);

}