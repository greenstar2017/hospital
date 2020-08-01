package com.green.framework.cache.utils;

import java.util.Date;

/**
 * 日期工具类
 * 
 * yuanhualiang
 */
public class DateUtil {

	/**
	 * 计算两个日期之前的时间差
	 * 
	 * @param start
	 *            - 开始时间
	 * @param end
	 *            - 结束时间
	 * @return - 时间差(单位:秒)
	 */
	public static long countDelta(Date start, Date end) {
		long time = start.getTime() - end.getTime();
		return time / 1000;
	}

}