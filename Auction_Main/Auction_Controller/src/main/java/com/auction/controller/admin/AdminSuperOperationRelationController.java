package com.auction.controller.admin;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountStatusCodes;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.EmailData;
import com.auction.commons.util.EmailTemplate;
import com.auction.commons.util.InternetTiming;
import com.auction.component.SpringSecuritySessionRegistry;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AccountStatusCodesBean;
import com.auction.model.bean.AnnouncementBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.RoyaltyCodesBean;
import com.auction.model.bean.ShippersRegistrationInfoBean;
import com.auction.service.AccountProfileService;
import com.auction.service.AccountStatusCodesService;
import com.auction.service.LoginDetailsService;
import com.auction.service.AnnouncementService;
import com.auction.service.RoyaltyCodeService;
import com.auction.service.security.EmailService;

@Controller
@RequestMapping("/sproptrel/")
public class AdminSuperOperationRelationController {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private AccountStatusCodesService accountStatusCodesService;

	@Autowired
	private RoyaltyCodeService royaltyCodeService;

	@Autowired
	private SpringSecuritySessionRegistry springSecuritySessionRegistry;

	@Autowired
	private EmailService emailService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	@Value("${announcements_depth}")
	private Integer announcementsDepth;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/* Update User Details */
	@RequestMapping("/userdetailslist")
	public String userDetailsList(Model model, HttpServletRequest request) {
		logger.info("AdminSuperOperationRelationController Call userDetailsList Method ");
		Character accountType = SessionHelper.getAccountType(request, false);
		if (accountType == ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType()) {
			model.addAttribute("loginDetailsList", loginDetailsService.getLoginDetailsListByAccountTypes(ENUM_AccountTypeCodes.getTypesForAdminRelationsType()));
		} else if (accountType == ENUM_AccountTypeCodes.ADMIN_OPERATION.getType()) {
			model.addAttribute("loginDetailsList", loginDetailsService.getLoginDetailsListByAccountTypes(ENUM_AccountTypeCodes.getTypesForAdminOperationType()));
		} else if (accountType == ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType()) {
			model.addAttribute("loginDetailsList", loginDetailsService.getLoginDetailsListByAccountTypes(ENUM_AccountTypeCodes.getTypesForAdminSuperuserType()));
		}
		logger.info("=== Ending  userDetailsList Method ===");
		return ViewConstant.ADMIN_USER_DETAILS_LIST;
	}

	/* Update User Details non ajax */
	@RequestMapping("/userDetailsList")
	public String userdetailsList(Model model, HttpServletRequest request) {
		logger.info("AdminSuperOperationRelationController Call userdetailsList  Method ");
		Character accountType = SessionHelper.getAccountType(request, false);
		if (accountType == ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType()) {
			model.addAttribute("loginDetailsList", loginDetailsService.getLoginDetailsListByAccountTypes(ENUM_AccountTypeCodes.getTypesForAdminRelationsType()));
		} else if (accountType == ENUM_AccountTypeCodes.ADMIN_OPERATION.getType()) {
			model.addAttribute("loginDetailsList", loginDetailsService.getLoginDetailsListByAccountTypes(ENUM_AccountTypeCodes.getTypesForAdminOperationType()));
		} else if (accountType == ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType()) {
			model.addAttribute("loginDetailsList", loginDetailsService.getLoginDetailsListByAccountTypes(ENUM_AccountTypeCodes.getTypesForAdminSuperuserType()));
		}
		logger.info("=== Ending  userdetailsList  Method ===");
		return ViewConstant.NONAJAX_ADMIN_USER_DETAILS_LIST;
	}

