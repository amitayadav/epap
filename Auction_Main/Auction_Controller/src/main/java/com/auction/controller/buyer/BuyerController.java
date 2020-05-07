package com.auction.controller.buyer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auction.commons.constant.ViewConstant;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

	/**
	 * By default method for this controller which will be accessible by /buyer
	 * request mapping with get method.
	 * 
	 * @return it will return on buyer home tiles definition.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return ViewConstant.BUYER_HOME;
	}
}