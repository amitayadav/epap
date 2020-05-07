package com.auction.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ip_blocking_details")
public class IpBlockingDetails implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String ip;
	private Integer noOfBlocking;
	private Date blockingTimestamp;

	public IpBlockingDetails() {
	}

	public IpBlockingDetails(String ip) {
		this.ip = ip;
	}

	@Id
	@Column(name = "ip", unique = true, nullable = false, length = 25)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "no_of_blocking", nullable = false)
	public Integer getNoOfBlocking() {
		return this.noOfBlocking;
	}

	public void setNoOfBlocking(Integer noOfBlocking) {
		this.noOfBlocking = noOfBlocking;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "blocking_timestamp", nullable = false, length = 19)
	public Date getBlockingTimestamp() {
		return this.blockingTimestamp;
	}

	public void setBlockingTimestamp(Date blockingTimestamp) {
		this.blockingTimestamp = blockingTimestamp;
	}

}