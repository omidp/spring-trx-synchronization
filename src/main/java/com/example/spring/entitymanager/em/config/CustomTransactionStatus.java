package com.example.spring.entitymanager.em.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

@RequiredArgsConstructor
public class CustomTransactionStatus implements TransactionStatus {
	@Getter private final TransactionStatus transaction2;
	@Getter private final TransactionStatus transaction3;
	@Override
	public Object createSavepoint() throws TransactionException {
		return null;
	}

	@Override
	public void rollbackToSavepoint(Object savepoint) throws TransactionException {

	}

	@Override
	public void releaseSavepoint(Object savepoint) throws TransactionException {

	}
}
