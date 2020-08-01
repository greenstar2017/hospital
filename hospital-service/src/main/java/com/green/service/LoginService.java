/**
 * 
 */
package com.green.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.green.dto.LoginForm;
import com.green.entity.UserAccount;

/**
 * @author yuanhualiang
 *
 */
public interface LoginService {

	/**
	 * 登录
	 */
	UserAccount doLogin(HttpServletRequest request, HttpServletResponse response,
			LoginForm loginForm);

	/**
	 * @param userId
	 * @return
	 */
	Object getUserInfo(long userId);
}
