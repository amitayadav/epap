package com.auction.controller.report;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auction.commons.constant.ViewConstant;

@Controller
@RequestMapping("/common/report")
public class GeneralReportController {

	@RequestMapping("/general")
	public String generalReport() {
		return ViewConstant.REPORT_GENERAL;
	}
	
	@RequestMapping("/generalReport")
	public String sysaGeneralReport() {
		return ViewConstant.NONAJAX_REPORT_GENERAL;
	}
	
}