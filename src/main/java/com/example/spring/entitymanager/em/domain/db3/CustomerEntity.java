package com.example.spring.entitymanager.em.domain.db3;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class CustomerEntity {

	@Id
	private UUID id;

}
