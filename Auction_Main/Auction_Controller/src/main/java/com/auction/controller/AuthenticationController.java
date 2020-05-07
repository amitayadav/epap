package com.auction.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.CommonConstants;
import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountStatusCodes;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_AgentPrivileges;
import com.auction.commons.security.PasswordSecurity;
import com.auction.commons.security.TokenSecurity;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.EmailData;
import com.auction.commons.util.EmailTemplate;
import com.auction.commons.util.InternetTiming;
import com.auction.component.AnnouncementHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.controller.validator.AuthenticationValidator;
import com.auction.model.bean.AccountStatusCodesBean;
import com.auction.model.bean.AgentOwnerBean;
import com.auction.model.bean.IpBlockingDetailsBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AccountTypeCodesService;
import com.auction.service.AgentOwnerService;
import com.auction.service.IpBlockingDetailsService;
import com.auction.service.LoginDetailsService;
import com.auction.service.security.EmailService;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private AccountTypeCodesService accountTypeCodesService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private AgentOwnerService agentOwnerService;

	@Autowired
	private AuthenticationValidator authenticationValidator;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IpBlockingDetailsService ipBlockingDetailsService;

	@Value("${generate.otp.number.range}")
	private Integer otpNumberRange;

	@Autowired
	private AnnouncementHelper announcementHelper;

	@Value("${announcementsView}")
	private String announcementsView;

	@Value("${announcements_depth}")
	private Integer announcementsDepth;

	@Value("${auction_trade_depth}")
	private Integer auctionTradeDepth;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/**
	 * This method is used by default all the user display login page
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return login page
	 */
	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		model.addAttribute("loginDetailsBean", new LoginDetailsBean());

		return ViewConstant.LOGIN_PAGE;
	}

	/**
	 * This method is used to display message and login button when session expired
	 * and request type is XMLHttpRequest
	 * 
	 * @return loginbox
	 */
	@RequestMapping("/loginbox")
	public String loginbox() {
		return "/common/loginbox";
	}

	/**
	 * This method is used to handling authentication failed by spring security.
	 * 
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @param request
	 *            HttpServletRequest is for getting request parameter(s).
	 * @return login page
	 */
	@RequestMapping("/loginfailed")
	public ModelAndView loginfailed(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("AuthenticationController Class call loginfailed method");
		Locale locale = localeResolver.resolveLocale(request);
		if (null != request.getUserPrincipal()) {
			/*System.out.println(request.getUserPrincipal().getName());*/
		}
		String errorStackStr1 = request.getSession().getAttribute("ERROR").toString();
		String msg1 = messageSource.getMessage("otpverification.msg.loginLogoutdiff", null, locale);
		if (errorStackStr1 != msg1) {
			if (null != request.getParameter("failed") && !request.getParameter("failed").isEmpty() && request.getParameter("failed").equals("true")) {
				if (request.getSession().getAttribute("ERROR").toString().contains("##")) {
					String errorStackStr = request.getSession().getAttribute("ERROR").toString();
					String[] errorStackArray = errorStackStr.split("##");
					List<String> errorStack = new ArrayList<String>(0);
					for (int i = 0; i < errorStackArray.length; i++) {
						if (null != errorStackArray[i] && !errorStackArray[i].isEmpty()) {
							errorStack.add(errorStackArray[i]);
						}
					}
					redirectAttributes.addFlashAttribute("errorStack", errorStack);
				} else {
					redirectAttributes.addFlashAttribute("ERROR", request.getSession().getAttribute("ERROR"));
				}
				request.getSession().removeAttribute("ERROR");
			}
		} else {
			request.getSession().removeAttribute("ERROR");
		}
		String msg = messageSource.getMessage("userauthenticationprovider.msg.ipblock", null, locale);
		RedirectView red = null;
		if (errorStackStr1 == msg) {
			redirectAttributes.addFlashAttribute("errorStack", msg);
			red = new RedirectView("/home", true);
		} else {
			red = new RedirectView("/auth/login", true);
		}
		logger.info("===loginfailed Ending method==========");
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used to return the registration phase two page and also send
	 * the account type data
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return registration phase one
	 */
	@RequestMapping("/registration")
	public String userLoginRequest(Model model) {
		logger.info("AuthenticationController Class call userLoginRequest method");
		Map<String, Object> mapModel = model.asMap();
		if (!mapModel.containsKey("loginDetailsBean")) {
			model.addAttribute("loginDetailsBean", new LoginDetailsBean());
		}
		logger.info("===== userLoginRequest Ending method ==========");
		model.addAttribute("accountTypeCode", accountTypeCodesService.getAllAccountTypeCodeExceptAdmin(ENUM_AccountTypeCodes.getNonAdminAndNonUserTypes()));
		return ViewConstant.REGISTRATION_FIRST_PHASE;
	}

	/**
	 * This method is used to return the forget password page
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return forget password page
	 */
	@RequestMapping("/forgotpassword")
	public String forgotpassword(Model model) {
		logger.info("AuthenticationController Class call forgotpassword method");
		logger.info("===== forgotpassword Ending method ======");
		return ViewConstant.FORGET_PASSWORD_PAGE;
	}

	/**
	 * This method is used to return the account verify page
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return account verify page
	 */
	@RequestMapping("/account-activation")
	public String accountverify(Model model) {
		logger.info("AuthenticationController Class call accountverify method");
		logger.info("===== accountverify Ending method ======");
		return ViewConstant.ACCOUNT_VERIFY;
	}

	/**
	 * This method is used to particular user logout functionality
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @return login page
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("Authentication Controller Class call logout method");
		logger.info("===== logout Ending method ======");
		SessionHelper.destroyLoginSession(request);
		Map<String, Object> map = model.asMap();
		if (null != map && map.size() > 0) {
			Set<String> keys = map.keySet();
			for (String key : keys) {
				redirectAttributes.addFlashAttribute(key, map.get(key));
			}
		}
		logger.info("===== logout Ending method ======");
		RedirectView red = new RedirectView("/auth/login", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used to check the user authentication by entering the user
	 * email and password
	 * 
	 * @param loginDetailsBean
	 *            by user entering user email and password
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param rAttr
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @return if user authentication success then return the home or registration
	 *         phase 2 from otherwise redirect to login page
	 */
	@RequestMapping(value = "/loginAuthentication")
	public ModelAndView loginAuthentication(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.info("Authentication Controller Class call loginAuthentication method");
		String view = "/auth/login";
		String loginUserId = request.getUserPrincipal().getName();
		Boolean isLoginUserVerifyOtp = (Boolean) request.getSession().getAttribute("isLoginUserVerifyOtp");
		if (null != loginUserId && !loginUserId.isEmpty()) {

			LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);// loginDetailsService.findById(request.getUserPrincipal().getName());
			// Block will execute if provided email address(login User Id) is not null and
			// not empty.
			if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid()) {

				Short statusCode = loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode();

				if ((statusCode == ENUM_AccountStatusCodes.PENDING_PHASE_2.getStatus()) || statusCode == ENUM_AccountStatusCodes.ACTIVE.getStatus()
						|| statusCode == ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus() || statusCode == ENUM_AccountStatusCodes.PENDING_OWNER_APPROVAL.getStatus()) {

					if ((null == loginDetailsBean.getAccountProfileBean() || null == loginDetailsBean.getAccountProfileBean().getAccountId())
							|| (statusCode == ENUM_AccountStatusCodes.PENDING_PHASE_2.getStatus())) {
						view = "/setting/registeraccountprofile";
					} else {
						if (null != loginDetailsBean.getAccountProfileBean() && null != loginDetailsBean.getAccountProfileBean().getLandingPagesBean()) {
							view = "/" + loginDetailsBean.getAccountProfileBean().getLandingPagesBean().getLandingPageUrl();
						} else {
							view = "/setting/dashboard";
						}
						localeResolver.setLocale(request, response, new Locale(loginDetailsBean.getAccountProfileBean().getPreferredLanguage()));
					}
				}
			} else {
				// Block will execute if any other status is set for user.
				if (!isLoginUserVerifyOtp) {
					redirectAttributes.addFlashAttribute("ERROR",
							(messageSource.getMessage("authenticationcontroller.loginauthentication.error.providedemail", null, localeResolver.resolveLocale(request)) + " "
									+ loginUserId + " "
									+ messageSource.getMessage("authenticationcontroller.loginauthentication.error.notregistered", null, localeResolver.resolveLocale(request))));
				}

			}
		} else {
			// Block will execute if provided email address(login User Id) is null or empty.
			redirectAttributes.addFlashAttribute("ERROR",
					(messageSource.getMessage("authenticationcontroller.loginauthentication.error.providedemail", null, localeResolver.resolveLocale(request)) + " " + loginUserId
							+ " " + messageSource.getMessage("authenticationcontroller.loginauthentication.error.notregistered", null, localeResolver.resolveLocale(request))));
		}
		logger.info("===== loginAuthentication Ending method ======");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used to persist the registration phase one user details by
	 * enter
	 * 
	 * @param loginDetailsBean
	 *            by enter the user details
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @return login page
	 */
	@RequestMapping(value = "/registrationphase1", method = RequestMethod.POST)
	public ModelAndView saveRegistrationPhase1(@ModelAttribute("loginDetailsBean") LoginDetailsBean loginDetailsBean, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		logger.info("Authentication Controller Class call registrationphase1 method");
		boolean unique = true;
		String view = "/auth/login";
		List<String> errorStack = authenticationValidator.validateRegistrationPhase1(loginDetailsBean, request);
		LoginDetailsBean ownerLoginDetailsBean = null;
		if (null == errorStack || errorStack.size() == 0) {
			List<String> errors = new ArrayList<String>(0);
			if (!loginDetailsService.isPublicNameUnique(loginDetailsBean.getPublicName())) {
				errors.add(messageSource.getMessage("authenticationcontroller.saveregistrationphase1.error.enteredpublicname", null, localeResolver.resolveLocale(request)) + " "
						+ "<b>" + loginDetailsBean.getPublicName() + "</b> "
						+ messageSource.getMessage("authenticationcontroller.saveregistrationphase1.error.alreadyregistered", null, localeResolver.resolveLocale(request)));
				unique = false;
				loginDetailsBean.setPublicName(null);
			}
			if (!loginDetailsService.isPrimaryEmailUnique(loginDetailsBean.getLoginUserid())) {
				errors.add(messageSource.getMessage("authenticationcontroller.saveregistrationphase1.error.enteredemailaddress", null, localeResolver.resolveLocale(request)) + " "
						+ "<b>" + loginDetailsBean.getLoginUserid() + "</b> "
						+ messageSource.getMessage("authenticationcontroller.saveregistrationphase1.error.alreadyregistered", null, localeResolver.resolveLocale(request)));
				unique = false;
				loginDetailsBean.setLoginUserid(null);
			}
			if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType().charValue())) {
				List<LoginDetailsBean> ownersList = loginDetailsService
						.findByAccountStatusCodesAccountStatusCodeInAndLoginUserid(new Short[] { ENUM_AccountStatusCodes.ACTIVE.getStatus() }, loginDetailsBean.getOwnerEmail());
				if (null != ownersList && ownersList.size() > 0) {
					ownerLoginDetailsBean = ownersList.get(0);
				} else {
					errors.add(
							messageSource.getMessage("authenticationcontroller.saveregistrationphase1.error.enteredowneremailaddress", null, localeResolver.resolveLocale(request))
									+ " " + "<b>" + loginDetailsBean.getOwnerEmail() + "</b> "
									+ messageSource.getMessage("authenticationcontroller.saveregistrationphase1.error.notregistered", null, localeResolver.resolveLocale(request)));
					unique = false;
					loginDetailsBean.setOwnerEmail(null);
				}
			}
			if (unique) {
				if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
					// agentOwnerBean =
					// loginDetailsService.findById(loginDetailsBean.getOwnerEmail());
					char agentAccountType = loginDetailsBean.getAccountTypeCodesBean().getAccountType().charValue();
					char ownerAccountType = ownerLoginDetailsBean.getAccountTypeCodesBean().getAccountType().charValue();
					if (agentAccountType == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
						if (ownerAccountType != ENUM_AccountTypeCodes.BUYER.getType()) {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("authenticationcontroller.saveregistrationphase1.error.notbuyeremail", null, localeResolver.resolveLocale(request)));
							loginDetailsBean.setOwnerEmail(null);
							redirectAttributes.addFlashAttribute("loginDetailsBean", loginDetailsBean);
							RedirectView red = new RedirectView("/auth/registration", true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						}
					} else if (agentAccountType == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
						if (ownerAccountType != ENUM_AccountTypeCodes.SELLER.getType()) {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("authenticationcontroller.saveregistrationphase1.error.notselleremail", null, localeResolver.resolveLocale(request)));
							loginDetailsBean.setOwnerEmail(null);
							redirectAttributes.addFlashAttribute("loginDetailsBean", loginDetailsBean);
							RedirectView red = new RedirectView("/auth/registration", true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						}
					} else if (agentAccountType == ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()) {
						if (ownerAccountType != ENUM_AccountTypeCodes.SHIPPER.getType()) {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("authenticationcontroller.saveregistrationphase1.error.notshipperemail", null, localeResolver.resolveLocale(request)));
							loginDetailsBean.setOwnerEmail(null);
							redirectAttributes.addFlashAttribute("loginDetailsBean", loginDetailsBean);
							RedirectView red = new RedirectView("/auth/registration", true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						}
					}
				}
				loginDetailsBean.setCreationTimestamp(InternetTiming.getInternetDateTime());
				loginDetailsBean.setPasswordSalt(PasswordSecurity.generatePasswordSalt());
				loginDetailsBean.setPassword(PasswordSecurity.generateSecuredPassword(loginDetailsBean.getPassword(), loginDetailsBean.getPasswordSalt()));
				loginDetailsBean.setFailedLoginCount(0);
				loginDetailsBean.setLoginCount(0);
				loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_EMAIL_VERIFICATION.getStatus()));
				String agetnLoginUserId = loginDetailsService.save(loginDetailsBean);
				loginDetailsBean.setLoginUserid(agetnLoginUserId);
				if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
					AgentOwnerBean agentOwnerBean = new AgentOwnerBean();
					agentOwnerBean.setLoginDetailsByAgentLoginUserid(new LoginDetailsBean(loginDetailsBean.getLoginUserid()));
					agentOwnerBean.setLoginDetailsByOwnerLoginUserid(new LoginDetailsBean(loginDetailsBean.getOwnerEmail()));
					agentOwnerBean.setPrivileges(ENUM_AgentPrivileges.PRIVILEGE1.getPrivilege());
					agentOwnerBean.setPurchaseLimit(new BigDecimal(999999.99));
					agentOwnerBean.setLimitSpent(new BigDecimal(0.00));
					agentOwnerService.save(agentOwnerBean);
				}
				Locale locale = localeResolver.resolveLocale(request);
				EmailData emailData = EmailTemplate.verifyEmailAddress(loginDetailsBean.getPublicName(), loginDetailsBean.getLoginUserid(),
						EmailTemplate.getHostContext(request) + "/auth/accountverify?token=" + TokenSecurity.generateToken(loginDetailsBean.getLoginUserid()), messageSource,
						locale);

				emailService.sendEmail(emailData);

				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("authenticationcontroller.saveregistrationphase1.success.checkemailbox", null, localeResolver.resolveLocale(request)));
			} else {
				redirectAttributes.addFlashAttribute("loginDetailsBean", loginDetailsBean);
				view = "/auth/registration";
				redirectAttributes.addFlashAttribute("ERRORS", errors);
			}
		} else {
			view = "/auth/registration";
			loginDetailsBean.setPassword("");
			loginDetailsBean.setPasswordSalt("");
			redirectAttributes.addFlashAttribute("loginDetailsBean", loginDetailsBean);
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
		}
		logger.info("===== registrationphase1 Ending method ======");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used to user account verification check and update the
	 * particular fields
	 * 
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @return redirects on appropriate URL.
	 */
	@RequestMapping("/accountverify")
	public ModelAndView accountverify(RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("Authentication Controller Class call accountverify method");
		String token = request.getParameter("token");
		if (null != token && !token.isEmpty() && TokenSecurity.isValidToken(token)) {
			String loginUserId = TokenSecurity.decodeToken(token);
			LoginDetailsBean loginDetailsBean = loginDetailsService.findById(loginUserId);
			if (null != loginDetailsBean) {
				if (loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode() == ENUM_AccountStatusCodes.PENDING_EMAIL_VERIFICATION.getStatus()) {
					loginDetailsBean.setAccountStatusCodesBean(new AccountStatusCodesBean(ENUM_AccountStatusCodes.PENDING_PHASE_2.getStatus()));
					loginDetailsBean.setEmailVerified(true);
					loginDetailsService.save(loginDetailsBean);
					redirectAttributes.addFlashAttribute("status", true);
					redirectAttributes.addFlashAttribute("message",
							messageSource.getMessage("authenticationcontroller.accountverify.success", null, localeResolver.resolveLocale(request)));
					Locale locale = localeResolver.resolveLocale(request);
					EmailData emailData = EmailTemplate.emailAddressVerified((loginDetailsBean.getPublicName()), loginDetailsBean.getLoginUserid(), true, true, messageSource,
							locale);

					emailService.sendEmail(emailData);

				} else {
					redirectAttributes.addFlashAttribute("status", false);
					redirectAttributes.addFlashAttribute("message",
							(messageSource.getMessage("authenticationcontroller.accountverify.error.emailaddress", null, localeResolver.resolveLocale(request)) + " <b>"
									+ loginDetailsBean.getLoginUserid() + "</b> "
									+ messageSource.getMessage("authenticationcontroller.accountverify.error.alreadyvarified", null, localeResolver.resolveLocale(request))));
				}
			} else {
				redirectAttributes.addFlashAttribute("status", false);
				redirectAttributes.addFlashAttribute("message",
						messageSource.getMessage("authenticationcontroller.accountverify.error", null, localeResolver.resolveLocale(request)));
			}
		}
		logger.info("===== accountverify Ending method ======");
		RedirectView red = new RedirectView("/auth/account-activation", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used to provide the password changing functionality
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @return login page
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ModelAndView forgotpassword(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("Authentication Controller Class call forgotpassword method");
		String view = "/auth/forgotpassword";
		String emailAddress = request.getParameter("emailAddress");
		List<String> errorStack = authenticationValidator.validateForgotPassword(emailAddress, request);
		Locale locale = localeResolver.resolveLocale(request);
		if (null == errorStack || errorStack.size() == 0) {
			LoginDetailsBean loginDetailsBean = loginDetailsService.findById(emailAddress);
			if (null != loginDetailsBean) {
				Short statusCode = loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode();
				if ((!statusCode.equals(ENUM_AccountStatusCodes.DELETED.getStatus())) && !statusCode.equals(ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus())
						&& !statusCode.equals(ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus())) {
					EmailData emailData = EmailTemplate.forgotPassword((loginDetailsBean.getPublicName()), loginDetailsBean.getLoginUserid(),
							EmailTemplate.getHostContext(request) + "/auth/checktoken?token=" + TokenSecurity.generateToken(loginDetailsBean.getLoginUserid()), messageSource,
							locale);

					emailService.sendEmail(emailData);

					redirectAttributes.addFlashAttribute("SUCCESS",
							(messageSource.getMessage("authenticationcontroller.forgotpassword.success.resetinstruction", null, localeResolver.resolveLocale(request)) + " "
									+ emailAddress));
					view = "/auth/login";
				} else if (loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode().equals(ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus())) {
					redirectAttributes.addFlashAttribute("ERROR",
							(messageSource.getMessage("your.account", null, localeResolver.resolveLocale(request)) + " " + emailAddress + " "
									+ messageSource.getMessage("has", null, localeResolver.resolveLocale(request)) + " "
									+ messageSource.getMessage(("status." + ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus()), null, localeResolver.resolveLocale(request))
									+ " " + messageSource.getMessage("contact.to.owner", null, localeResolver.resolveLocale(request))));
				} else if (loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode().equals(ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus())) {
					redirectAttributes.addFlashAttribute("ERROR",
							(messageSource.getMessage("your.account", null, localeResolver.resolveLocale(request)) + " " + emailAddress + " "
									+ messageSource.getMessage("has", null, localeResolver.resolveLocale(request)) + " "
									+ messageSource.getMessage(("status." + ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus()), null, localeResolver.resolveLocale(request))
									+ " " + messageSource.getMessage("contact.to.management", null, localeResolver.resolveLocale(request))));
				} else {
					redirectAttributes.addFlashAttribute("ERROR",
							(messageSource.getMessage("authenticationcontroller.forgotpassword.error.notallowresetpassword", null, localeResolver.resolveLocale(request)) + " "
									+ emailAddress));
				}
			} else {
				redirectAttributes.addFlashAttribute("ERROR", (messageSource.getMessage("provided.email.address", null, localeResolver.resolveLocale(request)) + " " + emailAddress
						+ " " + messageSource.getMessage("not.registered", null, localeResolver.resolveLocale(request))));
			}
		} else {
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
		}

		logger.info("===== forgotpassword Ending method ======");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	@RequestMapping("/checktoken")
	public ModelAndView checkToken(RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("Authentication Controller Class call checkToken method");
		String view = "/auth/login";
		String token = request.getParameter("token");
		if (null != token && !token.isEmpty() && TokenSecurity.isValidToken(token)) {
			String loginUserId = TokenSecurity.decodeToken(token);
			LoginDetailsBean loginDetailsBean = loginDetailsService.findById(loginUserId);
			if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid() && !loginDetailsBean.getLoginUserid().isEmpty()) {
				SessionHelper.setTokenSession(request, token);
				view = "/auth/resetpassword";
			} else {
				redirectAttributes.addFlashAttribute("ERROR",
						messageSource.getMessage("authenticationcontroller.resetPassword.error.notvalidtoken", null, localeResolver.resolveLocale(request)));
			}
		} else {
			redirectAttributes.addFlashAttribute("ERROR",
					messageSource.getMessage("authenticationcontroller.resetPassword.error.notvalidtoken", null, localeResolver.resolveLocale(request)));
		}

		logger.info("===== checkToken Ending method ======");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This Method is used to decode the reset password Token and return the reset
	 * password form
	 * 
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return reset password form
	 */
	@RequestMapping("/resetpassword")
	public String resetPassword(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("Authentication Controller Class call resetPassword method");
		String token = SessionHelper.getTokenSession(request);
		String loginUserId = "";
		LoginDetailsBean loginDetailsBean = null;
		if (null != token && !token.isEmpty()) {
			loginUserId = TokenSecurity.decodeToken(token);
			loginDetailsBean = loginDetailsService.findById(loginUserId);
			if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid() && !loginDetailsBean.getLoginUserid().isEmpty()) {
				Short statusCode = loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode();
				if ((!statusCode.equals(ENUM_AccountStatusCodes.DELETED.getStatus())) && (!statusCode.equals(ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus()))
						&& (!statusCode.equals(ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus()))) {
					if (null != loginDetailsBean && null != loginDetailsBean.getAccountProfileBean() && null != loginDetailsBean.getAccountProfileBean().getAccountId()
							&& loginDetailsBean.getAccountProfileBean().getAccountId() > 0) {
						model.addAttribute("accountId", loginDetailsBean.getAccountProfileBean().getAccountId());
					}
					model.addAttribute("loginDetailsBean", new LoginDetailsBean());
				} else {
					SessionHelper.destroyTokenSession(request);
					model.addAttribute("ERROR",
							(messageSource.getMessage("authenticationcontroller.resetPassword.error.notallowresetpassword", null, localeResolver.resolveLocale(request)) + " "
									+ messageSource.getMessage("contact.to.management", null, localeResolver.resolveLocale(request))));
				}
			}

		}
		logger.info("===== resetPassword Ending method ======");
		return ViewConstant.RESET_PASSWORD_PAGE;
	}

	/**
	 * This Method is used to reset the password based on primary email and
	 * government id
	 * 
	 * @param loginDetailsBean
	 *            set the primary email ,government id and password
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * @return login page
	 */
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ModelAndView resetpassword(@ModelAttribute("loginDetailsBean") LoginDetailsBean loginDetailsBean, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("Authentication Controller Class call resetPassword method");
		String view = "/auth/resetpassword";
		String token = SessionHelper.getTokenSession(request);
		String loginUserId = TokenSecurity.decodeToken(token);

		if (null != loginUserId) {

			if (loginUserId.equals(loginDetailsBean.getLoginUserid())) {

				LoginDetailsBean existingLoginDetailsBean = loginDetailsService.findById(loginUserId);
				Short statusCode = existingLoginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode();

				if ((!statusCode.equals(ENUM_AccountStatusCodes.DELETED.getStatus())) && (!statusCode.equals(ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus()))
						&& (!statusCode.equals(ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus()))) {
					if (null != existingLoginDetailsBean.getAccountProfileBean()) {
						if (existingLoginDetailsBean.getAccountProfileBean().getGovernmentId().equals(loginDetailsBean.getAccountProfileBean().getGovernmentId())) {
							existingLoginDetailsBean.setPasswordSalt(PasswordSecurity.generatePasswordSalt());
							existingLoginDetailsBean
									.setPassword(PasswordSecurity.generateSecuredPassword(loginDetailsBean.getPassword(), existingLoginDetailsBean.getPasswordSalt()));
							if (existingLoginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode() == ENUM_AccountStatusCodes.BLOCKED.getStatus()) {
								existingLoginDetailsBean.getAccountStatusCodesBean().setAccountStatusCode(ENUM_AccountStatusCodes.ACTIVE.getStatus());
							}
							existingLoginDetailsBean.setFailedLoginCount(0);
							loginDetailsService.save(existingLoginDetailsBean);
							final String ip = getClientIP(request);
							ipBlockingDetailsService.delete(new IpBlockingDetailsBean(ip));
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("authenticationcontroller.resetPassword.success.reset", null, localeResolver.resolveLocale(request)));
							SessionHelper.destroyTokenSession(request);
							view = "/auth/login";
						} else {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("authenticationcontroller.resetPassword.error.invalidgovid", null, localeResolver.resolveLocale(request)));
							loginDetailsBean.getAccountProfileBean().setGovernmentId("");

						}
					} else {

						if (loginDetailsBean.getPublicName().equals(existingLoginDetailsBean.getPublicName())) {
							existingLoginDetailsBean.setPasswordSalt(PasswordSecurity.generatePasswordSalt());
							existingLoginDetailsBean
									.setPassword(PasswordSecurity.generateSecuredPassword(loginDetailsBean.getPassword(), existingLoginDetailsBean.getPasswordSalt()));
							if (existingLoginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode() == ENUM_AccountStatusCodes.BLOCKED.getStatus()) {
								existingLoginDetailsBean.getAccountStatusCodesBean().setAccountStatusCode(ENUM_AccountStatusCodes.ACTIVE.getStatus());
							}
							existingLoginDetailsBean.setFailedLoginCount(0);
							loginDetailsService.save(existingLoginDetailsBean);
							final String ip = getClientIP(request);
							ipBlockingDetailsService.delete(new IpBlockingDetailsBean(ip));
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("authenticationcontroller.resetPassword.success.reset", null, localeResolver.resolveLocale(request)));
							view = "/auth/login";
						} else {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("authenticationcontroller.resetPassword.error.invalidpubname", null, localeResolver.resolveLocale(request)));
							/* loginDetailsBean.setPublicName(""); */
							redirectAttributes.addFlashAttribute("loginDetailsBean", loginDetailsBean);
						}
					}
				} else {
					redirectAttributes.addFlashAttribute("ERROR",
							messageSource.getMessage("authenticationcontroller.resetPassword.error.notallowresetpassword", null, localeResolver.resolveLocale(request)));
				}
			} else {
				redirectAttributes.addFlashAttribute("ERROR",
						messageSource.getMessage("authenticationcontroller.resetPassword.error.invalidemailadd", null, localeResolver.resolveLocale(request)));
			}
		}
		logger.info("===== resetPassword Ending method ======");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used to check the public name unique or not
	 * 
	 * @param publicName
	 *            public name by enter the user
	 * @return same page
	 */
	@RequestMapping("/checkPublicNameUnique/{publicName}")
	public @ResponseBody Boolean publicNameUnique(@PathVariable(value = "publicName") String publicName) {
		logger.info("Authentication Controller Class call publicNameUnique method");
		logger.info("===== publicNameUnique Ending method ======");
		return loginDetailsService.isPublicNameUnique(publicName);
	}

	/**
	 * This method is used to check the primary email unique or not
	 * 
	 * @param primaryEmail
	 *            primary email by enter user
	 * @return same page
	 */
	@RequestMapping("/checkPrimaryEmailUnique/{primaryEmail:.+}")
	public @ResponseBody String isPrimaryEmailUnique(@PathVariable(value = "primaryEmail") String primaryEmail) {
		logger.info("Authentication Controller Class call isPrimaryEmailUnique method");
		Boolean temp = loginDetailsService.isPrimaryEmailUnique(primaryEmail);
		logger.info("===== isPrimaryEmailUnique Ending method ======");
		return temp == true ? "Y" : "N";
	}

	private final String getClientIP(HttpServletRequest request) {
		logger.info("Authentication Controller Class call getClientIP method");
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		logger.info("===== getClientIP Ending method ======");
		return xfHeader.split(",")[0];
	}

	/**
	 * this method announcement and auctionTrade info get
	 * 
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/announcementmovingbanner", produces = "text/plain;charset=UTF-8")
	public String announcementMovingBanner(HttpServletResponse response) {
		logger.info("Authentication Controller Class call announcementMovingBanner method");
		String gannouncementMovingBannerString = "";
		if (announcementsView.equals(CommonConstants.ANNOUNCEMENT)) {
			String announcementString = announcementHelper.announcementString(announcementsDepth);
			String auctionTradeString = announcementHelper.getAuctionTrades(auctionTradeDepth);
			logger.info("Authentication Controller Class call announcementMovingBanner method ending ");
			return gannouncementMovingBannerString = announcementString + auctionTradeString;
		}
		logger.info("Authentication Controller Class call announcementMovingBanner method ending ");
		return gannouncementMovingBannerString;
	}
}