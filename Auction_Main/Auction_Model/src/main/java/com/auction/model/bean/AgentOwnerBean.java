package com.auction.model.bean;

import java.math.BigDecimal;

public class AgentOwnerBean {

	private Integer agentOwnerId;
	private LoginDetailsBean loginDetailsByAgentLoginUserid;
	private LoginDetailsBean loginDetailsByOwnerLoginUserid;
	private Short privileges;
	private String comments;
	private BigDecimal purchaseLimit;
	private BigDecimal limitSpent;

	public AgentOwnerBean() {
	}

	public AgentOwnerBean(Integer agentOwnerId) {
		this.agentOwnerId = agentOwnerId;
	}

	public Integer getAgentOwnerId() {
		return agentOwnerId;
	}

	public void setAgentOwnerId(Integer agentOwnerId) {
		this.agentOwnerId = agentOwnerId;
	}

	public LoginDetailsBean getLoginDetailsByAgentLoginUserid() {
		return loginDetailsByAgentLoginUserid;
	}

	public void setLoginDetailsByAgentLoginUserid(LoginDetailsBean loginDetailsByAgentLoginUserid) {
		this.loginDetailsByAgentLoginUserid = loginDetailsByAgentLoginUserid;
	}

	public LoginDetailsBean getLoginDetailsByOwnerLoginUserid() {
		return loginDetailsByOwnerLoginUserid;
	}

	public void setLoginDetailsByOwnerLoginUserid(LoginDetailsBean loginDetailsByOwnerLoginUserid) {
		this.loginDetailsByOwnerLoginUserid = loginDetailsByOwnerLoginUserid;
	}

	public Short getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Short privileges) {
		this.privileges = privileges;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BigDecimal getPurchaseLimit() {
		return purchaseLimit;
	}

	public void setPurchaseLimit(BigDecimal purchaseLimit) {
		this.purchaseLimit = purchaseLimit;
	}

	public BigDecimal getLimitSpent() {
		return limitSpent;
	}

	public void setLimitSpent(BigDecimal limitSpent) {
		this.limitSpent = limitSpent;
	}



}
