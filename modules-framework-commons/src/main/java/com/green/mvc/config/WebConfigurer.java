package com.green.mvc.config;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import com.green.mvc.interceptor.LoginInterceptor;

/**
 * 添加拦截器
 * 
 * @author yuanhualiang
 */
@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {

	@Resource
	private LoginInterceptor loginInterceptor;

	@Resource
	private WebArgumentResolver userInfoArgumentResolver;

	/**
	 * 拦截器
	 *
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 登录拦截器
		registry.addInterceptor(loginInterceptor);

		super.addInterceptors(registry);
	}

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new ServletWebArgumentResolverAdapter(
				userInfoArgumentResolver));
		super.addArgumentResolvers(argumentResolvers);
	}

}
