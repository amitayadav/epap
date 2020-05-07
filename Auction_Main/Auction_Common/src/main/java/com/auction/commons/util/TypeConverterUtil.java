package com.auction.commons.util;

import java.math.BigDecimal;
import java.util.Date;

public class TypeConverterUtil {

	public static float scaleFloatValue(float values) {
		float currentValues = values;
		String floatAsString = String.format("%.2f", currentValues);
		float newCurrentValues = Float.parseFloat(floatAsString);
		return newCurrentValues;
	}
	
	public static Float convertObjectToFloat(Object values) {
	  Float output = new Float(0.0);
		if(null!=values) {
			output=new Float(values.toString());
		}
		return output;
	}
	public static Integer convertObjectToInteger(Object values) {
		  Integer output = new Integer(0);
			if(null!=values) {
				output=new Integer(values.toString());
			}
			return output;
		}
	public static BigDecimal convertObjectToBigDecimal(Object values) {
		BigDecimal output = new BigDecimal(0.0);
			if(null!=values) {
				output=new BigDecimal(values.toString());
			}
			return output;
		}
	public static String convertObjectToString(Object values) {
		String output = "";
			if(null!=values) {
				output= values.toString();
			}
			return output;
		}
	
	public static Date convertObjectToDate(Object values) {
		Date output = new Date();
			if(null!=values && values instanceof java.util.Date) {
				output= (java.util.Date)values;
			}
			return output;
		}
	
	public static void main(String[] args) {
		System.out.println(TypeConverterUtil.convertObjectToDate("2018-08-07 13:19:13.646000"));
	}
}
