package com.auction.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountStatusCodes;
import com.auction.commons.enums.ENUM_AgentPrivileges;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.EmailData;
import com.auction.commons.util.EmailTemplate;
import com.auction.component.SpringSecuritySessionRegistry;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountStatusCodesBean;
import com.auction.model.bean.AgentOwnerBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AccountStatusCodesService;
import com.auction.service.AgentOwnerService;
import com.auction.service.LoginDetailsService;
import com.auction.service.security.EmailService;

@Controller
@RequestMapping("/sbs")
public class SellerBuyerShipperController {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private AgentOwnerService agentOwnerService;

	@Autowired
	private AccountStatusCodesService accountStatusCodesService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SpringSecuritySessionRegistry springSecuritySessionRegistry;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/**
	 * This method is used to get all the agent of current login user
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return agent list page
	 */
	@RequestMapping("/agentlist")
	public String agentList(HttpServletRequest request, Model model) {
		logger.info("SellerBuyerShipperController Call agentList Method ");
		logger.info("=== Ending  agentList Method === ");
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		String loginUserId = SessionHelper.getLoginUserId(request);
		if (null != loginUserId && !loginUserId.isEmpty()) {
			List<AgentOwnerBean> agentList = agentOwnerService.getAgentByOwnerLoginUserId(loginUserId);
			model.addAttribute("agentList", agentList);
			model.addAttribute("loginUserinAccountType", loginDetailsBean.getAccountTypeCodesBean().getAccountType());
		}
	
		logger.info("=== Ending  agentList Method === ");
		return ViewConstant.AGENTI_LIST;
	}

	/** non Ajax */
	@RequestMapping("/agentList")
	public String syacAgentList(HttpServletRequest request, Model model) {
		logger.info("SellerBuyerShipperController Call syacAgentList  Method ");
		logger.info("=== Ending  syacAgentList Method === ");
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		String loginUserId = SessionHelper.getLoginUserId(request);
		if (null != loginUserId && !loginUserId.isEmpty()) {
			List<AgentOwnerBean> agentList = agentOwnerService.getAgentByOwnerLoginUserId(loginUserId);
			model.addAttribute("agentList", agentList);
			model.addAttribute("loginUserinAccountType", loginDetailsBean.getAccountTypeCodesBean().getAccountType());
		}
		logger.info("=== Ending  syacAgentList Method === ");
		return ViewConstant.NONAJAX_AGENTI_LIST;
	}

	/**
	 * This method is used to get the agent status by particular agent owner
	 * 
	 * @param agentOwnerId
	 *            selecting all agent owner id
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return agent status model from
	 */
	@RequestMapping("/getaccountstatus/{agentOwnerId}")
	public String getAccountStatus(@PathVariable("agentOwnerId") Integer agentOwnerId, Model model) {
		logger.info("SellerBuyerShipperController Call getAccountStatus  Method ");
		AgentOwnerBean agentOwnerBean = agentOwnerService.findById(agentOwnerId);
		if (null != agentOwnerBean && null != agentOwnerBean.getAgentOwnerId()) {
			LoginDetailsBean agentLoginDetailsBean = agentOwnerBean.getLoginDetailsByAgentLoginUserid();
			model.addAttribute("loginDetailsBean", agentLoginDetailsBean);
			model.addAttribute("agentOwnerBean", agentOwnerBean);

			Short agentAccountStatus = agentLoginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode();

			// When Agent Status is PENDING_OWNER_APPROVAL
			if (agentAccountStatus == ENUM_AccountStatusCodes.PENDING_OWNER_APPROVAL.getStatus()) {
				Short[] codeList = { ENUM_AccountStatusCodes.PENDING_OWNER_APPROVAL.getStatus(), ENUM_AccountStatusCodes.APPROVED.getStatus() };
				model.addAttribute("accountStatusCodeList", accountStatusCodesService.findByAccountStatusCodes(codeList));

				// When Agent Status is SUSPENDED_BY_OWNER
			} else if (agentAccountStatus == ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus()) {
				Short[] codeList = { ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus(), ENUM_AccountStatusCodes.APPROVED.getStatus() };
				model.addAttribute("accountStatusCodeList", accountStatusCodesService.findByAccountStatusCodes(codeList));

				// When Agent Status is PENDING_ADMIN_APPROVAL
			} else if (agentAccountStatus == ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus()) {
				Short[] codeList = { ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus() };
				model.addAttribute("accountStatusCodeList", accountStatusCodesService.findByAccountStatusCodes(codeList));

				// When Agent Status is ACTIVE
			} else if (agentAccountStatus == ENUM_AccountStatusCodes.ACTIVE.getStatus()) {
				Short[] codeList = { ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus(), ENUM_AccountStatusCodes.ACTIVE.getStatus() };
				model.addAttribute("accountStatusCodeList", accountStatusCodesService.findByAccountStatusCodes(codeList));
			} else {
				Short[] codeList = ENUM_AccountStatusCodes.getAllStatusCodes();
				model.addAttribute("accountStatusCodeList", accountStatusCodesService.findByAccountStatusCodes(codeList));
			}

			// Setting privileges list.
			List<Short> privilegesList = new ArrayList<Short>();
			for (ENUM_AgentPrivileges privileges : ENUM_AgentPrivileges.values()) {
				privilegesList.add(privileges.getPrivilege());
			}
			model.addAttribute("agentOwnerPriviList", privilegesList);
		}
		logger.info("=== Ending  getAccountStatus Method === ");
		return "/common/agentStatusAjaxForm";
	}

