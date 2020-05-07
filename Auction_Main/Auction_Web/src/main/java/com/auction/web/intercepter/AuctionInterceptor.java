package com.auction.web.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.LoginDetailsBean;

public class AuctionInterceptor extends HandlerInterceptorAdapter {

	private static final String[] IGNORE_URI = { "/home", "/auth/", "/rest/auth", "/403-forbidden" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

		// HTTP 1.0 backward compatibility Directs caches not to store the
		response.setHeader("Pragma", "no-cache");

		// page under any circumstance
		response.setDateHeader("Expires", 0);

		// Setting for proper display of UI in Internet Explorer
		response.setHeader("X-UA-Compatible", "IE=edge");

		// Setting for content type and character set
		response.setHeader("Content-Type", "text/html; charset=UTF-8");

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String requestURI = request.getRequestURI();
		Boolean isLoginUserVerifyOtp= (Boolean) request.getSession().getAttribute("isLoginUserVerifyOtp");
		if(isLoginUserVerifyOtp == null) {
			SessionHelper.isLoginUserOtpVerify(request, false);
			isLoginUserVerifyOtp=false;
		}
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		for (String uri : AuctionInterceptor.IGNORE_URI) {
			if (requestURI.contains(uri)) {
				if (null != loginDetailsBean && null == loginDetailsBean.getAccountProfileBean()
						&& (!requestURI.contains("/registeraccountprofile") || !requestURI.contains("/logout"))) {
					response.sendRedirect(request.getContextPath() + "/setting/registeraccountprofile");
				} else if (loginDetailsBean != null
						&& (requestURI.contains("home") || requestURI.contains("login") || requestURI.contains("/registration") || requestURI.contains("registeraccountprofile"))) {
						//response.sendRedirect(request.getContextPath() + "/setting/dashboard");
					//response.sendRedirect(request.getContextPath() + "/auctions/auctionlist");
					/* if(loginDetailsBean.getAccountStatusCodesBean().getAccountStatusCode()== ENUM_AccountStatusCodes.PENDING_PHONE_NUMBER.getStatus()) {
						 response.sendRedirect(request.getContextPath() + "/setting/otpverification");
					 }else*/ if(loginDetailsBean.getAccountProfileBean().getLandingPagesBean() == null) {
							 response.sendRedirect(request.getContextPath() + "/auctions/auctionlist");
					}else {
							 response.sendRedirect((request.getContextPath() + "/" + loginDetailsBean.getAccountProfileBean().getLandingPagesBean().getLandingPageUrl()));
					}
					
					return false;
				} else if (requestURI.endsWith("/auth/login") && null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
					response.sendRedirect(request.getContextPath() + "/auth/loginbox");
					return false;
				} if(isLoginUserVerifyOtp) {
					 response.sendRedirect(request.getContextPath()+"/setting/loginotpbox");
				 }
				return true;
			}
		}

		if (loginDetailsBean == null) {
			if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
				 if(requestURI.contains("/setting/reSendOtpLoginTime")) {
					return true;
				}else {
					response.sendRedirect(request.getContextPath() + "/auth/loginbox");
					return false;
				}
			}else if ((request.getContextPath() + "/").equals(requestURI)) {
				response.sendRedirect(request.getContextPath() + "/home");
			} else if (requestURI.contains("/home/")) {
				return true;
			} else if(requestURI.contains("/setting/loginotpbox")) {
				return true;
			}else if(requestURI.contains("/setting/syncloginotpbox")) {
				return true;
			}else if(requestURI.contains("/setting/loginTimeValidateOtp")) {
				return true;
			}
			else {
				//response.sendRedirect(request.getContextPath() + "/auth/login");
				response.sendRedirect(request.getContextPath() + "/home");
			}
			return false;
		} else if (null != loginDetailsBean && null == loginDetailsBean.getAccountProfileBean()
				&& (!requestURI.contains("/setting/addlocation") && !requestURI.contains("/registeraccountprofile") && !requestURI.contains("/setting/otpverification/") && !requestURI.contains("/setting/validateOtp") && !requestURI.contains("/setting/reSendOtp"))) {
			response.sendRedirect(request.getContextPath() + "/setting/registeraccountprofile");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}