package com.example.demo.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class ScoreService {

	@PersistenceContext
	EntityManager em;

	@Autowired
	PlatformTransactionManager tx;

	public synchronized void addScore(String id) {
		DefaultTransactionDefinition txDef = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = tx.getTransaction(txDef);
		try {
			Score s = em.find(Score.class, id);
			s.add();
			tx.commit(status);
		} catch (Exception e) {
			tx.rollback(status);
			throw e;
		}
	}
}
