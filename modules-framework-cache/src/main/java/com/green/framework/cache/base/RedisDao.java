package com.green.framework.cache.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.green.framework.cache.key.ICacheKey;
import com.green.framework.cache.utils.DateUtil;
import com.green.framework.cache.utils.ParseUtil;

/**
 * Redis缓存操作类
 * 
 * @author yuanhualiang
 *
 */
@SuppressWarnings("unchecked")
public class RedisDao extends RedisBase {
	public static void del(ICacheKey iCacheKey) {
		valueOperations.getOperations().delete(iCacheKey.toString());
	}

	public static void del(Collection<ICacheKey> keys) {
		Iterator<ICacheKey> it = keys.iterator();
		List<String> list = new ArrayList<String>();
		while (it.hasNext()) {
			ICacheKey iCacheKey = (ICacheKey) it.next();
			list.add(iCacheKey.toString());
		}
		valueOperations.getOperations().delete(list);
	}

	public static boolean expire(ICacheKey iCacheKey, long timeout,
			TimeUnit unit) {
		if (timeout < 0L) {
			return false;
		}
		Boolean result = valueOperations.getOperations().expire(
				iCacheKey.toString(), timeout, unit);
		return ParseUtil.parse(result);
	}

	public static boolean expire(ICacheKey iCacheKey) {
		return expire(iCacheKey, iCacheKey.getExpirationTime(),
				iCacheKey.getExpirationTimeUnit());
	}

	public static boolean expireAt(ICacheKey iCacheKey, Date expireDate) {
		long timeout = DateUtil.countDelta(expireDate, new Date());
		return expire(iCacheKey, timeout, TimeUnit.SECONDS);
	}

	public static long ttl(ICacheKey iCacheKey) {
		return ttl(iCacheKey, TimeUnit.SECONDS);
	}

	public static long ttl(ICacheKey iCacheKey, TimeUnit unit) {
		Long result = valueOperations.getOperations().getExpire(
				iCacheKey.toString(), unit);
		return ParseUtil.parse(result);
	}

	public static boolean exist(ICacheKey iCacheKey) {
		return valueOperations.getOperations().hasKey(iCacheKey.toString())
				.booleanValue();
	}

	public static void set(ICacheKey iCacheKey, Object value) {
		if ((value instanceof String)) {
			setString(iCacheKey, (String) value);
			return;
		}

		if (iCacheKey.getExpirationTime() > -1) {
			valueOperations.set(iCacheKey.toString(), value,
					iCacheKey.getExpirationTime(),
					iCacheKey.getExpirationTimeUnit());
		} else
			valueOperations.set(iCacheKey.toString(), value);
	}

	public static void set(String key, Object value) {
		if ((value instanceof String)) {
			strValueOperations.set(key, (String) value);
			return;
		}
		valueOperations.set(key, value);
	}

	private static void setString(ICacheKey iCacheKey, String value) {
		if (iCacheKey.getExpirationTime() > -1) {
			strValueOperations.set(iCacheKey.toString(), value,
					iCacheKey.getExpirationTime(),
					iCacheKey.getExpirationTimeUnit());
		} else
			strValueOperations.set(iCacheKey.toString(), value);
	}

	public static <T> T get(ICacheKey iCacheKey, Class<T> clazz) {
		if (clazz == String.class) {
			return (T) getString(iCacheKey);
		}

		Object result = valueOperations.get(iCacheKey.toString());
		if (result == null) {
			return null;
		}
		return (T) result;
	}

	private static String getString(ICacheKey iCacheKey) {
		String result = (String) strValueOperations.get(iCacheKey.toString());
		return result;
	}

	public static boolean setnx(ICacheKey iCacheKey, Object value) {
		boolean result = ParseUtil.parse(valueOperations.setIfAbsent(
				iCacheKey.toString(), value));

		if (result) {
			expire(iCacheKey);
		}

		return result;
	}

	public static void mset(Map<ICacheKey, Object> map) {
		Map<String, Object> strMap = new HashMap<String, Object>();
		List<ICacheKey> expireKeyList = new ArrayList<ICacheKey>();
		for (ICacheKey iCacheKey : map.keySet()) {
			strMap.put(iCacheKey.toString(), map.get(iCacheKey));
			if (iCacheKey.getExpirationTime() > -1) {
				expireKeyList.add(iCacheKey);
			}
		}
		valueOperations.multiSet(strMap);

		for (ICacheKey iCacheKey : expireKeyList)
			expire(iCacheKey);
	}

