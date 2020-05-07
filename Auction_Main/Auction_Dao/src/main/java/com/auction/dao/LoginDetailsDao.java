package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.LoginDetails;

@Repository
public interface LoginDetailsDao extends GenericDao<LoginDetails, String> {

	@Query("SELECT COUNT(i.publicName) FROM LoginDetails i WHERE i.publicName = :publicName")
	public Integer isPublicNameUnique(@Param("publicName") String publicName);

	@Query("SELECT COUNT(i.loginUserid) FROM LoginDetails i WHERE i.loginUserid = :primaryEmail")
	public Integer isPrimaryEmailUnique(@Param("primaryEmail") String primaryEmail);

	@Modifying
	@Query("UPDATE LoginDetails ld SET ld.accountProfile.accountId = :account_id WHERE ld.loginUserid = :loginUserId")
	public void updateLoginUserByAccountId(@Param("loginUserId") String loginUserId, @Param("account_id") Integer account_id);

	@Query("SELECT l FROM LoginDetails l INNER JOIN l.accountTypeCodes atype WHERE atype.accountType IN (:accountTypes)")
	public List<LoginDetails> getLoginDetailsListByAccountTypes(@Param("accountTypes") Character[] accountTypes);

	@Query("SELECT ld FROM LoginDetails ld INNER JOIN ld.accountProfile ap INNER JOIN ap.royaltyCodes rc WHERE rc.royaltyCode=:royaltyCode")
	public List<LoginDetails> getLoginDetailsByRoyaltyCode(@Param("royaltyCode") Short royaltyCode);

	@Query("SELECT ld FROM LoginDetails ld INNER JOIN ld.accountProfile ap WHERE ap.accountId=:accountId")
	public LoginDetails getLoginDetailsByAccountProfileId(@Param("accountId") Integer accountId);

	@Query("SELECT l FROM LoginDetails l INNER JOIN l.accountTypeCodes atype WHERE atype.accountType=:accountType")
	public LoginDetails  getLoginDetailsByAccountProfile(@Param("accountType") Character accountType);
	
	@Query("SELECT ld FROM LoginDetails ld INNER JOIN ld.accountTypeCodes atc WHERE atc.accountType IN (:typeList)")
	public List<LoginDetails> findByAccountTypeCodesIn(@Param("typeList") List<Character> typeList);

	public List<LoginDetails> findByAccountStatusCodesAccountStatusCodeAndLoginUseridNot(Short active, String loginUserid);

	public List<LoginDetails> findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeInAndLoginUseridNot(Short active, Character[] accountTypes,
			String loginUserid);

	public List<LoginDetails> findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeIn(Short active, Character[] accountTypes);
	
	public List<LoginDetails> findByAccountStatusCodesAccountStatusCodeInAndLoginUserid(Short[] accountStatusCodeList, String loginUserid);

	@Query("SELECT ownerLD FROM AgentOwner ao INNER JOIN ao.loginDetailsByAgentLoginUserid agentLD INNER JOIN ao.loginDetailsByOwnerLoginUserid ownerLD WHERE agentLD.loginUserid=:loginUserid")
	public LoginDetails findOwnerLoginDetailsByAgentLoginUserId(@Param("loginUserid") String loginUserid);
	
	@Query("SELECT ld" + 
		" FROM LoginDetails ld" + 
		" INNER JOIN AccountProfile ap ON ap.accountId=ld.accountProfile" + 
		" INNER JOIN ld.accountStatusCodes ascode" + 
		" INNER JOIN ld.accountTypeCodes atcode" + 
		" WHERE ascode.accountStatusCode IN :accountStatusCodeList AND atcode.accountType IN :accountTypeCodeList" + 
		" AND ld.loginUserid != :loginUserId" + 
		" AND ld.loginUserid NOT IN" + 
		" (SELECT ao1.loginDetailsByOwnerLoginUserid.loginUserid FROM AgentOwner ao1" + 
		" WHERE ao1.loginDetailsByAgentLoginUserid.loginUserid=:loginUserId)" + 
		" AND ld.loginUserid NOT IN" + 
		" (SELECT ao1.loginDetailsByAgentLoginUserid.loginUserid FROM AgentOwner ao1" + 
		" WHERE ao1.loginDetailsByOwnerLoginUserid.loginUserid=:loginUserId) " + 
		" AND ld.loginUserid NOT IN" + 
		" (SELECT ao1.loginDetailsByAgentLoginUserid.loginUserid FROM AgentOwner ao1" + 
		" WHERE ao1.loginDetailsByOwnerLoginUserid.loginUserid=(SELECT ao1.loginDetailsByOwnerLoginUserid.loginUserid FROM AgentOwner ao1" + 
		" WHERE ao1.loginDetailsByAgentLoginUserid.loginUserid=:loginUserId))" + 
		" ORDER BY ap.FName, ap.LName")
	public List<LoginDetails> getComplaintUsers(@Param("accountStatusCodeList") Short[] accountStatusCodeList, @Param("accountTypeCodeList") Character[] accountTypeCodeList, @Param("loginUserId") String loginUserId);	


	
}