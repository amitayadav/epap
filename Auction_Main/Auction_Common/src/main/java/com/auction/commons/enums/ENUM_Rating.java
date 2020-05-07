package com.auction.commons.enums;

public enum ENUM_Rating {

	RATING0((float)0), RATING1((float)1), RATING2((float)2), RATING3((float)3), RATING4((float)4), RATING5((float)5);
	private float status;

	ENUM_Rating(float status) {
		this.status = status;
	}

	public float getStatus() {
		return status;
	}

}