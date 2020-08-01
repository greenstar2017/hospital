/**
 *
 * yuanhualiang
 */
package com.green.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 * 
 * @author yuanhualiang
 *
 */
public class ThreadPoolUtils {

	public static String CLEAR_HISTORY_DATA_POOL = "CLEAR_HISTORY_DATA_POOL";

	public static ThreadPoolExecutor getPool(String name) {
		if (CLEAR_HISTORY_DATA_POOL.equals(name)) {
			return clearHistoryDataExecutor.executor;
		} else {
			return null;
		}
	}

	/**
	 * 清理历史数据线程池
	 * 
	 * @author yuanhualiang
	 *
	 */
	static class clearHistoryDataExecutor {
		private static ThreadPoolExecutor executor = new ThreadPoolExecutor(50,
				50, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
						50 * 10));
	}
}
