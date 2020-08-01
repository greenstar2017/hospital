package com.green.cache;

import com.green.framework.cache.key.ICacheKey;

/**
 * 项目缓存key
 * 
 * @author jlege
 *
 */
public abstract class ICrawlProjectCacheKey extends ICacheKey {

	@Override
	public String getProjectPrefixKey() {
		return "hospital";
	}

}
