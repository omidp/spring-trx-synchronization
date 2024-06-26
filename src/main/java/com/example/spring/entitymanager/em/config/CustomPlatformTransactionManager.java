package com.example.spring.entitymanager.em.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

public class CustomPlatformTransactionManager implements PlatformTransactionManager {


	@Setter private  JpaTransactionManager jpaTransactionManager2;
	@Setter private  JpaTransactionManager jpaTransactionManager3;

	@Override
	public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
		TransactionStatus transaction2 = jpaTransactionManager2.getTransaction(definition);
		TransactionStatus transaction3 = jpaTransactionManager3.getTransaction(definition);
		return new CustomTransactionStatus(transaction2, transaction3);
	}

	@Override
	public void commit(TransactionStatus status) throws TransactionException {
		CustomTransactionStatus st = (CustomTransactionStatus) status;
		TransactionStatus transaction2 = st.getTransaction2();
		TransactionStatus transaction3 = st.getTransaction3();
		if (transaction2.isNewTransaction() && !transaction2.isCompleted()) {
			jpaTransactionManager2.commit(transaction2);
		}
		if (transaction3.isNewTransaction() && !transaction3.isCompleted()) {
			TransactionStatus transaction = jpaTransactionManager3.getTransaction(new DefaultTransactionDefinition(PROPAGATION_REQUIRES_NEW));
			jpaTransactionManager3.commit(transaction);
		}
	}

	@Override
	public void rollback(TransactionStatus status) throws TransactionException {
		CustomTransactionStatus st = (CustomTransactionStatus) status;
		jpaTransactionManager2.rollback(st.getTransaction2());
		jpaTransactionManager3.rollback(st.getTransaction3());
	}
}
