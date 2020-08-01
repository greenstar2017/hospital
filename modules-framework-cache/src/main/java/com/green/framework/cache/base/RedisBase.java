package com.green.framework.cache.base;

import javax.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

/**
 * redisTemplate组件化
 * 
 * @author yuanhualiang
 *
 */
@Component
public class RedisBase implements InitializingBean {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	protected static ValueOperations<String, Object> valueOperations;
	public static ValueOperations<String, String> strValueOperations;
	protected static ListOperations<String, Object> listOperations;
	protected static SetOperations<String, Object> setOperations;
	protected static HashOperations<String, String, Object> hashOperations;
	protected static ZSetOperations<String, Object> zsetOperations;
	protected static RedisTemplate<String, Object> redisTemplateIns;

	public void afterPropertiesSet() throws Exception {
		valueOperations = this.redisTemplate.opsForValue();
		listOperations = this.redisTemplate.opsForList();
		setOperations = this.redisTemplate.opsForSet();
		hashOperations = this.redisTemplate.opsForHash();
		zsetOperations = this.redisTemplate.opsForZSet();
		strValueOperations = this.stringRedisTemplate.opsForValue();
		redisTemplateIns = this.redisTemplate;
	}

	protected static String[] toStringArray(Object[] members) {
		String[] memberStrs = new String[members.length];
		for (int i = 0; i < members.length; i++) {
			Object member = members[i];
			if (member != null) {
				memberStrs[i] = member.toString();
			}
		}
		return memberStrs;
	}
}