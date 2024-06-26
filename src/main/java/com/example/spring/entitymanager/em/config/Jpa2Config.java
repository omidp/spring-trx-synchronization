package com.example.spring.entitymanager.em.config;

import com.example.spring.entitymanager.em.domain.db2.ProductEntity;
import com.example.spring.entitymanager.em.repo2.ProductDao;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackageClasses = ProductDao.class, transactionManagerRef = "transactionManager2", entityManagerFactoryRef = "entityManagerFactory2")
public class Jpa2Config {

	///TESTDB2
	@Bean
	public PlatformTransactionManager transactionManager2(EntityManagerFactory entityManagerFactory2) {
		return new JpaTransactionManager(entityManagerFactory2);
	}

	public DataSource dataSource2() {
		HikariConfig config = new HikariConfig();
		config.setUsername("root");
		config.setPassword("");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb2");
		HikariDataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory2(JpaProperties jpaProps) {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource2());
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

}
