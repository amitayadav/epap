package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.AccountStatusCodes;

@Repository
public interface AccountStatusCodesDao extends GenericDao<AccountStatusCodes, Short> {

	@Query("SELECT ac FROM AccountStatusCodes ac WHERE ac.accountStatusCode IN (:codeList) ORDER BY ac.accountStatusCode asc")
	public List<AccountStatusCodes> findByAccountStatusCodes(@Param("codeList") Short[] codeList);

	@Query("SELECT ac FROM AccountStatusCodes ac WHERE ac.accountStatusCode NOT IN (:codeList) ORDER BY ac.accountStatusCode asc")
	public List<AccountStatusCodes> findByNotInAccountStatusCodes(@Param("codeList") Short[] codeList);

	public List<AccountStatusCodes> findAllByOrderByAccountStatusCodeAsc();

}