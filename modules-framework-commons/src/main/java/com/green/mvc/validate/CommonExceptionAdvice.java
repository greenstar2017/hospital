package com.green.mvc.validate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.green.response.RestObject;

/**
 * @author JE哥
 * @email 1272434821@qq.com
 * @description:全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionAdvice {

  private static Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
    logger.error("缺少请求参数", e);
    //BusinessAssert.error(CommonError.ARGS_ERROR.newMessage("缺少请求参数"));
    return RestObject.newError("缺少请求参数");
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    logger.error("参数解析失败", e);
    //BusinessAssert.error(CommonError.ARGS_ERROR.newMessage("参数解析失败"));
    return RestObject.newError("参数解析失败");
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    logger.error("参数验证失败", e);
    BindingResult result = e.getBindingResult();
    FieldError error = result.getFieldError();
    String field = error.getField();
    String code = error.getDefaultMessage();
    String message = String.format("%s:%s", field, code);
    //BusinessAssert.error(CommonError.ARGS_ERROR.newMessage(message));
    return RestObject.newError(message);
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  public Object handleBindException(BindException e) {
    logger.error("参数绑定失败", e);
    BindingResult result = e.getBindingResult();
    FieldError error = result.getFieldError();
    String field = error.getField();
    String code = error.getDefaultMessage();
    String message = String.format("%s:%s", field, code);
    //BusinessAssert.error(CommonError.ARGS_ERROR.newMessage(message));
    return RestObject.newError(message);
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public Object handleServiceException(ConstraintViolationException e) {
    logger.error("参数验证失败", e);
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    ConstraintViolation<?> violation = violations.iterator().next();
    String message = violation.getMessage();
    //BusinessAssert.error(CommonError.ARGS_ERROR.newMessage("parameter:" + message));
    return RestObject.newError("parameter:" + message);
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public Object handleValidationException(ValidationException e) {
    logger.error("参数验证失败", e);
    //BusinessAssert.error(CommonError.ARGS_ERROR.newMessage("参数验证失败"));
    return RestObject.newError("参数验证失败");
  }

  /**
   * 405 - Method Not Allowed
   */
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    logger.error("不支持当前请求方法", e);
    //BusinessAssert.error(CommonError.ARGS_ERROR.newMessage("不支持当前请求方法"));
    return RestObject.newError("不支持当前请求方法");
  }

  /**
   * 415 - Unsupported Media Type
   */
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public Object handleHttpMediaTypeNotSupportedException(Exception e) {
    logger.error("不支持当前媒体类型", e);
    //BusinessAssert.error(CommonError.ARGS_ERROR.newMessage("不支持当前媒体类型"));
    return RestObject.newError("不支持当前媒体类型");
  }

  /**
   * 操作数据库出现异常:名称重复，外键关联
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(DataIntegrityViolationException.class)
  public Object handleException(DataIntegrityViolationException e) {
    logger.error("操作数据库出现异常:", e);
    //BusinessAssert.error(CommonError.ARGS_ERROR.newMessage("操作数据库出现异常：字段重复、有外键关联等"));
    return RestObject.newError("操作数据库出现异常：字段重复、有外键关联等");
  }
}