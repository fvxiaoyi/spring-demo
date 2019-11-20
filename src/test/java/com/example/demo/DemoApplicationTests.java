package com.example.demo;

import java.util.EnumSet;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.boot.model.TypeContributor;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.spi.Bootstrap;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.hbm2ddl.UniqueConstraintSchemaUpdateStrategy;
import org.hibernate.tool.schema.TargetType;
import org.junit.runner.RunWith;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	public static void main(String... strings) throws Exception {
		String pkg = DemoApplicationTests.class.getPackage().getName();
		stdoutUpdateSchema(pkg);
		System.out.println("--> Version: V1_0_" + System.currentTimeMillis());
	}

	public static void stdoutUpdateSchema(String pkg, TypeContributor... contributors) {
		EntityManagerFactoryBuilderImpl builder = genEntityManagerFactoryBuilder(pkg, null, contributors);
		builder.build();
		new SchemaUpdate().setFormat(true).execute(EnumSet.of(TargetType.STDOUT), builder.getMetadata());
	}

	private static EntityManagerFactoryBuilderImpl genEntityManagerFactoryBuilder(String pkg, String mappingResource,
			TypeContributor... contributors) {
		SpringApplication application = new SpringApplication(DataSourceAutoConfiguration.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.setBannerMode(Banner.Mode.OFF);
		ApplicationContext applicationContext = application.run();
		DataSource dataSource = applicationContext.getBean(DataSource.class);

		Properties props = new Properties();
		props.put(Environment.DIALECT, MySQL57Dialect.class);
		props.put(Environment.DATASOURCE, dataSource);
		props.put(Environment.UNIQUE_CONSTRAINT_SCHEMA_UPDATE_STRATEGY,
				UniqueConstraintSchemaUpdateStrategy.RECREATE_QUIETLY);
		DefaultPersistenceUnitManager manager = new DefaultPersistenceUnitManager();
		manager.setPackagesToScan(pkg);

		manager.afterPropertiesSet();
		EntityManagerFactoryBuilder builder = Bootstrap
				.getEntityManagerFactoryBuilder(manager.obtainDefaultPersistenceUnitInfo(), props);
		return (EntityManagerFactoryBuilderImpl) builder;
	}
}
