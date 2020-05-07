package com.auction.controller.general;

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
@RequestMapping("/general")
public class GeneralUserController {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private AccountLocationsService accountLocationsService;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@RequestMapping("/shipperlist")
	public String shipperAndShipperAgentList(Model model) {
		logger.info("GenerAlUserController Call shipperAndShipperAgentList Method ");
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
		logger.info("=== Ending  shipperAndShipperAgentList Method === ");
		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		return ViewConstant.GENERAL_SHIPPER_AND_SHIPPER_AGENT_LIST;
	}

	/**NON AJAX Shipper List (seller Buyer shipper)       */
	@RequestMapping("/shipperList")
	public String syscShipperAndShipperAgentList(Model model) {
		logger.info("GenerAlUserController Call syscShipperAndShipperAgentList  Method ");
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
		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		logger.info("=== Ending  syscShipperAndShipperAgentList Method === ");
		return ViewConstant.NONAJAX_GENERAL_SHIPPER_AND_SHIPPER_AGENT_LIST;
	}

	
	
	
	
	
	
	
	
	
	
}