package com.example.spring.entitymanager.em.controller;

import com.example.spring.entitymanager.em.config.CustomPlatformTransactionManager;
import com.example.spring.entitymanager.em.config.CustomTransactionDefinition;
import com.example.spring.entitymanager.em.config.CustomTransactionStatus;
import com.example.spring.entitymanager.em.repo2.ProductService;
import com.example.spring.entitymanager.em.repo3.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

	private final ProductService productService;
	private final CustomerService customerService;
	private final CustomPlatformTransactionManager transactionManager;

	@GetMapping("/testTrx")
	public String ok() {
		var c = new CustomTransactionDefinition(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
		CustomTransactionStatus transaction = null;
		try {
			productService.delete();
			customerService.delete();
			transaction = (CustomTransactionStatus) transactionManager.getTransaction(c);
			productService.insert();
			customerService.insert();
			transactionManager.commit(transaction);
		} catch (Exception e) {
			transactionManager.rollback(transaction);
		}

		return "ok";
	}
}
