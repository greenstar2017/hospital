/**
 * 
 */
package com.green.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.annotations.NoRequireLogin;
import com.green.annotations.UserId;
import com.green.auth.utils.LoginUtils;
import com.green.common.EnumTool;
import com.green.common.MD5Util;
import com.green.constants.AccountTypeEnum;
import com.green.constants.DelFlagEnum;
import com.green.dto.LoginForm;
import com.green.entity.UserAccount;
import com.green.response.RestObject;
import com.green.service.LoginService;
import com.green.service.UserAccountService;

/**
 * @author yuanhualiang
 *
 */
@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private UserAccountService userAccountService;

	/**
	 * 登录页面跳转
	 * 
	 * @return
	 */
	@NoRequireLogin
	@RequestMapping(value = { "", "/index.html", "login.html" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	/**
	 * 使用说明
	 * 
	 * @return
	 */
	@NoRequireLogin
	@RequestMapping(value = { "/help.html" }, method = RequestMethod.GET)
	public String help() {
		return "help";
	}

	/**
	 * 静态页面跳转
	 * 
	 * @return
	 */
	@NoRequireLogin
	@RequestMapping(value = "/ezzysouq_static/{page}.html", method = RequestMethod.GET)
	public String staticPage(@PathVariable String page) {
		return "static/" + page;
	}

	/**
	 * 后台管理首页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@NoRequireLogin
	@RequestMapping(value = "/console", method = RequestMethod.GET)
	public String console(HttpServletRequest request, Model model,
			@UserId long userId) {
		if (userId == -1) {
			return "redirect:login.html";
		}
		return "consoleIndex";
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param loginForm
	 * @return
	 */
	@NoRequireLogin
	@ResponseBody
	@RequestMapping("/userLogin")
	public RestObject login(HttpServletRequest request,
			HttpServletResponse response, LoginForm loginForm) {
		UserAccount userAccount = loginService.doLogin(request, response,
				loginForm);
		Map<String, Object> resultData = new HashMap<>();
		Map<String, Object> baseData = new HashMap<>();
		resultData.put("baseData", baseData);
		if (null != userAccount) {
			return RestObject.newOk("登录成功", resultData);
		} else {
			return RestObject.newError("账号或者密码不正确");
		}
	}

	/**
	 * 系统基础数据
	 * 
	 * @param request
	 * @param response
	 * @param userAccount
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/systemBaseData")
	public RestObject systemBaseData(HttpServletRequest request,
			HttpServletResponse response, UserAccount userAccount) {
		Map<String, Object> resultData = new HashMap<>();
		Map<String, Object> baseData = new HashMap<>();
		resultData.put("baseData", baseData);
		if (null != userAccount) {
			resultData.put("userAccount", userAccount);
			baseData.put("accountTypeEnum",
					EnumTool.getEnumPropertyMap(AccountTypeEnum.class));
			baseData.put("delFlagEnum",
					EnumTool.getEnumPropertyMap(DelFlagEnum.class));

			return RestObject.newOk("", resultData);
		} else {
			return RestObject.newError("系统错误");
		}
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@NoRequireLogin
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response, @UserId long userId) {
		LoginUtils.logout(request, response, userId);
		return "login";
	}

	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 * @param userAccount
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetPwd")
	public RestObject resetPwd(HttpServletRequest request,
			HttpServletResponse response, UserAccount userAccount,
			@Null(message = "原密码不能为空") String oldPwd,
			@Null(message = "新密码不能为空") String newPwd) {
		String oldPwdMd5 = MD5Util.getMD5code(oldPwd);
		if (!oldPwdMd5.equals(userAccount.getPassword())) {
			return RestObject.newError("原密码不正确");
		} else {
			String newPwdMd5 = MD5Util.getMD5code(newPwd);

			userAccount.setPassword(newPwdMd5);
			boolean flag = userAccountService.updateById(userAccount);
			if (flag) {
				return RestObject.newOk("密码重置成功");
			} else {
				return RestObject.newError("密码重置失败");
			}
		}
	}

}
