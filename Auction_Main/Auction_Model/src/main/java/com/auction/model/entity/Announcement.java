package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name ="announcement")
public class Announcement implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer announcementId;
	private AccountProfile addedBy;
	private AccountProfile modifiedBy;
	private String announcement;
	private Date addedtimestamp;
	private Date modifiedimestamp;
		public Announcement() {
			
		}

		public Announcement(Integer announcementId) {
			this.announcementId = announcementId;
			
		}
		
		@Id
		@GeneratedValue(strategy = IDENTITY)
		@Column(name = "announcements_id", unique = true, nullable = false)
		public Integer getAnnouncementId() {
			return announcementId;
		}


		public void setAnnouncementId(Integer announcementId) {
			this.announcementId = announcementId;
		}

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "added_by" , nullable = false)
		public AccountProfile getAddedBy() {
			return addedBy;
		}


		public void setAddedBy(AccountProfile addedBy) {
			this.addedBy = addedBy;
		}

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "modified_by")
		public AccountProfile getModifiedBy() {
			return modifiedBy;
		}


		public void setModifiedBy(AccountProfile modifiedBy) {
			this.modifiedBy = modifiedBy;
		}

		@Column(name ="announcement", nullable = false , length = 1024)
		public String getAnnouncement() {
			return announcement;
		}


		public void setAnnouncement(String announcement) {
			this.announcement = announcement;
		}

		@Temporal(TemporalType.TIMESTAMP)
		@Column(name ="added_by_timestamp",nullable = false)
		public Date getAddedtimestamp() {
			return addedtimestamp;
		}


		public void setAddedtimestamp(Date addedtimestamp) {
			this.addedtimestamp = addedtimestamp;
		}

		@Temporal(TemporalType.TIMESTAMP)
		@Column(name ="modified_by_timestamp")
		public Date getModifiedimestamp() {
			return modifiedimestamp;
		}


		public void setModifiedimestamp(Date modifiedimestamp) {
			this.modifiedimestamp = modifiedimestamp;
		}

		public static Announcement save(String announcement) {
			// TODO Auto-generated method stub
			return null;
		}
		

	
		
			
		
	

}
