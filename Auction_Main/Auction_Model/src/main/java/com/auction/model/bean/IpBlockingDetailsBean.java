package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.IpBlockingDetails;

public class IpBlockingDetailsBean {

	private String ip;
	private Integer noOfBlocking;
	private Date blockingTimestamp;

	public IpBlockingDetailsBean() {

	}

	public IpBlockingDetailsBean(String ip) {
		this.ip = ip;
	}

	public IpBlockingDetailsBean(IpBlockingDetails entity) {
		this.ip = entity.getIp();
		this.noOfBlocking = entity.getNoOfBlocking();
		this.blockingTimestamp = entity.getBlockingTimestamp();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getNoOfBlocking() {
		return noOfBlocking;
	}

	public void setNoOfBlocking(Integer noOfBlocking) {
		this.noOfBlocking = noOfBlocking;
	}

	public Date getBlockingTimestamp() {
		return blockingTimestamp;
	}

	public void setBlockingTimestamp(Date blockingTimestamp) {
		this.blockingTimestamp = blockingTimestamp;
	}

}