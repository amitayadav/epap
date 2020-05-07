package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seller_pictures")
public class SellerPictures implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer pictureId;
	private AccountProfile accountProfile;
	private String pictureLocation;
	private   Blob     contents ;
	public SellerPictures() {
	}

	public SellerPictures(Integer pictureId) {
		this.pictureId = pictureId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "picture_id", unique = true, nullable = false)
	public Integer getPictureId() {
		return this.pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false)
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@Column(name = "picture_location", nullable = false, length = 100)
	public String getPictureLocation() {
		return this.pictureLocation;
	}

	public void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}

	@Column(name = "contents")
	@Lob
	public Blob getContents() {
		return contents;
	}

	public void setContents(Blob contents) {
		this.contents = contents;
	}

}
