package org.loocsij.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.loocsij.util.StringUtil;

public class WebRequestParser {

	private HttpServletRequest request;

	private static SimpleDateFormat format = null;

	public WebRequestParser(HttpServletRequest request) {
		super();
		if (request == null) {
			throw new NullPointerException("argument 'request' can not be null.");
		}
		this.request = request;
	}

	public String getParameter(String key) {
		if (key == null) {
			return null;
		}
		String value = request.getParameter(key);
		if (value != null
				&& (value.trim().length() == 0 || value.trim()
						.equalsIgnoreCase("null"))) {
			return null;
		}
		return value;
	}

	public String getParameter(String key, boolean sensitive) {
		if (sensitive) {
			return getParameter(key);
		}
		Enumeration e = request.getParameterNames();
		String param = null;
		String value = null;
	
		while (e.hasMoreElements()) {
			param = e.nextElement().toString();
			if (param.equalsIgnoreCase(key)) {
				value = request.getParameter(param);
				if (value != null
						&& (value.trim().length() == 0 || value.trim()
								.equalsIgnoreCase("null"))) {
					return null;
				}
				return value;
			}
		}
		return null;
	}

	public Date getDateParameter(String key, String pattern, boolean sensitive) {
		if (format == null) {
			format = new SimpleDateFormat(pattern);
		}
		String value = getParameter(key, sensitive);
		if (value == null) {
			return null;
		}
		try {
			return format.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Date getDateParameter(String key, String pattern) {
		return getDateParameter(key, pattern);
	}

	public double getDoubleParameter(String key, boolean sensitive) {
		String value = getParameter(key, sensitive);
		if (value == null) {
			return Integer.MIN_VALUE;
		}
		if (StringUtil.isNumber(value)) {
			return Double.parseDouble(value);
		}
		return Integer.MIN_VALUE;
	}

	public double getDoubleParameter(String key) {
		return getDoubleParameter(key, true);
	}

	public long getLongParameter(String key, boolean sensitive) {
		String value = getParameter(key, sensitive);
		if (value == null) {
			return Integer.MIN_VALUE;
		}
		if (!StringUtil.isNumber(value)) {
			return Integer.MIN_VALUE;
		}

		return Long.parseLong(value);
	}

	public long getLongParameter(String key) {
		return getLongParameter(key, true);
	}
}
