package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.CurrencyCodes;

@Repository
public interface CurrencyCodesDao extends GenericDao<CurrencyCodes, String> {

	@Query("SELECT cc FROM CurrencyCodes cc ORDER BY cc.currencyName")
	public List<CurrencyCodes> getCurrencyCodesOrderBycurrencyName();
}
