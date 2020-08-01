package com.green.auth.bean;

import java.util.Date;

/**
 * 登录TOKEN
 * 
 * yuanhualiang
 */
public class Token {

	public Token() {
	}

	public Token(long userId, long expire) {
		this.userId = userId;
		this.expire = expire;
	}

	public Token(long userId, Date expire) {
		this(userId, expire.getTime());
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}

	public String toString() {
		return (new StringBuilder()).append("Token [userId=").append(userId)
				.append(", expire=").append(expire).append("]").toString();
	}

	private long userId;
	private long expire;
}