package com.green;

import java.util.List;

import javax.servlet.Servlet;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import com.globalexception.GlobalBasicErrorController;

/**
 * 修改SPRINGBOOT自动配置
 *
 * @author yuanhualiang
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(ResourceProperties.class)
public class ConfigSpringboot {
	
	private List<ErrorViewResolver> errorViewResolvers;
	
	private final ServerProperties serverProperties;

	public ConfigSpringboot(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	/**
	 * 覆盖springboot统一异常处理  ErrorMvcAutoConfiguration.basicErrorController()
	 * @param errorAttributes
	 * @return
	 */
	@Bean
	public GlobalBasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
		return new GlobalBasicErrorController(errorAttributes, this.serverProperties.getError(), this.errorViewResolvers);
	}

}