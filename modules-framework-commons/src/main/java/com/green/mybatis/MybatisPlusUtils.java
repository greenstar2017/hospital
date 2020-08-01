package com.green.mybatis;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 
 * @author yuanhualiang
 *
 */
public class MybatisPlusUtils {
	
	//创建的表名
	private static String table = "crawl_data_record_bak";

	public static void main(String[] args) {
		String[][] models = new String[][] {
				{ "model",
						"F:/workspace_smallload/easycrawl-dao/src/main/java" },
				{ "mapper",
						"F:/workspace_smallload/easycrawl-dao/src/main/java" },
				{ "xml",
						"F:/workspace_smallload/easycrawl-dao/src/main/resources" },
				{ "service",
						"F:/workspace_smallload/easycrawl-service/src/main/java" },
				{
						"serviceImpl",
						"F:/workspace_smallload/easycrawl-service/src/main/java" } };
		for (String[] model : models) {
			shell(model);
		}
	}

	private static void shell(String[] model) {
		String path = model[1];
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(path);
		gc.setOpen(false);
		gc.setFileOverride(true);
		gc.setActiveRecord(false);
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor("yuanhualiang");

		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sMapper");
		gc.setServiceName("%sService");
		gc.setServiceImplName("%sServiceImpl");
		// gc.setControllerName("%sController");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert() {
			// 自定义数据库表字段类型转换【可选】
			@Override
			public DbColumnType processTypeConvert(String fieldType) {
//				System.out.println("转换类型：" + fieldType);
				// 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
				return super.processTypeConvert(fieldType);
			}
		});
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("easycrawl");
		dsc.setPassword("Yhl890627.");
		dsc.setUrl("jdbc:mysql://47.105.89.91:3306/easycrawl?characterEncoding=utf8");
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(new String[] { table }); // 需要生成的表
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("");
		// pc.setController("controller");
		pc.setEntity("com.green.entity");
		pc.setMapper("com.green.mapper");
		pc.setXml("com.green.mapper");
		pc.setService("com.green.service");
		pc.setServiceImpl("com.green.service.impl");
		mpg.setPackageInfo(pc);

		// 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
		// 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
		TemplateConfig tc = new TemplateConfig();
		if ("mapper".equals(model[0])) {
			tc.setController(null);
			tc.setEntity(null);
			tc.setService(null);
			tc.setServiceImpl(null);
			tc.setXml(null);
		} else if ("model".equals(model[0])) {
			tc.setController(null);
			tc.setService(null);
			tc.setServiceImpl(null);
			tc.setMapper(null);
			tc.setXml(null);
		} else if ("xml".equals(model[0])) {
			tc.setController(null);
			tc.setService(null);
			tc.setServiceImpl(null);
			tc.setMapper(null);
			tc.setEntity(null);
		} else if ("service".equals(model[0])) {
			tc.setController(null);
			tc.setMapper(null);
			tc.setXml(null);
			tc.setEntity(null);
			tc.setServiceImpl(null);
		} else if ("serviceImpl".equals(model[0])) {
			tc.setController(null);
			tc.setMapper(null);
			tc.setXml(null);
			tc.setService(null);
			tc.setEntity(null);
		}
		mpg.setTemplate(tc);

		// 执行生成
		mpg.execute();
	}

}