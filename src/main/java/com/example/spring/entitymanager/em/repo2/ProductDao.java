package com.example.spring.entitymanager.em.repo2;

import com.example.spring.entitymanager.em.domain.db2.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductDao extends CrudRepository<ProductEntity, UUID> {
}
