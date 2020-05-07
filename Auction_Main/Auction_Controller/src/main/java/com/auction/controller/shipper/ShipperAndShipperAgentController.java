package com.auction.controller.shipper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auction.commons.constant.ViewConstant;

@Controller
@RequestMapping("/shsha/")
public class ShipperAndShipperAgentController {

	/*Confirm Delivery*/
	@RequestMapping(value = "/confirmdelivery")
	public String confirmDelivery() {
		return ViewConstant.SHIPPER_CONFIRM_DELIVERY;
	}
	
	/*Confirm Delivery non ajax*/
	@RequestMapping(value = "/confirmdelivery/List")
	public String sysaconfirmDelivery() {
		return ViewConstant.NONAJAX_SHIPPER_CONFIRM_DELIVERY;
	}
	
	/*Shipping Listing*/
	@RequestMapping(value = "/shipperlist")
	public String shippersList() {
		return ViewConstant.SHIPPER_SHIPPERS;
	}
	
}