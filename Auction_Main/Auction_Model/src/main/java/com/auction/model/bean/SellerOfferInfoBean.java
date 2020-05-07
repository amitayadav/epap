package com.auction.model.bean;

import com.auction.model.entity.SellerOfferInfo;

public class SellerOfferInfoBean {

	private Integer sellerOfferInfoId;
	private AccountProfileBean accountProfileBean;
	private ProductCatalogBean productCatalogBean;
	private String infoLine1;
	private String infoLine2;

	public SellerOfferInfoBean() {

	}

	public SellerOfferInfoBean(Integer sellerOfferInfoId) {
		this.sellerOfferInfoId = sellerOfferInfoId;
	}

	public SellerOfferInfoBean(SellerOfferInfo entity) {
		if (null != entity && null != entity.getSellerOfferInfoId()) {
			this.sellerOfferInfoId = entity.getSellerOfferInfoId();
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.productCatalogBean = new ProductCatalogBean(entity.getProductCatalog());
			this.infoLine1 = entity.getInfoLine1();
			this.infoLine2 = entity.getInfoLine2();
		}
	}
	
	public SellerOfferInfoBean(Integer sellerOfferInfoId, AccountProfileBean accountProfileBean,
			ProductCatalogBean productCatalogBean, String infoLine1, String infoLine2) {
		super();
		this.sellerOfferInfoId = sellerOfferInfoId;
		this.accountProfileBean = accountProfileBean;
		this.productCatalogBean = productCatalogBean;
		this.infoLine1 = infoLine1;
		this.infoLine2 = infoLine2;
	}

	public Integer getSellerOfferInfoId() {
		return sellerOfferInfoId;
	}

	public void setSellerOfferInfoId(Integer sellerOfferInfoId) {
		this.sellerOfferInfoId = sellerOfferInfoId;
	}

	public String getInfoLine1() {
		return infoLine1;
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public ProductCatalogBean getProductCatalogBean() {
		return productCatalogBean;
	}

	public void setProductCatalogBean(ProductCatalogBean productCatalogBean) {
		this.productCatalogBean = productCatalogBean;
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