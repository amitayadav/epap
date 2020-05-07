package com.auction.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.auction.model.bean.EpapBalanceBean;
import com.auction.service.generic.GenericService;

public interface EpapService extends GenericService<EpapBalanceBean, Integer> {
	public BigDecimal getLastEpapBalance();
	
	List<EpapBalanceBean> getEpapBalanceBeanBetweenDate(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);
}
