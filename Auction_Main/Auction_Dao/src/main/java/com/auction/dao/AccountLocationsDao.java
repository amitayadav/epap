package com.auction.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.auction.model.entity.AccountLocations;

@Repository
public interface AccountLocationsDao extends GenericDao<AccountLocations, Integer> {

	public List<AccountLocations> findByAccountProfileAccountIdAndStatusIn(Integer accountId, Short[] status);

}