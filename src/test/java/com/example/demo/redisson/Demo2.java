package com.example.demo.redisson;

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class Demo2 {
	public static void main(String[] args) throws InterruptedException {
		Config config = new Config();
		//默认30
		config.setLockWatchdogTimeout(9000l);
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
		new Thread(() -> {
			RLock lock = redisson.getLock("lock:order:1");
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getId() + " do st before ..");
				Thread.sleep(15000l);
				System.out.println(Thread.currentThread().getId() + " do st end ..");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}).start();
		Thread.sleep(1000l);
		new Thread(() -> {
			RLock lock = redisson.getLock("lock:order:1");
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getId() + " do st before ..");
				Thread.sleep(10000l);
				System.out.println(Thread.currentThread().getId() + " do st end ..");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}).start();

		Thread.sleep(100000l);
	}
}
