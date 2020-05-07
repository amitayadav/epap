package com.auction.model.views;

import com.auction.model.entity.LoginDetails;

public class LoginDetailsViews {

	private Integer accountId;
	private String publicName;
	private String profileImage;
	
	public LoginDetailsViews(){
		
	}
	
public LoginDetailsViews(LoginDetails entity){
	if (null != entity && null != entity.getLoginUserid()) {
		this.accountId =entity.getAccountProfile().getAccountId();
		this.publicName=entity.getPublicName();
		this.profileImage=entity.getAccountProfile().getProfileImage();
	}
	}
	
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getPublicName() {
		return publicName;
	}
	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
}
