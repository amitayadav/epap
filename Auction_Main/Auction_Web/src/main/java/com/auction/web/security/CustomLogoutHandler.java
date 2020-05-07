package com.auction.web.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.LoginLogoutLogService;

@Component
public class CustomLogoutHandler implements LogoutHandler {

	@Autowired
	private LoginLogoutLogService loginLogoutLogService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);

		if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid() && !loginDetailsBean.getLoginUserid().isEmpty()) {

			Date loginTimeStamp = loginDetailsBean.getLoginTimestamp();
			if (null != loginTimeStamp) {
				loginLogoutLogService.updateLogoutTime(DateHelper.dateToString(InternetTiming.getInternetDateTime()), loginDetailsBean.getLoginUserid(), DateHelper.dateToString(loginTimeStamp));
			}
		}
	}
}