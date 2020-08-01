/**
 *
 * yuanhualiang
 */
package com.green.constants;

/**
 * @author yuanhualiang
 *
 *         用户类型
 */
public enum AccountTypeEnum {

	/**
	 * 管理员
	 */
	ADMIN("ADMIN", 0, "管理员"),

	/**
	 * 客户
	 */
	CLIENT("CLIENT", 1, "客户"),

	/**
	 * 游客
	 */
	ANON("ANON", 2, "游客");

	private String typeName;

	private int key;

	private String typeDes;

	AccountTypeEnum(String typeName, int key, String typeDes) {
		this.typeName = typeName;
		this.key = key;
		this.typeDes = typeDes;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	public String getTypeDes() {
		return typeDes;
	}

	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}
}
