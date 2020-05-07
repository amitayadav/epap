package com.auction.service;

import java.util.List;

import com.auction.model.bean.RoyaltyCodesBean;
import com.auction.service.generic.GenericService;

public interface RoyaltyCodeService extends GenericService<RoyaltyCodesBean, Short> {

	public List<RoyaltyCodesBean> getRoyaltyCodesOrderByRoyaltyValue();

	public RoyaltyCodesBean findByRoyaltyCodeAndRoyaltyValue(Short royaltyCode, Float royaltyValue);

	public Float findByRoyaltyCodeByAccountProfileId(Integer accountId);
}