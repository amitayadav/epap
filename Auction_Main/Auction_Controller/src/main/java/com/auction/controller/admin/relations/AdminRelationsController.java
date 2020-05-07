package com.auction.controller.admin.relations;

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
@RequestMapping("/relation")
public class AdminRelationsController {
	
	@Autowired
	private LoginDetailsService loginDetailsService;
	
	@Autowired
	private	AccountLocationsService accountLocationsService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	/*Shipping Listing*/
	@RequestMapping(value = "/shipperlist")
	public String shippersList(Model model) {
		logger.info("AdminRelationsController Call shippersList method");
		List<LoginDetailsBean> shipperAndShipperAgentList = loginDetailsService
				.getLoginDetailsListByAccountTypes(new Character[] { ENUM_AccountTypeCodes.SHIPPER.getType(), ENUM_AccountTypeCodes.SHIPPER_AGENT.getType() });

		for (LoginDetailsBean loginDetailsBean : shipperAndShipperAgentList) {
			if (null != loginDetailsBean.getAccountProfileBean() && null != loginDetailsBean.getAccountProfileBean().getAccountId()
					&& loginDetailsBean.getAccountProfileBean().getAccountId() > 0) {
				List<AccountLocationsBean> accountLocationBeanList = accountLocationsService.findByAccountProfileAccountIdAndStatusIn(
						loginDetailsBean.getAccountProfileBean().getAccountId(), new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() });
				if (null != accountLocationBeanList && accountLocationBeanList.size() > 0) {
					loginDetailsBean.getAccountProfileBean().setExistingAccountLocationBean(accountLocationBeanList);
				}
			}
		}
		logger.info(" === Ending shippersList method ===");
		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		return ViewConstant.ADMIN_RELATION_SHIPPERS;
	}
	
	
	/*Shipping Listing NONAJAX*/
	@RequestMapping(value = "/shipperList")
	public String syacShippersList(Model model) {
		logger.info("AdminRelationsController Call syacShippersList method");
		List<LoginDetailsBean> shipperAndShipperAgentList = loginDetailsService
				.getLoginDetailsListByAccountTypes(new Character[] { ENUM_AccountTypeCodes.SHIPPER.getType(), ENUM_AccountTypeCodes.SHIPPER_AGENT.getType() });

		for (LoginDetailsBean loginDetailsBean : shipperAndShipperAgentList) {
			if (null != loginDetailsBean.getAccountProfileBean() && null != loginDetailsBean.getAccountProfileBean().getAccountId()
					&& loginDetailsBean.getAccountProfileBean().getAccountId() > 0) {
				List<AccountLocationsBean> accountLocationBeanList = accountLocationsService.findByAccountProfileAccountIdAndStatusIn(
						loginDetailsBean.getAccountProfileBean().getAccountId(), new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() });
				if (null != accountLocationBeanList && accountLocationBeanList.size() > 0) {
					loginDetailsBean.getAccountProfileBean().setExistingAccountLocationBean(accountLocationBeanList);
				}
			}
		}
		logger.info(" === Ending syacShippersList method ===");
		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		return ViewConstant.NONAJAX_ADMIN_RELATION_SHIPPERS;
	}
}