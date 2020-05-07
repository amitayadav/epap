package com.auction.controller.admin.shipping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auction.commons.constant.ViewConstant;

@Controller
@RequestMapping("/shipping/report")
public class AdminShippingReportController {

	/* Shipping Report */
	@RequestMapping(method = RequestMethod.GET)
	public String shippingReports() {
		return ViewConstant.ADMIN_REPORT_SHIPPING;
	}

}