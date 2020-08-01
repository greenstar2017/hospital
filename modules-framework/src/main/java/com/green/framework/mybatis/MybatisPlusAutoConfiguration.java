package com.green.framework.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.green.framework.druid.DruidProperties;

/**
 * 加载mybatis
 * 
 * @author yuanhualiang
 *
 */
@Configuration
public class MybatisPlusAutoConfiguration {

	@Autowired
    DruidProperties druidProperties;

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
    	 PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
         paginationInterceptor.setDialectType(DBType.MYSQL.getDb());
         return paginationInterceptor;
    }
    
    /**
     * druid数据库连接池
     */
    @Bean(initMethod = "init", destroyMethod="close")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }
}