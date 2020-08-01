package com.green.exception;

/**
 * 公共错误定义
 * 
 * yuanhualiang
 */
public class CommonError extends ErrorEntity
{

    private CommonError(String errorCode, String errorMessage)
    {
        super(errorCode, errorMessage);
    }

    public String getHeadCode()
    {
        return "-00";
    }
    
    public static final ErrorEntity ARGS_EMPTY = new CommonError("001", "参数为空");
    public static final ErrorEntity ARGS_ERROR = new CommonError("002", "参数错误");
    public static final ErrorEntity NO_LOGIN = new CommonError("003", "请登录");
    public static final ErrorEntity LOGIN_EXPIRE = new CommonError("004", "登录状态已过期，请重新登录");
    public static final ErrorEntity SERVER_ERROR = new CommonError("005", "系统错误");
    public static final ErrorEntity SMS_SEND_FAILURE = new CommonError("006", "\u77ED\u4FE1\u53D1\u9001\u5931\u8D25");
    public static final ErrorEntity CAPTCHA_EXPIRE = new CommonError("007", "\u9A8C\u8BC1\u7801\u5DF2\u7ECF\u8FC7\u671F");
    public static final ErrorEntity INCORRECT_CAPTCHA = new CommonError("008", "\u9A8C\u8BC1\u7801\u9519\u8BEF");
    public static final ErrorEntity INVALID_REQUEST = new CommonError("009", "请求不合法");
    public static final ErrorEntity PHONE_IS_NULL = new CommonError("010", "\u624B\u673A\u53F7\u7801\u4E0D\u80FD\u4E3A\u7A7A");
    public static final ErrorEntity PHONE_FORMAT_ERROR = new CommonError("011", "\u8BF7\u8F93\u5165\u6B63\u786E\u7684\u624B\u673A\u53F7");
    public static final ErrorEntity INVALID_IMAGE_SUFFIX = new CommonError("012", "\u65E0\u6548\u56FE\u7247\u540E\u7F00");
    public static final ErrorEntity OPERATE_TOO_FREQUENT = new CommonError("013", "\u60A8\u7684\u64CD\u4F5C\u8FC7\u4E8E\u9891\u7E41");
    public static final ErrorEntity ACCOUNT_NOT_EXIST = new CommonError("014", "\u8D26\u53F7\u4E0D\u5B58\u5728");
    public static final ErrorEntity EQUIPMENT_DISTRICT = new CommonError("015", "\u60A8\u5DF2\u5728\u53E6\u4E00\u53F0\u8BBE\u5907\u767B\u5F55\uFF0C\u5F53\u524D\u8BBE\u5907\u6388\u6743\u5DF2\u5931\u6548");
    public static final ErrorEntity GENDER_WALL = new CommonError("016", "\u53EA\u80FD\u4E92\u52A8\u5F02\u6027");
    public static final ErrorEntity BUSINESS_ERROR = new CommonError("017", "\u4E1A\u52A1\u5C42\u9A8C\u8BC1\u53C2\u6570\u9519\u8BEF");
    public static final ErrorEntity NO_SIGN_REQUEST = new CommonError("018", "\u65E0\u6548\u8BF7\u6C42");

}