	public static <T> List<T> mget(Collection<ICacheKey> iCacheKeys) {
		List<String> keyStrList = new ArrayList<String>();
		for (ICacheKey iCacheKey : iCacheKeys) {
			keyStrList.add(iCacheKey.toString());
		}

		Object list = valueOperations.multiGet(keyStrList);
		if (list == null) {
			list = new ArrayList<Object>();
		}

		List<T> resultList = new ArrayList<T>();
		for (Iterator<T> localIterator2 = ((List<T>) list).iterator(); localIterator2
				.hasNext();) {
			T result = localIterator2.next();
			resultList.add(result);
		}

		return resultList;
	}

	public static <T> List<T> mget(Collection<ICacheKey> iCacheKeys,
			Class<T> clazz) {
		return mget(iCacheKeys);
	}

	public static <T> T getSet(ICacheKey iCacheKey, Object value, Class<T> clazz) {
		T result = (T) valueOperations.getAndSet(iCacheKey.toString(), value);
		expire(iCacheKey);
		return result;
	}

	public static int append(ICacheKey iCacheKey, String value,
			boolean renewExpired) {
		Integer len = strValueOperations.append(iCacheKey.toString(), value);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(len);
	}

	public static long incr(ICacheKey iCacheKey, boolean renewExpired) {
		return incrBy(iCacheKey, 1L, renewExpired);
	}

