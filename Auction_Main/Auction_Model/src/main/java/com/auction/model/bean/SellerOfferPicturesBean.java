package com.auction.model.bean;

import java.sql.Blob;

import com.auction.model.entity.SellerOfferPictures;

public class SellerOfferPicturesBean {

	private Integer pictureId;
	private AccountProfileBean accountProfileBean;
	private ProductCatalogBean productCatalogBean;
	private String pictureLocation;
	private   Blob     contents ;
	public SellerOfferPicturesBean() {

	}

	public SellerOfferPicturesBean(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public SellerOfferPicturesBean(SellerOfferPictures entity) {
		if (null != entity) {
			this.pictureId = entity.getPictureId();
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.productCatalogBean = new ProductCatalogBean(entity.getProductCatalog());
			this.pictureLocation = entity.getPictureLocation();
			this.contents =entity.getContents();
		}
	}

	public SellerOfferPicturesBean(Integer pictureId, AccountProfileBean accountProfileBean,
			ProductCatalogBean productCatalogBean, String pictureLocation,Blob contents) {
		super();
		this.pictureId = pictureId;
		this.accountProfileBean = accountProfileBean;
		this.productCatalogBean = productCatalogBean;
		this.pictureLocation = pictureLocation;
		this.contents =contents;
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

	public ProductCatalogBean getProductCatalogBean() {
		return productCatalogBean;
	}

	public void setProductCatalogBean(ProductCatalogBean productCatalogBean) {
		this.productCatalogBean = productCatalogBean;
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