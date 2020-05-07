package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "royalty_codes", uniqueConstraints = @UniqueConstraint(columnNames = "royalty_value"))
public class RoyaltyCodes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Short royaltyCode;
	private Float royaltyValue;

	public RoyaltyCodes() {
	}

	public RoyaltyCodes(Short royaltyCode) {
		this.royaltyCode = royaltyCode;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "royalty_code", unique = true, nullable = false)
	public Short getRoyaltyCode() {
		return this.royaltyCode;
	}

	public void setRoyaltyCode(Short royaltyCode) {
		this.royaltyCode = royaltyCode;
	}

	@Column(name = "royalty_value", unique = true, nullable = false, precision = 4)
	public Float getRoyaltyValue() {
		return this.royaltyValue;
	}

	public void setRoyaltyValue(Float royaltyValue) {
		this.royaltyValue = royaltyValue;
	}

}
