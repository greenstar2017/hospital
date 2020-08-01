package com.globalexception;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.green.annotations.NoRequireLogin;
import com.green.response.RestObject;

/**
 * 统一异常处理
 *
 * @author yuanhualiang
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalBasicErrorController extends AbstractErrorController {

	private final ErrorProperties errorProperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalBasicErrorController.class);

	/**
	 * Create a new
	 * {@link org.springframework.boot.autoconfigure.web.BasicErrorController}
	 * instance.
	 *
	 * @param errorAttributes
	 *            the error attributes
	 * @param errorProperties
	 *            configuration properties
	 */
	public GlobalBasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
		this(errorAttributes, errorProperties, Collections.<ErrorViewResolver> emptyList());
	}

	/**
	 * Create a new
	 * {@link org.springframework.boot.autoconfigure.web.BasicErrorController}
	 * instance.
	 *
	 * @param errorAttributes
	 *            the error attributes
	 * @param errorProperties
	 *            configuration properties
	 * @param errorViewResolvers
	 *            error view resolvers
	 */
	public GlobalBasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties,
			List<ErrorViewResolver> errorViewResolvers) {
		super(errorAttributes, errorViewResolvers);
		Assert.notNull(errorProperties, "ErrorProperties must not be null");
		this.errorProperties = errorProperties;
	}

	@NoRequireLogin
	@Override
	public String getErrorPath() {
		return this.errorProperties.getPath();
	}

	/**
	 * GET提交异常处理==》跳转error页面
	 * @param request
	 * @param response
	 * @return
	 */
	@NoRequireLogin
	@RequestMapping(produces = "text/html")
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		Object messageObj = body.get("message");
		String view = "error";
		if(null != messageObj) {
			String message = messageObj.toString();
			if(message.contains("errorCode:004")) {//登录状态已过期，请重新登录
				view = "login";
			}
		}
		Map<String, Object> model = Collections
				.unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
		response.setStatus(status.value());
		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
		return modelAndView == null ? new ModelAndView(view, model) : modelAndView;
	}

	/**
	 * POST提交返回JSON异常处理==》输出错误信息
	 * @param request
	 * @return
	 */
	@NoRequireLogin
	@RequestMapping
	@ResponseBody
	public RestObject error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = getStatus(request);
		String message = (null == body.get("message")) ? "" : body.get("message").toString();
		body.put("httpStatus", status.name());
		
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> global catch error params start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		for(String key : body.keySet()) {
			LOGGER.info(key + ":" + body.get(key));
		}
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> global catch error params end >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if(message.contains("errorCode:004")) {//登录状态已过期，请重新登录
			return RestObject.newTimeout("登录状态已过期，请重新登录");
		}
		return RestObject.newError(message, body);
	}

	/**
	 * Determine if the stacktrace attribute should be included.
	 *
	 * @param request
	 *            the source request
	 * @param produces
	 *            the media type produced (or {@code MediaType.ALL})
	 * @return if the stacktrace attribute should be included
	 */
	protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
		ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
		if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
			return true;
		}
		if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
			return getTraceParameter(request);
		}
		return false;
	}

	/**
	 * Provide access to the error properties.
	 *
	 * @return the error properties
	 */
	protected ErrorProperties getErrorProperties() {
		return this.errorProperties;
	}
}