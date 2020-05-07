package com.auction.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.IpBlockingDetails;

@Repository
public interface IpBlockingDetailsDao extends GenericDao<IpBlockingDetails, String> {

	@Query("SELECT ip FROM IpBlockingDetails ip WHERE ip.ip=:ip AND ip.noOfBlocking>=:maxFailCount")
	public IpBlockingDetails isBlocked(@Param("ip") String ip, @Param("maxFailCount") Integer maxFailCount);

}