package com.auction.web.security;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	@Autowired
	MultipartResolver multipartResolver;

	@Override
	protected boolean enableHttpSessionEventPublisher() {
		return true;
	}

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		MultipartFilter multipartFilter = new MultipartFilter();
		multipartFilter.setMultipartResolverBeanName("multipartResolver");
		servletContext.addListener(new RequestContextListener());
		// servletContext.addListener(requestContextListener);
		Dynamic d =  servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		d.setInitParameter("encoding", "UTF-8");
        d.setInitParameter("forceEncoding", "true");
        d.addMappingForUrlPatterns(null, true, "/*");
		insertFilters(servletContext, multipartFilter);
	}
	
}