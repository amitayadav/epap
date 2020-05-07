package com.auction.service;

import java.util.List;

import com.auction.model.bean.AccountLocationsBean;
import com.auction.service.generic.GenericService;

public interface AccountLocationsService extends GenericService<AccountLocationsBean, Integer> {

	public List<AccountLocationsBean> findByAccountProfileAccountIdAndStatusIn(Integer accountId, Short[] status);

}