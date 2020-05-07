package com.auction.controller.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import com.auction.model.bean.ShipperInfoBean;

@Component
public class ShipperValidator {

	@Autowired
	MessageSource messageSource;

	@Autowired
	LocaleResolver localeResolver;
	
	// Validation : Shipper Info
		public List<String> validateShipperInfo(ShipperInfoBean shipperInfoBean, HttpServletRequest request) {
			List<String> errorStack = new ArrayList<String>(0);
			Locale locale = localeResolver.resolveLocale(request);

			// Validating Shipper Info 1
			if (null == shipperInfoBean.getInfoLine1() || shipperInfoBean.getInfoLine1().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("shipper.shipperinfopicture.validation.info1.required", null, locale));
			} else if (null != shipperInfoBean.getInfoLine1() && shipperInfoBean.getInfoLine1().length() > 255) {
				errorStack.add(messageSource.getMessage("shipper.shipperinfopicture.validation.info1.maxlength", null, locale));
			}

			// Validating Shipper Info 2
			if (null != shipperInfoBean.getInfoLine2() && shipperInfoBean.getInfoLine2().length() > 255) {
				errorStack.add(messageSource.getMessage("shipper.shipperinfopicture.validation.info2.maxlength", null, locale));
			}

			return errorStack;
		}
	
	
}
