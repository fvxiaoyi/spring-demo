package com.example.demo;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.example.demo.jpa.Book;
import com.example.demo.jpa.Catalog;
import com.example.demo.jpa.CatalogService;
import com.example.demo.jpa.School;
import com.example.demo.jpa.Score;
import com.example.demo.jpa.ScoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {

	@PersistenceContext
	EntityManager em;

	@Autowired
	PlatformTransactionManager tx;

	@Autowired
	private ScoreService service;

	@Autowired
	private CatalogService catalogService;

	@Test
	@Transactional
	@Rollback(false)
	public void testVO() {
		Book find = em.find(Book.class, "e80bc61c-7b35-4bd4-9f2e-da40c6291b6c");
		School s = new School();
		s.setAuthor(Arrays.asList(find.getAuthor()));
		em.persist(s);
	}

	@Test
	public void test() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				DefaultTransactionDefinition txDef = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
				TransactionStatus status = tx.getTransaction(txDef);
				try {
					Catalog c = new Catalog();
					c.setName("abc");
					em.persist(c);
					tx.commit(status);
				} catch (Exception e) {
					throw e;
				}
			}).start();
		}
		Thread.sleep(100000l);
	}

	@Test
	public void test1() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					catalogService.persist();
				} catch (Exception e) {
					throw e;
				}
			}).start();
		}
		Thread.sleep(100000l);
	}

	@Test
	public void get() {
		Catalog find = em.find(Catalog.class, "e86c1844-7453-4535-9563-9d3a5053067e");
		System.out.println(find);
	}

	@Test
	public void testScore() throws InterruptedException {

		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				DefaultTransactionDefinition txDef = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
				TransactionStatus status = tx.getTransaction(txDef);
				try {
					Score s = em.find(Score.class, "911ff7c9-5310-4db2-af90-ed4f5a259fed", LockModeType.PESSIMISTIC_WRITE);
					s.add();
					Catalog log = new Catalog();
					em.persist(log);
					tx.commit(status);
				} catch (Exception e) {
					tx.rollback(status);
					throw e;
				}
			}).start();
		}
		Thread.sleep(10000l);
	}

	@Test
	public void testLock1() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				service.addScore("911ff7c9-5310-4db2-af90-ed4f5a259fed");
			}).start();
		}
		Thread.sleep(10000l);
	}
}
