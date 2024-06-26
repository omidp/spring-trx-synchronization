package com.example.spring.entitymanager.em.config;

import com.example.spring.entitymanager.em.domain.db2.ProductEntity;
import com.example.spring.entitymanager.em.domain.db3.CustomerEntity;
import com.example.spring.entitymanager.em.repo3.CustomerDao;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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
@EnableJpaRepositories(basePackageClasses = CustomerDao.class, transactionManagerRef = "transactionManager3", entityManagerFactoryRef = "entityManagerFactory3")
public class Jpa3Config implements SmartInitializingSingleton, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Bean
	CustomPlatformTransactionManager customPlatformTransactionManager(){
		return new CustomPlatformTransactionManager();
	}



	///TESTDB3
	@Bean
	public PlatformTransactionManager transactionManager3(@Qualifier("entityManagerFactory3")EntityManagerFactory entityManagerFactory3) {
		return new JpaTransactionManager(entityManagerFactory3);
	}

	public DataSource dataSource3() {
		HikariConfig config = new HikariConfig();
		config.setUsername("root");
		config.setPassword("");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb3");
		HikariDataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory3(JpaProperties jpaProps) {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource3());
		jpaVendorAdapter.setGenerateDdl(true);
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setJpaPropertyMap(jpaProps.getProperties());
		factoryBean.setPackagesToScan(CustomerEntity.class.getPackage().getName());
		factoryBean.setPersistenceUnitName("spring-jpa-3");
		return factoryBean;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public void afterSingletonsInstantiated() {
		CustomPlatformTransactionManager bean = applicationContext.getBean(CustomPlatformTransactionManager.class);
		JpaTransactionManager tx2 = (JpaTransactionManager) applicationContext.getBean("transactionManager2");
		JpaTransactionManager tx3 = (JpaTransactionManager) applicationContext.getBean("transactionManager3");
		bean.setJpaTransactionManager2(tx2);
		bean.setJpaTransactionManager3(tx3);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
