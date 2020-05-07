package com.auction.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auction.commons.constant.ViewConstant;

@Controller
public class StartupController {

	/**
	 * This method is for displaying error when a user going to access unauthorized
	 * URL or resources.
	 * 
	 * 
	 * @return it will return forbidden 403 tiles definition name.
	 * 
	 */
	@RequestMapping("/403-forbidden")
	public String getforbiddenPage(HttpServletRequest request, Model model) {
		return ViewConstant.FORBIDDEN_403;
	}
	
}