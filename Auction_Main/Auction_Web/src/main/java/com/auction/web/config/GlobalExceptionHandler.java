package com.auction.web.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/* CreadDate, updatedDate format */
		/*SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		sdf.setLenient(true);*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setLenient(true);
		// SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		// sdf2.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		// binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf2, true));
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView resourceNotFound(HttpServletRequest request, Exception ex) {
		if (request.getUserPrincipal() != null) {
			return getExceptionInfo("404.notfound.layout", request, ex);
		} else {
			return getExceptionInfo("404.notfound.layout", request, ex);
		}
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest request, Exception ex) {
		if (request.getUserPrincipal() != null) {
			//return getExceptionInfo("exception.global.login", request, ex);
			return getExceptionInfo("exception.layout", request, ex);
		} else {
			return getExceptionInfo("exception.layout", request, ex);
		}
	}

	private ModelAndView getExceptionInfo(String viewName, HttpServletRequest request, Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(viewName);
		modelAndView.addObject("exception", ex);
		modelAndView.addObject("exception_stack_trace", stackTraceToString(ex));
		modelAndView.addObject("requestedUrl", request.getRequestURL());
		return modelAndView;
	}

	private String stackTraceToString(Exception ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
	
}