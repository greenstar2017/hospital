/**
 *
 * yuanhualiang
 */
package com.green.constants;

/**
 * @author yuanhualiang
 *
 *         用户状态
 */
public enum AccountStatusEnum {

	/**
	 * 有效
	 */
	ENABLED("ENABLED", 0, "有效"),

	/**
	 * 无效
	 */
	DISABLED("DISABLED", 1, "无效");

	private String typeName;

	private int key;

	private String typeDes;

	AccountStatusEnum(String typeName, int key, String typeDes) {
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
