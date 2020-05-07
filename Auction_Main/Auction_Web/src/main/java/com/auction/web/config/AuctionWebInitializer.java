package com.auction.web.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.auction.web.security.SessionListener;

public class AuctionWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public AuctionWebInitializer() {

	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(new SessionListener());
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AuctionWebConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/", "*.json" };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
		registration.setInitParameter("contentType", "text/html");
		registration.setInitParameter("encoding", "UTF-8");
		registration.addMapping("/");
	}

	@Override
	protected Filter[] getServletFilters() {
		super.getServletFilters();
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		characterEncodingFilter.setForceResponseEncoding(true);
		return new Filter[] { characterEncodingFilter };
	}

	@Override
	protected void registerDispatcherServlet(ServletContext servletContext) {
		servletContext.addListener(new SessionListener());
		super.registerDispatcherServlet(servletContext);
	}

}