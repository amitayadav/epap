package com.auction.service;
import java.util.List;
import com.auction.model.bean.AgentOwnerBean;
import com.auction.service.generic.GenericService;

public interface AgentOwnerService extends GenericService<AgentOwnerBean, Integer> {

	public AgentOwnerBean getAgentOwnerByLoginUserId(String loginUserid);

	public List<AgentOwnerBean> getAgentByOwnerLoginUserId(String loginUserid);

	public Boolean checkAgentByPrivilegeAndLoginUserId(String loginUserid, Short privileges);

	public AgentOwnerBean getPurchaseLimitOfAgentByAccountId(String loginUserid);
}
