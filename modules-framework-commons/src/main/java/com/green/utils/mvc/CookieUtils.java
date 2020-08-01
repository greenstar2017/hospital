package com.green.utils.mvc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.*;
import org.apache.commons.lang3.StringUtils;

/**
 * cookie工具
 * 
 * @author yuanhualiang
 */
public class CookieUtils {

	public static final String VALUE_ENCODE = "UTF-8";
	public static final String DEFAULT_PATH = "/";
	public static final int DEFAULT_MAX_AGE = 31536000;

	public CookieUtils() {
	}

	public static final void addCookie(HttpServletResponse response,
			String key, String value, String domain, String path, int maxAge) {
		String encodedValue;
		try {
			encodedValue = value != null ? URLEncoder.encode(value, "UTF-8")
					: "";
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		Cookie c = new Cookie(key, encodedValue);
		if (domain != null)
			c.setDomain(domain);
		c.setPath(path);
		c.setMaxAge(maxAge);
		response.addCookie(c);
	}

	public static final void addCookie(HttpServletResponse response,
			String key, String value, String path, int maxAge) {
		addCookie(response, key, value, null, path, maxAge);
	}

	public static final void addCookie(HttpServletResponse response,
			String key, String value, int maxAge) {
		addCookie(response, key, value, null, "/", maxAge);
	}

	public static final void addCookie(HttpServletResponse response,
			String key, String value) {
		addCookie(response, key, value, null, "/", 31536000);
	}

	public static final void removeCookie(HttpServletResponse response,
			String key, String domain, String path) {
		addCookie(response, key, "", domain, path, 0);
	}

	public static final void removeCookie(HttpServletResponse response,
			String key, String path) {
		addCookie(response, key, "", null, path, 0);
	}

	public static final void removeCookie(HttpServletResponse response,
			String key) {
		addCookie(response, key, "", null, "/", 0);
	}

	public static final String getCookie(HttpServletRequest request, String key) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null)
			return null;
		Cookie acookie[] = cookies;
		int i = acookie.length;
		for (int j = 0; j < i; j++) {
			Cookie c = acookie[j];
			if (StringUtils.equals(key, c.getName()))
				try {
					return URLDecoder.decode(c.getValue(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
		}

		return null;
	}

}