package com.green;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * springboot内嵌tomcat启动类
 * @author yuanhualiang
 *
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.green.mapper")
@ComponentScan(basePackages = { "com.green" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
} 