package com.auction.service;

import java.util.List;

import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.generic.GenericService;

public interface LoginDetailsService extends GenericService<LoginDetailsBean, String> {

	public Boolean isPublicNameUnique(String publicName);

	public Boolean isPrimaryEmailUnique(String primaryEmail);

	public void updateLoginUserByAccountId(String loginUserId, Integer account_id);

	public List<LoginDetailsBean> getLoginDetailsListByAccountTypes(Character[] accountTypes);

	public List<LoginDetailsBean> getLoginDetailsByRoyaltyCode(Short royaltyCode);

	public LoginDetailsBean getLoginDetailsByAccountProfileId(Integer accountId);

	public LoginDetailsBean   getLoginDetailsByAccountProfile(Character accountType);
	
	public List<LoginDetailsBean> findByAccountTypeCodesIn(List<Character> typeList);

	public List<LoginDetailsBean> findByAccountStatusCodesAccountStatusCodeAndLoginUseridNot(Short status, String loginUserid);

	public List<LoginDetailsBean> findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeInAndLoginUseridNot(Short active, Character[] accountTypes,
			String loginUserid);

	public List<LoginDetailsBean> findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeIn(Short status, Character[] accountTypes);

	public List<LoginDetailsBean> findByAccountStatusCodesAccountStatusCodeInAndLoginUserid(Short[] accountStatusCodeList, String loginUserid);

	public LoginDetailsBean findOwnerLoginDetailsByAgentLoginUserId(String loginUserid);

	public List<LoginDetailsBean> getComplaintUsers(Short[] accountStatusCodeList, Character[] accountTypeCodeList, String loginUserid);

	
}