	/**
	 * This method is used to update the specific agent status by owner agent
	 * 
	 * @param loginDetailsBean
	 *            get the current login user details
	 * @return agent list page
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/updateaccountstatus", method = RequestMethod.POST)
	public ModelAndView updateAccountStatus(@ModelAttribute("agentOwnerBean") AgentOwnerBean agentOwnerBean, RedirectAttributes redirectAttributes, HttpServletRequest request)
			throws UnsupportedEncodingException {
		logger.info("SellerBuyerShipperController Call updateAccountStatus  Method ");
		
		String view = "/sbs/agentlist";
		boolean isStatusChange = false;

		AgentOwnerBean currentAgentOwnerBean = agentOwnerService.findById(agentOwnerBean.getAgentOwnerId());
		short agentAccountStatusCode = agentOwnerBean.getLoginDetailsByAgentLoginUserid().getAccountStatusCodesBean().getAccountStatusCode();

		if (currentAgentOwnerBean.getLoginDetailsByAgentLoginUserid().getAccountStatusCodesBean().getAccountStatusCode() != agentAccountStatusCode) {
			isStatusChange = true;
		}

		// When Agent Status is ACTIVE
		if (ENUM_AccountStatusCodes.ACTIVE.getStatus() == agentOwnerBean.getLoginDetailsByAgentLoginUserid().getAccountStatusCodesBean().getAccountStatusCode()) {
			currentAgentOwnerBean.getLoginDetailsByAgentLoginUserid().setFailedLoginCount(0);

			// When Agent Status is APPROVED
		} else if (ENUM_AccountStatusCodes.APPROVED.getStatus() == agentAccountStatusCode) {
			currentAgentOwnerBean.getLoginDetailsByAgentLoginUserid()
					.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus()));

			// When Agent Status is SUSPENDED_BY_OWNER
		} else {
			currentAgentOwnerBean.getLoginDetailsByAgentLoginUserid()
					.setAccountStatusCodesBean(new AccountStatusCodesBean(agentOwnerBean.getLoginDetailsByAgentLoginUserid().getAccountStatusCodesBean().getAccountStatusCode()));
		}

		currentAgentOwnerBean.setPrivileges(agentOwnerBean.getPrivileges());
		currentAgentOwnerBean.setPurchaseLimit(agentOwnerBean.getPurchaseLimit());
		agentOwnerService.save(currentAgentOwnerBean);

		LoginDetailsBean loginDtlBean = loginDetailsService.findById(agentOwnerBean.getLoginDetailsByAgentLoginUserid().getLoginUserid());
		loginDtlBean.setAccountStatusCodesBean(
				new AccountStatusCodesBean(currentAgentOwnerBean.getLoginDetailsByAgentLoginUserid().getAccountStatusCodesBean().getAccountStatusCode()));
		loginDetailsService.save(loginDtlBean);

		if (isStatusChange) {

			String username = "";
			if (null != loginDtlBean && null != loginDtlBean.getAccountStatusCodesBean()) {
				username = (loginDtlBean.getAccountProfileBean().getFName() + " " + loginDtlBean.getAccountProfileBean().getLName());
			} else {
				username = loginDtlBean.getPublicName();
			}

			if (null != loginDtlBean && null != loginDtlBean.getAccountStatusCodesBean()
					&& ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus() == loginDtlBean.getAccountStatusCodesBean().getAccountStatusCode()) {
				springSecuritySessionRegistry.expiredUserSession(loginDtlBean.getLoginUserid());
			}
			Locale locale = localeResolver.resolveLocale(request);
			EmailData emailData = EmailTemplate.accountActivation(username, loginDtlBean.getLoginUserid(), loginDtlBean.getAccountStatusCodesBean().getAccountStatusCode(),
					messageSource, locale);
			emailService.sendEmail(emailData);

		}
		redirectAttributes.addFlashAttribute("SUCCESS",
				messageSource.getMessage("accountsettingcontroller.updateaccountstatus.success", null, localeResolver.resolveLocale(request)));
		logger.info("=== Ending  updateAccountStatus Method === ");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used to get request for refund and deposit amount.
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return account request
	 */
	@RequestMapping("/accountrequest")
	public String accountRequest() {
		return ViewConstant.ACCOUNT_REQUEST;
	}

	@RequestMapping("/accountRequest")
	public String AccountRequest() {
		return ViewConstant.NONAJAX_ACCOUNT_REQUEST;
	}

}