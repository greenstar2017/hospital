package com.green.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制器支持类
 * 
 * @author yuanhualiang
 * @version 2013-3-23
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 默认每页显示条数 */
	protected static final String PAGE_SIZE = "10";
	
	/** 默认当前页 */
	protected static final Integer PAGE_NO = 1;

}
