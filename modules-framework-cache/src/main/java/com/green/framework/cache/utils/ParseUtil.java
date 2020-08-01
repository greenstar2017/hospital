package com.green.framework.cache.utils;

import org.apache.commons.lang.CharUtils;

import com.alibaba.fastjson.JSON;

/**
 * 类型转换工具
 * 
 * @author yuanhualiang
 *
 */
public class ParseUtil {
	public static int parse(Integer value) {
		return value == null ? 0 : value;
	}

	public static long parse(Long value) {
		return value == null ? 0 : value;
	}

	public static double parse(Double value) {
		return value == null ? 0 : value;
	}

	public static boolean parse(Boolean value) {
		return value == null ? false : value;
	}

	public static String jsonSerial(Object value) {
		// 优先判断性能较高
		if (value == null) {
			return null;

		} else if (value instanceof Boolean) {
			return value.toString();

		} else if (value instanceof Character) {
			return value.toString();

		} else if (value instanceof Short) {
			return value.toString();

		} else if (value instanceof Integer) {
			return value.toString();

		} else if (value instanceof Long) {
			return value.toString();

		} else if (value instanceof Float) {
			return value.toString();

		} else if (value instanceof Double) {
			return value.toString();

		} else if (value instanceof Byte) {
			return value.toString();

		} else if (value instanceof String) {
			return value.toString();

		}
		return JSON.toJSONString(value);
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> T toObject(String value, Class<T> clazz) {
		if (value == null) {
			return null;

		} else if (clazz == Boolean.class) {
			return (T) Boolean.valueOf(value);

		} else if (clazz == Character.class) {
			return (T) CharUtils.toCharacterObject(value);

		} else if (clazz == Short.class) {
			return (T) Short.valueOf(value);

		} else if (clazz == Integer.class) {
			return (T) Integer.valueOf(value);

		} else if (clazz == Long.class) {
			return (T) Long.valueOf(value);

		} else if (clazz == Float.class) {
			return (T) Float.valueOf(value);

		} else if (clazz == Double.class) {
			return (T) Double.valueOf(value);

		} else if (clazz == Byte.class) {
			return (T) Byte.valueOf(value);

		} else if (clazz == String.class) {
			return (T) value;

		}
		return (T) JSON.parseObject(value, clazz);
	}
}
