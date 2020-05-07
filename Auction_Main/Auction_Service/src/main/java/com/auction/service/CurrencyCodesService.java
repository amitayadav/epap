package com.auction.service;

import java.util.List;

import com.auction.model.bean.CurrencyCodesBean;
import com.auction.service.generic.GenericService;

public interface CurrencyCodesService extends GenericService<CurrencyCodesBean, String> {

	public List<CurrencyCodesBean> getCurrencyCodesOrderBycurrencyName();
}
