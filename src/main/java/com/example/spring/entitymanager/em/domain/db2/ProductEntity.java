package com.example.spring.entitymanager.em.domain.db2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class ProductEntity {

	@Id
	private UUID id;

}
