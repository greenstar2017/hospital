package com.green.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不登录注解
 * 
 * yuanhualiang
 */
@Target({ java.lang.annotation.ElementType.METHOD,
		java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRequireLogin {
}