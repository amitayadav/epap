package com.auction.controller.admin.relations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auction.commons.constant.ViewConstant;

@Controller
@RequestMapping("/relation/report")
public class AdminRelationsReportController {

	/* Relation Report */
	@RequestMapping(method = RequestMethod.GET)
	public String relationReports() {
		return ViewConstant.ADMIN_REPORT_RELATIONS;
	}

}