package com.auction.model.bean;

import com.auction.model.entity.RoyaltyCodes;

public class RoyaltyCodesBean {

	private Short royaltyCode;
	private Float royaltyValue;

	public RoyaltyCodesBean() {
	}

	public RoyaltyCodesBean(RoyaltyCodes entity) {
		if (null != entity) {
			this.royaltyCode = entity.getRoyaltyCode();
			this.royaltyValue = entity.getRoyaltyValue();
		}
	}

	public RoyaltyCodesBean(Short royaltyCode) {
		this.royaltyCode = royaltyCode;
	}

	public Short getRoyaltyCode() {
		return royaltyCode;
	}

	public void setRoyaltyCode(Short royaltyCode) {
		this.royaltyCode = royaltyCode;
	}

	public Float getRoyaltyValue() {
		return royaltyValue;
	}

	public void setRoyaltyValue(Float royaltyValue) {
		this.royaltyValue = royaltyValue;
	}

}
