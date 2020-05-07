package com.auction.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SpringSecuritySessionRegistry {

	@Autowired
	SessionRegistry sessionRegistry;
	
	public void expiredUserSession(String loginUserId) {
		List<Object> principals = sessionRegistry.getAllPrincipals();
		for (Object principal : principals) {
			if(principal instanceof User) {
				UserDetails userDetails = (UserDetails) principal;
				if(userDetails.getUsername().equals(loginUserId)) {
					List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(userDetails, true);
					for(SessionInformation info : sessionInformationList) {
						info.expireNow();
					}
				}
			}
		}
	}
}