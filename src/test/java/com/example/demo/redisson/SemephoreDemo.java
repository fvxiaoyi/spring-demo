package com.example.demo.redisson;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemephoreDemo {
	public static void main(String[] args) throws InterruptedException {
		Semaphore semaphore = new Semaphore(3);
		CountDownLatch latch = new CountDownLatch(12);
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 12; i++) {
			threadPool.execute(() -> {
				while (true) {
					try {
						boolean tryAcquire = semaphore.tryAcquire(2, TimeUnit.SECONDS);
						if (tryAcquire) {
							System.out.println(Thread.currentThread().getName() + " begin");
							Thread.sleep(4000l);
							semaphore.release();
							latch.countDown();
							break;
						}

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
					}
				}

			});
		}
		threadPool.shutdown();
		latch.await();
		System.out.println("finish");
	}
}
