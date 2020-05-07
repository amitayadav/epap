package com.auction.controller.admin.shipping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountLocationStatus;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.model.bean.AccountLocationsBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AccountLocationsService;
import com.auction.service.LoginDetailsService;

@Controller
@RequestMapping("/shipping")
public class AdminShippingController {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private AccountLocationsService accountLocationsService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/**
	 * This method will display list of the shippers it contains tiles definition
	 * name. This is the main method of the application.
	 * 
	 * @return it will return shipperlist tiles definition name.
	 * 
	 */
	@RequestMapping(value = "/shipperlist")
	public String shippersList(Model model) {
		logger.info("AdminShipping Controller Call shippersList method");
		List<LoginDetailsBean> shipperAndShipperAgentList = loginDetailsService
				.getLoginDetailsListByAccountTypes(new Character[] { ENUM_AccountTypeCodes.SHIPPER.getType(),
						ENUM_AccountTypeCodes.SHIPPER_AGENT.getType() });

		for (LoginDetailsBean loginDetailsBean : shipperAndShipperAgentList) {
			if (null != loginDetailsBean.getAccountProfileBean()
					&& null != loginDetailsBean.getAccountProfileBean().getAccountId()
					&& loginDetailsBean.getAccountProfileBean().getAccountId() > 0) {
				List<AccountLocationsBean> accountLocationBeanList = accountLocationsService
						.findByAccountProfileAccountIdAndStatusIn(
								loginDetailsBean.getAccountProfileBean().getAccountId(),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() });
				if (null != accountLocationBeanList && accountLocationBeanList.size() > 0) {
					loginDetailsBean.getAccountProfileBean().setExistingAccountLocationBean(accountLocationBeanList);
				}
			}
		}
		logger.info("=== Ending  shippersList method ===");
		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		return ViewConstant.ADMIN_SHIPPING_SHIPPERS;
	}
	
	@RequestMapping(value = "/shipperList")
	public String syacShippersList(Model model) {
		logger.info("AdminShipping Controller Call syacShippersList  method");
		List<LoginDetailsBean> shipperAndShipperAgentList = loginDetailsService
				.getLoginDetailsListByAccountTypes(new Character[] { ENUM_AccountTypeCodes.SHIPPER.getType(),
						ENUM_AccountTypeCodes.SHIPPER_AGENT.getType() });

		for (LoginDetailsBean loginDetailsBean : shipperAndShipperAgentList) {
			if (null != loginDetailsBean.getAccountProfileBean()
					&& null != loginDetailsBean.getAccountProfileBean().getAccountId()
					&& loginDetailsBean.getAccountProfileBean().getAccountId() > 0) {
				List<AccountLocationsBean> accountLocationBeanList = accountLocationsService
						.findByAccountProfileAccountIdAndStatusIn(
								loginDetailsBean.getAccountProfileBean().getAccountId(),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() });
				if (null != accountLocationBeanList && accountLocationBeanList.size() > 0) {
					loginDetailsBean.getAccountProfileBean().setExistingAccountLocationBean(accountLocationBeanList);
				}
			}
		}
		logger.info("=== Ending  syacShippersList  method ===");
		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		return ViewConstant.NONAJAX_ADMIN_SHIPPING_SHIPPERS;
	}

}