package com.auction.commons.security;

import org.springframework.security.crypto.codec.Base64;

public class TokenSecurity {

	/**
	 * This method helps to prepare system generated encrypted string of given plain
	 * string
	 * 
	 * @param plainText
	 *            pass plain-password entered by the user
	 * @return the value {@code true} if password is correct as per secured password
	 *         otherwise given password by user is incorrect if {@code false}
	 * 
	 */
	public static String generateToken(String plainText) {
		String token = new String(Base64.encode(plainText.getBytes()));
		return token;
	}

	/**
	 * This method helps to prepare system generated encrypted string of given plain
	 * string
	 * 
	 * @param plainText
	 *            pass system generated encrypted string or token requested by the
	 *            user
	 * @return it will return decoded string of passed token
	 * 
	 */
	public static String decodeToken(String token) {
		String plainText = new String(Base64.decode(token.getBytes()));
		return plainText;
	}

	/**
	 * This method helps to identify that given encrypted string or token is valid
	 * or invalid
	 * 
	 * @param token
	 *            pass encrypted string or token
	 * @return the value {@code true} if given encrypted string or token is valid or
	 *         {@code false} if given encrypted string or token is invalid
	 * 
	 */
	public static boolean isValidToken(String token) {
		return Base64.isBase64(token.getBytes());
	}

}
