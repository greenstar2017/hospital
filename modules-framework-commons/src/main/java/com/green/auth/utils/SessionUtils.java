package com.green.auth.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.green.auth.constants.LoginConstants;
import com.green.constants.PropertiesContants;
import com.green.utils.mvc.CookieUtils;

/**
 * 会话工具
 * 
 * @author yuanhualiang
 */
public class SessionUtils {

	public SessionUtils() {
	}

	public static String getSid(HttpServletRequest request) {
		String sid = CookieUtils.getCookie(request, LoginConstants.SID_NAME);
		if (StringUtils.isBlank(sid)) {
			Object objSid = request.getAttribute(LoginConstants.SID_NAME);
			return (objSid instanceof String) ? (String) objSid : "";
		} else {
			return sid;
		}
	}

	public static void newSid(HttpServletRequest request,
			HttpServletResponse response) {
		String sid = RandomStringUtils.randomAlphanumeric(20);
		request.setAttribute(LoginConstants.SID_NAME, sid);
		CookieUtils.addCookie(response, LoginConstants.SID_NAME, sid,
				PropertiesContants.that.getCookieBaseDomain(), "/", 31536000);
	}

}