package com.example.spring.entitymanager.em.repo2;

import com.example.spring.entitymanager.em.domain.db2.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductDao productDao;

	public void insert(){
		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(UUID.randomUUID());
		productDao.save(productEntity);
	}

	public void delete() {
		productDao.deleteAll();
	}
}
