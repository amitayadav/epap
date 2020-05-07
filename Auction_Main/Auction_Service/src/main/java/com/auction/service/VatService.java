package com.auction.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.auction.model.bean.VatBalanceBean;
import com.auction.service.generic.GenericService;

public interface VatService extends GenericService<VatBalanceBean, Integer> {
	public BigDecimal getLastVatBalance();
	
	public List<VatBalanceBean> getVatBalanceBeanBetweenDate(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);
}
