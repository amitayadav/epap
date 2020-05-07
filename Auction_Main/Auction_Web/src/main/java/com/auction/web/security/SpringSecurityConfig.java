package com.auction.web.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.auction.web.util.UserAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * @Autowired private UserDetailsService userDetailsService;
	 */

	@Autowired
	protected UserAuthenticationProvider userAuthenticationProvider;

	@Autowired
	protected AuthenticationFailureHandler authenticationFailureHandler;

	/*
	 * @Autowired protected CustomLogoutHandler customLogoutHandler;
	 */

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/error/**");
	}

	@Bean
	SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	class CsrfMatcher implements RequestMatcher {
	    @Override
	    public boolean matches(HttpServletRequest request) {

	        if (request.getRequestURI().indexOf("/rest/") != -1) {
	        	return false;
	        }
	        return true;
	    }	   
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.formLogin()
			.loginPage("/auth/login")
			.usernameParameter("loginUserid")
			.passwordParameter("password")
			.defaultSuccessUrl("/auth/loginAuthentication",true)
			.failureHandler(authenticationFailureHandler)
			//.failureUrl("/auth/loginfailed?failed=true")
			.loginProcessingUrl("/auth/loginme") // just display as a request url will not call actually for login.
		.and()
		.sessionManagement()
			.maximumSessions(1)
			//.maxSessionsPreventsLogin(true)
			.expiredUrl("/auth/logout").sessionRegistry(sessionRegistry())
			.and()
			.invalidSessionUrl("/auth/logout")
	    	.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	    	.sessionFixation().none()
	    .and()
		.csrf()//.disable()//ignoringAntMatchers("/rest/")
	    //.csrf().requireCsrfProtectionMatcher(new CsrfMatcher())
		.and()
		.logout()
			.logoutUrl("/auth/logoutme")
			.logoutSuccessUrl("/auth/logout")
			.clearAuthentication(true)
			//.addLogoutHandler(customLogoutHandler)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
		.and()
		.exceptionHandling().accessDeniedPage("/403-forbidden")
		.and()
		.authorizeRequests()
		.antMatchers("/home/**", "/auth/**", "/rest/auth/**", "/403-forbidden").permitAll()
		.antMatchers("/setting/**", "/common/**").authenticated()
		.antMatchers("/spropt/**").access("hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION')")
		.antMatchers("/sproptrel/**").access("hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_RELATIONS')")
		.antMatchers("/sprfinshp/**").access("hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_FINANCE', 'ROLE_ADMIN_SHIPPING')")
		.antMatchers("/sproptshp/**").access("hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_SHIPPING')")
		.antMatchers("/sprfinvat/**").access("hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_FINANCE', 'ROLE_ADMIN_VAT')")
		.antMatchers("/super/**").access("hasRole('ROLE_ADMIN_SUPERUSER')")
		.antMatchers("/operation/**").access("hasRole('ROLE_ADMIN_OPERATION')")
		.antMatchers("/finance/**").access("hasRole('ROLE_ADMIN_FINANCE')")
		.antMatchers("/relation/**").access("hasRole('ROLE_ADMIN_RELATIONS')")
		.antMatchers("/shipping/**").access("hasRole('ROLE_ADMIN_SHIPPING')")
		.antMatchers("/vat/**").access("hasRole('ROLE_ADMIN_VAT')")

		//General Users(Seller, Seller Agent, Buyer, Buyer Agent, Shipper & Shipper Agents Request Mapping
		.antMatchers("/general/**").access("hasAnyRole('ROLE_SELLER', 'ROLE_SELLER_AGENT', 'ROLE_BUYER', 'ROLE_BUYER_AGENT', 'ROLE_SHIPPER', 'ROLE_SHIPPER_AGENT')")
		
		//Common Admin Request Mapping
		.antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN_SUPERUSER','ROLE_ADMIN_OPERATION','ROLE_ADMIN_FINANCE','ROLE_ADMIN_RELATIONS','ROLE_ADMIN_SHIPPING','ROLE_ADMIN_VAT')")

		//Seller, Buyer & Shipper Request Mapping
		.antMatchers("/sbs/**").access("hasAnyRole('ROLE_SELLER','ROLE_BUYER','ROLE_SHIPPER')")

		//Seller Request Mapping
		.antMatchers("/seller/**").access("hasRole('ROLE_SELLER')")
		
		//Buyer Request Mapping
		.antMatchers("/buyer/**").access("hasRole('ROLE_BUYER')")
		
		//Shipper Request Mapping
		.antMatchers("/shipper/**").access("hasRole('ROLE_SHIPPER')")
		
		.antMatchers("/stransaction/**").access("hasAnyRole('ROLE_SELLER', 'ROLE_SELLER_AGENT_2')")
		.antMatchers("/btransaction/**").access("hasAnyRole('ROLE_BUYER', 'ROLE_BUYER_AGENT_2')")
		.antMatchers("/shtransaction/**").access("hasAnyRole('ROLE_SHIPPER', 'ROLE_SHIPPER_AGENT_2')")
		
		//Seller + Seller Agent
		.antMatchers("/ssa/**").access("hasAnyRole('ROLE_SELLER', 'ROLE_SELLER_AGENT')")
		
		//Buyer + Buyer Agent
		.antMatchers("/bba/**").access("hasAnyRole('ROLE_BUYER', 'ROLE_BUYER_AGENT')")
		
		//Shipper + Shipper Agent
		.antMatchers("/shsha/**").access("hasAnyRole('ROLE_SHIPPER', 'ROLE_SHIPPER_AGENT')")
		
		//Allow all other request mapping for all users
		.anyRequest().permitAll();
	}

	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.userDetailsService(userDetailsService);
	}*/
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(userAuthenticationProvider);
	}
}
