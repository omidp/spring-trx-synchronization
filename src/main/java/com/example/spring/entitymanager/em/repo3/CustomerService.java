package com.example.spring.entitymanager.em.repo3;

import com.example.spring.entitymanager.em.domain.db3.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {


	private final CustomerDao customerDao;

	public void insert(){
		throw new RuntimeException("errr");
//		CustomerEntity customerEntity = new CustomerEntity();
//		customerEntity.setId(UUID.randomUUID());
//		customerDao.save(customerEntity);
	}

	public void delete() {

		customerDao.deleteAll();
	}
}
