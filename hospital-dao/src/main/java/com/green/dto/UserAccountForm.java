/**
 *
 * yuanhualiang
 */
package com.green.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author yuanhualiang
 * 
 * @NotEmpty 用在集合类上面 @NotBlank 用在String上面 @NotNull 用在基本类型上
 *
 */
public class UserAccountForm {

	private Integer id;

	/**
	 * 账号
	 */
	@NotBlank(message = "账号不能为空")
	@Length(max = 30, message = "账号长度不超过30个字符")
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 名称
	 */
	@NotBlank(message = "名称不能为空")
	@Length(max = 30, message = "名称长度不超过30个字符")
	private String name;
	/**
	 * 类型 参考accountTypeEnum
	 */
	@NotNull(message = "类型不能为空")
	private Integer type;

	/**
	 * 用户手机号码
	 */
	private String mobile;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
