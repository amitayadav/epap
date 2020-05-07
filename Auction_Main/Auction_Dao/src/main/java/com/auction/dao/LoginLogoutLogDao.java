package com.auction.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.LoginLogoutLog;

@Repository
public interface LoginLogoutLogDao extends GenericDao<LoginLogoutLog, String> {

	@Query("SELECT lll FROM LoginLogoutLog lll INNER JOIN lll.id.loginDetails ld WHERE ld.loginUserid = :loginUserid AND lll.id.loginTimestamp = :loginTimestamp")
	public List<LoginLogoutLog> getByAccountIdAndLoginTimestamp(@Param("loginUserid") String loginUserid,
			@Param("loginTimestamp") @Temporal(TemporalType.TIMESTAMP) Date loginTimestamp);

	@Modifying
	@Query(value = "UPDATE login_logout_log SET logout_timestamp=?1 WHERE login_userid=?2 AND login_timestamp=?3", nativeQuery = true)
	public void updateLogoutTime(String logoutTimestamp, String loginUserid, String loginTimestamp);

	@Query(value ="SELECT * FROM login_logout_log  where login_userid=:loginUserid order by login_timestamp desc limit 1", nativeQuery = true)
	public  LoginLogoutLog getLogoutTimestampByAccountId(@Param("loginUserid") String loginUserid);
}