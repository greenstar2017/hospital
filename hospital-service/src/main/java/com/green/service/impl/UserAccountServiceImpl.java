package com.green.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.green.constants.AccountStatusEnum;
import com.green.constants.AccountTypeEnum;
import com.green.entity.UserAccount;
import com.green.mapper.UserAccountMapper;
import com.green.service.UserAccountService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuanhualiang
 * @since 2018-09-23
 */
@Service
public class UserAccountServiceImpl extends
		ServiceImpl<UserAccountMapper, UserAccount> implements
		UserAccountService {

	@Autowired
	private UserAccountMapper userAccountMapper;

	@Override
	public boolean checkExistAccount(UserAccount userAccount) {
		String account = userAccount.getAccount();
		Wrapper<UserAccount> wrapper = new EntityWrapper<UserAccount>();
		wrapper.eq("account", account);
		if (null != userAccount.getId()) {
			wrapper.ne("id", userAccount.getId());
		}
		wrapper.eq("status", AccountStatusEnum.ENABLED.getKey());
		int count = this.selectCount(wrapper);
		return count > 0;
	}

	@Override
	public UserAccount findAdminAccount() {
		Wrapper<UserAccount> wrapper = new EntityWrapper<UserAccount>();
		wrapper.eq("type", AccountTypeEnum.ADMIN.getKey());
		wrapper.eq("status", AccountStatusEnum.ENABLED.getKey());
		wrapper.last(" limit 1");
		return this.selectOne(wrapper);
	}
}
