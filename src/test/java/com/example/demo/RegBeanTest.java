package com.example.demo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.regbean.ApplicationContextHolder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegBeanTest {

	@Autowired
	private ApplicationContextHolder holder;

	@Test
	public void test() throws SQLException {
		String beanName = "myDataSource";
		String entityManagerFactoryBean = "myEntityManager";
		holder.registerBean(beanName, newDataSource());
		DataSource bean = holder.getBean(beanName, DataSource.class);
		holder.registerBean(entityManagerFactoryBean, newEntityManagerFactoryBean(bean));
		EntityManager em = holder.getBean(entityManagerFactoryBean);
		//		JdbcTemplate tpl = new JdbcTemplate(bean);
		//		List<Map<String, Object>> queryForList = tpl.queryForList("select * from t_base_data_def");
		System.out.println(em);
	}

	public LocalContainerEntityManagerFactoryBean newEntityManagerFactoryBean(DataSource dataSource) {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL57Dialect");
		EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter, new HashMap<>(), null);
		LocalContainerEntityManagerFactoryBean build = builder.dataSource(dataSource).packages("com.example.demo.jpa.*").build();
		build.afterPropertiesSet();
		return build;
	}

	public DataSource newDataSource() throws SQLException {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(
				"jdbc:mysql://172.18.13.242:3306/test?useSSL=true&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setFilters("stat");
		dataSource.setMaxActive(3);
		dataSource.setInitialSize(1);
		dataSource.setMaxWait(60000);
		dataSource.setMinIdle(2);
		dataSource.setTimeBetweenEvictionRunsMillis(3000);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		return dataSource;
	}
}
