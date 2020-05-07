package com.auction.controller;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.LimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.CommonConstants;
import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountDepartmentName;
import com.auction.commons.enums.ENUM_AccountLocationStatus;
import com.auction.commons.enums.ENUM_AccountStatusCodes;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_CommentsStatus;
import com.auction.commons.enums.ENUM_Rating;
import com.auction.commons.security.PasswordSecurity;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.EmailData;
import com.auction.commons.util.EmailTemplate;
import com.auction.commons.util.FileUtils;
import com.auction.commons.util.InternetTiming;
import com.auction.component.SmsGenerateHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.controller.validator.AccountSettingValidator;
import com.auction.model.bean.AccountLocationsBean;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AccountStatusCodesBean;
import com.auction.model.bean.AgentOwnerBean;
import com.auction.model.bean.BankDetailsBean;
import com.auction.model.bean.CommentsBean;
import com.auction.model.bean.EventsProfileBean;
import com.auction.model.bean.LandingPagesBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.LoginLogoutLogBean;
import com.auction.model.bean.LoginLogoutLogIdBean;
import com.auction.model.bean.NotificationCodesBean;
import com.auction.model.bean.RoyaltyCodesBean;
import com.auction.model.entity.AuctionTradeGroupView;
import com.auction.service.AccountLocationsService;
import com.auction.service.AccountProfileService;
import com.auction.service.AgentOwnerService;
import com.auction.service.AuctionTradesService;
import com.auction.service.CommentsService;
import com.auction.service.EventsMeaningService;
import com.auction.service.EventsProfileService;
import com.auction.service.LandingPagesService;
import com.auction.service.LoginDetailsService;
import com.auction.service.LoginLogoutLogService;
import com.auction.service.ProductCatalogService;
import com.auction.service.SellerPicturesService;
import com.auction.service.ShipperPicturesService;
import com.auction.service.security.EmailService;

@Controller
@RequestMapping("/setting")
public class AccountSettingController {

	@Autowired
	private ProductCatalogService productCatalogService;

	@Autowired
	private AuctionTradesService auctionTradesService;

    @Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private SellerPicturesService sellerPicturesService;

	@Autowired
	private ShipperPicturesService shipperPicturesService;

	@Autowired
	private AgentOwnerService agentOwnerService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private EventsMeaningService eventsMeaningService;

	@Autowired
	private EventsProfileService eventsProfileService;

	@Autowired
	private LandingPagesService landingPagesService;

	@Autowired
	private CommentsService commentsService;

	@Autowired
	private AccountLocationsService accountLocationsService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AccountSettingValidator accountSettingValidator;

	@Autowired
	private LoginLogoutLogService loginLogoutLogService;

	@Value("${google.map.key}")
	private String googleMapKey;

	@Value("${generate.otp.number.range}")
	private Integer otpNumberRange;

	private static final String INBOX = "inbox";
	private static final String SENT = "sent";
	private static final String BOTH = "both";

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/**
	 * This method is used to display the welcome or home page of application
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return welcome page
	 */
	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		logger.info("AuctionCommonControllerCall dashboard");
		
		List<AuctionTradeGroupView> auctionTradesViewList = auctionTradesService.findAllAuctionTrades();
		List<String> productNameList = productCatalogService.getProductName();
		List<String> productNameTypeList = productCatalogService.getProductTypeName();
		model.addAttribute("auctionTradeList",auctionTradesViewList);
		model.addAttribute("productNameList",productNameList);
		model.addAttribute("productTypeNameList",productNameTypeList);
		
