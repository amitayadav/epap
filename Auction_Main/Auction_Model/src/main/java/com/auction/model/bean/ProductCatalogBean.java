package com.auction.model.bean;

import com.auction.model.entity.ProductCatalog;

public class ProductCatalogBean {

	private Integer productId;
	private String productGroupName;
	private String productName;
	private String productTypeName;
	private String productDescription;
	private String containerSpecs;
	private Short status;
	private String reserved;

	public ProductCatalogBean() {
	}

	public ProductCatalogBean(Integer productId) {
		this.productId = productId;
	}

	public ProductCatalogBean(ProductCatalog entity) {

		if (null != entity) {
			this.productId = entity.getProductId();
			this.productGroupName = entity.getProductGroupName();
			this.productName = entity.getProductName();
			this.productTypeName = entity.getProductTypeName();
			this.productDescription = entity.getProductDescription();
			this.containerSpecs = entity.getContainerSpecs();
			this.status = entity.getStatus();
			this.reserved = entity.getReserved();
		}

	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getContainerSpecs() {
		return containerSpecs;
	}

	public void setContainerSpecs(String containerSpecs) {
		this.containerSpecs = containerSpecs;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}
