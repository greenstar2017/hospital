/**
 *
 * yuanhualiang
 */
package com.green.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Null;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.green.common.BeanUtils;
import com.green.common.MD5Util;
import com.green.constants.AccountStatusEnum;
import com.green.dto.UserAccountForm;
import com.green.entity.UserAccount;
import com.green.response.RestObject;
import com.green.service.UserAccountService;

/**
 * @author yuanhualiang
 * 
 *         系统用户控制类
 */
@Controller
@RequestMapping("/console/systemAccount")
public class UserAccountController extends BaseController {

	@Autowired
	private UserAccountService userAccountService;

	/**
	 * 查找系统用户信息
	 * 
	 * @param request
	 * @param response
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/systemAccountList")
	public RestObject systemAccountList(
			HttpServletRequest request,
			HttpServletResponse response,
			UserAccountForm form,
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize) {

		Wrapper<UserAccount> wrapper = new EntityWrapper<>();
		wrapper.eq("status", AccountStatusEnum.ENABLED.getKey());
		if (StringUtils.isNotEmpty(form.getAccount())) {
			wrapper.like("account", form.getAccount());
		}
		if (StringUtils.isNotEmpty(form.getName())) {
			wrapper.like("name", form.getName());
		}
		wrapper.orderBy("id", false);
		Page<UserAccount> page = new Page<UserAccount>(pageNo, pageSize);
		page = userAccountService.selectPage(page, wrapper);

		return RestObject.newOk("查询成功", page.getRecords(), page.getTotal());
	}

	/**
	 * 添加/修改系统用户信息
	 * 
	 * @param request
	 * @param response
	 * @param userAccountForm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveSystemAccount")
	public RestObject saveSystemAccount(HttpServletRequest request,
			HttpServletResponse response, @Valid UserAccountForm userAccountForm) {
		UserAccount userAccount = new UserAccount();
		boolean flag = false;
		if (null != userAccountForm.getId()) {
			try {
				if (StringUtils.isNotBlank(userAccountForm.getPassword())) {
					userAccountForm.setPassword(MD5Util
							.getMD5code(userAccountForm.getPassword()));
				} else {
					userAccountForm.setPassword(null);
				}
				BeanUtils.copyProperties(userAccountForm, userAccount);
				if (userAccountService.checkExistAccount(userAccount)) {
					return RestObject.newError("账号已经存在");
				}
				flag = userAccountService.updateById(userAccount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (StringUtils.isBlank(userAccountForm.getPassword())) {
				return RestObject.newError("密码不能为空");
			}
			userAccountForm.setPassword(MD5Util.getMD5code(userAccountForm
					.getPassword()));
			try {
				BeanUtils.copyProperties(userAccountForm, userAccount);
				userAccount.setStatus(AccountStatusEnum.ENABLED.getKey());
				userAccount.setCreateTime(new Date());
				if (userAccountService.checkExistAccount(userAccount)) {
					return RestObject.newError("账号已经存在");
				}
				flag = userAccountService.insert(userAccount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {
			return RestObject.newOk("保存成功");
		} else {
			return RestObject.newError("保存失败");
		}
	}

	/**
	 * 删除系统用户信息
	 * 
	 * @param request
	 * @param response
	 * @param userAccountForm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delSystemAccount")
	public RestObject delSystemAccount(HttpServletRequest request,
			HttpServletResponse response, @Null(message = "ID不能为空") int id) {
		boolean flag = false;
		UserAccount userAccount = userAccountService.selectById(id);
		userAccount.setStatus(AccountStatusEnum.DISABLED.getKey());
		flag = userAccountService.updateById(userAccount);

		if (flag) {
			return RestObject.newOk("删除成功");
		} else {
			return RestObject.newError("删除失败");
		}
	}

	/**
	 * 基本信息
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/systemAccountInfo")
	public RestObject systemAccountInfo(HttpServletRequest request,
			HttpServletResponse response, @Null(message = "ID不能为空") int id) {

		Map<String, Object> resultData = new HashMap<String, Object>();

		UserAccount userAccount = userAccountService.selectById(id);
		userAccount.setPassword("");
		resultData.put("userAccount", userAccount);

		return RestObject.newOk("基本信息", resultData);
	}

}
