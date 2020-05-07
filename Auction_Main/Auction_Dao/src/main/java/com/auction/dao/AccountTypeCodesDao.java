package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.AccountTypeCodes;

@Repository
public interface AccountTypeCodesDao extends GenericDao<AccountTypeCodes, Character> {

	@Query("SELECT a FROM AccountTypeCodes a WHERE not accountType NOT IN (:accountTypes) ORDER BY accountTypeMeaning ASC")
	public List<AccountTypeCodes> getAllAccountTypeCodeExceptAdmin(@Param("accountTypes") Character[] accountTypes);

	@Query(" SELECT atc FROM LoginDetails ld" + 
		   " INNER JOIN ld.accountProfile ap" + 
		   " INNER JOIN ld.accountTypeCodes atc" + 
		   " WHERE ap.accountId=:accountId")
	public AccountTypeCodes getAccountTypeCodeByAccountId(@Param("accountId") Integer accountId);
	
}