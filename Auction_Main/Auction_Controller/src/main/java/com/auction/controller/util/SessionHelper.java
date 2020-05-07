package com.auction.controller.util;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.LoginDetailsBean;

public class SessionHelper {

	/**
	 * This method helps to get logged in user's id(loginUserId)
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * 
	 * @return it will return logged in user's id(loginUserId) from session if
	 *         available otherwise null.
	 */
	public static String getLoginUserId(HttpServletRequest request) {
		String value = null;
		try {
			LoginDetailsBean loginDetailsBean = getLoginDetailsBean(request);
			if (null != loginDetailsBean) {
				value = loginDetailsBean.getLoginUserid();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * This method helps to get logged in user's public name
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * 
	 * @return it will return logged in user's public name from session if available
	 *         otherwise null.
	 */
	public static String getPublicName(HttpServletRequest request) {
		String value = null;
		try {
			LoginDetailsBean loginDetailsBean = getLoginDetailsBean(request);
			if (null != loginDetailsBean) {
				value = loginDetailsBean.getPublicName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * This method helps to get logged in user's account type
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * 
	 * @return it will return logged in user's public name from session if available
	 *         otherwise null.
	 */
	public static Character getAccountType(HttpServletRequest request, boolean isOwner) {
		Character value = null;
		try {
			if(isOwner) {
				if (null != request.getSession().getAttribute("ownerAccountType")) {
					value = (Character) request.getSession().getAttribute("ownerAccountType");
				}
			}else {
				if (null != getLoginDetailsBean(request).getAccountTypeCodesBean().getAccountType()) {
					value = getLoginDetailsBean(request).getAccountTypeCodesBean().getAccountType();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
	/**
	 * This method helps to get logged in user's account profile id(accountId)
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * 
	 * @return it will return logged in user's account profile id(accountId) from
	 *         session if available otherwise null.
	 */
	public static Integer getAccountProfileId(HttpServletRequest request) {
		Integer value = null;
		try {
			LoginDetailsBean loginDetailsBean = getLoginDetailsBean(request);
			if (null != loginDetailsBean && null != loginDetailsBean.getAccountProfileBean()) {
				value = loginDetailsBean.getAccountProfileBean().getAccountId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * This method helps to get logged in user's owner account profile id(accountId)
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * 
	 * @return it will return logged in user's owner account profile id(accountId) from
	 *         session if available otherwise 0.
	 */
	public static Integer getOwnerAccountProfileId(HttpServletRequest request) {
		Integer value = 0;
		try {
			Integer ownerAccountId = (Integer) request.getSession().getAttribute("ownerAccountId");
			if (null != ownerAccountId && ownerAccountId > 0) {
				value = ownerAccountId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * This method helps to get logged in user's account profile bean
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * 
	 * @return it will return logged in user's account profile bean from session if
	 *         available otherwise null.
	 */
	public static AccountProfileBean getAccountProfile(HttpServletRequest request) {
		AccountProfileBean bean = null;
		try {
			LoginDetailsBean loginDetailsBean = getLoginDetailsBean(request);
			if (null != loginDetailsBean && null != loginDetailsBean.getAccountProfileBean()) {
				bean = loginDetailsBean.getAccountProfileBean();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * This method helps to get logged in user's object from session.
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * 
	 * @return it will return {@code LoginDetailsBean} if object is not null other
	 *         wise {@code null}.
	 */

	public static LoginDetailsBean getLoginDetailsBean(HttpServletRequest request) {
		Object obj = null;
		obj = request.getSession().getAttribute("loginUser");
		if (obj != null)
			return (LoginDetailsBean) obj;
		else
			return null;
	}

	/**
	 * This method helps to get logged in user's object from session.
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * 
	 * @return it will return {@code LoginDetailsBean} if object is not null other
	 *         wise {@code null}.
	 */

	public static LoginDetailsBean getLoginDetailsBean(HttpSession session) {
		Object obj = null;
		obj = session.getAttribute("loginUser");
		if (obj != null)
			return (LoginDetailsBean) obj;
		else
			return null;
	}

	/**
	 * This method helps to prepare system generated encrypted string of given plain
	 * string
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 * 
	 * @param loginDetailsBean
	 *            LoginDetailBean is for logged in user's details.
	 * 
	 */
	public static void setLoginSession(HttpServletRequest request, LoginDetailsBean loginDetailsBean) {
		LoginDetailsBean oldLoginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		if (null != oldLoginDetailsBean && null != oldLoginDetailsBean.getLoginUserid()) {
			loginDetailsBean.setLoginTimestamp(oldLoginDetailsBean.getLoginTimestamp());
		}
		request.getSession().setAttribute("loginUser", loginDetailsBean);
	}

	/**
	 * This method helps to prepare system generated encrypted string of given plain
	 * string
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 * 
	 * @param loginDetailsBean
	 *            loginDetailBean is for logged in user's details.
	 * 
	 * @param agentLoginDetailsBean
	 *            agentLoginDetailsBean is for logged in agent user's owner details.
	 * 
	 */
	public static void setLoginSession(HttpServletRequest request, LoginDetailsBean loginDetailsBean, LoginDetailsBean agentLoginDetailsBean) {
		setLoginSession(request, loginDetailsBean);
		request.getSession().setAttribute("ownerName", agentLoginDetailsBean.getAccountProfileBean().getFullName()+" ("+agentLoginDetailsBean.getPublicName()+")");
		request.getSession().setAttribute("ownerAccountId", agentLoginDetailsBean.getAccountProfileBean().getAccountId());
		request.getSession().setAttribute("ownerAccountType", agentLoginDetailsBean.getAccountTypeCodesBean().getAccountType());
	}

	/**
	 * This method helps to set available balance of user
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 * 
	 * @param availableBalance
	 *            User's availableBalance.
	 */
	public static void setAvailableBalance(HttpServletRequest request, BigDecimal availableBalance) {
		request.getSession().setAttribute("availableBalance", availableBalance);
	}

	/**
	 * This method helps to store the token in session string
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 * 
	 * @param loginUserId
	 *            loginUserId is for logged in user's email id.
	 * 
	 */
	public static void setTokenSession(HttpServletRequest request, String loginUserId) {
		request.getSession().setAttribute("tokenLoginUserId", loginUserId);
	}

	/**
	 * This method helps to store the token in session string
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 * 
	 * @param loginUserId
	 *            loginUserId is for logged in user's email id.
	 */
	public static void destroyTokenSession(HttpServletRequest request) {
		request.getSession().removeAttribute("tokenLoginUserId");
	}

	/**
	 * This method helps to get the token value.
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * 
	 * @return it will return token user id if object is not null other wise
	 *         {@code null}.
	 */
	public static String getTokenSession(HttpServletRequest request) {

		String loginuserId = (String) request.getSession().getAttribute("tokenLoginUserId");
		if (loginuserId != null)
			return loginuserId;
		else
			return null;
	}

	/**
	 * This method helps to destroy login session.
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 */
	public static void destroyLoginSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		session.removeAttribute("tokenLoginUserId");
	}

	/**
	 * This method helps to store message in session when account is pending by
	 * admin / owner
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 * 
	 * @param loginUserId
	 *            loginUserId is for logged in user's email id.
	 * 
	 */
	public static void setAccountStatusMessage(HttpServletRequest request, String pendingAccountStatus) {
		request.getSession().setAttribute("PENDING_ACCOUNT_STATUS", pendingAccountStatus);
	}
	
	
	/**
	 * This method helps to set phone Number of user
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 * 
	 * @param setLoginUserPhonenNumber
	 *            User's
	 */
	public static void setLoginUserPhonenNumber(HttpServletRequest request,String phonenNumber) {
		request.getSession().setAttribute("phonenNumber", phonenNumber);
	}
	
	
	/**
	 * This method helps to is check otp verify  of user
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 * 
	 * @param checkUserVerifyOtp
	 *            User's
	 */
	public static void checkUserVerifyOtp(HttpServletRequest request,Boolean checkUserVerifyOtp) {
		request.getSession().setAttribute("checkUserVerifyOtp", checkUserVerifyOtp);
	}

	/**
	 * This method helps to is check login time  otp verify  of user
	 * 
	 * @param request
	 *            HttpServletRequest is for setting session value.
	 * 
	 * @param isLoginUserOtpVerify
	 *            User's
	 */
	public static void isLoginUserOtpVerify(HttpServletRequest request,Boolean isLoginUserVerifyOtp) {
		request.getSession().setAttribute("isLoginUserVerifyOtp", isLoginUserVerifyOtp);
	}
	
	/**
	 * This method helps to is  set loginUserId
	 * @param request
	 * @param loginUserId
	 */
	public static void setLoginUserId(HttpServletRequest request, String loginUserId) {
		request.getSession().setAttribute("loginUserId", loginUserId);
	}
	
	
}