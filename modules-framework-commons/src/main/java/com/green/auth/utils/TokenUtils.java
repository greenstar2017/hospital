package com.green.auth.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.green.auth.constants.LoginConstants;
import com.green.utils.mvc.CookieUtils;

/**
 * TOKEN工具
 * 
 * @author yuanhualiang
 */
public class TokenUtils {

	public TokenUtils() {
	}

	public static String getLoginToken(HttpServletRequest request) {
		String tokenString = request.getHeader(LoginConstants.TOKEN_NAME);
		if (StringUtils.isBlank(tokenString))
			tokenString = CookieUtils.getCookie(request, LoginConstants.TOKEN_NAME);
		return tokenString;
	}

	public static String generateToken(String secret, long userId) {
		String prefixToken = (new StringBuilder())
				.append(String.valueOf(userId))
				.append(LoginConstants.TOKEN_SEPARATOR)
				.append(System.currentTimeMillis()).toString();
		return (new StringBuilder()).append(prefixToken)
				.append(LoginConstants.TOKEN_SEPARATOR)
				.append(HMACDigestUtils.md5Hex(secret, prefixToken)).toString();
	}

	public static long parseToken(String tokenString) {
		return parseTokenToLoginId(LoginConstants.TOKEN_SIGN_SECRET,
				tokenString);
	}

	public static long parseTokenToLoginId(String secret, String tokenString) {
		if (StringUtils.isBlank(tokenString))
			return -1L;
		String tokenSplited[] = tokenString.split("\\"
				+ LoginConstants.TOKEN_SEPARATOR);
		if (tokenSplited.length != 3)
			return -1L;
		String userId = tokenSplited[0];
		String time = tokenSplited[1];
		String sign = tokenSplited[2];
		if (!StringUtils.equals(
				HMACDigestUtils.md5Hex(
						secret,
						(new StringBuilder()).append(userId)
								.append(LoginConstants.TOKEN_SEPARATOR)
								.append(time).toString()), sign))
			return -1L;
		if (DateUtils.addDays(new Date(NumberUtils.toLong(time)), 14).before(
				new Date()))
			return -1L;
		else
			return NumberUtils.toLong(userId);
	}
}