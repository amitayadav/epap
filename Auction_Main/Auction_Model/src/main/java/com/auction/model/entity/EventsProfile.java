package com.auction.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "events_profile")
public class EventsProfile implements java.io.Serializable {

	private static final long serialVersionUID = -2733506282945232444L;
	private Integer accountId;
	private AccountProfile accountProfile;
	private String eventMap;

	public EventsProfile() {
	}

	public EventsProfile(Integer accountId) {
		this.accountId = accountId;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "accountProfile"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "account_id", unique = true, nullable = false)
	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@Column(name = "event_map", length = 16777215)
	public String getEventMap() {
		return this.eventMap;
	}

	public void setEventMap(String eventMap) {
		this.eventMap = eventMap;
	}

}
