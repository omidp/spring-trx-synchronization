package com.example.spring.entitymanager.em.repo3;

import com.example.spring.entitymanager.em.domain.db3.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerDao extends CrudRepository<CustomerEntity, UUID> {
}
