package com.example.spring.entitymanager.em;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.example.spring.entitymanager.em.domain.db2.ProductEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;



@TestConfiguration
@EntityScan(basePackageClasses = { ProductEntity.class })
//@EnableJpaRepositories(basePackageClasses = { ProductCategoryDao.class }, queryLookupStrategy = Key.USE_DECLARED_QUERY)
public class DBTestConfig {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource("jdbc:mysql://localhost:3306/webui-tenant-43",
				"root", "");
		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaProperties jpaProps) {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource);
		jpaVendorAdapter.setGenerateDdl(true);
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setJpaPropertyMap(jpaProps.getProperties());
		factoryBean.setPackagesToScan(ProductEntity.class.getPackage().getName());
		factoryBean.setPersistenceUnitName("spring-jpa-tenant");
		return factoryBean;
	}

	@Bean("jpaProps")
	public JpaProperties jpaProperties() {
		JpaProperties prop = new JpaProperties();
		prop.setShowSql(true);
		prop.setDatabase(Database.MYSQL);
		prop.setGenerateDdl(true);
		Map<String, String> m = new HashMap<>();
		m.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		m.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
		m.put("hibernate.show_sql", "true");
		m.put("hibernate.format_sql", "true");
		m.put("hibernate.highlight_sql", "true");

		prop.setProperties(m);
		return prop;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}