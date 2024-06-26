package com.example.spring.entitymanager.em.config;

import lombok.Setter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class CustomPlatformTransactionManager implements PlatformTransactionManager {


	@Setter private JpaTransactionManager jpaTransactionManager2;
	@Setter private JpaTransactionManager jpaTransactionManager3;

	@Override
	public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
		CustomTransactionDefinition transactionDef = (CustomTransactionDefinition) definition;
		TransactionStatus transaction2 = jpaTransactionManager2.getTransaction(transactionDef);
		TransactionStatus transaction3 = jpaTransactionManager3.getTransaction(transactionDef.getNestedTransactionDefinition());
		return new CustomTransactionStatus(transaction2, transaction3);
	}

	@Override
	public void commit(TransactionStatus status) throws TransactionException {
		CustomTransactionStatus st = (CustomTransactionStatus) status;
		TransactionStatus transaction2 = st.getTransaction2();
		DefaultTransactionStatus transaction3 = (DefaultTransactionStatus) st.getTransaction3();
		if (transaction2.isNewTransaction() && !transaction2.isCompleted()) {
			jpaTransactionManager2.commit(transaction2);
		}
		if (transaction3.isNewTransaction() && !transaction3.isCompleted()) {
			var a = new DefaultTransactionStatus(
				transaction3.getTransactionName(),
				transaction3.getTransaction(),
				true,
				false,
				false,
				false,
				true,
				transaction3.getSuspendedResources()
			);
			jpaTransactionManager3.commit(a);
		}
		TransactionSynchronizationManager.clear();
	}

	@Override
	public void rollback(TransactionStatus status) throws TransactionException {
		CustomTransactionStatus st = (CustomTransactionStatus) status;
		TransactionStatus transaction2 = st.getTransaction2();
		if (transaction2.isNewTransaction() && !transaction2.isCompleted()) {
			jpaTransactionManager2.rollback(transaction2);
		}
		DefaultTransactionStatus transaction3 = (DefaultTransactionStatus) st.getTransaction3();
		if (transaction3.isNewTransaction() && !transaction3.isCompleted()) {
			var a = new DefaultTransactionStatus(
				transaction3.getTransactionName(),
				transaction3.getTransaction(),
				true,
				false,
				false,
				false,
				true,
				transaction3.getSuspendedResources()
			);
			jpaTransactionManager3.rollback(a);
		}
		TransactionSynchronizationManager.clear();

	}
}