	/**
	 * This method is used To update UserDetails *
	 * 
	 * @param accountId
	 *            selecting accountId
	 * 
	 * @return user details list page
	 */
	@RequestMapping("/updateUserDetails/{accountId}")
	public String updateUserDetails(Model model, @PathVariable(value = "accountId") Integer accountId, HttpServletRequest request) {
		logger.info("AdminSuperOperationRelationController Call updateUserDetails  Method ");
		if (null != accountId && accountId > 0) {
			LoginDetailsBean loginDetailsBean = loginDetailsService.getLoginDetailsByAccountProfileId(accountId);
			if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid() && !loginDetailsBean.getLoginUserid().isEmpty()) {
				Character adminAccountType = SessionHelper.getAccountType(request, false);
				Character accountType = loginDetailsBean.getAccountTypeCodesBean().getAccountType();
				boolean hasPrivilege = false;
				if (adminAccountType == ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType()) {
					hasPrivilege = Arrays.asList(ENUM_AccountTypeCodes.getTypesForAdminRelationsType()).contains(accountType);
				} else if (adminAccountType == ENUM_AccountTypeCodes.ADMIN_OPERATION.getType()) {
					hasPrivilege = Arrays.asList(ENUM_AccountTypeCodes.getTypesForAdminOperationType()).contains(accountType);
				} else {
					hasPrivilege = true;
				}
				if (hasPrivilege) {
					if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid()) {
						model.addAttribute("loginDetailsBean", loginDetailsBean);
						model.addAttribute("accountStatusCodeList",
								accountStatusCodesService.findByAccountStatusCodes(ENUM_AccountStatusCodes.getStatusCodesForUserDetailsUpdatingByAdmin()));
						if (ENUM_AccountTypeCodes.USER.getType() == loginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
							return ViewConstant.ADMIN_UPDATE_USER_DETAILS_ROLE_USER;
						}
						if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
							model.addAttribute("ownerLoginDetailsBean", loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginDetailsBean.getLoginUserid()));
						}
						model.addAttribute("royaltyCodeList", royaltyCodeService.findAll());
					}
				} else {
					return "redirect: sproptrel/userdetailslist";
				}
			} else {
				return "redirect: sproptrel/userdetailslist";
			}
		} else {
			return "redirect: sproptrel/userdetailslist";
		}
		logger.info("=== Ending  updateUserDetails  Method ===");
		return ViewConstant.ADMIN_UPDATE_USER_DETAILS;
	}

	/**
	 * This method is used To update UserDetails *
	 * 
	 * @param LoginDetailsBean
	 *            selecting LoginDetailsBean
	 * 
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * 
	 * @return user details list page
	 */
	@RequestMapping(value = "/updateUserDetails", method = RequestMethod.POST)
	public ModelAndView updateUserDetails(@ModelAttribute("loginDetailsBean") LoginDetailsBean loginDetailsBean, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		logger.info("AdminSuperOperationRelationController Call updateUserDetails  Method ");
		String view = "/sproptrel/userdetailslist";
		Character adminAccountType = SessionHelper.getAccountType(request, false);
		LoginDetailsBean currentLoginDetailsBean = loginDetailsService.findById(loginDetailsBean.getLoginUserid());
		if (adminAccountType == ENUM_AccountTypeCodes.ADMIN_OPERATION.getType() || adminAccountType == ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType()) {
			if (ENUM_AccountTypeCodes.USER.getType() != currentLoginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
				currentLoginDetailsBean.getAccountProfileBean().setFName(loginDetailsBean.getAccountProfileBean().getFName());
				currentLoginDetailsBean.getAccountProfileBean().setMName(loginDetailsBean.getAccountProfileBean().getMName());
				currentLoginDetailsBean.getAccountProfileBean().setLName(loginDetailsBean.getAccountProfileBean().getLName());
				currentLoginDetailsBean.getAccountProfileBean().setEmailAddress2(loginDetailsBean.getAccountProfileBean().getEmailAddress2());
				currentLoginDetailsBean.getAccountProfileBean().setPhoneNumber2(loginDetailsBean.getAccountProfileBean().getPhoneNumber2());
				currentLoginDetailsBean.getAccountProfileBean().setContactUs(loginDetailsBean.getAccountProfileBean().getContactUs());
				currentLoginDetailsBean.getAccountProfileBean().setPostalAddress(loginDetailsBean.getAccountProfileBean().getPostalAddress());
			}
			currentLoginDetailsBean.getAccountProfileBean().setPhoneNumber1(loginDetailsBean.getAccountProfileBean().getPhoneNumber1());
			accountProfileService.save(currentLoginDetailsBean.getAccountProfileBean());
			loginDetailsService.save(currentLoginDetailsBean);
		} else {
			boolean isStatusChange = false;
			if (currentLoginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode() != loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode()) {
				isStatusChange = true;
			}
			if (ENUM_AccountTypeCodes.USER.getType() != currentLoginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
				currentLoginDetailsBean.setAccountStatusCodesBean(loginDetailsBean.getAccountStatusCodesBean());
				currentLoginDetailsBean.getAccountProfileBean().setAccountId(loginDetailsBean.getAccountProfileBean().getAccountId());
				currentLoginDetailsBean.getAccountProfileBean().setFName(loginDetailsBean.getAccountProfileBean().getFName());
				currentLoginDetailsBean.getAccountProfileBean().setMName(loginDetailsBean.getAccountProfileBean().getMName());
				currentLoginDetailsBean.getAccountProfileBean().setLName(loginDetailsBean.getAccountProfileBean().getLName());
				currentLoginDetailsBean.getAccountProfileBean().setBusinessName(loginDetailsBean.getAccountProfileBean().getBusinessName());
				currentLoginDetailsBean.getAccountProfileBean().setEmailAddress2(loginDetailsBean.getAccountProfileBean().getEmailAddress2());
				currentLoginDetailsBean.getAccountProfileBean().setPostalAddress(loginDetailsBean.getAccountProfileBean().getPostalAddress());
				currentLoginDetailsBean.getAccountProfileBean().setGovernmentId(loginDetailsBean.getAccountProfileBean().getGovernmentId());
				currentLoginDetailsBean.getAccountProfileBean().setPhoneNumber1(loginDetailsBean.getAccountProfileBean().getPhoneNumber1());
				currentLoginDetailsBean.getAccountProfileBean().setPhoneNumber2(loginDetailsBean.getAccountProfileBean().getPhoneNumber2());
				currentLoginDetailsBean.getAccountProfileBean().setContactUs(loginDetailsBean.getAccountProfileBean().getContactUs());
				currentLoginDetailsBean.getAccountProfileBean()
						.setRoyaltyCodesBean(new RoyaltyCodesBean(loginDetailsBean.getAccountProfileBean().getRoyaltyCodesBean().getRoyaltyCode()));
				if (null != loginDetailsBean.getAccountProfileBean().getShippersRegistrationInfoBean()) {
					ShippersRegistrationInfoBean shippersRegistrationInfoBean = new ShippersRegistrationInfoBean();
					shippersRegistrationInfoBean.setAccountId(loginDetailsBean.getAccountProfileBean().getShippersRegistrationInfoBean().getAccountId());
					shippersRegistrationInfoBean
							.setDriverLicenseExpirationDate(DateHelper.getDateStringWithMonthAndYear(request.getParameter("driverLicenseExpirationDate"), true));
					shippersRegistrationInfoBean.setTruckModelYear(loginDetailsBean.getAccountProfileBean().getShippersRegistrationInfoBean().getTruckModelYear());
					shippersRegistrationInfoBean.setTruckType(loginDetailsBean.getAccountProfileBean().getShippersRegistrationInfoBean().getTruckType());
					currentLoginDetailsBean.getAccountProfileBean().setShippersRegistrationInfoBean(shippersRegistrationInfoBean);
				}

			}
			currentLoginDetailsBean.setPublicName(loginDetailsBean.getPublicName());
			currentLoginDetailsBean.getAccountProfileBean().setPublicName(loginDetailsBean.getPublicName());
			currentLoginDetailsBean.getAccountProfileBean().setPhoneNumber1(loginDetailsBean.getAccountProfileBean().getPhoneNumber1());

			accountProfileService.save(currentLoginDetailsBean.getAccountProfileBean());
			currentLoginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode()));
			if (ENUM_AccountStatusCodes.ACTIVE.getStatus() == currentLoginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode()) {
				currentLoginDetailsBean.setFailedLoginCount(0);
			}
			loginDetailsService.save(currentLoginDetailsBean);

			if (isStatusChange) {
				String username = "";
				if ((ENUM_AccountTypeCodes.USER.getType() != currentLoginDetailsBean.getAccountTypeCodesBean().getAccountType())
						&& (null != currentLoginDetailsBean && null != currentLoginDetailsBean.getAccountStatusCodesBean())) {
					username = (currentLoginDetailsBean.getAccountProfileBean().getFName() + " " + currentLoginDetailsBean.getAccountProfileBean().getLName());
				} else {
					username = currentLoginDetailsBean.getPublicName();
				}
				Locale locale = localeResolver.resolveLocale(request);
				springSecuritySessionRegistry.expiredUserSession(currentLoginDetailsBean.getLoginUserid());
				EmailData emailData = EmailTemplate.accountActivation(username, currentLoginDetailsBean.getLoginUserid(),
						currentLoginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode(), messageSource, locale);
				emailService.sendEmail(emailData);

			}
		}
		logger.info("=== Ending  updateUserDetails  Method ===");
		redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("admincontroller.updateuserdetails.success", null, localeResolver.resolveLocale(request)));
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used To view announcement page *
	 * 
	 * 
	 * @return announcementService list data
	 */

	@RequestMapping("/announcement")
	public String announcements(Model model) {
		logger.info("AdminSuperOperationRelationController Call announcements  Method ");
		model.addAttribute("announcementBeanList", announcementService.findAll());
		model.addAttribute("modifiedimestamp",DateHelper.decrementtwoDateDay(InternetTiming.getInternetDateTime(), announcementsDepth));
		logger.info("=== Ending  announcementBean method ===");
		return ViewConstant.ADMIN_ANNOUCEMENT;
	}

	@RequestMapping("/syncannouncement")
	public String syncsannouncements(Model model) {
		logger.info("AdminSuperOperationRelationController Call announcements  Method ");
		model.addAttribute("announcementBeanList", announcementService.findAll());
		 model.addAttribute("modifiedimestamp",DateHelper.decrementtwoDateDay(InternetTiming.getInternetDateTime(), announcementsDepth));
		 logger.info("=== Ending  announcementBean method ===");
		return ViewConstant.NONAJAX_ADMIN_ANNOUCEMENT;
	}

	/**
	 * This method is used show add announcement page *
	 * 
	 * 
	 * @return AM_announcementAjaxForm form
	 */
	@RequestMapping("/addannouncement/{announcementId}")
	public String addannouncement(@PathVariable(value = "announcementId", required = false) Integer announcementId, Model model) {
		logger.info("AdminSuperOperationRelationController Call addannouncement method");
		if (null != announcementId && announcementId > 0) {
			model.addAttribute("announcementBean", announcementService.findById(announcementId));
		} else {
			model.addAttribute("announcementBean", new AnnouncementBean());
		}
		logger.info("=== Ending  addannouncement method ===");
		return "/admin/AM_announcementAjaxForm";
	}

	/**
	 * This method is used show add announcement *
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/saveaddannouncement", method = RequestMethod.POST)
	public ModelAndView addannouncement(@ModelAttribute("announcementBean") AnnouncementBean announcementBean, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("AdminSuperOperationRelationController call saveproductcatalog method");
		LoginDetailsBean loginUserInfo = SessionHelper.getLoginDetailsBean(request);
		if (null != announcementBean) {

			if (null != announcementBean.getAnnouncementId()) {
				AnnouncementBean newAnnouncementBean = new AnnouncementBean();
				newAnnouncementBean = announcementService.findById(announcementBean.getAnnouncementId());
				newAnnouncementBean.setModifiedBy(new AccountProfileBean(loginUserInfo.getAccountProfileBean().getAccountId()));
				newAnnouncementBean.setModifiedimestamp(InternetTiming.getInternetDateTime());
				newAnnouncementBean.setAnnouncement(announcementBean.getAnnouncement());
				announcementService.save(newAnnouncementBean);

				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("admincontroller.addannouncement.success.updated", null, localeResolver.resolveLocale(request)));
			} else {
				AccountProfileBean accountProfileBean = new AccountProfileBean();
				accountProfileBean.setAccountId(loginUserInfo.getAccountProfileBean().getAccountId());
				announcementBean.setAddedtimestamp(InternetTiming.getInternetDateTime());
				announcementBean.setModifiedimestamp(InternetTiming.getInternetDateTime());
				announcementBean.setAddedBy(accountProfileBean);
				announcementService.save(announcementBean);
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("admincontroller.addannouncement.success.added", null, localeResolver.resolveLocale(request)));

			}
		}
		simpMessagingTemplate.convertAndSend("/wssauctions/refreshBanner", "MovingBanner");
		logger.info("=== Ending  saveproductcatalog method ===");
		RedirectView red = new RedirectView("/sproptrel/announcement", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/*
	 * 
	 * this method is used to delete announcement
	 * 
	 * @return announement page
	 */

	@RequestMapping("/deleteannouncement/{announcementId}")
	public @ResponseBody String deleteAnnouncement(@PathVariable("announcementId") Integer announcementId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("AdminSuperOperationRelationController Call deleteannouncement method");
		announcementService.delete(new AnnouncementBean(announcementId));
		simpMessagingTemplate.convertAndSend("/wssauctions/refreshBanner", "MovingBanner");
		logger.info("=== Ending  deleteannouncement method ===");
		return "true";
	}
	
	/*
	 * this method is used to hide the annouuncement]
	 * 
	 * @return announement page
	 * */
	 

	@RequestMapping("/hideannouncement/{announcementId}")
	public @ResponseBody String hideAnnouncement(@PathVariable("announcementId") Integer announcementId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("AdminSuperOperationRelationController Call hideannouncement method");
		 Date modifiedimestamp = DateHelper.decrementtwoDateDay(InternetTiming.getInternetDateTime(), announcementsDepth);
		 announcementService.updateAnnouncementmodifiedimestampBYId(announcementId, modifiedimestamp);
	   simpMessagingTemplate.convertAndSend("/wssauctions/refreshBanner", "MovingBanner");
		logger.info("=== Ending  hideannouncement method ===");
		return "true";
	}
	
	
	
}