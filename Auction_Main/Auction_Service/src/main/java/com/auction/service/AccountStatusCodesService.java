package com.auction.service;

import java.util.List;

import com.auction.model.bean.AccountStatusCodesBean;
import com.auction.service.generic.GenericService;

public interface AccountStatusCodesService extends GenericService<AccountStatusCodesBean, Short> {

	public List<AccountStatusCodesBean> findByAccountStatusCodes(Short[] codeList);
	
	public List<AccountStatusCodesBean> findAllByOrderByAccountStatusCodeAsc();
	
	public List<AccountStatusCodesBean> findByNotInAccountStatusCodes(Short[] codeList);
	
}