		return ViewConstant.DASHBOARD;
	}
	
	  @ResponseBody
	  @RequestMapping(value = "/searchProduct", produces = { MediaType.APPLICATION_JSON_VALUE })
	  public List<AuctionTradeGroupView> searchChart(HttpServletRequest request) {
		  logger.info("AuctionCommonControllerCall searchChart");
		  
		  String productName = request.getParameter("name");
		  String productTypeName = request.getParameter("type");
		  
		  Integer productId = productCatalogService.getProductId(productName, productTypeName);		 		  
		  List<AuctionTradeGroupView> auctionTradesViewList = auctionTradesService.findAllAuctionTradesById(productId);
		  
		  return auctionTradesViewList;	  
	  }
	 
	/**
	 * This method is used by default all the user display loginOtpbox page
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return loginOtpbox page
	 */
	@RequestMapping("/syncloginotpbox")
	public String syncloginOtpbox() {
		return ViewConstant.LOGIN_OTP_BOX;
	}

	@RequestMapping("/ajax/dashboard")
	public String ajaxDashboard(Model model) {
		return ViewConstant.AJAX_DASHBOARD;
	}

	/**
	 * This method is used to return the registration phase two page and also sent
	 * the related data
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @return registration phase two
	 */
	@RequestMapping("/registeraccountprofile")
	public String registerAccountProfile(Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call registeraccountprofile method");
		Boolean checkUserVerifyOtp = false;
		SessionHelper.checkUserVerifyOtp(request, checkUserVerifyOtp);
		Map<String, Object> mapModel = model.asMap();
		if (!mapModel.containsKey("AccountProfileBean")) {
			AccountProfileBean accountProfileBean = new AccountProfileBean();
			accountProfileBean.setPreferredLanguage(localeResolver.resolveLocale(request).getLanguage());
			model.addAttribute("AccountProfileBean", accountProfileBean);
		}
		model.addAttribute("emailAddress", SessionHelper.getLoginUserId(request));
		model.addAttribute("publicName", SessionHelper.getPublicName(request));
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);

		char accounType = loginDetailsBean.getAccountTypeCodesBean().getAccountType();
		if ((accounType == ENUM_AccountTypeCodes.SELLER.getType()) && (accounType == ENUM_AccountTypeCodes.BUYER.getType())
				&& (accounType == ENUM_AccountTypeCodes.SHIPPER.getType())) {
			Map<Short, String> accountLocationStatusList = new HashMap<Short, String>(0);
			for (ENUM_AccountLocationStatus val : ENUM_AccountLocationStatus.values()) {
				if (val != ENUM_AccountLocationStatus.DELETED) {
					accountLocationStatusList.put(val.getStatus(), val.getDesc());
				}
			}
			model.addAttribute("accountLocationStatusList", accountLocationStatusList);
		}
		logger.info("AccountSettingController Class call registeraccountprofile Ending   method");
		if (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.USER.getType()) {
			return ViewConstant.REGISTRATION_SECOND_PHASE_FOR_ROLE_USER;
		} else {
			return ViewConstant.REGISTRATION_SECOND_PHASE;
		}

	}

	/**
	 * This method is used to persist the registration phase two user details
	 * 
	 * @param accountProfileBean
	 *            by enter the user for registration phase two details
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @return welcome page
	 */
	@RequestMapping(value = "/registeraccountprofile", method = RequestMethod.POST)
	public ModelAndView saveRegistrationSecondPhase(@ModelAttribute("AccountProfileBean") AccountProfileBean accountProfileBean,
			@RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AccountSettingController Class call saveRegistrationSecondPhase method");
		String fileName = "";
		String viewPage = "/setting/dashboard";
		Blob blob = null;
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		List<String> errorStack = accountSettingValidator.validateRegistrationPhase2(loginDetailsBean, accountProfileBean, request);
		Integer otp = (Integer) request.getSession().getAttribute("generateOtp");
		Date otpExpiredDate = (Date) request.getSession().getAttribute("otpExpireddate");
		Integer otpCount = (Integer) request.getSession().getAttribute("resendOtpCount");
		Boolean checkUserVerifyOtp = (Boolean) request.getSession().getAttribute("checkUserVerifyOtp");

		if (null == errorStack || errorStack.size() == 0) {

			// Setting default values for account profile.
			accountProfileBean.setPublicName(loginDetailsBean.getPublicName());
			accountProfileBean.setRating(ENUM_Rating.RATING5.getStatus());
			accountProfileBean.setRoyaltyCodesBean(new RoyaltyCodesBean(CommonConstants.DUFAULT_ROYALTY_CODE));

			// Checking and setting preferred language selection
			if (null == accountProfileBean.getPreferredLanguage() || accountProfileBean.getPreferredLanguage().isEmpty()) {
				accountProfileBean.setPreferredLanguage(localeResolver.resolveLocale(request).getLanguage());
			}

			// checking profile picture is uploaded or not.
			if (null != profilePicture && !profilePicture.isEmpty()) {
				try {
					byte[] contents = profilePicture.getBytes();
					blob = BlobProxy.generateProxy(contents);
				} catch (Exception e) {
					e.printStackTrace();
				}
				fileName = profilePicture.getOriginalFilename();
				if (fileName.contains(".")) {
					String[] arr = fileName.split("\\.");
					fileName = ("account_profile_" + new Date().getTime() + "." + arr[1]);
				} else {
					fileName = ("account_profile_" + new Date().getTime());
				}
				accountProfileBean.setProfileImage(fileName);
			}

			// Saving account profile
			if (ENUM_AccountTypeCodes.USER.getType() == loginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
				accountProfileBean.setFName("");
				accountProfileBean.setMName("");
				accountProfileBean.setLName("");
				accountProfileBean.setBusinessName("");
				accountProfileBean.setGovernmentId("");
				// accountProfileBean.setPhoneNumber1("");
			}
			// Saving Shipper Profile
			if (null != accountProfileBean.getShippersRegistrationInfoBean() && null != accountProfileBean.getAccountId()) {
				accountProfileBean.getShippersRegistrationInfoBean().setAccountId(accountProfileBean.getAccountId());
				accountProfileBean.getShippersRegistrationInfoBean()
						.setDriverLicenseExpirationDate(DateHelper.getDateStringWithMonthAndYear(request.getParameter("driverLicenseExpirationDate"), true));
				accountProfileBean.getShippersRegistrationInfoBean().setTruckModelYear(accountProfileBean.getShippersRegistrationInfoBean().getTruckModelYear());
				accountProfileBean.getShippersRegistrationInfoBean().setTruckType(accountProfileBean.getShippersRegistrationInfoBean().getTruckType());
			}
			// Uploading physical file on file save in db .
			if (null != profilePicture && !profilePicture.isEmpty()) {
				accountProfileBean.setContents(blob);
			}
			accountProfileBean.setOtp(otp);
			accountProfileBean.setOtpExpiredDate(otpExpiredDate);
			accountProfileBean.setOtpCount(otpCount);
			accountProfileService.save(accountProfileBean);
			loginDetailsBean = loginDetailsService.findById(loginDetailsBean.getLoginUserid());
			if (ENUM_AccountTypeCodes.USER.getType() == loginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
				loginDetailsBean.setAccountProfileBean(accountProfileBean);
				loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.ACTIVE.getStatus()));
				loginDetailsService.save(loginDetailsBean);
				SessionHelper.setLoginSession(request, loginDetailsBean);
			} else if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
				loginDetailsBean.setAccountProfileBean(accountProfileBean);
				if (true == checkUserVerifyOtp) {
					loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_OWNER_APPROVAL.getStatus()));
					SessionHelper.setAccountStatusMessage(request,
							messageSource.getMessage("accountsettingcontroller.saveregistrationsecondphase.success.agent", null, localeResolver.resolveLocale(request)));
				} else {
					// viewPage ="/auth/logout";
					loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_PHONE_NUMBER.getStatus()));
					SessionHelper.setAccountStatusMessage(request,
							messageSource.getMessage("otpverification.validation.phonepending", null, localeResolver.resolveLocale(request)));

				}
				loginDetailsService.save(loginDetailsBean);
				AgentOwnerBean agentOwnerBean = agentOwnerService.getAgentOwnerByLoginUserId(loginDetailsBean.getLoginUserid());

				LoginDetailsBean agentLoginDetailsBean = agentOwnerBean.getLoginDetailsByAgentLoginUserid();

				String agetnUserName = (agentLoginDetailsBean.getAccountProfileBean().getFName() + " " + agentLoginDetailsBean.getAccountProfileBean().getLName());

				LoginDetailsBean ownerLoginDetailsBean = agentOwnerBean.getLoginDetailsByOwnerLoginUserid();
				String ownerUserName = (ownerLoginDetailsBean.getAccountProfileBean().getFName() + " " + ownerLoginDetailsBean.getAccountProfileBean().getLName());
				Locale locale = localeResolver.resolveLocale(request);
				EmailData emailData = EmailTemplate.pendingForApprovalOfAccount(agetnUserName, agentLoginDetailsBean.getLoginUserid(), true, messageSource, locale);
				emailService.sendEmail(emailData);
				emailData = EmailTemplate.ownerApproval(ownerUserName, ownerLoginDetailsBean.getLoginUserid(), agetnUserName, agentLoginDetailsBean.getLoginUserid(), messageSource,
						locale);
				emailService.sendEmail(emailData);
				SessionHelper.setLoginSession(request, loginDetailsBean);

			} else {
				loginDetailsBean.setAccountProfileBean(accountProfileBean);
				if (true == checkUserVerifyOtp) {
					loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus()));
					SessionHelper.setAccountStatusMessage(request,
							messageSource.getMessage("accountsettingcontroller.saveregistrationsecondphase.success.normal", null, localeResolver.resolveLocale(request)));
				} else {
					// viewPage ="/auth/logout";
					loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_PHONE_NUMBER.getStatus()));
					SessionHelper.setAccountStatusMessage(request,
							messageSource.getMessage("otpverification.validation.phonepending", null, localeResolver.resolveLocale(request)));
				}
				loginDetailsService.save(loginDetailsBean);
				SessionHelper.setLoginSession(request, loginDetailsBean);
			}

			// Save Location Management
			if (null != accountProfileBean.getNewAccountLocation() && accountProfileBean.getNewAccountLocation().size() > 0) {
				for (AccountLocationsBean accountLocation : accountProfileBean.getNewAccountLocation()) {
					if (null != accountLocation.getLocationName()) {
						accountLocation.setAccountProfileBean(new AccountProfileBean(accountProfileBean.getAccountId()));
						accountLocationsService.save(accountLocation);
					}
				}
			}
			logger.info("AccountSettingController Class call saveRegistrationSecondPhase Ending   method");
		} else {
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
			viewPage = "/setting/registeraccountprofile";
			redirectAttributes.addFlashAttribute("AccountProfileBean", accountProfileBean);
		}
		RedirectView red = new RedirectView(viewPage, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is load the change password page and set the user login id.
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @return change password page
	 */
	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String changepassword(Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call changepassword method");
		model.addAttribute("emailAddress", SessionHelper.getLoginUserId(request));
		model.addAttribute("loginDetailsBean", new LoginDetailsBean());
		logger.info("AccountSettingController Class call changepassword Ending   method");
		return ViewConstant.CHANGE_PASSWORD;
	}

	/**
	 * This method is used to change the current user password and set the new
	 * password
	 * 
	 * @param loginDetailsBean
	 *            current password and new password enter by user
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @return if success the return login page otherwise return change password
	 *         page
	 */
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public ModelAndView changepassword(@ModelAttribute("loginDetailsBean") LoginDetailsBean loginDetailsBean, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AccountSettingController Class call changepassword method");
		String view = "/auth/logout";
		List<String> errorStack = accountSettingValidator.validatePasswordChange(loginDetailsBean, request);
		if (null == errorStack || errorStack.size() == 0) {
			LoginDetailsBean currentLoginDetailsBean = loginDetailsService.findById(SessionHelper.getLoginUserId(request));
			if (null != currentLoginDetailsBean) {
				if (loginDetailsBean != null && PasswordSecurity.isValidPassword(loginDetailsBean.getPassword(), currentLoginDetailsBean.getPassword())) {
					String newPassword = request.getParameter("newPassword");
					currentLoginDetailsBean.setPasswordSalt(PasswordSecurity.generatePasswordSalt());
					currentLoginDetailsBean.setPassword(PasswordSecurity.generateSecuredPassword(newPassword, currentLoginDetailsBean.getPasswordSalt()));
					loginDetailsService.save(currentLoginDetailsBean);
					redirectAttributes.addFlashAttribute("SUCCESS",
							messageSource.getMessage("accountsettingcontroller.changepassword.success", null, localeResolver.resolveLocale(request)));
				} else {
					redirectAttributes.addFlashAttribute("ERROR",
							messageSource.getMessage("accountsettingcontroller.changepassword.error", null, localeResolver.resolveLocale(request)));
					view = "/setting/changepassword";
				}
			}
			logger.info("AccountSettingController Class call changepassword Ending   method");
		} else {
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
			view = "/setting/changepassword";
		}
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is load the Edit profile page with also load the login user
	 * details
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @return edit profile page
	 */
	@RequestMapping("/editProfile")
	public String editProfile(Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call editProfile method");
		LoginDetailsBean loginUser = SessionHelper.getLoginDetailsBean(request);
		Boolean checkUserVerifyOtp = false;
		SessionHelper.checkUserVerifyOtp(request, checkUserVerifyOtp);
		if (null != loginUser) {
			if (ENUM_AccountTypeCodes.isUserAgent(loginUser.getAccountTypeCodesBean().getAccountType())) {
				model.addAttribute("Agent", true);
				model.addAttribute("ownerLoginDetailsBean", loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid()));
			} else {
				model.addAttribute("Agent", false);
			}
			AccountProfileBean accountProfileBean = accountProfileService.findById(loginUser.getAccountProfileBean().getAccountId());
			model.addAttribute("accountProfileBean", accountProfileBean);
			model.addAttribute("publicName", SessionHelper.getPublicName(request));
			model.addAttribute("emailAddress", SessionHelper.getLoginUserId(request));
			model.addAttribute("userAccountStatus", loginUser.getAccountStatusCodesBean().getAccountStatusCode());
			if (ENUM_AccountTypeCodes.USER.getType() == loginUser.getAccountTypeCodesBean().getAccountType()) {
				return ViewConstant.EDIT_PROFILE_USER;
			}
			model.addAttribute("landingPagesBean", landingPagesService.findAll());
			model.addAttribute("accountLocationsList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(loginUser.getAccountProfileBean().getAccountId(),
					ENUM_AccountLocationStatus.getNonDeletedStatus()));

			Map<Short, String> accountLocationStatusList = new HashMap<Short, String>(0);
			for (ENUM_AccountLocationStatus val : ENUM_AccountLocationStatus.values()) {
				if (val != ENUM_AccountLocationStatus.DELETED) {
					accountLocationStatusList.put(val.getStatus(), val.getDesc());
				}
			}

			model.addAttribute("accountLocationStatusList", accountLocationStatusList);
		}
		logger.info("AccountSettingController Class call editProfile Ending   method");
		return ViewConstant.EDIT_PROFILE;
	}

	/**
	 * This method is load the Edit profile page with also load the login user
	 * details
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @return edit profile page
	 */
	@RequestMapping("/syncseditProfile")
	public String syncseditProfile(Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call syncseditProfile method");
		LoginDetailsBean loginUser = SessionHelper.getLoginDetailsBean(request);
		Boolean checkUserVerifyOtp = false;
		SessionHelper.checkUserVerifyOtp(request, checkUserVerifyOtp);
		if (null != loginUser) {
			if (ENUM_AccountTypeCodes.isUserAgent(loginUser.getAccountTypeCodesBean().getAccountType())) {
				model.addAttribute("Agent", true);
				model.addAttribute("ownerLoginDetailsBean", loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid()));
			} else {
				model.addAttribute("Agent", false);
			}
			AccountProfileBean accountProfileBean = accountProfileService.findById(loginUser.getAccountProfileBean().getAccountId());
			model.addAttribute("accountProfileBean", accountProfileBean);
			model.addAttribute("publicName", SessionHelper.getPublicName(request));
			model.addAttribute("emailAddress", SessionHelper.getLoginUserId(request));
			model.addAttribute("userAccountStatus", loginUser.getAccountStatusCodesBean().getAccountStatusCode());
			if (ENUM_AccountTypeCodes.USER.getType() == loginUser.getAccountTypeCodesBean().getAccountType()) {
				return ViewConstant.EDIT_PROFILE_USER;
			}
			model.addAttribute("landingPagesBean", landingPagesService.findAll());
			model.addAttribute("accountLocationsList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(loginUser.getAccountProfileBean().getAccountId(),
					ENUM_AccountLocationStatus.getNonDeletedStatus()));

			Map<Short, String> accountLocationStatusList = new HashMap<Short, String>(0);
			for (ENUM_AccountLocationStatus val : ENUM_AccountLocationStatus.values()) {
				if (val != ENUM_AccountLocationStatus.DELETED) {
					accountLocationStatusList.put(val.getStatus(), val.getDesc());
				}
			}

			model.addAttribute("accountLocationStatusList", accountLocationStatusList);
		}
		logger.info("AccountSettingController Class call syncseditProfile Ending   method");
		return ViewConstant.NONAJAX_EDIT_PROFILE;
	}

	/**
	 * This method is used to update the current login user profile details
	 * 
	 * @param accountProfileBean
	 *            update all account profile details by entering the user
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @return welcome page
	 */

	@RequestMapping(value = "/saveEditProfile", method = RequestMethod.POST)
	public ModelAndView saveEditProfile(@ModelAttribute("accountProfileBean") AccountProfileBean accountProfileBean,
			@RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		logger.info("AccountSettingController Class call saveEditProfile method");
		Boolean phonumberChange = true;
		Boolean isPendingphonumber = true;
		Boolean checkUserVerifyOtp = (Boolean) request.getSession().getAttribute("checkUserVerifyOtp");
		String fileName = "";
		String view = "/setting/dashboard";
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		List<String> errorStack = accountSettingValidator.validateEditProfile(loginDetailsBean, accountProfileBean, request);
		if (null == errorStack || errorStack.size() == 0) {
			AccountProfileBean currentAccountProfile = accountProfileService.findById(accountProfileBean.getAccountId());
			if (ENUM_AccountTypeCodes.USER.getType() == loginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
				accountProfileBean.setFName("");
				accountProfileBean.setMName("");
				accountProfileBean.setLName("");
				accountProfileBean.setBusinessName("");
				accountProfileBean.setGovernmentId("");
				// accountProfileBean.setPhoneNumber1("");
				currentAccountProfile.setPreferredLanguage(accountProfileBean.getPreferredLanguage());
			} else {
				if (loginDetailsBean.getAccountProfileBean().getPhoneNumber1().compareTo(accountProfileBean.getPhoneNumber1()) == 0) {
					phonumberChange = true;
				} else {
					phonumberChange = false;
				}
				if (loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode() == ENUM_AccountStatusCodes.PENDING_PHONE_NUMBER.getStatus()) {
					isPendingphonumber = false;

				} else {
					isPendingphonumber = true;
				}
				currentAccountProfile.setEmailAddress2(accountProfileBean.getEmailAddress2());
				if (checkUserVerifyOtp) {
					currentAccountProfile.setPhoneNumber1(accountProfileBean.getPhoneNumber1());
				}
				currentAccountProfile.setPhoneNumber2(accountProfileBean.getPhoneNumber2());
				currentAccountProfile.setContactUs(accountProfileBean.getContactUs());
				currentAccountProfile.setPreferredLanguage(accountProfileBean.getPreferredLanguage());
				if (accountProfileBean.getLandingPagesBean().getLandingPageId() == 0) {
					currentAccountProfile.setLandingPagesBean(null);
				} else {
					currentAccountProfile.setLandingPagesBean(new LandingPagesBean(accountProfileBean.getLandingPagesBean().getLandingPageId()));
				}
				if (!checkUserVerifyOtp) {
					currentAccountProfile.setOtp(accountProfileBean.getOtp());
					currentAccountProfile.setOtpExpiredDate(accountProfileBean.getOtpExpiredDate());
					currentAccountProfile.setOtpCount(accountProfileBean.getOtpCount());
				}
				if (null != accountProfileBean.getBankDetailsBean()) {
					BankDetailsBean bankBean = new BankDetailsBean();
					bankBean.setAccountId(accountProfileBean.getBankDetailsBean().getAccountId());
					bankBean.setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance());
					bankBean.setBankAccountNumber(accountProfileBean.getBankDetailsBean().getBankAccountNumber());
					bankBean.setBankName(accountProfileBean.getBankDetailsBean().getBankName());
					bankBean.setBlockedAmount(accountProfileBean.getBankDetailsBean().getBlockedAmount());
					bankBean.setIban(accountProfileBean.getBankDetailsBean().getIban());
					bankBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
					bankBean.setCashposition(accountProfileBean.getBankDetailsBean().getCashposition());
					currentAccountProfile.setBankDetailsBean(bankBean);
				}
				if (null != accountProfileBean.getNewAccountLocation() && accountProfileBean.getNewAccountLocation().size() > 0) {
					for (AccountLocationsBean accountLocation : accountProfileBean.getNewAccountLocation()) {
						if (null != accountLocation.getLocationName()) {
							accountLocation.setAccountProfileBean(new AccountProfileBean(accountProfileBean.getAccountId()));
							accountLocationsService.save(accountLocation);
						}
					}
				}
			}

			localeResolver.setLocale(request, response, new Locale(accountProfileBean.getPreferredLanguage()));

			if (null != accountProfileBean.getNotificationCodesBean() && null != accountProfileBean.getNotificationCodesBean().getNotificationCode()) {
				currentAccountProfile.setNotificationCodesBean(new NotificationCodesBean(accountProfileBean.getNotificationCodesBean().getNotificationCode()));
			} else {
				currentAccountProfile.setNotificationCodesBean(null);
			}

			if (null != profilePicture && !profilePicture.isEmpty()) {
				Blob blob = null;
				try {
					byte[] contents = profilePicture.getBytes();
					blob = BlobProxy.generateProxy(contents);
				} catch (Exception e) {
					e.printStackTrace();
				}
				fileName = profilePicture.getOriginalFilename();
				if (fileName.contains(".")) {
					String[] arr = fileName.split("\\.");
					fileName = ("account_profile_" + new Date().getTime() + "." + arr[1]);
				} else {
					fileName = ("account_profile_" + new Date().getTime());
				}
				currentAccountProfile.setProfileImage(fileName);
				currentAccountProfile.setContents(blob);

			}

			accountProfileService.save(currentAccountProfile);
			loginDetailsBean = loginDetailsService.getLoginDetailsByAccountProfileId(currentAccountProfile.getAccountId());
			if (true == checkUserVerifyOtp) {
				SessionHelper.setLoginSession(request, loginDetailsBean);
				if (isPendingphonumber != true) {
					if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
						loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_OWNER_APPROVAL.getStatus()));
						SessionHelper.setAccountStatusMessage(request,
								messageSource.getMessage("accountsettingcontroller.saveregistrationsecondphase.success.agent", null, localeResolver.resolveLocale(request)));
					} else {
						loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus()));
						SessionHelper.setAccountStatusMessage(request,
								messageSource.getMessage("accountsettingcontroller.saveregistrationsecondphase.success.normal", null, localeResolver.resolveLocale(request)));
					}
				}
				loginDetailsService.save(loginDetailsBean);
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("accountsettingcontroller.saveEditProfile.success", null, localeResolver.resolveLocale(request)));
			} else {
				if (phonumberChange == false) {
					// view ="/auth/logout";
					if (isPendingphonumber != true) {
						if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
							loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_OWNER_APPROVAL.getStatus()));
						} else {
							loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus()));
						}
					}
					loginDetailsService.save(loginDetailsBean);
				} else {
					SessionHelper.setLoginSession(request, loginDetailsBean);
					redirectAttributes.addFlashAttribute("SUCCESS",
							messageSource.getMessage("accountsettingcontroller.saveEditProfile.success", null, localeResolver.resolveLocale(request)));
				}

			}

		} else {
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
			view = "/setting/editProfile";
		}
		logger.info("AccountSettingController Class call saveEditProfile Ending   method");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/* Notification Event Master */
	/**
	 * This method is used to get the all the notification event by current login
	 * user
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @return notification subscription page
	 */
	@RequestMapping("/notificationeventsubscription")
	public String notificationeventsubscription(Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call notificationeventsubscription method");
		EventsProfileBean eventsProfileBean = eventsProfileService.findById(SessionHelper.getAccountProfileId(request));
		String eventMap = eventsProfileBean.getEventMap();
		List<String> eventMaps = Arrays.asList(eventMap);
		model.addAttribute("eventMaps", eventMaps);
		model.addAttribute("eventsMeaningBeanList", eventsMeaningService.findAll());
		logger.info("AccountSettingController Class call notificationeventsubscription Ending   method");
		return ViewConstant.NOTIFFICATION_EVENT_SUBSCRIPTION;
	}

	/** non ajax */
	@RequestMapping("/notificationeventSubscription")
	public String syscNotificationeventsubscription(Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call syscNotificationeventsubscription method");
		EventsProfileBean eventsProfileBean = eventsProfileService.findById(SessionHelper.getAccountProfileId(request));
		String eventMap = eventsProfileBean.getEventMap();
		List<String> eventMaps = Arrays.asList(eventMap);
		model.addAttribute("eventMaps", eventMaps);
		model.addAttribute("eventsMeaningBeanList", eventsMeaningService.findAll());
		logger.info("AccountSettingController Class call syscNotificationeventsubscription Ending   method");
		return ViewConstant.NONAJAX_NOTIFFICATION_EVENT_SUBSCRIPTION;
	}

	/**
	 * This method is used to persist the notification subscription event
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @return same page
	 */
	@RequestMapping("/savenotificationeventsubscription")
	public ModelAndView savenotificationeventsubscription(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AccountSettingController Class call savenotificationeventsubscription method");
		String[] eventMaps = request.getParameterValues("eventMap");
		String eventMap = null;
		if (null != eventMaps && eventMaps.length > 0) {
			eventMap = Arrays.toString(eventMaps);
			eventMap = eventMap.replaceAll("\\s", "").replaceAll("\\[", "").replaceAll("\\]", "");
		}
		EventsProfileBean eventsProfileBean = eventsProfileService.findById(SessionHelper.getAccountProfileId(request));
		eventsProfileBean.setAccountProfilebean(accountProfileService.findById(SessionHelper.getAccountProfileId(request)));
		eventsProfileBean.setEventMap(eventMap);
		eventsProfileService.save(eventsProfileBean);
		redirectAttributes.addFlashAttribute("SUCCESS",
				messageSource.getMessage("accountsettingcontroller.savenotificationeventsubscription.success", null, localeResolver.resolveLocale(request)));
		RedirectView red = new RedirectView("notificationeventsubscription", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		logger.info("AccountSettingController Class call savenotificationeventsubscription Ending   method");
		return new ModelAndView(red);
	}

	/**
	 * This method is used to get all the uploaded image for current login user
	 * 
	 * @param accountId
	 *            pass the image by particular account id folder
	 * @param fileName
	 *            pass the file name by get the image
	 * @return seller info and picture page
	 */
	@RequestMapping("/pictures/{accountId}/{fileName:.+}")
	public @ResponseBody byte[] getProcuctImage(@PathVariable("accountId") Integer accountId, @PathVariable("fileName") String fileName) {
		logger.info("AccountSettingController Class call getProcuctImage method");
		Blob pictureFile = sellerPicturesService.getBySellerInfoPictures(fileName, accountId);
		byte[] imgBytes = new byte[1024];
		try {
			// imgBytes = FileUtils.getFileContent(accountId,
			// CommonConstants.SELLER_PICTURE_PATH, fileName);
			imgBytes = FileUtils.getFileContent(pictureFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("AccountSettingController Class call getProcuctImage Ending   method");
		return imgBytes;
	}

	/**
	 * This method is used to get all the uploaded image for current login user
	 * 
	 * @param accountId
	 *            pass the image by particular account id folder
	 * @param fileName
	 *            pass the file name by get the image
	 * @return shipper info and picture page
	 */
	@RequestMapping("/shipper/pictures/{accountId}/{fileName:.+}")
	public @ResponseBody byte[] getProcuctImageInshipper(@PathVariable("accountId") Integer accountId, @PathVariable("fileName") String fileName) {
		logger.info("AccountSettingController Class call getProcuctImageInshipper method");
		Blob pictureFile = shipperPicturesService.getByShipperInfoPictures(fileName, accountId);
		byte[] imgBytes = new byte[1024];
		try {
			// imgBytes = FileUtils.getFileContent(accountId,
			// CommonConstants.SELLER_PICTURE_PATH, fileName);
			imgBytes = FileUtils.getFileContent(pictureFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("AccountSettingController Class call getProcuctImageInshipper Ending   method");
		return imgBytes;
	}

	/**
	 * This method is used to get all the uploaded image for current login user
	 * 
	 * @param accountId
	 *            pass the image by particular account id folder
	 * @param fileName
	 *            pass the file name by get the image
	 * @return seller info and picture page
	 */
	@RequestMapping("/profilePictures/{accountId}/{fileName:.+}")
	public @ResponseBody byte[] getProfilePictures(@PathVariable("accountId") Integer accountId, @PathVariable("fileName") String fileName) {
		logger.info("AccountSettingController Class call getProfilePictures method");
		Blob editProfileImage = accountProfileService.getByEditProfileImage(fileName, accountId);
		byte[] imgBytes = new byte[1024];
		try {
			imgBytes = FileUtils.getFileContent(editProfileImage);
			// imgBytes = FileUtils.getFileContent(accountId,
			// CommonConstants.PROFILE_PICTURE_PATH, fileName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("AccountSettingController Class call getProfilePictures Ending   method");
		return imgBytes;
	}

	/**
	 * This method is used to add location on user
	 * 
	 * @param accountId
	 *            selecting accountId
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return location model from
	 */
	@RequestMapping("/addlocation")
	public String addlocation(Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call addlocation method");
		if (null != request.getParameter("locationId") && !request.getParameter("locationId").isEmpty()) {
			Integer locationId = Integer.parseInt(request.getParameter("locationId"));
			if (null != locationId && locationId > 0) {
				AccountLocationsBean accountLocationsBean = accountLocationsService.findById(locationId);
				model.addAttribute("accountLocation", accountLocationsBean);
			}
		}
		Map<Short, String> accountLocationStatusList = new HashMap<Short, String>(0);
		for (ENUM_AccountLocationStatus val : ENUM_AccountLocationStatus.values()) {
			if (val != ENUM_AccountLocationStatus.DELETED) {
				accountLocationStatusList.put(val.getStatus(), val.getDesc());
			}
		}
		logger.info("AccountSettingController Class call addlocation Ending   method");
		model.addAttribute("accountLocationStatusList", accountLocationStatusList);
		model.addAttribute("googleMapKey", googleMapKey);
		return "/common/locationManagementAjaxForm";
	}

	/**
	 * This method is used to remove location on user
	 * 
	 * @param accountId
	 *            selecting accountId
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return location model from
	 */
	@RequestMapping("/deletelocation/{locationId}")
	public @ResponseBody String deletelocation(@PathVariable("locationId") Integer locationId) {
		logger.info("AccountSettingController Class call deletelocation method");
		AccountLocationsBean accountLocationsBean = accountLocationsService.findById(locationId);
		accountLocationsBean.setStatus(ENUM_AccountLocationStatus.DELETED.getStatus());
		accountLocationsService.save(accountLocationsBean);
		logger.info("AccountSettingController Class call deletelocation Ending   method");
		return "success";
	}
	/* Comment Management */

	/**
	 * This method is used to Comments Management view return
	 * 
	 * @param request
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return Comment Management view return
	 */

	@RequestMapping(value = "/comments", method = { RequestMethod.GET, RequestMethod.POST })
	public String comments(HttpServletRequest request, Model model) {
		logger.info("AccountSettingController Class call comments method");
		Integer accountId = SessionHelper.getAccountProfileId(request);

		String commentFilter = request.getParameter("commentFilter");
		if (null != commentFilter && commentFilter.equalsIgnoreCase(INBOX)) {
			model.addAttribute("commentslist", commentsService.getByInboxAccountProfileByAccountId(accountId));
		} else if (null != commentFilter && commentFilter.equalsIgnoreCase(SENT)) {
			model.addAttribute("commentslist", commentsService.getBySentAccountProfileByAccountId(accountId));
		} else if (null != commentFilter && commentFilter.equalsIgnoreCase(BOTH)) {
			model.addAttribute("commentslist", commentsService.getByAccountProfileByAccountId(accountId));

		} else {
			model.addAttribute("commentslist", commentsService.getByAccountProfileByAccountId(accountId));
			commentFilter = BOTH;
		}
		logger.info("AccountSettingController Class call comments Ending   method");
		model.addAttribute("commentFilter", commentFilter);

		return ViewConstant.COMMENTS_MANAGEMENT;
	}

	/**
	 * This method is used to Comments Management view return
	 * 
	 * @param request
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return Comment Management view return
	 */

	@RequestMapping(value = "/commentsList", method = { RequestMethod.GET, RequestMethod.POST })
	public String syncComments(HttpServletRequest request, Model model) {
		logger.info("AccountSettingController Class call commentsList method");
		Integer accountId = SessionHelper.getAccountProfileId(request);
		String commentFilter = request.getParameter("commentFilter");
		if (null != commentFilter && commentFilter.equalsIgnoreCase(INBOX)) {
			model.addAttribute("commentslist", commentsService.getByInboxAccountProfileByAccountId(accountId));
		} else if (null != commentFilter && commentFilter.equalsIgnoreCase(SENT)) {
			model.addAttribute("commentslist", commentsService.getBySentAccountProfileByAccountId(accountId));
		} else if (null != commentFilter && commentFilter.equalsIgnoreCase(BOTH)) {
			model.addAttribute("commentslist", commentsService.getByAccountProfileByAccountId(accountId));

		} else {
			model.addAttribute("commentslist", commentsService.getByAccountProfileByAccountId(accountId));
			commentFilter = BOTH;
		}
		logger.info("AccountSettingController Class call commentsList Ending   method");
		model.addAttribute("commentFilter", commentFilter);
		return ViewConstant.NONAJAX_COMMENTS_MANAGEMENT;
	}

	/**
	 * This method is used To Add Comment View Return
	 *
	 * * @param request
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * 
	 * @return commentsAjax page
	 */
	@RequestMapping("/addcomment")
	public String addComment(Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call addComment method");
		Character loggedUserAccountType = SessionHelper.getAccountType(request, false);
		// Integer accountId = SessionHelper.getAccountProfileId(request);
		model.addAttribute("commentsBean", new CommentsBean());

		Map<Integer, String> departmentList = new HashMap<Integer, String>(0);
		for (ENUM_AccountDepartmentName val : ENUM_AccountDepartmentName.values()) {
			departmentList.put(val.getCode(), val.getName());
		}
		model.addAttribute("departmentList", departmentList);

		// Checking user is from Administrator Department.
		List<Character> accountType = Arrays.asList(ENUM_AccountTypeCodes.ADMIN_FINANCE.getType(), ENUM_AccountTypeCodes.ADMIN_OPERATION.getType(),
				ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType(), ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType(), ENUM_AccountTypeCodes.ADMIN_SHIPPING.getType());

		if (accountType.contains(loggedUserAccountType)) {
			List<AccountProfileBean> accountProfileBeanList = new ArrayList<AccountProfileBean>();
			accountProfileBeanList = accountProfileService.findByAccountTypeCodes(ENUM_AccountTypeCodes.getNonAdminAndNonUserTypes());
			model.addAttribute("accountProfileBeanList", accountProfileBeanList);
		}
		logger.info("AccountSettingController Class call addComment Ending   method");
		return "/common/commentsAjax";
	}

	/**
	 * This method is used To Add Comment
	 *
	 * * @param request
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return commentslist page
	 */
	@RequestMapping(value = "/addcomment", method = RequestMethod.POST)
	public ModelAndView addcomment(@ModelAttribute("commentsBean") CommentsBean commentsBean, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AccountSettingController Class call addcomment method");
		String view = "/setting/comments";
		if ((null == commentsBean.getAccountProfileBeanByAccountId() || null == commentsBean.getAccountProfileBeanByAccountId().getAccountId())
				&& (null == commentsBean.getCommentText() || commentsBean.getCommentText().isEmpty())) {
			redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("commentsajax.validation.error.save.update", null, localeResolver.resolveLocale(request)));
		} else if (null != commentsBean.getCommentText() && commentsBean.getCommentText().length() > 255) {
			redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("commentsajax.validation.commenttext.maxlength", null, localeResolver.resolveLocale(request)));
		} else {
			commentsBean.setAccountProfileBeanByCreatedBy(new AccountProfileBean(SessionHelper.getAccountProfileId(request)));

			commentsBean.setCreatedTimestamp(InternetTiming.getInternetDateTime());
			commentsBean.setCommentStatus(ENUM_CommentsStatus.PENDING.getDesc());

			if (null != commentsBean.getCommentId()) {
				commentsBean.setAccountProfileBeanByUpdatedBy(new AccountProfileBean(SessionHelper.getAccountProfileId(request)));
				commentsBean.setUpdatedTimestamp(InternetTiming.getInternetDateTime());
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("accountsettingcontroller.addcomment.updated.success", null, localeResolver.resolveLocale(request)));
			} else {
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("accountsettingcontroller.addcomment.created.success", null, localeResolver.resolveLocale(request)));
			}
			commentsService.save(commentsBean);
		}
		List<Character> accountType = Arrays.asList(ENUM_AccountTypeCodes.ADMIN_FINANCE.getType(), ENUM_AccountTypeCodes.ADMIN_OPERATION.getType(),
				ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType(), ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType(), ENUM_AccountTypeCodes.ADMIN_SHIPPING.getType());

		Character loggedUserAccountType = SessionHelper.getAccountType(request, false);
		if (accountType.contains(loggedUserAccountType)) {
			view = "/admin/commentslist";
		}
		logger.info("AccountSettingController Class call addcomment Ending   method");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used To Edit Comment * @param commentId selecting commentId
	 * 
	 * @return commentsAjax page
	 */

	@RequestMapping("/editcomment/{commentId}")
	public String editCommment(@PathVariable("commentId") Integer commentId, Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call editCommment method");
		Character loggedUserAccountType = SessionHelper.getAccountType(request, false);
		model.addAttribute("commentsBean", commentsService.findById(commentId));
		Map<Integer, String> departmentList = new HashMap<Integer, String>(0);
		for (ENUM_AccountDepartmentName val : ENUM_AccountDepartmentName.values()) {
			departmentList.put(val.getCode(), val.getName());
		}
		model.addAttribute("departmentList", departmentList);

		// Checking user is from Administrator Department.
		List<Character> accountType = Arrays.asList(ENUM_AccountTypeCodes.ADMIN_FINANCE.getType(), ENUM_AccountTypeCodes.ADMIN_OPERATION.getType(),
				ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType(), ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType(), ENUM_AccountTypeCodes.ADMIN_SHIPPING.getType());

		if (accountType.contains(loggedUserAccountType)) {
			List<AccountProfileBean> accountProfileBeanList = new ArrayList<AccountProfileBean>();
			accountProfileBeanList = accountProfileService.findByAccountTypeCodes(ENUM_AccountTypeCodes.getNonAdminAndNonUserTypes());
			model.addAttribute("accountProfileBeanList", accountProfileBeanList);
		}
		logger.info("AccountSettingController Class call editCommment Ending   method");
		return "/common/commentsAjax";
	}

	/**
	 * This method is used to view comment view return * @param commentId selecting
	 * commentId
	 * 
	 * @return viewCommentsAjax page
	 */
	@RequestMapping("/viewcomment/{commentId}")
	public String viewCommment(@PathVariable("commentId") Integer commentId, Model model, HttpServletRequest request) {
		logger.info("AccountSettingController Class call viewCommment method");
		model.addAttribute("commentsBean", commentsService.findById(commentId));
		logger.info("AccountSettingController Class call viewCommment Ending   method");
		return "/common/viewCommentsAjax";
	}

	/**
	 * This method is used by default all the user display loginOtpbox page
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return loginOtpbox page
	 * @throws LimitExceededException
	 */
	@RequestMapping("/otpverification/{phoneNumber}")
	public String otpverification(@PathVariable("phoneNumber") String phoneNumber, Model model, HttpServletRequest request) throws LimitExceededException {
		logger.info("AccountSettingController Class call otpverification  method");
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		LoginDetailsBean newLoginDetailsBean = loginDetailsService.findById(loginDetailsBean.getLoginUserid());
		int newOtpCount = 0;
		long remainingMiunte = 0;
		SessionHelper.setLoginUserPhonenNumber(request, phoneNumber);
		Integer otpCount = newLoginDetailsBean.getOtpCount();
		if (otpCount != null) {
			newOtpCount = otpCount + 1;
		} else {
			newOtpCount = 1;
		}
		if (newOtpCount == 4) {
			updateLoginDetail(newLoginDetailsBean, newLoginDetailsBean.getOtp(), newOtpCount);
			model.addAttribute("otpCount", messageSource.getMessage("otpverification.validation.otp.countotp", null, localeResolver.resolveLocale(request)));
		} else if (newOtpCount > 4) {
			Date otpExpireddate = newLoginDetailsBean.getOtpExpiredDate();
			if (otpExpireddate != null) {
				remainingMiunte = DateHelper.getRemainingTimeToSms(otpExpireddate);
				if (0 == remainingMiunte) {
					newOtpCount = 0;
					sendOtpEditProfile(newLoginDetailsBean, otpNumberRange, newOtpCount, phoneNumber);
				} else {
					String msg = messageSource.getMessage("otpverification.validation.otp.after", null, localeResolver.resolveLocale(request)) + "  " + remainingMiunte + " "
							+ messageSource.getMessage("otpverification.validation.otp.minutes", null, localeResolver.resolveLocale(request));
					model.addAttribute("remainingMiunte", msg);
				}
			}
		} else {
			sendOtpEditProfile(newLoginDetailsBean, otpNumberRange, newOtpCount, phoneNumber);
		}
		logger.info("AccountSettingController Class call otpverification Ending   method");
		return "/common/otpVerificationAjaxForm";
	}

	/**
	 * This method is used to reSendOtp
	 * 
	 * @return same page
	 * @throws Throwable
	 */

	@ResponseBody
	@RequestMapping(value = "/reSendOtp", method = RequestMethod.POST)
	public String reSendOtp(HttpServletRequest request, Model model) throws Throwable {
		logger.info("AccountSettingController Class call reSendOtp method");
		int newOtpCount = 0;
		String msg = "sendOtp";
		String phoneNumber = (String) request.getSession().getAttribute("phonenNumber");
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		LoginDetailsBean newLoginDetailsBean = loginDetailsService.findById(loginDetailsBean.getLoginUserid());
		Integer otpCount = newLoginDetailsBean.getOtpCount();
		if (otpCount != null) {
			newOtpCount = otpCount + 1;
		} else {
			newOtpCount = 1;
		}
		if (newOtpCount == 5) {
			updateLoginDetail(newLoginDetailsBean, newLoginDetailsBean.getOtp(), newOtpCount);
			msg = "countotp";
		} else if (newOtpCount > 5) {
			Date otpExpireddate = newLoginDetailsBean.getOtpExpiredDate();
			if (otpExpireddate != null) {
				long remainingMiunte = DateHelper.getRemainingTimeToSms(otpExpireddate);
				if (0 == remainingMiunte) {
					newOtpCount = 0;
					sendOtpEditProfile(newLoginDetailsBean, otpNumberRange, newOtpCount, phoneNumber);
				} else {
					msg = "  " + remainingMiunte + " ";
				}
			}
		} else {
			sendOtpEditProfile(newLoginDetailsBean, otpNumberRange, newOtpCount, phoneNumber);
		}
		logger.info("AccountSettingController Class call reSendOtp Ending   method");
		return msg;

	}

	/**
	 * This method is used to check the validateOtp or not
	 * 
	 * @param mobileOtp
	 *            mobileOtp by enter user
	 * @return same page
	 */

	@ResponseBody
	@RequestMapping(value = "/validateOtp", method = RequestMethod.POST)
	public String validateOtp(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
		logger.info("AccountSettingController Class call validateOtp method");
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		LoginDetailsBean newLoginDetailsBean = loginDetailsService.findById(loginDetailsBean.getLoginUserid());
		Date otpExpireddate = newLoginDetailsBean.getOtpExpiredDate();
		int newOtpCount = 0;
		int mobileOtp = Integer.parseInt(request.getParameter("mobileOtp"));
		if (newLoginDetailsBean.getOtp() == mobileOtp) {
			Boolean otpDiffTime = DateHelper.twoDateAndTimeDifference(otpExpireddate);
			if (otpDiffTime == true) {
				Boolean CheckUserVerifyOtp = true;
				SessionHelper.checkUserVerifyOtp(request, CheckUserVerifyOtp);
				logger.info("AccountSettingController Class call validateOtp Ending   method");
				return "";
			} else {
				logger.info("AccountSettingController Class call validateOtp Ending   method");
				return "wrongnumber";
			}
		} else {
			Integer otpCount = newLoginDetailsBean.getOtpCount();
			if (otpCount != null) {
				newOtpCount = otpCount + 1;
			} else {
				newOtpCount = 1;
			}
			updateLoginDetail(newLoginDetailsBean, newLoginDetailsBean.getOtp(), newOtpCount);
			if (otpCount == 4) {
				logger.info("AccountSettingController Class call validateOtp Ending   method");
				return "otpCount";

			} else {
				logger.info("AccountSettingController Class call validateOtp Ending   method");
				return "invalid";
			}

		}
	}

	/**
	 * This method is used to Login Time and Logout difference 45 more then send
	 * user mobileOtp
	 * 
	 */
	@RequestMapping("/loginotpbox")
	public ModelAndView loginOtpbox(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		SessionHelper.isLoginUserOtpVerify(request, false);
		String loginUserId = (String) request.getSession().getAttribute("loginUserId");
		LoginDetailsBean loginDetailsBean = loginDetailsService.findById(loginUserId);
		RedirectView red = null;
		logger.info("AuthenticationController Class call loginOtpbox method");
		int newOtpCount = 0;
		long remainingMiunte = 0;
		Integer otpCount = loginDetailsBean.getAccountProfileBean().getOtpCount();
		if (otpCount != null) {
			newOtpCount = otpCount + 1;
		} else {
			newOtpCount = 1;
		}
		if (newOtpCount == 4) {
			redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("otpverification.validation.otp.countotp", null, localeResolver.resolveLocale(request)));
			updateAccountProfile(loginDetailsBean, loginDetailsBean.getAccountProfileBean().getOtp(), newOtpCount);
			red = new RedirectView("/auth/logout", true);
		} else if (newOtpCount > 4) {
			Date otpExpireddate = loginDetailsBean.getAccountProfileBean().getOtpExpiredDate();
			if (otpExpireddate != null) {
				remainingMiunte = DateHelper.getRemainingTimeToSms(otpExpireddate);
				if (0 == remainingMiunte) {
					newOtpCount = 1;
					sendOtpLoginTime(loginDetailsBean, otpNumberRange, newOtpCount);
					red = new RedirectView("/setting/syncloginotpbox", true);
					red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
					return new ModelAndView(red);
				} else {
					String msg = messageSource.getMessage("otpverification.validation.otp.after", null, localeResolver.resolveLocale(request)) + "  " + remainingMiunte + " "
							+ messageSource.getMessage("otpverification.validation.otp.minutes", null, localeResolver.resolveLocale(request));
					redirectAttributes.addFlashAttribute("ERROR", msg);
					red = new RedirectView("/auth/logout", true);
				}
			}
		} else {
			sendOtpLoginTime(loginDetailsBean, otpNumberRange, newOtpCount);
			red = new RedirectView("/setting/syncloginotpbox", true);

		}
		logger.info("AccountSettingController Class call loginOtpbox Ending   method");
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);

	}

	/**
	 * This method is used to check the validateOtp or not
	 * 
	 * @param mobileOtp
	 *            mobileOtp by enter user
	 * @return mobileOtp validate true return login page and return false same page
	 */

	@RequestMapping(value = "/loginTimeValidateOtp", method = RequestMethod.POST)
	public ModelAndView loginTimeValidateOtp(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
		String view = "/setting/syncloginotpbox";
		logger.info("AuthenticationController Class call validateOtp method");
		String loginUserId = (String) request.getSession().getAttribute("loginUserId");
		LoginDetailsBean loginDetailsBean = loginDetailsService.findById(loginUserId);
		int mobileOtp = Integer.parseInt(request.getParameter("mobileOtp"));
		int newOtpCount = 0;
		if (loginDetailsBean.getAccountProfileBean().getOtp() == mobileOtp) {
			logger.info("generateOtp and  mobileOtp is equal");
			Boolean otpDiffTime = DateHelper.twoDateAndTimeDifference(loginDetailsBean.getAccountProfileBean().getOtpExpiredDate());
			if (otpDiffTime) {
				/***
				 * User loginTimeValidateOtp Set Login Time and login Count And SessionHelper
				 * Set loginDetailsBean
				 *
				 */
				SessionHelper.checkUserVerifyOtp(request, true);
				loginDetailsBean.setLoginCount(loginDetailsBean.getLoginCount() + 1);
				loginDetailsBean.setFailedLoginCount(0);
				updateAccountProfile(loginDetailsBean, loginDetailsBean.getAccountProfileBean().getOtp(), 0);
				loginDetailsService.save(loginDetailsBean);
				LoginLogoutLogIdBean loginLogoutLogIdBean = new LoginLogoutLogIdBean();
				loginLogoutLogIdBean.setLoginDetailsBean(new LoginDetailsBean(loginDetailsBean.getLoginUserid()));
				logger.info("loginLog time set user" + loginDetailsBean.getLoginUserid());
				loginLogoutLogIdBean.setLoginTimestamp(DateHelper.removeMilliSecondFromDate(InternetTiming.getInternetDateTime()));
				logger.info("loginLog time set  time " + DateHelper.removeMilliSecondFromDate(InternetTiming.getInternetDateTime()));
				LoginLogoutLogBean loginLogoutLogBean = new LoginLogoutLogBean(loginLogoutLogIdBean);
				loginLogoutLogService.save(loginLogoutLogBean);
				accountProfileService.save(loginDetailsBean.getAccountProfileBean());
				logger.info("save loginLogoutLogBean " + loginLogoutLogBean.getLogoutTimestamp());
				loginDetailsBean.setLoginTimestamp(loginLogoutLogBean.getId().getLoginTimestamp());

				if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
					LoginDetailsBean agentLoginDetails = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginDetailsBean.getLoginUserid());
					SessionHelper.setLoginSession(request, loginDetailsBean, agentLoginDetails);
				} else {
					SessionHelper.setLoginSession(request, loginDetailsBean);
				}
				redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("otpverification.validation.msg.verify", null, localeResolver.resolveLocale(request)));
				if (loginDetailsBean != null) {
					if (loginDetailsBean.getAccountProfileBean().getLandingPagesBean() != null) {
						SessionHelper.isLoginUserOtpVerify(request, false);
						String view1 = "/" + loginDetailsBean.getAccountProfileBean().getLandingPagesBean().getLandingPageUrl();
						view = view1;
					} else {
						SessionHelper.isLoginUserOtpVerify(request, false);
						view = "/auctions/auctionlist";
					}
				}

			} else {
				redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("otpverification.validation.otp.wrongnumber", null, localeResolver.resolveLocale(request)));
				view = "/auth/logout";
			}
		} else {
			Integer otpCount = loginDetailsBean.getAccountProfileBean().getOtpCount();
			if (otpCount != null) {
				newOtpCount = otpCount + 1;
			} else {
				newOtpCount = 1;
			}
			updateAccountProfile(loginDetailsBean, loginDetailsBean.getAccountProfileBean().getOtp(), newOtpCount);
			if (otpCount == 4) {
				redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("otpverification.validation.otp.countotp", null, localeResolver.resolveLocale(request)));
				view = "/auth/logout";
			} else {
				redirectAttributes.addFlashAttribute("otpCount", messageSource.getMessage("otpverification.validation.otp.invalid", null, localeResolver.resolveLocale(request)));
			}

		}
		logger.info("AccountSettingController Class call validateOtp Ending   method");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used Login Time to reSendOtp
	 * 
	 * @return same page
	 */

	@RequestMapping(value = "/reSendOtpLoginTime", method = RequestMethod.POST)
	public ModelAndView reSendOtpLoginTime(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		String loginUserId = (String) request.getSession().getAttribute("loginUserId");
		LoginDetailsBean loginDetailsBean = loginDetailsService.findById(loginUserId);
		logger.info("AuthenticationController Class call reSendOtp method");
		int newOtpCount = 0;
		Integer otpCount = loginDetailsBean.getAccountProfileBean().getOtpCount();
		if (otpCount != null) {
			newOtpCount = otpCount + 1;
		} else {
			newOtpCount = 1;
		}
		if (newOtpCount == 5) {
			redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("otpverification.validation.otp.countotp", null, localeResolver.resolveLocale(request)));
			updateAccountProfile(loginDetailsBean, loginDetailsBean.getAccountProfileBean().getOtp(), newOtpCount);
			accountProfileService.save(loginDetailsBean.getAccountProfileBean());
			SessionHelper.destroyLoginSession(request);
			session.setAttribute("ERROR", messageSource.getMessage("otpverification.validation.otp.countotp", null, localeResolver.resolveLocale(request)));
			String view = "/auth/login";
			RedirectView red = new RedirectView(view, true);
			return new ModelAndView(red);
		} else if (newOtpCount > 5) {
			Date otpExpireddate = loginDetailsBean.getAccountProfileBean().getOtpExpiredDate();
			if (otpExpireddate != null) {
				long remainingMiunte = DateHelper.getRemainingTimeToSms(otpExpireddate);
				if (0 == remainingMiunte) {
					newOtpCount = 1;
					sendOtpLoginTime(loginDetailsBean, otpNumberRange, newOtpCount);
				} else {
					redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("otpverification.validation.otp.after", null, localeResolver.resolveLocale(request))
							+ "  " + remainingMiunte + " " + messageSource.getMessage("otpverification.validation.otp.minutes", null, localeResolver.resolveLocale(request)));
					SessionHelper.destroyLoginSession(request);
					String view = "/auth/login";
					RedirectView red = new RedirectView(view, true);
					return new ModelAndView(red);
				}
			}
		} else {
			sendOtpLoginTime(loginDetailsBean, otpNumberRange, newOtpCount);
		}
		logger.info("AccountSettingController Class call reSendOtp Ending   method");
		return null;

	}

	/***
	 * this method used login time otp
	 * 
	 * @param loginDetailsBean
	 * @param otpNumberRange
	 * @param newOtpCount
	 */
	public void sendOtpLoginTime(LoginDetailsBean loginDetailsBean, Integer otpNumberRange, Integer newOtpCount) {
		logger.info("AccountSettingController Class call sendOtp   method");
		logger.info("AccountSettingController Class call sendOtp   method ==otpNumberRange" + otpNumberRange);
		logger.info("AccountSettingController Class call sendOtp   method ==newOtpCount" + newOtpCount);
		Integer generateOtpNumber = SmsGenerateHelper.generateOtp(otpNumberRange);
		try {
			SmsGenerateHelper.sendOtpSms(generateOtpNumber, loginDetailsBean.getAccountProfileBean().getPhoneNumber1());
		} catch (Exception e) {
			logger.info("AccountSettingController Class call sendOtp   method " + e);
			logger.error("AccountSettingController Class call send " + e);
		}
		updateAccountProfile(loginDetailsBean, generateOtpNumber, newOtpCount);
	}

	/**
	 * this method use generateOtpNumber,newOtpCount,OtpExpiredDate update
	 * AccountProfile
	 * 
	 * @param loginDetailsBean
	 * @param generateOtpNumber
	 * @param newOtpCount
	 */
	public void updateAccountProfile(LoginDetailsBean loginDetailsBean, Integer generateOtpNumber, Integer newOtpCount) {
		loginDetailsBean.getAccountProfileBean().setOtp(generateOtpNumber);
		loginDetailsBean.getAccountProfileBean().setOtpCount(newOtpCount);
		loginDetailsBean.getAccountProfileBean().setOtpExpiredDate(InternetTiming.getInternetDateTime());
		accountProfileService.save(loginDetailsBean.getAccountProfileBean());
	}

	public void updateLoginDetail(LoginDetailsBean newLoginDetailsBean, Integer generateOtpNumber, Integer newOtpCount) {
		newLoginDetailsBean.setOtp(generateOtpNumber);
		newLoginDetailsBean.setOtpCount(newOtpCount);
		newLoginDetailsBean.setOtpExpiredDate(InternetTiming.getInternetDateTime());
		loginDetailsService.save(newLoginDetailsBean);
	}

	/***
	 * this method used EditProfile time otp
	 * 
	 * @param request
	 * @param otpNumberRange
	 * @param newOtpCount
	 * @param phoneNumber
	 */
	public void sendOtpEditProfile(LoginDetailsBean newLoginDetailsBean, Integer otpNumberRange, Integer newOtpCount, String phoneNumber) {
		logger.info("AccountSettingController Class call sendOtpEditProfile   method");
		logger.info("AccountSettingController Class call sendOtpEditProfile   method ==otpNumberRange" + otpNumberRange);
		logger.info("AccountSettingController Class call sendOtpEditProfile   method ==newOtpCount" + newOtpCount);
		Integer generateOtpNumber = SmsGenerateHelper.generateOtp(otpNumberRange);
		try {
			SmsGenerateHelper.sendOtpSms(generateOtpNumber, phoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("AccountSettingController Class call sendOtpEditProfile   method " + e);
			logger.error("AccountSettingController Class call sendOtpEditProfile " + e);
		}
		updateLoginDetail(newLoginDetailsBean, generateOtpNumber, newOtpCount);
	}

}