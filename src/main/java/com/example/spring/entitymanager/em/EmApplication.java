package com.example.spring.entitymanager.em;

import com.example.spring.entitymanager.em.config.CustomPlatformTransactionManager;
import com.example.spring.entitymanager.em.config.CustomTransactionStatus;
import com.example.spring.entitymanager.em.domain.db2.ProductEntity;
import com.example.spring.entitymanager.em.domain.db3.CustomerEntity;
import com.example.spring.entitymanager.em.repo2.ProductDao;
import com.example.spring.entitymanager.em.repo2.ProductService;
import com.example.spring.entitymanager.em.repo3.CustomerDao;
import com.example.spring.entitymanager.em.repo3.CustomerService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class EmApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmApplication.class, args);
	}


	@Bean
	ApplicationRunner runner(ProductService productService, CustomerService customerService, CustomPlatformTransactionManager transactionManager){
		return args -> {

		};
	}

}
