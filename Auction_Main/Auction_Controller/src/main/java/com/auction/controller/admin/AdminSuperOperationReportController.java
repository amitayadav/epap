package com.auction.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auction.commons.constant.ViewConstant;

@Controller
@RequestMapping("/spropt/report/")
public class AdminSuperOperationReportController {

	/**
	 * This method is used to display system reports
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return System Report
	 */
	@RequestMapping("/systemreports")
	public String dashboard(Model model) {
		return ViewConstant.ADMIN_REPORT_SYSTEM;
	}
	
	@RequestMapping("/systemReports")
	public String syscDashboard(Model model) {
		return ViewConstant.NONAJAX_ADMIN_REPORT_SYSTEM;
	}
}