	public static long incrBy(ICacheKey iCacheKey, long delta,
			boolean renewExpired) {
		Long result = valueOperations.increment(iCacheKey.toString(), delta);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long decr(ICacheKey iCacheKey, boolean renewExpired) {
		return incrBy(iCacheKey, -1, renewExpired);
	}

	public static double decrBy(ICacheKey iCacheKey, double delta,
			boolean renewExpired) {
		Double result = valueOperations.increment(iCacheKey.toString(), -delta);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static void setRange(ICacheKey iCacheKey, String value, int offset,
			boolean renewExpired) {
		strValueOperations.set(iCacheKey.toString(), value, offset);
		if (renewExpired)
			expire(iCacheKey);
	}

	public static long strLen(ICacheKey iCacheKey) {
		Long result = valueOperations.size(iCacheKey.toString());
		return ParseUtil.parse(result);
	}

	public static boolean setBit(ICacheKey iCacheKey, int offset,
			boolean value, boolean renewExpired) {
		Boolean result = valueOperations.setBit(iCacheKey.toString(), offset,
				value);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static boolean getBit(ICacheKey iCacheKey, int offset) {
		Boolean result = valueOperations.getBit(iCacheKey.toString(), offset);
		return ParseUtil.parse(result);
	}

	public static void hdel(ICacheKey iCacheKey, String hashKey) {
		hashOperations.delete(iCacheKey.toString(), new Object[] { hashKey });
	}

	public static void hset(ICacheKey iCacheKey, String hashKey, Object value,
			boolean renewExpired) {
		hashOperations.put(iCacheKey.toString(), hashKey, value);
		if (renewExpired)
			expire(iCacheKey);
	}

	public static boolean hsetNx(ICacheKey iCacheKey, String hashKey,
			Object value, boolean renewExpired) {
		boolean result = ParseUtil.parse(hashOperations.putIfAbsent(
				iCacheKey.toString(), hashKey, value));

		if ((result) && (renewExpired)) {
			expire(iCacheKey);
		}
		return result;
	}

	public static <T> T hget(ICacheKey iCacheKey, String hashKey, Class<T> clazz) {
		T result = (T) hashOperations.get(iCacheKey.toString(), hashKey);
		return result;
	}

	public static <T> Map<String, T> hgetAll(ICacheKey iCacheKey) {
		Map<String, T> map = (Map<String, T>) hashOperations.entries(iCacheKey
				.toString());
		if (map == null) {
			map = new HashMap<String, T>();
		}
		Map<String, T> resultMap = new HashMap<String, T>();
		for (String key : map.keySet()) {
			resultMap.put(key, map.get(key));
		}
		return resultMap;
	}

	public static <T> Map<String, T> hgetAll(ICacheKey iCacheKey, Class<T> clazz) {
		return hgetAll(iCacheKey);
	}

	public static Set<String> hkeys(ICacheKey iCacheKey) {
		Set<String> set = hashOperations.keys(iCacheKey.toString());
		if (set == null) {
			set = new HashSet<String>();
		}
		return set;
	}

	public static <T> List<T> hvals(ICacheKey iCacheKey) {
		List<T> list = (List<T>) hashOperations.values(iCacheKey.toString());
		if (list == null) {
			list = new ArrayList<T>();
		}
		List<T> resultList = new ArrayList<T>();
		for (Iterator<T> localIterator = list.iterator(); localIterator
				.hasNext();) {
			T result = localIterator.next();
			resultList.add(result);
		}
		return resultList;
	}

	public static <T> List<T> hvals(ICacheKey iCacheKey, Class<T> clazz) {
		return hvals(iCacheKey);
	}

	public static boolean hexists(ICacheKey iCacheKey, String hashKey) {
		Boolean result = hashOperations.hasKey(iCacheKey.toString(), hashKey);
		return ParseUtil.parse(result);
	}

	public static long hincrby(ICacheKey iCacheKey, String hashKey, long delta,
			boolean renewExpired) {
		Long result = hashOperations.increment(iCacheKey.toString(), hashKey,
				delta);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long hlen(ICacheKey iCacheKey) {
		Long result = hashOperations.size(iCacheKey.toString());
		return ParseUtil.parse(result);
	}

	public static void hmset(ICacheKey iCacheKey, Map<String, Object> map,
			boolean renewExpired) {
		Map<String, Object> strMap = new HashMap<String, Object>();
		for (String hashKey : map.keySet()) {
			strMap.put(hashKey, map.get(hashKey));
		}
		hashOperations.putAll(iCacheKey.toString(), strMap);
		if (renewExpired)
			expire(iCacheKey);
	}

	public static <T> List<T> hmget(ICacheKey iCacheKey,
			Collection<String> hashKeys) {
		List<T> list = (List<T>) hashOperations.multiGet(iCacheKey.toString(),
				hashKeys);
		if (list == null) {
			list = new ArrayList<T>();
		}
		List<T> resultList = new ArrayList<T>();
		for (Iterator<T> localIterator = list.iterator(); localIterator
				.hasNext();) {
			T result = localIterator.next();
			resultList.add(result);
		}
		return resultList;
	}

	public static <T> List<T> hmget(ICacheKey iCacheKey,
			Collection<String> hashKeys, Class<T> clazz) {
		return hmget(iCacheKey, hashKeys);
	}

	public static <T> T lindex(ICacheKey iCacheKey, long index) {
		T result = (T) listOperations.index(iCacheKey.toString(), index);
		return result;
	}

	public static long llen(ICacheKey iCacheKey) {
		Long result = listOperations.size(iCacheKey.toString());
		return ParseUtil.parse(result);
	}

	public static <T> T lpop(ICacheKey iCacheKey, Class<T> clazz) {
		T result = (T) listOperations.leftPop(iCacheKey.toString());
		return result;
	}

	public static long lpush(ICacheKey iCacheKey, Object value,
			boolean renewExpired) {
		Long result = listOperations.leftPush(iCacheKey.toString(), value);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long lpushAll(ICacheKey iCacheKey, Collection<Object> values,
			boolean renewExpired) {
		Long result = listOperations.leftPushAll(iCacheKey.toString(), values);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long lpushHx(ICacheKey iCacheKey, Object value,
			boolean renewExpired) {
		Long result = listOperations.leftPushIfPresent(iCacheKey.toString(),
				value);
		if ((renewExpired) && (ParseUtil.parse(result) > 0L)) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static <T> List<T> lrange(ICacheKey iCacheKey, long start, long stop) {
		List<T> list = (List<T>) listOperations.range(iCacheKey.toString(),
				start, stop);
		if (list == null) {
			return new ArrayList<T>();
		}

		List<T> resultList = new ArrayList<T>();
		for (Iterator<T> localIterator = list.iterator(); localIterator
				.hasNext();) {
			T result = localIterator.next();
			resultList.add(result);
		}
		return resultList;
	}

	public static <T> List<T> lrange(ICacheKey iCacheKey, long start,
			long stop, Class<T> clazz) {
		return lrange(iCacheKey, start, stop);
	}

	public static long lrem(ICacheKey iCacheKey, Object value, long count) {
		Long result = listOperations.remove(iCacheKey.toString(), count, value);
		return ParseUtil.parse(result);
	}

	public static void lset(ICacheKey iCacheKey, Object value, long index,
			boolean renewExpired) {
		listOperations.set(iCacheKey.toString(), index, value);
		if (renewExpired)
			expire(iCacheKey);
	}

	public static void ltrim(ICacheKey iCacheKey, long start, long stop,
			boolean renewExpired) {
		listOperations.trim(iCacheKey.toString(), start, stop);
		if (renewExpired)
			expire(iCacheKey);
	}

	public static <T> T rpop(ICacheKey iCacheKey, Class<T> clazz) {
		T result = (T) listOperations.rightPop(iCacheKey.toString());
		return result;
	}

	public static <T> T rpopLpush(ICacheKey sourceICacheKey,
			ICacheKey destICacheKey, Class<T> clazz, boolean renewExpired) {
		T result = (T) listOperations.rightPopAndLeftPush(
				sourceICacheKey.toString(), destICacheKey.toString());
		if ((renewExpired) && (result != null)) {
			expire(destICacheKey);
		}
		return result;
	}

	public static long rpush(ICacheKey iCacheKey, Object value,
			boolean renewExpired) {
		Long result = listOperations.rightPush(iCacheKey.toString(), value);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long rpushAll(ICacheKey iCacheKey, Collection<Object> values,
			boolean renewExpired) {
		Long result = listOperations.rightPushAll(iCacheKey.toString(), values);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long rpushHx(ICacheKey iCacheKey, Object value,
			boolean renewExpired) {
		Long result = listOperations.rightPushIfPresent(iCacheKey.toString(),
				value);
		if ((renewExpired) && (ParseUtil.parse(result) > 0L)) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long sadd(ICacheKey iCacheKey, boolean renewExpired,
			Object[] values) {
		Long result = setOperations.add(iCacheKey.toString(), values);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long scard(ICacheKey iCacheKey) {
		Long result = setOperations.size(iCacheKey.toString());
		return ParseUtil.parse(result);
	}

	public static boolean sismember(ICacheKey iCacheKey, Object member) {
		Boolean result = setOperations.isMember(iCacheKey.toString(), member);
		return ParseUtil.parse(result);
	}

	public static <T> Set<T> smembers(ICacheKey iCacheKey) {
		Set<T> set = (Set<T>) setOperations.members(iCacheKey.toString());
		if (set == null) {
			return new HashSet<T>();
		}

		Set<T> resultSet = new HashSet<T>();
		for (Iterator<T> localIterator = set.iterator(); localIterator
				.hasNext();) {
			T result = localIterator.next();
			resultSet.add(result);
		}
		return resultSet;
	}

	public static <T> Set<T> smembers(ICacheKey iCacheKey, Class<T> clazz) {
		return smembers(iCacheKey);
	}

	public static <T> T spop(ICacheKey iCacheKey, Class<T> clazz) {
		T result = (T) setOperations.pop(iCacheKey.toString());
		return result;
	}

	public static void srem(ICacheKey iCacheKey, Object[] members) {
		setOperations.remove(iCacheKey.toString(), members);
	}

	public static boolean zadd(ICacheKey iCacheKey, Object value, double score,
			boolean renewExpired) {
		Boolean result = zsetOperations.add(iCacheKey.toString(), value, score);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long zcard(ICacheKey iCacheKey) {
		Long result = zsetOperations.size(iCacheKey.toString());
		return ParseUtil.parse(result);
	}

	public static long zcount(ICacheKey iCacheKey, double min, double max) {
		Long result = zsetOperations.count(iCacheKey.toString(), min, max);
		return ParseUtil.parse(result);
	}

	public static double zincrby(ICacheKey iCacheKey, Object value,
			double delta, boolean renewExpired) {
		Double result = zsetOperations.incrementScore(iCacheKey.toString(),
				value, delta);
		if (renewExpired) {
			expire(iCacheKey);
		}
		return ParseUtil.parse(result);
	}

	public static long zrank(ICacheKey iCacheKey, Object member) {
		Long result = zsetOperations.rank(iCacheKey.toString(), member);
		return ParseUtil.parse(result);
	}

	public static long zrevRank(ICacheKey iCacheKey, Object member) {
		Long result = zsetOperations.reverseRank(iCacheKey.toString(), member);
		return ParseUtil.parse(result);
	}

	public static <T> Set<T> zrange(ICacheKey iCacheKey, long start, long stop) {
		Set<T> set = (Set<T>) zsetOperations.range(iCacheKey.toString(), start,
				stop);
		if (set == null) {
			return new LinkedHashSet<T>();
		}
		Set<T> resultSet = new LinkedHashSet<T>();
		for (Iterator<T> localIterator = set.iterator(); localIterator
				.hasNext();) {
			T result = localIterator.next();
			resultSet.add(result);
		}
		return resultSet;
	}

	public static <T> Set<T> zrange(ICacheKey iCacheKey, long start, long stop,
			Class<T> clazz) {
		return zrange(iCacheKey, start, stop);
	}

	public static <T> Set<T> zrevRange(ICacheKey iCacheKey, long start,
			long stop) {
		Set<T> set = (Set<T>) zsetOperations.reverseRange(iCacheKey.toString(),
				start, stop);
		if (set == null) {
			return new LinkedHashSet<T>();
		}

		Set<T> resultSet = new LinkedHashSet<T>();
		for (Iterator<T> localIterator = set.iterator(); localIterator
				.hasNext();) {
			T result = localIterator.next();
			resultSet.add(result);
		}
		return resultSet;
	}

	public static <T> Set<T> zrevRange(ICacheKey iCacheKey, long start,
			long stop, Class<T> clazz) {
		return zrevRange(iCacheKey, start, stop);
	}

	public static <T> Set<T> zrangeByScore(ICacheKey iCacheKey, long min,
			long max) {
		Set<T> set = (Set<T>) zsetOperations.rangeByScore(iCacheKey.toString(),
				min, max);
		if (set == null) {
			return new LinkedHashSet<T>();
		}

		Set<T> resultSet = new LinkedHashSet<T>();
		for (Iterator<T> localIterator = set.iterator(); localIterator
				.hasNext();) {
			T result = localIterator.next();
			resultSet.add(result);
		}
		return resultSet;
	}

	public static <T> Set<T> zrangeByScore(ICacheKey iCacheKey, long min,
			long max, Class<T> clazz) {
		return zrangeByScore(iCacheKey, min, max);
	}

	public static <T> Set<T> zrevRangeByScore(ICacheKey iCacheKey, long min,
			long max) {
		Set<T> set = (Set<T>) zsetOperations.reverseRangeByScore(
				iCacheKey.toString(), min, max);
		if (set == null) {
			return new LinkedHashSet<T>();
		}
		Set<T> resultSet = new LinkedHashSet<T>();
		for (Iterator<T> localIterator = set.iterator(); localIterator
				.hasNext();) {
			T result = localIterator.next();
			resultSet.add(result);
		}
		return resultSet;
	}

	public static <T> Set<T> zrevRangeByScore(ICacheKey iCacheKey, long min,
			long max, Class<T> clazz) {
		return zrevRangeByScore(iCacheKey, min, max);
	}

	public static double zscore(ICacheKey iCacheKey, Object member) {
		Double result = zsetOperations.score(iCacheKey.toString(), member);
		return ParseUtil.parse(result);
	}

	public static void zrem(ICacheKey iCacheKey, Object[] members) {
		zsetOperations.remove(iCacheKey.toString(), members);
	}

	public static long zremRangeByRank(ICacheKey iCacheKey, long start, long end) {
		Long result = zsetOperations.removeRange(iCacheKey.toString(), start,
				end);
		return ParseUtil.parse(result);
	}

	public static long zremRangeByScore(ICacheKey iCacheKey, long min, long max) {
		Long result = zsetOperations.removeRangeByScore(iCacheKey.toString(),
				min, max);
		return ParseUtil.parse(result);
	}
}