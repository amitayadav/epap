package com.auction.web.security;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.LoginLogoutLogService;

@Component
public class SessionListener implements HttpSessionListener, ApplicationListener<SessionDestroyedEvent> {
	
	@Autowired
	private LoginLogoutLogService loginLogoutLogService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(60 * 60);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.printf("Session ID %s destroyed at %s%n", event.getSession().getId(), InternetTiming.getInternetDateTime());
		logger.info("SessionListener class call in sessionDestroyed method "+InternetTiming.getInternetDateTime());
	}

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		logger.info("SessionListener class call in onApplicationEvent  method ");
		logger.info("this is method used update logoutTimeStamp ");
		HttpSession session = (HttpSession) event.getSource();
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(session);
		if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid() && !loginDetailsBean.getLoginUserid().isEmpty()) {
			Date loginTimeStamp = loginDetailsBean.getLoginTimestamp();
			logger.info("get Login Timestamp "+loginTimeStamp);
			if (null != loginTimeStamp) {
				String logoutTimeStamp = DateHelper.dateToString(InternetTiming.getInternetDateTime());
				logger.info("loginDetailsBean login user id   "+loginDetailsBean.getLoginUserid());
				logger.info("logoutTimeStamp Update  "+logoutTimeStamp);
				loginLogoutLogService.updateLogoutTime(logoutTimeStamp, loginDetailsBean.getLoginUserid(), DateHelper.dateToString(loginDetailsBean.getLoginTimestamp()));
			}
		}
		
		
	}

}