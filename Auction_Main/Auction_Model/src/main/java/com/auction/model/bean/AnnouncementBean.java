package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.Announcement;


public class AnnouncementBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer announcementId;
	private AccountProfileBean addedBy;
	private AccountProfileBean modifiedBy;
	private String announcement;
	private Date addedtimestamp;
	private Date modifiedimestamp;


	public AnnouncementBean() {
	}

	public AnnouncementBean(Integer announcementId) {
		this.announcementId = announcementId;
	}

	public AnnouncementBean(Announcement entity) {

		if (null != entity) {
			this.announcementId = entity.getAnnouncementId();
			this.addedBy = new AccountProfileBean(entity.getAddedBy());
			this.modifiedBy =new AccountProfileBean(entity.getModifiedBy());
			this.announcement=(entity.getAnnouncement());
			this.addedtimestamp = entity.getAddedtimestamp();
			this.modifiedimestamp = entity.getModifiedimestamp();

		}

	}

	public Integer getAnnouncementId() {
		return announcementId;
	}


	public void setAnnouncementId(Integer announcementId) {
		this.announcementId = announcementId;
	}
	
	public AccountProfileBean getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(AccountProfileBean addedBy) {
		this.addedBy = addedBy;
	}

	public AccountProfileBean getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(AccountProfileBean modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public Date getAddedtimestamp() {
		return addedtimestamp;
	}

	public void setAddedtimestamp(Date addedtimestamp) {
		this.addedtimestamp = addedtimestamp;
	}
	public Date getModifiedimestamp() {
		return modifiedimestamp;
	}

	public void setModifiedimestamp(Date modifiedimestamp) {
		this.modifiedimestamp = modifiedimestamp;
	}


	
	}

	

