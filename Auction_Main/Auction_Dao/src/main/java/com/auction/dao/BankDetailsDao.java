package com.auction.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.BankDetails;

@Repository
public interface BankDetailsDao extends GenericDao<BankDetails, Integer>{

	@Query("SELECT bd.availableBalance FROM BankDetails bd WHERE bd.accountId=:accountId")
	public BigDecimal getAvailableBalanceByAccountId(@Param("accountId") Integer accountId);
	
	
	@Query(value = "SELECT bd.available_balance" + 
			" FROM agent_owner ao" + 
			" INNER JOIN login_details ld ON ld.login_userid = ao.owner_login_userid" + 
			" INNER JOIN account_profile ap ON ap.account_id = ld.account_id" + 
			" INNER JOIN bank_details bd ON bd.account_id = ap.account_id" + 
			" WHERE ao.agent_login_userid=:loginUserId", nativeQuery = true)
	public BigDecimal getAvailableBalanceOfOwnerByAccountId(@Param("loginUserId") String loginUserId);
}