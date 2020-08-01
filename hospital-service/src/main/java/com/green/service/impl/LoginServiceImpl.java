/**
 * 
 */
package com.green.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.green.auth.utils.LoginUtils;
import com.green.common.MD5Util;
import com.green.constants.AccountStatusEnum;
import com.green.dto.LoginForm;
import com.green.entity.UserAccount;
import com.green.service.LoginService;
import com.green.service.UserAccountService;

/**
 * @author yuanhualiang
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	//private Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private UserAccountService userAccountService;

	@Override
	public UserAccount doLogin(HttpServletRequest request,
			HttpServletResponse response, LoginForm loginForm) {
		
		String account = loginForm.getAccount();
		String password = loginForm.getPassword();
		
		Wrapper<UserAccount> wrapper = new EntityWrapper<UserAccount>();
		wrapper.eq("account", account);
		wrapper.eq("password", MD5Util.getMD5code(password));
		wrapper.eq("status", AccountStatusEnum.ENABLED.getKey());
		wrapper.last(" limit 1");
		
		
		UserAccount userAccount = userAccountService.selectOne(wrapper);
		if(null == userAccount) {
			return null;
		}
		
		LoginUtils.setLoginId(request, response, userAccount.getId());
		return userAccount;
	}

	@Override
	public UserAccount getUserInfo(long userId) {
		return userAccountService.selectById(userId);
	}

}
