package com.green.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author lenovo
 *
 */
public class BeanUtils {
	/**
	 * 利用反射实现对象之间相同属性复制
	 * 
	 * @param source
	 *            要复制的
	 * @param to
	 *            复制给
	 * @param ignoreNull
	 *            忽略null值
	 */
	public static void copyProperties(Object source, Object target,
			boolean ignoreNull) throws Exception {

		copyPropertiesExclude(source, target, null, ignoreNull);
	}

	/**
	 * 利用反射实现对象之间相同属性复制
	 * 
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void copyProperties(Object source, Object target)
			throws Exception {

		copyPropertiesExclude(source, target, null, true);
	}

	/**
	 * 复制对象属性
	 * 
	 * @param from
	 * @param to
	 * @param excludsArray
	 *            排除属性列表
	 * @throws Exception
	 */
	public static void copyPropertiesExclude(Object from, Object to,
			String[] excludsArray, boolean ignoreNull) throws Exception {

		List<String> excludesList = null;

		if (excludsArray != null && excludsArray.length > 0) {

			excludesList = Arrays.asList(excludsArray); // 构造列表对象
		}

		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;

		for (int i = 0; i < fromMethods.length; i++) {

			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();

			if (!fromMethodName.contains("get"))
				continue;
			// 排除列表检测
			if (excludesList != null
					&& excludesList.contains(fromMethodName.substring(3, 4)
							.toLowerCase() + fromMethodName.substring(4))) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);

			if (toMethod == null)
				continue;
			Object value = fromMethod.invoke(from, new Object[0]);

			if (value == null && ignoreNull)
				continue;
			// 集合类判空处理
			if (value instanceof Collection) {

				Collection<?> newValue = (Collection<?>) value;

				if (newValue.size() <= 0)
					continue;
			}
			toMethod.invoke(to, new Object[] { value });
		}
	}

	/**
	 * 对象属性值复制，仅复制指定名称的属性值
	 * 
	 * @param from
	 * @param to
	 * @param includsArray
	 * @throws Exception
	 */
	public static void copyPropertiesInclude(Object from, Object to,
			String[] includsArray) throws Exception {

		List<String> includesList = null;

		if (includsArray != null && includsArray.length > 0) {

			includesList = Arrays.asList(includsArray);

		} else {

			return;
		}
		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;

		for (int i = 0; i < fromMethods.length; i++) {

			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();

			if (!fromMethodName.contains("get"))
				continue;

			// 排除列表检测
			String str = fromMethodName.substring(3);

			if (!includesList.contains(str.substring(0, 1).toLowerCase()
					+ str.substring(1))) {
				continue;
			}

			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);

			if (toMethod == null)
				continue;

			Object value = fromMethod.invoke(from, new Object[0]);

			if (value == null)
				continue;

			// 集合类判空处理
			if (value instanceof Collection) {

				Collection<?> newValue = (Collection<?>) value;

				if (newValue.size() <= 0)
					continue;
			}

			toMethod.invoke(to, new Object[] { value });
		}
	}

	/**
	 * 从方法数组中获取指定名称的方法
	 * 
	 * @param methods
	 * @param name
	 * @return
	 */
	public static Method findMethodByName(Method[] methods, String name) {

		for (int j = 0; j < methods.length; j++) {

			if (methods[j].getName().equals(name)) {

				return methods[j];
			}

		}
		return null;
	}

	/**
	 * Bean转MAP
	 * 
	 * @param obj
	 * @param excludeProperties
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj,
			String[] excludeProperties, String[] includeProperties) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (obj == null) {
			return null;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")
						&& (null == includeProperties || ArrayUtils.contains(
								includeProperties, key))
						&& (null == excludeProperties || !ArrayUtils.contains(
								excludeProperties, key))) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

	/**
	 * Bean转MAP
	 * 
	 * @param obj
	 * @param excludeProperties
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {

		return transBean2Map(obj, null, null);

	}

	/**
	 * Bean转MAP
	 * 
	 * @param obj
	 * @param excludeProperties
	 * @return
	 */
	public static Map<String, Object> transBean2MapEx(Object obj,
			String[] excludeProperties) {

		return transBean2Map(obj, excludeProperties, null);

	}

	/**
	 * Bean转MAP
	 * 
	 * @param obj
	 * @param excludeProperties
	 * @return
	 */
	public static Map<String, Object> transBean2MapIn(Object obj,
			String[] includeProperties) {

		return transBean2Map(obj, null, includeProperties);

	}
}