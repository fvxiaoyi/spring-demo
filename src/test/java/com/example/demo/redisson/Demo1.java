package com.example.demo.redisson;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class Demo1 {

	public static void main(String[] args) throws InterruptedException {
		Config config = new Config();
		// @formatter:off
		config.useClusterServers()
			  .addNodeAddress("redis://192.168.247.236:6379")
			  .addNodeAddress("redis://192.168.247.236:6380")
			  .addNodeAddress("redis://192.168.247.234:6379")
			  .addNodeAddress("redis://192.168.247.234:6380")
			  .addNodeAddress("redis://192.168.247.235:6379")
			  .addNodeAddress("redis://192.168.247.235:6380")
			  .setPassword("redis");
		// @formatter:on
		RedissonClient redisson = Redisson.create(config);
		int num = 10;
		CountDownLatch latch = new CountDownLatch(num);
		ExecutorService executorService = Executors.newFixedThreadPool(num);
		for (int i = 0; i < 10; i++) {
			executorService.execute(() -> {
				RLock lock = redisson.getLock("lock:order:1");
				try {
					lock.lock();
					System.out.println(Thread.currentThread().getName() + " do st before ..");
					Thread.sleep(10000l);
					System.out.println(Thread.currentThread().getName() + " do st end ..");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
					latch.countDown();
				}
			});
		}

		latch.await();
	}
}
