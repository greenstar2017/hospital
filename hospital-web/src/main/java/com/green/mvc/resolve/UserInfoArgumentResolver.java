package com.green.mvc.resolve;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.green.annotations.UserId;
import com.green.auth.utils.LoginUtils;
import com.green.entity.UserAccount;
import com.green.service.LoginService;

/**
 * 注入UserInfo
 * 
 * @author yuanhualiang
 */
@Component
public class UserInfoArgumentResolver implements WebArgumentResolver {
	private final static Logger logger = LoggerFactory
			.getLogger(UserInfoArgumentResolver.class);
	@Autowired
	private LoginService loginService;

	@Override
	public Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception {
		HttpServletRequest request = (HttpServletRequest) webRequest
				.getNativeRequest();
		long userId = LoginUtils.getLoginId(request);
		if (userId < 0) {
			Object tokenUserId = request.getAttribute("NO_PASS_TOKEN");
			if (null != tokenUserId) {
				userId = Long.parseLong(tokenUserId.toString());
			}
		}
		logger.info(">>>>>>>>>>>>resolveArgument.userId:" + userId);
		boolean isLogin = userId > 0;

		if (methodParameter.hasParameterAnnotation(UserId.class)) {
			return isLogin ? userId : -1;
		}

		if (UserAccount.class.equals(methodParameter.getParameterType())) {
			return isLogin ? loginService.getUserInfo(userId) : null;
		}

		return UNRESOLVED;
	}
}