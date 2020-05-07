package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.RoyaltyCodes;

@Repository
public interface RoyaltyCodeDao extends GenericDao<RoyaltyCodes, Short> {

	@Query("SELECT rc FROM RoyaltyCodes rc ORDER BY rc.royaltyValue")
	public List<RoyaltyCodes> getRoyaltyCodesOrderByRoyaltyValue();

	@Query("SELECT rc FROM RoyaltyCodes rc WHERE rc.royaltyValue = :royaltyValue and rc.royaltyCode <> :royaltyCode")
	public RoyaltyCodes findByRoyaltyCodeAndRoyaltyValue(@Param("royaltyCode") Short royaltyCode, @Param("royaltyValue") Float royaltyValue);

	@Query("SELECT rc.royaltyValue FROM AccountProfile ap INNER JOIN ap.royaltyCodes rc WHERE ap.accountId = :accountId")
	public Float findByRoyaltyCodeByAccountProfileId(@Param("accountId") Integer accountId);

}