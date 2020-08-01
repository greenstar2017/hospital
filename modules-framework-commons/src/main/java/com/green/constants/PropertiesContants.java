package com.green.constants;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置常量
 * 
 * @author yuanhualiang
 */
@Component
@ConfigurationProperties(prefix = "")
public class PropertiesContants {

	public static PropertiesContants that;

	@PostConstruct
	private void init() {
		that = this;
	}

	/**
	 * cookie存储域名
	 */
	private String cookieBaseDomain = "";
	
	private boolean userProxy = false;
	
	private String proxyAgentUrl = "";

	public String getCookieBaseDomain() {
		return cookieBaseDomain;
	}

	public void setCookieBaseDomain(String cookieBaseDomain) {
		this.cookieBaseDomain = cookieBaseDomain;
	}

	public boolean isUserProxy() {
		return userProxy;
	}

	public void setUserProxy(boolean userProxy) {
		this.userProxy = userProxy;
	}

	public String getProxyAgentUrl() {
		return proxyAgentUrl;
	}

	public void setProxyAgentUrl(String proxyAgentUrl) {
		this.proxyAgentUrl = proxyAgentUrl;
	}
}
