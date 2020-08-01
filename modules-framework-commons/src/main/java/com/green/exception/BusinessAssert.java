package com.green.exception;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 业务断言
 * 
 * yuanhualiang
 */
public class BusinessAssert {

	public BusinessAssert() {
	}

	public static void error(ErrorEntity errorEntity) {
		throw BusinessException.error(errorEntity);
	}

	public static void isNotNull(Object obj, ErrorEntity errorEntity) {
		if (obj == null)
			throw BusinessException.error(errorEntity);
		else
			return;
	}

	public static void isNotBlank(String value, ErrorEntity errorEntity) {
		if (StringUtils.isBlank(value))
			throw BusinessException.error(errorEntity);
		else
			return;
	}

	public static void isTrue(Boolean isTrue, ErrorEntity errorEntity) {
		if (isTrue == null || !isTrue.booleanValue())
			throw BusinessException.error(errorEntity);
		else
			return;
	}

	public static void isNotEmpty(Collection<Object> collection,
			ErrorEntity errorEntity) {
		if (CollectionUtils.isEmpty(collection))
			throw BusinessException.error(errorEntity);
		else
			return;
	}

	public static void isNotPresent(Optional<ErrorEntity> optional) {
		if (optional.isPresent())
			throw BusinessException.error((ErrorEntity) optional.get());
		else
			return;
	}

	public static void error(ErrorEntity errorEntity, String msg,
			Object params[]) {
		throw BusinessException.error(errorEntity, msg, params);
	}

	public static void isNotNull(Object obj, ErrorEntity errorEntity,
			String msg, Object params[]) {
		if (obj == null)
			throw BusinessException.error(errorEntity, msg, params);
		else
			return;
	}

	public static void isNotBlank(String value, ErrorEntity errorEntity,
			String msg, Object params[]) {
		if (StringUtils.isBlank(value))
			throw BusinessException.error(errorEntity, msg, params);
		else
			return;
	}

	public static void isTrue(Boolean isTrue, ErrorEntity errorEntity,
			String msg, Object params[]) {
		if (isTrue == null || !isTrue.booleanValue())
			throw BusinessException.error(errorEntity, msg, params);
		else
			return;
	}

	public static void isNotEmpty(Collection<Object> collection,
			ErrorEntity errorEntity, String msg, Object params[]) {
		if (CollectionUtils.isEmpty(collection))
			throw BusinessException.error(errorEntity, msg, params);
		else
			return;
	}

	public static void isNotPresent(Optional<ErrorEntity> optional, String msg,
			Object params[]) {
		if (optional.isPresent())
			throw BusinessException.error((ErrorEntity) optional.get(), msg,
					params);
		else
			return;
	}
}