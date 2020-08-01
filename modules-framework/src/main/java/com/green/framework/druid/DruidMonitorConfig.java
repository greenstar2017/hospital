package com.green.framework.druid;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

/**
 * 数据库源连接池配置
 *
 */
@Configuration
public class DruidMonitorConfig {

	/**
	 * druidServlet注册
	 */
	@Bean
	public ServletRegistrationBean druidServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(
				new StatViewServlet());
		registration.addUrlMappings("/druid/*");
		registration.addInitParameter("loginUsername", "admin");
		registration.addInitParameter("loginPassword", "admin");
		registration
		.addInitParameter("principalSessionName", "druid管理员");
		return registration;
	}

	/**
	 * druid监控 配置URI拦截策略
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean druidStatFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
				new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions",
				"/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*");
		// 用于session监控页面的用户名显示 需要登录后主动将username注入到session里
		filterRegistrationBean
				.addInitParameter("principalSessionName", "druid管理员");
		return filterRegistrationBean;
	}

	/**
	 * druid数据库连接池监控
	 */
	@Bean
	public DruidStatInterceptor druidStatInterceptor() {
		return new DruidStatInterceptor();
	}

	@Bean
	public JdkRegexpMethodPointcut druidStatPointcut() {
		JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
		String patterns = "com.jiansha.*.service.*";
		// 可以set多个
		druidStatPointcut.setPatterns(patterns);
		return druidStatPointcut;
	}

	/**
	 * druid数据库连接池监控
	 */
	@Bean
	public BeanTypeAutoProxyCreator beanTypeAutoProxyCreator() {
		BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
		beanTypeAutoProxyCreator.setTargetBeanType(DruidDataSource.class);
		beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
		return beanTypeAutoProxyCreator;
	}

	/**
	 * druid 为druidStatPointcut添加拦截
	 * 
	 * @return
	 */
	@Bean
	public Advisor druidStatAdvisor() {
		return new DefaultPointcutAdvisor(druidStatPointcut(),
				druidStatInterceptor());
	}
}
