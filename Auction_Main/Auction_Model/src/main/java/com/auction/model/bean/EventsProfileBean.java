package com.auction.model.bean;

import com.auction.model.entity.EventsProfile;

public class EventsProfileBean {

	private Integer accountId;
	private AccountProfileBean accountProfilebean;
	private String eventMap;

	public EventsProfileBean() {
	}

	public EventsProfileBean(EventsProfile entity) {
		if (null != entity) {
			this.accountId = entity.getAccountId();
			this.accountProfilebean = new AccountProfileBean(entity.getAccountProfile());
			this.eventMap = entity.getEventMap();
		}
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public AccountProfileBean getAccountProfilebean() {
		return accountProfilebean;
	}

	public void setAccountProfilebean(AccountProfileBean accountProfilebean) {
		this.accountProfilebean = accountProfilebean;
	}

	public String getEventMap() {
		return eventMap;
	}

	public void setEventMap(String eventMap) {
		this.eventMap = eventMap;
	}

}
