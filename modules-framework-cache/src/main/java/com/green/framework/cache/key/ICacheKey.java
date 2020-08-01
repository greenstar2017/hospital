package com.green.framework.cache.key;

import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * 缓存KEY定义基类
 * 
 * @author yuanhualiang
 *
 */
public abstract class ICacheKey {

	public static final String CACHE_KEY_SEPARATOR = ":";

	/**
	 * 项目前缀
	 * 
	 * @return
	 */
	public abstract String getProjectPrefixKey();

	/**
	 * 自定义后缀
	 * 
	 * @return
	 */
	public abstract String getSuffixKey();

	/**
	 * 超时时间 -1不设置超时
	 * 
	 * @return
	 */
	public int getExpirationTime() {
		return -1;
	}

	/**
	 * 超时时间单位
	 * 
	 * @return
	 */
	public TimeUnit getExpirationTimeUnit() {
		return TimeUnit.SECONDS;
	}

	/**
	 * 业务包名加入key中
	 * 
	 * @return
	 */
	public String getPrefixKey() {
		String[] packages = getClass().getPackage().getName().split("\\.");
		int len = packages.length;
		String prefixKey = "";
		if (len >= 2)
			prefixKey = packages[(len - 1)].equals("cachekey") ? packages[(len - 2)]
					: packages[(len - 1)];
		else {
			prefixKey = "common";
		}
		prefixKey = prefixKey + ":" + getClass().getSimpleName();
		return prefixKey;
	}

	/**
	 * 组建缓存KEY 业务工程名称:包名:自定义后缀
	 */
	public String toString() {
		String suffixKey = getSuffixKey();
		String key = getProjectPrefixKey() + ":" + getPrefixKey();
		if (StringUtils.isNotEmpty(suffixKey)) {
			key = key + ":" + getSuffixKey();
		}
		return key;
	}
}