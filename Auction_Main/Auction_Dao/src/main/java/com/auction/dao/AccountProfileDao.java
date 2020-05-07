package com.auction.dao;

import java.sql.Blob;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.AccountProfile;

@Repository
public interface AccountProfileDao extends GenericDao<AccountProfile, Integer> {

	@Query("SELECT ownerAP" + " FROM AgentOwner ao" + " INNER JOIN ao.loginDetailsByAgentLoginUserid agentLD" + " INNER JOIN agentLD.accountProfile agentAP"
			+ " INNER JOIN ao.loginDetailsByOwnerLoginUserid ownerLD" + " INNER JOIN ownerLD.accountProfile ownerAP" + " WHERE agentLD.accountProfile.accountId=:accountId")
	public AccountProfile findOwnerAccountProfileByAgentAccountId(@Param("accountId") Integer accountId);

	@Query("SELECT ownerAP.accountId" + " FROM AgentOwner ao" + " INNER JOIN ao.loginDetailsByAgentLoginUserid agentLD" + " INNER JOIN agentLD.accountProfile agentAP"
			+ " INNER JOIN ao.loginDetailsByOwnerLoginUserid ownerLD" + " INNER JOIN ownerLD.accountProfile ownerAP" + " WHERE agentLD.accountProfile.accountId=:accountId")
	public Integer findOwnerAccountIdByAgentAccountId(@Param("accountId") Integer accountId);

	@Query("SELECT ownerLD.publicName" + " FROM AgentOwner ao" + " INNER JOIN ao.loginDetailsByAgentLoginUserid agentLD" + " INNER JOIN agentLD.accountProfile agentAP"
			+ " INNER JOIN ao.loginDetailsByOwnerLoginUserid ownerLD" + " INNER JOIN ownerLD.accountProfile ownerAP" + " WHERE agentLD.accountProfile.accountId=:accountId")
	public String findOwnerPublicNameByAgentAccountId(@Param("accountId") Integer accountId);

	@Query("SELECT ld.accountProfile" + " FROM LoginDetails ld" + " INNER JOIN ld.accountProfile acp" + " INNER JOIN ld.accountTypeCodes atc"
			+ " WHERE atc.accountType IN (:accountTypeCodes)" + " ORDER BY acp.FName, acp.LName")
	public List<AccountProfile> findByAccountTypeCodes(@Param("accountTypeCodes") Character[] accountTypeCodes);

	@Query("SELECT a.contents FROM AccountProfile a WHERE a.profileImage =:profileImage and a.accountId=:accountId")
	public Blob getByEditProfileImage(@Param("profileImage") String profileImage, @Param("accountId") Integer accountId);

	@Modifying
	@Query("UPDATE AccountProfile SET rating = :rating , ratingTotal = :ratingTotal , numOfRatings = :numOfRatings  WHERE accountId = :accountId")
	public void updateRatingByAccountProflieId(@Param("rating") Float rating, @Param("ratingTotal") Integer ratingTotal, @Param("numOfRatings") Integer numOfRatings,
			@Param("accountId") Integer accountId);

	@Query("SELECT a.otp =:otp  FROM AccountProfile a WHERE a.accountId=:accountId")
	public Boolean checkValidateOtpByAccountProfiledId(@Param("accountId") Integer accountId, @Param("otp") Integer otp);
}