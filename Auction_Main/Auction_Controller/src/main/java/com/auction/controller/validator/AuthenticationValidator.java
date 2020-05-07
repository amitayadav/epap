package com.auction.controller.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.util.RegExpPatterns;
import com.auction.model.bean.LoginDetailsBean;

@Component
public class AuthenticationValidator {

	@Autowired
	MessageSource messageSource;

	@Autowired
	LocaleResolver localeResolver;

	// Validation : Login Module
	public List<String> validateLogin(LoginDetailsBean loginDetailsBean, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
		Locale locale = localeResolver.resolveLocale(request);
		// Validating Email Address
		if (null == loginDetailsBean.getLoginUserid() || loginDetailsBean.getLoginUserid().trim().length() == 0) {
			errorStack.add(messageSource.getMessage("validation.email.required", null, locale));
		} else if (!loginDetailsBean.getLoginUserid().trim().matches(RegExpPatterns.EMAIL)) {
			errorStack.add(messageSource.getMessage("validation.email.emailPattern", null, locale));
		}

		// Validating Password
		if (null == loginDetailsBean.getPassword() || loginDetailsBean.getPassword().trim().length() == 0) {
			errorStack.add(messageSource.getMessage("validation.password.required", null, locale));
		}

		return errorStack;
	}

	// Validation : Registration Phase 1 Module
	public List<String> validateRegistrationPhase1(LoginDetailsBean loginDetailsBean, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
		Locale locale = localeResolver.resolveLocale(request);
		
		// Validating Account Type
		if (null == loginDetailsBean.getAccountTypeCodesBean() || null == loginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
			errorStack.add(messageSource.getMessage("validation.accountType.required", null, locale));
		}

		// Validating Owner Email Address
		if (null != loginDetailsBean.getAccountTypeCodesBean() && null != loginDetailsBean.getAccountTypeCodesBean().getAccountType()
				&& (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType()))) {
			if (null == loginDetailsBean.getOwnerEmail() || loginDetailsBean.getOwnerEmail().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("validation.ownerEmail.required", null, locale));
			} else if (!loginDetailsBean.getOwnerEmail().trim().matches(RegExpPatterns.EMAIL)) {
				errorStack.add(messageSource.getMessage("validation.ownerEmail.emailPattern", null, locale));
			}
		}

		// Validating Public Name
		if (null == loginDetailsBean.getPublicName() || loginDetailsBean.getPublicName().trim().length() == 0) {
			errorStack.add(messageSource.getMessage("validation.publicName.required", null, locale));
		} else if (loginDetailsBean.getPublicName().trim().length() > 30) {
			errorStack.add(messageSource.getMessage("validation.publicName.maxLength", null, locale));
		}

		// Validating Email Address
		if (null == loginDetailsBean.getLoginUserid() || loginDetailsBean.getLoginUserid().trim().length() == 0) {
			errorStack.add(messageSource.getMessage("validation.email.required", null, locale));
		} else if (loginDetailsBean.getLoginUserid().trim().length() > 40) {
			errorStack.add(messageSource.getMessage("validation.email.maxLength", null, locale));
		} else if (!loginDetailsBean.getLoginUserid().trim().matches(RegExpPatterns.EMAIL)) {
			errorStack.add(messageSource.getMessage("validation.email.emailPattern", null, locale));
		}

		// Validating Password
		if (null == loginDetailsBean.getPassword() || loginDetailsBean.getPassword().trim().length() == 0) {
			errorStack.add(messageSource.getMessage("validation.password.required", null, locale));
		} else if (loginDetailsBean.getPassword().trim().length() > 20) {
			errorStack.add(messageSource.getMessage("validation.password.maxLength", null, locale));
		} else if (!loginDetailsBean.getPassword().trim().matches(RegExpPatterns.PASSWORD)) {
			errorStack.add(messageSource.getMessage("validation.password.passwordPattern", null, locale));
		}

		// Confirm Password
		String confirmPass = request.getParameter("confirmPass");
		if (null == confirmPass || confirmPass.trim().length() == 0) {
			errorStack.add(messageSource.getMessage("validation.confirmPassword.required", null, locale));
		} else if (null != loginDetailsBean.getPassword() && !loginDetailsBean.getPassword().equals(confirmPass)) {
			errorStack.add(messageSource.getMessage("validation.pwdAndConfPwd.match", null, locale));
		} 

		return errorStack;
	}

	// Validation : Forgot Password Module
	public List<String> validateForgotPassword(String emailAddress, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
		Locale locale = localeResolver.resolveLocale(request);
		// Validating Email Address
		if (null == emailAddress || emailAddress.length() == 0) {
			errorStack.add(messageSource.getMessage("validation.email.required", null, locale));
		} else if (!emailAddress.trim().matches(RegExpPatterns.EMAIL)) {
			errorStack.add(messageSource.getMessage("validation.email.emailPattern", null, locale));
		}

		return errorStack;
	}
}