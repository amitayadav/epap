package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "agent_owner")
public class AgentOwner implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer agentOwnerId;
	private LoginDetails loginDetailsByAgentLoginUserid;
	private LoginDetails loginDetailsByOwnerLoginUserid;
	private Short privileges;
	private String comments;
	private BigDecimal purchaseLimit;
	private BigDecimal limitSpent;

	public AgentOwner() {
	}

	public AgentOwner(Integer agentOwnerId) {
		this.agentOwnerId = agentOwnerId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "agent_owner_id", unique = true, nullable = false)
	public Integer getAgentOwnerId() {
		return this.agentOwnerId;
	}

	public void setAgentOwnerId(Integer agentOwnerId) {
		this.agentOwnerId = agentOwnerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_login_userid", nullable = false)
	public LoginDetails getLoginDetailsByAgentLoginUserid() {
		return this.loginDetailsByAgentLoginUserid;
	}

	public void setLoginDetailsByAgentLoginUserid(LoginDetails loginDetailsByAgentLoginUserid) {
		this.loginDetailsByAgentLoginUserid = loginDetailsByAgentLoginUserid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_login_userid", nullable = false)
	public LoginDetails getLoginDetailsByOwnerLoginUserid() {
		return this.loginDetailsByOwnerLoginUserid;
	}

	public void setLoginDetailsByOwnerLoginUserid(LoginDetails loginDetailsByOwnerLoginUserid) {
		this.loginDetailsByOwnerLoginUserid = loginDetailsByOwnerLoginUserid;
	}

	@Column(name = "privileges", nullable = false)
	public Short getPrivileges() {
		return this.privileges;
	}

	public void setPrivileges(Short privileges) {
		this.privileges = privileges;
	}

	@Column(name = "comments", length = 100)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "limit_spent")
	public BigDecimal getLimitSpent() {
		return limitSpent;
	}
	@Column(name = "purchase_limit")
	public BigDecimal getPurchaseLimit() {
		return purchaseLimit;
	}

	public void setPurchaseLimit(BigDecimal purchaseLimit) {
		this.purchaseLimit = purchaseLimit;
	}

	public void setLimitSpent(BigDecimal limitSpent) {
		this.limitSpent = limitSpent;
	}

}
