package com.green.auth.constants;

/**
 * 登录常量
 * 
 * yuanhualiang
 */
public interface LoginConstants
{

    public static final String LOGIN_PSW_FAIL_COUNT_KEY = "login:fail:password:account:";
    public static final String LOGIN_PSW_FAIL_LOCK_KEY = "login:fail:password:lock:";
    public static final int LOGIN_PSW_FAIL_COUNT = 5;
    public static final int LOGIN_PSW_FAIL_LIMIT_TIME = 2;
    public static final String LOGIN_PSW_FAIL_LOCK_EXISTS = "yes";
    public static final String REQUEST_ATTR_LOGIN_ID = "request_attr_login_ID";
    public static final String SID_NAME = "sid";
    public static final String TOKEN_NAME = "token";
    public static final String TOKEN_SIGN_SECRET = "f$12oHak.uC&^~";
    public static final String TOKEN_SEPARATOR = ".";
    public static final long INVALID_MID = -1L;
    public static final int IP_LOGINED_ACC_NUM_THRESHOLD = 2;
}