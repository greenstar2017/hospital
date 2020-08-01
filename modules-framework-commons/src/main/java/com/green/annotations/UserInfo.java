package com.green.annotations;


/**
 * 用户信息注解
 * 
 * yuanhualiang
 */
public class UserInfo
{
	private Integer id;
    /**
     * 账号
     */
	private String account;
	/**
	 * 密码
	 */
	private String password;
    /**
     * 名称
     */
	private String name;
    /**
     * 账号类型 参考accountTypeEnum
     */
	private Integer type;
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
	
}