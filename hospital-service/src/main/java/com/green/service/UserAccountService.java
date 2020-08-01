package com.green.service;

import com.green.entity.UserAccount;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuanhualiang
 * @since 2018-09-23
 */
public interface UserAccountService extends IService<UserAccount> {

	/**
	 * 检查账号是否存在
	 * 
	 * @param userAccount
	 * @return
	 */
	boolean checkExistAccount(UserAccount userAccount);

	/**
	 * 查找管理员
	 * 
	 * @return
	 */
	UserAccount findAdminAccount();
}
