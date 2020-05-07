package com.auction.model.bean;
import com.auction.model.entity.ShipperInfo;

public class ShipperInfoBean {
	
	private Integer shipperId;
	private AccountProfileBean accountProfileBean;
	private String infoLine1;
	private String infoLine2;
	
	public ShipperInfoBean() {
		
	}
	public ShipperInfoBean(ShipperInfo entity) {
		if (null != entity) {
			this.shipperId=entity.getShipperId();
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.infoLine1 = entity.getInfoLine1();
			this.infoLine2 = entity.getInfoLine2();
		}
	}
	public Integer getShipperId() {
		return shipperId;
	}
	public void setShipperId(Integer shipperId) {
		this.shipperId = shipperId;
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}
	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}
	public String getInfoLine1() {
		return infoLine1;
	}
	public void setInfoLine1(String infoLine1) {
		this.infoLine1 = infoLine1;
	}
	public String getInfoLine2() {
		return infoLine2;
	}
	public void setInfoLine2(String infoLine2) {
		this.infoLine2 = infoLine2;
	}

	
	
}
