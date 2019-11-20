package com.example.demo.redisson;

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class Demo3 {
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
		RMap<Object, Object> map = redisson.getMap("map");
		System.out.println(map.isExists());
	}
}
