package com.auction.service;

import java.util.List;

import com.auction.model.bean.AccountTypeCodesBean;
import com.auction.service.generic.GenericService;

public interface AccountTypeCodesService extends GenericService<AccountTypeCodesBean, Character> {

	public List<AccountTypeCodesBean> getAllAccountTypeCodeExceptAdmin(Character[] accountTypes);
	
	public AccountTypeCodesBean getAccountTypeCodeByAccountId(Integer accountId);

}
