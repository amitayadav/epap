package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "product_catalog", uniqueConstraints = @UniqueConstraint(columnNames = { "product_group_name", "product_name", "product_type_name" }))
public class ProductCatalog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer productId;
	private String productGroupName;
	private String productName;
	private String productTypeName;
	private String productDescription;
	private String containerSpecs;
	private Short status;
	private String reserved;

	public ProductCatalog() {
	}

	public ProductCatalog(Integer productId) {
		this.productId = productId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "product_id", unique = true, nullable = false)
	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "product_group_name", nullable = false, length = 20)
	public String getProductGroupName() {
		return this.productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	@Column(name = "product_name", nullable = false, length = 20)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_type_name", nullable = false, length = 20)
	public String getProductTypeName() {
		return this.productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	@Column(name = "product_description", nullable = false)
	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@Column(name = "container_specs", nullable = false)
	public String getContainerSpecs() {
		return this.containerSpecs;
	}

	public void setContainerSpecs(String containerSpecs) {
		this.containerSpecs = containerSpecs;
	}

	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "reserved", length = 10)
	public String getReserved() {
		return this.reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}
