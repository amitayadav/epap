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
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.commons.util.RegExpPatterns;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.LoginDetailsBean;

@Component
public class AccountSettingValidator {

	@Autowired
	MessageSource messageSource;

	@Autowired
	LocaleResolver localeResolver;

	// Validation : Registration Phase 2 Module
	public List<String> validateRegistrationPhase2(LoginDetailsBean loginDetailsBean, AccountProfileBean accountProfileBean, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
		Locale locale = localeResolver.resolveLocale(request);
		if (ENUM_AccountTypeCodes.USER.getType() != loginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
			// Validating First Name
			if (null == accountProfileBean.getFName() || accountProfileBean.getFName().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("regProfile.validation.firstName.required", null, locale));
			} else if (null != accountProfileBean.getFName() && accountProfileBean.getFName().length() > 20) {
				errorStack.add(messageSource.getMessage("regProfile.validation.firstName.maxlength", null, locale));
			}

			// Validating Middle Name
			if (null != accountProfileBean.getMName() && accountProfileBean.getMName().length() > 20) {
				errorStack.add(messageSource.getMessage("regProfile.validation.middleName.maxlength", null, locale));
			}

			// Validating Last Name
			if (null == accountProfileBean.getLName() || accountProfileBean.getLName().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("regProfile.validation.lastName.required", null, locale));
			} else if (null != accountProfileBean.getLName() && accountProfileBean.getLName().length() > 20) {
				errorStack.add(messageSource.getMessage("regProfile.validation.lastName.maxlength", null, locale));
			}

