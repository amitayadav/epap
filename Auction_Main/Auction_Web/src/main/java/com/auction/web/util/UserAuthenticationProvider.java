package com.auction.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.auction.commons.enums.ENUM_AccountStatusCodes;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_AgentPrivileges;
import com.auction.commons.security.PasswordSecurity;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.commons.util.RegExpPatterns;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.LoginLogoutLogBean;
import com.auction.model.bean.LoginLogoutLogIdBean;
import com.auction.service.AgentOwnerService;
import com.auction.service.LoginDetailsService;
import com.auction.service.LoginLogoutLogService;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private LoginLogoutLogService loginLogoutLogService;

	@Autowired
	private AgentOwnerService agentOwnerService;

	/*@Autowired
	private IpBlockingDetailsService ipBlockingDetailsService;*/

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localeResolver;

	@Value("${login.max.fail.count}")
	private int maxFailCount;
	
	@Value("${TWO_FACTOR_ON}")
	private String twoFactorValue;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("Login Time UserAuthenticationProvider Class in Method  authenticate call");
		String factorValue= "ON";
		Locale locale = localeResolver.resolveLocale(request);
		String loginUserId = authentication.getName();
		String password = authentication.getCredentials().toString();
		String errorStack = "";
		
		/*	final String ip = getClientIP();*/

		LoginDetailsBean loginDetailsBean = loginDetailsService.findById(loginUserId);
		if (null == loginUserId || loginUserId.trim().length() == 0) {
			errorStack += ("##" + messageSource.getMessage("validation.email.required", null, locale));
		} else if (!loginUserId.trim().matches(RegExpPatterns.EMAIL)) {
			errorStack += ("##" + messageSource.getMessage("validation.email.emailPattern", null, locale));
		}

		// Validating Password
		if (null == password || password.trim().length() == 0) {
			errorStack += ("##" + messageSource.getMessage("validation.password.required", null, locale));
		}

		if (errorStack.equals("") && errorStack.isEmpty()) {
			/*
			 * if (loginAttemptService.isBlocked(ip)) { throw new
			 * LockedException(messageSource.getMessage(
			 * "userauthenticationprovider.msg.ipblock", null, locale)); }
			 */

			/*if (ipBlockingDetailsService.isBlocked(ip, maxFailCount)) {
				ipBlockingDetailsService.loginFail(ip);
				throw new LockedException(messageSource.getMessage("userauthenticationprovider.msg.ipblock", null, locale));
			}*/

			if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid()) {
				Short statusCode = loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode();
			/*	IpBlockingDetailsBean ipBlockingDetailsBean = new IpBlockingDetailsBean();
				if (null != ipBlockingDetailsBean && null != ipBlockingDetailsBean.getIp()) {
					ipBlockingDetailsBean = ipBlockingDetailsService.findById(ip);
				}*/
				if ((!statusCode.equals(ENUM_AccountStatusCodes.DELETED.getStatus())) && !statusCode.equals(ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus())
						&& !statusCode.equals(ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus())) {
				/*	if (null != ipBlockingDetailsBean && null != ipBlockingDetailsBean.getIp()) {
						if (ipBlockingDetailsBean.getNoOfBlocking() == maxFailCount) {
							ipBlockingDetailsService.loginFail(ip);
							throw new LockedException(messageSource.getMessage("userauthenticationprovider.msg.actblock", null, locale));
						}
					}*/
					if (PasswordSecurity.isValidPassword(password, loginDetailsBean.getPassword())) {
						if (!loginDetailsBean.getEmailVerified() || statusCode == ENUM_AccountStatusCodes.PENDING_EMAIL_VERIFICATION.getStatus()) {
							throw new LockedException(messageSource.getMessage("authenticationcontroller.loginauthentication.error.noemailvarified", null, locale));
						}if(loginDetailsBean.getFailedLoginCount() == maxFailCount) {
							throw new LockedException(messageSource.getMessage("userauthenticationprovider.msg.ipblock", null, locale));
						}
					/*	if(statusCode == ENUM_AccountStatusCodes.PENDING_PHONE_NUMBER.getStatus()) {
							throw new LockedException(messageSource.getMessage("userauthenticationprovider.msg.ipblock", null, locale));
						}*/
						
						/*if (null != ipBlockingDetailsBean && null != ipBlockingDetailsBean.getIp()) {
							if (ipBlockingDetailsBean.getNoOfBlocking() == maxFailCount) {
								throw new LockedException(messageSource.getMessage("authenticationcontroller.loginauthentication.error.blocked", null, locale));
							}
						}*/
						/**
						 * This is used to loginLogoutLog more than 45 minutes difference  and check factorValue
						 */
						if(twoFactorValue.equals(factorValue)) {
							logger.info("Check twoFactorValue  Value ON ");
						if(loginDetailsBean.getAccountProfileBean() !=null) {
							 LoginLogoutLogBean  loginLogoutLog =loginLogoutLogService.getLogoutTimestampByAccountId(loginUserId);
							 if(null !=loginLogoutLog) {
								 logger.info("Check loginLogoutLog object Not Null");	
									Boolean loginLogOutDiff=DateHelper.getloginAndLogOutTimeDiff(loginLogoutLog.getLogoutTimestamp());
									if(loginLogOutDiff) {
											logger.info("loginLogOutDiff  diiff  true in userAuthentioncationProviderClass");	
											/*** check login time OTP Count And remainingMiunte  **/
											if(loginDetailsBean.getAccountProfileBean().getOtpCount() != null && loginDetailsBean.getAccountProfileBean().getOtpCount() >= 4 ) {
												if (loginDetailsBean.getAccountProfileBean().getOtpExpiredDate() != null) {
													Long remainingMiunte = DateHelper.getRemainingTimeToSms( loginDetailsBean.getAccountProfileBean().getOtpExpiredDate());
													if (0 == remainingMiunte) {
														SessionHelper.isLoginUserOtpVerify(request, true);
														SessionHelper.setLoginUserId(request, loginUserId);
													}else {
														String msg = messageSource.getMessage("otpverification.validation.otp.after", null, localeResolver.resolveLocale(request)) + "  " + remainingMiunte + " "
																+ messageSource.getMessage("otpverification.validation.otp.minutes", null, localeResolver.resolveLocale(request));
														throw new LockedException(msg);
													}
												}else {
													SessionHelper.isLoginUserOtpVerify(request, true);
													SessionHelper.setLoginUserId(request, loginUserId);
												}
											}else {
												SessionHelper.isLoginUserOtpVerify(request, true);
												SessionHelper.setLoginUserId(request, loginUserId);
											}
										/**  end  check login time Out Count And remainingMiunte */
									}else {
										SessionHelper.isLoginUserOtpVerify(request, false);
									}
									}else {
										SessionHelper.isLoginUserOtpVerify(request, false);
										}
						}else {
							SessionHelper.isLoginUserOtpVerify(request, false);
							}
						
					}
						List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

						if ((ENUM_AccountTypeCodes.USER.getType() != loginDetailsBean.getAccountTypeCodesBean().getAccountType())
								&& (statusCode != ENUM_AccountStatusCodes.ACTIVE.getStatus())) {
							authorities.add(new SimpleGrantedAuthority(("ROLE_" + ENUM_AccountTypeCodes.USER.toString().toUpperCase())));
						} else {
							authorities.add(
									new SimpleGrantedAuthority(("ROLE_" + loginDetailsBean.getAccountTypeCodesBean().getAccountTypeMeaning().toUpperCase().replace(" ", "_"))));
							if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
								if (agentOwnerService.checkAgentByPrivilegeAndLoginUserId(loginDetailsBean.getLoginUserid(), ENUM_AgentPrivileges.PRIVILEGE2.getPrivilege())) {
									authorities.add(new SimpleGrantedAuthority(
											("ROLE_" + (loginDetailsBean.getAccountTypeCodesBean().getAccountTypeMeaning().toUpperCase().replace(" ", "_") + "_"
													+ ENUM_AgentPrivileges.PRIVILEGE2.getPrivilege()))));
								}
							}
						}
						//ipBlockingDetailsService.deleteBlockedIp(ip);

						

						if(twoFactorValue.equals(factorValue)) {
							Boolean isLoginUserVerifyOtp= (Boolean) request.getSession().getAttribute("isLoginUserVerifyOtp");
							if(!isLoginUserVerifyOtp) {
								loginDetailsBean.setLoginCount(loginDetailsBean.getLoginCount() + 1);
								loginDetailsBean.setFailedLoginCount(0);
								loginDetailsService.save(loginDetailsBean);
								LoginLogoutLogIdBean loginLogoutLogIdBean = new LoginLogoutLogIdBean();
								loginLogoutLogIdBean.setLoginDetailsBean(new LoginDetailsBean(loginDetailsBean.getLoginUserid()));
								logger.info("loginLog time set user"+loginDetailsBean.getLoginUserid());	
								loginLogoutLogIdBean.setLoginTimestamp(DateHelper.removeMilliSecondFromDate(InternetTiming.getInternetDateTime()));
								logger.info("loginLog time set  time "+DateHelper.removeMilliSecondFromDate(InternetTiming.getInternetDateTime()));	
								LoginLogoutLogBean loginLogoutLogBean = new LoginLogoutLogBean(loginLogoutLogIdBean);
								loginLogoutLogService.save(loginLogoutLogBean);
								logger.info("save loginLogoutLogBean "+loginLogoutLogBean.getLogoutTimestamp());	
								loginDetailsBean.setLoginTimestamp(loginLogoutLogBean.getId().getLoginTimestamp());
								if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
									LoginDetailsBean agentLoginDetails = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginDetailsBean.getLoginUserid());
									SessionHelper.setLoginSession(request, loginDetailsBean, agentLoginDetails);
								} else {
									SessionHelper.setLoginSession(request, loginDetailsBean);
								}
							}
						}else {
							LoginLogoutLogIdBean loginLogoutLogIdBean = new LoginLogoutLogIdBean();
							loginLogoutLogIdBean.setLoginDetailsBean(new LoginDetailsBean(loginDetailsBean.getLoginUserid()));
							logger.info("loginLog time set user"+loginDetailsBean.getLoginUserid());	
							loginLogoutLogIdBean.setLoginTimestamp(DateHelper.removeMilliSecondFromDate(InternetTiming.getInternetDateTime()));
							logger.info("loginLog time set  time "+DateHelper.removeMilliSecondFromDate(InternetTiming.getInternetDateTime()));	
							LoginLogoutLogBean loginLogoutLogBean = new LoginLogoutLogBean(loginLogoutLogIdBean);
							loginLogoutLogService.save(loginLogoutLogBean);
							logger.info("save loginLogoutLogBean "+loginLogoutLogBean.getLogoutTimestamp());	
							loginDetailsBean.setLoginTimestamp(loginLogoutLogBean.getId().getLoginTimestamp());
							if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
								LoginDetailsBean agentLoginDetails = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginDetailsBean.getLoginUserid());
								SessionHelper.setLoginSession(request, loginDetailsBean, agentLoginDetails);
							} else {
								SessionHelper.setLoginSession(request, loginDetailsBean);
							}
						}
						
						//ipBlockingDetailsService.delete(new IpBlockingDetailsBean(ip));

					

						if (ENUM_AccountTypeCodes.USER.getType() != loginDetailsBean.getAccountTypeCodesBean().getAccountType()) {
							if (statusCode == ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus()) {
								SessionHelper.setAccountStatusMessage(request, messageSource.getMessage("accountsettingcontroller.saveregistrationsecondphase.success.normal", null,
										localeResolver.resolveLocale(request)));
							} else if (statusCode == ENUM_AccountStatusCodes.PENDING_OWNER_APPROVAL.getStatus()) {
								SessionHelper.setAccountStatusMessage(request, messageSource.getMessage("accountsettingcontroller.saveregistrationsecondphase.success.agent", null,
										localeResolver.resolveLocale(request)));
							}else if (statusCode == ENUM_AccountStatusCodes.PENDING_PHONE_NUMBER.getStatus()) {
								SessionHelper.setAccountStatusMessage(request, messageSource.getMessage("otpverification.validation.phonepending", null,
										localeResolver.resolveLocale(request)));
							}
						}
						return new UsernamePasswordAuthenticationToken(buildUserForAuthentication(loginDetailsBean, authorities), password, authorities);
					} else {
						loginDetailsBean.setFailedLoginCount(loginDetailsBean.getFailedLoginCount() + 1);
						loginDetailsService.save(loginDetailsBean);
						logger.info(" else save loginLogoutLogBean "+loginDetailsBean.getLoginTimestamp());	
						if (maxFailCount < loginDetailsBean.getFailedLoginCount()) {
							// loginDetailsBean.setAccountStatusCodesBean(new
							// AccountStatusCodesBean(ENUM_AccountStatusCodes.BLOCKED.getStatus()));
							throw new LockedException(messageSource.getMessage("userauthenticationprovider.msg.ipblock", null, locale));
						}else {
							throw new BadCredentialsException(messageSource.getMessage("userauthenticationprovider.msg.wrongcredentials", null, locale));
						}
						//ipBlockingDetailsService.loginFail(ip);
					
					}
				} else if (loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode().equals(ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus())) {

					throw new DisabledException(
							((messageSource.getMessage("your.account", null, locale) + " " /* + loginUserId + " " */ + messageSource.getMessage("is", null, locale) + " "
									+ messageSource.getMessage(("status." + ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus()), null, locale) + " "
									+ messageSource.getMessage("contact.to.owner", null, locale))));
				} else if (loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode().equals(ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus())) {
					/*
					 * throw new DisabledException(((messageSource.getMessage("your.account", null,
					 * locale) + " " + loginUserId + " " + messageSource.getMessage("is", null,
					 * locale) + " " + messageSource.getMessage(("status." +
					 * ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus()), null, locale) + ". "
					 * + messageSource.getMessage("contact.to.management", null, locale))));
					 */
					throw new DisabledException((messageSource.getMessage("userauthenticationprovider.msg.suspended.by.admin", null, locale)));
				} else {
					throw new DisabledException(
							messageSource.getMessage("provided.email.address", null, locale) + " " + loginUserId + " " + messageSource.getMessage("not.registered", null, locale));
				}
			} else {
				throw new DisabledException(
						messageSource.getMessage("provided.email.address", null, locale) + " " + loginUserId + " " + messageSource.getMessage("not.registered", null, locale));
			}
		} else {
			throw new AuthenticationCredentialsNotFoundException(errorStack);
		}
	}

	private User buildUserForAuthentication(LoginDetailsBean loginDetailsBean, List<GrantedAuthority> authorities) {
		/*System.out.println("Authority : " + authorities.get(0).getAuthority());*/
		return new User(loginDetailsBean.getLoginUserid(), loginDetailsBean.getPassword(),
				(loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode() == ENUM_AccountStatusCodes.ACTIVE.getStatus()), true, true, true, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

/*	private final String getClientIP() {
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}*/

}