package com.green.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.green.annotations.NoRequireLogin;
import com.green.auth.utils.LoginUtils;
import com.green.auth.utils.SessionUtils;
import com.green.common.DESUtils;
import com.green.exception.BusinessAssert;
import com.green.exception.CommonError;

/**
 * 登录拦截
 * 
 * @author yuanhualiang
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	private final static Logger logger = LoggerFactory
			.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			BusinessAssert.error(CommonError.INVALID_REQUEST);
		}

		// 取sid,如没sid，则新生成
		String sid = SessionUtils.getSid(request);
		if (sid == null || sid.length() != 20) {
			SessionUtils.newSid(request, response);
		}

		if (noRequireLogin((HandlerMethod) handler)) {
			return true;
		}
		long userId = LoginUtils.getLoginId(request);
		if (userId < 0) {
			long result = parseTokenParam(request);
			if (result > 0) {
				userId = result;
			}
		}
		BusinessAssert.isTrue(userId > 0, CommonError.LOGIN_EXPIRE
				.newMessage(CommonError.LOGIN_EXPIRE.getErrorMessage()
						+ "[errorCode:"
						+ CommonError.LOGIN_EXPIRE.getErrorCode() + "]"));
		LoginUtils.setLoginId(request, response, userId);
		return true;
	}

	/**
	 * 解析免密登录的TOKEN
	 * 
	 * @param request
	 * @return
	 */
	private long parseTokenParam(HttpServletRequest request) {
		String token = request.getParameter("token");
		logger.info(">>>>>>>>>>>>>.token:" + token);
		if (StringUtils.isNotEmpty(token)) {
			try {
				String result = DESUtils.jdkDESDecode(token);
				
				logger.info(">>>>>>>>>>>>>.result:" + result);
				if (StringUtils.isNotEmpty(result)) {
					return Long.parseLong(result);
				}
			} catch (Exception e) {
			}
		}
		return -1;
	}

	private boolean noRequireLogin(HandlerMethod method) {
		return method.getMethod().isAnnotationPresent(NoRequireLogin.class);
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}
}