package com.example.demo.jpa;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class CatalogService {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void persist() {

		Catalog c = new Catalog();
		c.setName("2");
		try {
			em.persist(c);
		} catch (EntityExistsException e) {
			throw e;
		}
	}

}
