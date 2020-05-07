package com.auction.model.bean;

import java.sql.Blob;
import com.auction.model.entity.ShipperPictures;

public class ShipperPicturesBean {
	private Integer pictureId;
	private AccountProfileBean accountProfileBean;
	private String pictureLocation;
	private   Blob     contents ;

	public ShipperPicturesBean() {
	}
	public ShipperPicturesBean(Integer pictureId) {
		this.pictureId = pictureId;
	}
	public ShipperPicturesBean(ShipperPictures entity) {
		if(null != entity) {}
		this.pictureId = entity.getPictureId();
		this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
		this.pictureLocation = entity.getPictureLocation();
		this.contents=entity.getContents();
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public String getPictureLocation() {
		return pictureLocation;
	}

	public void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}
	public Blob getContents() {
		return contents;
	}
	public void setContents(Blob contents) {
		this.contents = contents;
	}

}
