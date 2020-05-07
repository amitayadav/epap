package com.auction.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

public class AuctionLogger extends Logger {

	public AuctionLogger(String name) {
		super(name);
	}

	public AuctionLogger(Class<?> cls) {
		super(cls.getName());
	}

	public void debug(String msg) {
		super.getLogger("debug").debug(msg);
	}

	public void trace(String msg) {
		super.getLogger("trace").info(msg);
	}

	public void info(String msg) {
		super.getLogger("info").info(msg);
	}

	/*public void error(String error) {
		getLogger("error").error(error);
	}*/

	public void error(Throwable e) {
		super.getLogger("error").error(stackTraceToString(e));
	}

	public void error(Exception e) {
		super.getLogger("error").error(stackTraceToString(e));
	}

	private String stackTraceToString(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	private String stackTraceToString(Throwable e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
}