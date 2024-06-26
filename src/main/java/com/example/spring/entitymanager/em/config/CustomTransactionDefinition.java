package com.example.spring.entitymanager.em.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@RequiredArgsConstructor
public class CustomTransactionDefinition extends DefaultTransactionDefinition {

	@Getter private final TransactionDefinition nestedTransactionDefinition;

}
