package com.auction.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component("authenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception)
			throws IOException, ServletException {
		setDefaultFailureUrl("/auth/loginfailed?failed=true");

		super.onAuthenticationFailure(request, response, exception);
		/*
		 * final Locale locale = localeResolver.resolveLocale(request); String errorMessage = messages.getMessage("message.badCredentials", null, locale); if
		 * (exception.getMessage().equalsIgnoreCase("User is disabled")) { errorMessage = messages.getMessage("auth.message.disabled", null, locale); } else if
		 * (exception.getMessage().equalsIgnoreCase("User account has expired")) { errorMessage = messages.getMessage("auth.message.expired", null, locale); } else if
		 * (exception.getMessage().equalsIgnoreCase("blocked")) { errorMessage = messages.getMessage("auth.message.blocked", null, locale); }
		 */
		String errorMessage = exception.getMessage();
		// request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
		request.getSession().setAttribute("ERROR", errorMessage);
		// loginAttemptService.loginFailed(getClientIP(request));
	}
}