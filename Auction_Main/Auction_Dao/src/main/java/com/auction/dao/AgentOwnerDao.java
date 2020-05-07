package com.auction.dao;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.AgentOwner;

@Repository
public interface AgentOwnerDao extends GenericDao<AgentOwner, Integer> {

	@Query("SELECT a FROM AgentOwner a INNER JOIN a.loginDetailsByAgentLoginUserid ld WHERE ld.loginUserid = :loginUserid ")
	public AgentOwner getAgentOwnerByLoginUserId(@Param("loginUserid") String loginUserid);

	@Query("SELECT a FROM AgentOwner a INNER JOIN a.loginDetailsByOwnerLoginUserid ld WHERE ld.loginUserid = :loginUserid ")
	public List<AgentOwner> getAgentByOwnerLoginUserId(@Param("loginUserid") String loginUserid);

	@Query("SELECT (count(a.agentOwnerId) > 0)" + " FROM AgentOwner a" + " WHERE a.loginDetailsByAgentLoginUserid.loginUserid = :loginUserid" + " AND a.privileges = :privileges")
	public Boolean checkAgentByPrivilegeAndLoginUserId(@Param("loginUserid") String loginUserid, @Param("privileges") Short privileges);

	@Query("SELECT a FROM AgentOwner a WHERE a.loginDetailsByAgentLoginUserid.loginUserid = :loginUserid")
	public AgentOwner getPurchaseLimitOfAgentByAccountId(@Param("loginUserid") String loginUserid);
}
