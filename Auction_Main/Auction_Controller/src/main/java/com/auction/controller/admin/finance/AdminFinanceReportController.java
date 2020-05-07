package com.auction.controller.admin.finance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auction.commons.constant.ViewConstant;

@Controller
@RequestMapping("/finance/report")
public class AdminFinanceReportController {

	/* Finance Report */
	@RequestMapping(method = RequestMethod.GET)
	public String financeReport() {
		return ViewConstant.ADMIN_REPORT_FINANCE;
	}
}