			// Validating Company Name
			if (null == accountProfileBean.getBusinessName() || accountProfileBean.getBusinessName().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("regProfile.validation.companyName.required", null, locale));
			} else if (null != accountProfileBean.getBusinessName() && accountProfileBean.getBusinessName().length() > 50) {
				errorStack.add(messageSource.getMessage("regProfile.validation.companyName.maxlength", null, locale));
			}

			// Validating Secondary Email Address
			if (null != accountProfileBean.getEmailAddress2() && !accountProfileBean.getEmailAddress2().isEmpty()) {
				if (accountProfileBean.getEmailAddress2().length() > 30) {
					errorStack.add(messageSource.getMessage("regProfile.validation.secondaryEmail.maxlength", null, locale));
				} else if (!accountProfileBean.getEmailAddress2().matches(RegExpPatterns.EMAIL)) {
					errorStack.add(messageSource.getMessage("regProfile.validation.secondaryEmail.pattern", null, locale));
				}
			}

			// Validating National Id / Government Id
			if (null == accountProfileBean.getGovernmentId() || accountProfileBean.getGovernmentId().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("regProfile.validation.govtId.required", null, locale));
			} else if (null != accountProfileBean.getGovernmentId() && accountProfileBean.getGovernmentId().length() > 10) {
				errorStack.add(messageSource.getMessage("regProfile.validation.govtId.maxlength", null, locale));
			}

			// Validating Mobile Number 1
			if (null == accountProfileBean.getPhoneNumber1() || accountProfileBean.getPhoneNumber1().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo1.required", null, locale));
			} else {
				if (!accountProfileBean.getPhoneNumber1().matches(RegExpPatterns.MOBILE)) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo1.digits", null, locale));
				} else if (accountProfileBean.getPhoneNumber1().length() < 10) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo1.minLength", null, locale));
				} else if (accountProfileBean.getPhoneNumber1().length() > 10) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo1.maxlength", null, locale));
				}
			}

			// Validating Mobile Number 2
			if (null != accountProfileBean.getPhoneNumber2() && !accountProfileBean.getPhoneNumber2().isEmpty()) {
				if (!accountProfileBean.getPhoneNumber2().matches(RegExpPatterns.MOBILE)) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo2.digits", null, locale));
				} else if (accountProfileBean.getPhoneNumber2().length() < 10) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo2.minLength", null, locale));
				} else if (accountProfileBean.getPhoneNumber2().length() > 10) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo2.maxlength", null, locale));
				}
			}

			// Validating Bank Details
			if (null != loginDetailsBean.getAccountTypeCodesBean() && null != loginDetailsBean.getAccountTypeCodesBean().getAccountType()
					&& ((loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER.getType())
							|| (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER.getType())
							|| (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER.getType()))) {

				if (null != accountProfileBean.getBankDetailsBean()) {
					if (null == accountProfileBean.getBankDetailsBean().getBankName() || accountProfileBean.getBankDetailsBean().getBankName().trim().length() == 0) {
						errorStack.add(messageSource.getMessage("regProfile.validation.bankName.required", null, locale));
					} else if (null != accountProfileBean.getBankDetailsBean().getBankName() && accountProfileBean.getBankDetailsBean().getBankName().length() > 30) {
						errorStack.add(messageSource.getMessage("regProfile.validation.bankName.maxlength", null, locale));
					}

					if (null == accountProfileBean.getBankDetailsBean().getBankAccountNumber()
							|| accountProfileBean.getBankDetailsBean().getBankAccountNumber().trim().length() == 0) {
						errorStack.add(messageSource.getMessage("regProfile.validation.bankAccount.required", null, locale));
					} else if (null != accountProfileBean.getBankDetailsBean().getBankAccountNumber()
							&& accountProfileBean.getBankDetailsBean().getBankAccountNumber().trim().length() > 16) {
						errorStack.add(messageSource.getMessage("regProfile.validation.bankAccount.maxlength", null, locale));
					}

					if (null == accountProfileBean.getBankDetailsBean().getIban() || accountProfileBean.getBankDetailsBean().getIban().trim().length() == 0) {
						errorStack.add(messageSource.getMessage("regProfile.validation.iban.required", null, locale));
					} else if (null != accountProfileBean.getBankDetailsBean().getIban() && accountProfileBean.getBankDetailsBean().getIban().trim().length() > 24) {
						errorStack.add(messageSource.getMessage("regProfile.validation.iban.maxlength", null, locale));
					}
				}
			}
		}

		// Validating Driver’s License
		if (null != loginDetailsBean.getAccountTypeCodesBean() && null != loginDetailsBean.getAccountTypeCodesBean().getAccountType()
				&& (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER.getType()
						|| loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER_AGENT.getType())) {

			accountProfileBean.getShippersRegistrationInfoBean()
					.setDriverLicenseExpirationDate(DateHelper.getDateStringWithMonthAndYear(request.getParameter("driverLicenseExpirationDate"), true));

			if (null == accountProfileBean.getShippersRegistrationInfoBean().getDriverLicenseExpirationDate()) {
				errorStack.add(messageSource.getMessage("regProfile.validation.driverLicenseExpirationDate.required", null, locale));
			} else if (null != accountProfileBean.getShippersRegistrationInfoBean().getDriverLicenseExpirationDate()
					&& (accountProfileBean.getShippersRegistrationInfoBean().getDriverLicenseExpirationDate().getTime() <= InternetTiming.getInternetDateTimeAsMiliSeconds())) {
				errorStack.add(messageSource.getMessage("regProfile.validation.driverLicenseExpirationDate.dateerror", null, locale));
			}

			if (null == accountProfileBean.getShippersRegistrationInfoBean().getTruckModelYear()) {
				errorStack.add(messageSource.getMessage("regProfile.validation.truckModelYear.required", null, locale));
			}
			if (null == accountProfileBean.getShippersRegistrationInfoBean().getTruckType()) {
				errorStack.add(messageSource.getMessage("regProfile.validation.truckType.required", null, locale));
			}
		}

		// Validating Preferred Language
		if (null == accountProfileBean.getPreferredLanguage() || accountProfileBean.getPreferredLanguage().trim().length() == 0) {
			errorStack.add(messageSource.getMessage("regProfile.validation.govtId.required", null, locale));
		}
		return errorStack;
	}

	// Validation : Edit Profile Module
	public List<String> validateEditProfile(LoginDetailsBean loginDetailsBean, AccountProfileBean accountProfileBean, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
		Locale locale = localeResolver.resolveLocale(request);

		if (ENUM_AccountTypeCodes.USER.getType() != loginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
			// Validating Secondary Email Address
			if (null != accountProfileBean.getEmailAddress2() && !accountProfileBean.getEmailAddress2().isEmpty()) {
				if (accountProfileBean.getEmailAddress2().length() > 30) {
					errorStack.add(messageSource.getMessage("regProfile.validation.secondaryEmail.maxlength", null, locale));
				} else if (!accountProfileBean.getEmailAddress2().matches(RegExpPatterns.EMAIL)) {
					errorStack.add(messageSource.getMessage("regProfile.validation.secondaryEmail.pattern", null, locale));
				}
			}

			// Validating Mobile Number 1
			if (null == accountProfileBean.getPhoneNumber1() || accountProfileBean.getPhoneNumber1().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo1.required", null, locale));
			} else {
				if (!accountProfileBean.getPhoneNumber1().matches(RegExpPatterns.MOBILE)) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo1.digits", null, locale));
				} else if (accountProfileBean.getPhoneNumber1().length() < 10) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo1.minLength", null, locale));
				} else if (accountProfileBean.getPhoneNumber1().length() > 10) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo1.maxlength", null, locale));
				}
			}

			// Validating Mobile Number 2
			if (null != accountProfileBean.getPhoneNumber2() && !accountProfileBean.getPhoneNumber2().isEmpty()) {
				if (!accountProfileBean.getPhoneNumber2().matches(RegExpPatterns.MOBILE)) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo2.digits", null, locale));
				} else if (accountProfileBean.getPhoneNumber2().length() < 10) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo2.minLength", null, locale));
				} else if (accountProfileBean.getPhoneNumber2().length() > 10) {
					errorStack.add(messageSource.getMessage("regProfile.validation.phoneNo2.maxlength", null, locale));
				}
			}

			// Validating Bank Details
			if (null != loginDetailsBean.getAccountTypeCodesBean() && null != loginDetailsBean.getAccountTypeCodesBean().getAccountType()
					&& ((loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER.getType())
							|| (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER.getType())
							|| (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER.getType()))) {

				if (null != accountProfileBean.getBankDetailsBean()) {
					if (null == accountProfileBean.getBankDetailsBean().getBankName() || accountProfileBean.getBankDetailsBean().getBankName().trim().length() == 0) {
						errorStack.add(messageSource.getMessage("regProfile.validation.bankName.required", null, locale));
					} else if (null != accountProfileBean.getBankDetailsBean().getBankName() && accountProfileBean.getBankDetailsBean().getBankName().length() > 30) {
						errorStack.add(messageSource.getMessage("regProfile.validation.bankName.maxlength", null, locale));
					}

					if (null == accountProfileBean.getBankDetailsBean().getBankAccountNumber()
							|| accountProfileBean.getBankDetailsBean().getBankAccountNumber().trim().length() == 0) {
						errorStack.add(messageSource.getMessage("regProfile.validation.bankAccount.required", null, locale));
					} else if (null != accountProfileBean.getBankDetailsBean().getBankAccountNumber()
							&& accountProfileBean.getBankDetailsBean().getBankAccountNumber().trim().length() > 16) {
						errorStack.add(messageSource.getMessage("regProfile.validation.bankAccount.maxlength", null, locale));
					}

					if (null == accountProfileBean.getBankDetailsBean().getIban() || accountProfileBean.getBankDetailsBean().getIban().trim().length() == 0) {
						errorStack.add(messageSource.getMessage("regProfile.validation.iban.required", null, locale));
					} else if (null != accountProfileBean.getBankDetailsBean().getIban() && accountProfileBean.getBankDetailsBean().getIban().trim().length() > 24) {
						errorStack.add(messageSource.getMessage("regProfile.validation.iban.maxlength", null, locale));
					}
				}
			}

		}

		// Validating Preferred Language
		if (null == accountProfileBean.getPreferredLanguage() || accountProfileBean.getPreferredLanguage().trim().length() == 0) {
			errorStack.add(messageSource.getMessage("regProfile.validation.govtId.required", null, locale));
		}

		return errorStack;
	}

	// Validation : Password Change
	public List<String> validatePasswordChange(LoginDetailsBean loginDetailsBean, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
		Locale locale = localeResolver.resolveLocale(request);

		String newPassword = request.getParameter("newPassword");
		String cPassword = request.getParameter("cPassword");

		// Validating Current Password
		if (null == loginDetailsBean.getPassword() || loginDetailsBean.getPassword().length() == 0) {
			errorStack.add(messageSource.getMessage("changepassword.validation.password.required", null, locale));
		} else if (null != loginDetailsBean.getPassword() && loginDetailsBean.getPassword().length() > 20) {
			errorStack.add(messageSource.getMessage("changepassword.validation.password.maxlength", null, locale));
		}

		// Validating New Password
		if (null == newPassword || newPassword.length() == 0) {
			errorStack.add(messageSource.getMessage("changepassword.validation.newPassword.required", null, locale));
		} else if (null != newPassword && newPassword.length() > 20) {
			errorStack.add(messageSource.getMessage("changepassword.validation.newPassword.maxlength", null, locale));
		} else if (null != newPassword && !newPassword.matches(RegExpPatterns.PASSWORD)) {
			errorStack.add(messageSource.getMessage("changepassword.validation.newPassword.passwordpattern", null, locale));
		}

		// Validating Confirm Password
		if (null == cPassword || cPassword.length() == 0) {
			errorStack.add(messageSource.getMessage("changepassword.validation.newPassword.required", null, locale));
		} else if (null != cPassword && cPassword.length() > 20) {
			errorStack.add(messageSource.getMessage("changepassword.validation.newPassword.maxlength", null, locale));
		} else if (null != newPassword && null != cPassword && !cPassword.equals(newPassword)) {
			errorStack.add(messageSource.getMessage("changepassword.validation.newPassword.passwordpattern", null, locale));
		}

		return errorStack;
	}
}