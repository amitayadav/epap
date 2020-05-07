package com.auction.service;

import java.math.BigDecimal;

import com.auction.model.bean.BankDetailsBean;
import com.auction.service.generic.GenericService;

public interface BankDetailsService extends GenericService<BankDetailsBean, Integer> {

	public BigDecimal getAvailableBalanceByAccountId(Integer accountId);

	public BigDecimal getAvailableBalanceOfOwnerByAccountId(String loginUserId);

}