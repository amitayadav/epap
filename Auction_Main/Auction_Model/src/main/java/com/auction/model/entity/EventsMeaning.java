package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events_meaning")
public class EventsMeaning implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer eventId;
	private String eventMeaning;

	public EventsMeaning() {
	}

	public EventsMeaning(String eventMeaning) {
		this.eventMeaning = eventMeaning;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_id", unique = true, nullable = false)
	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	@Column(name = "event_meaning", nullable = false)
	public String getEventMeaning() {
		return this.eventMeaning;
	}

	public void setEventMeaning(String eventMeaning) {
		this.eventMeaning = eventMeaning;
	}

}
