package com.example.demo.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTest2 {

	@Autowired
	public StringRedisTemplate tpl;

	@SuppressWarnings("unchecked")
	@Test
	public void test1() throws InterruptedException {
		tpl.opsForValue().set("count", "4");
		for (int i = 0; i < 100; i++) {
			final int x = i;
			new Thread(() -> {
				Integer count = Integer.valueOf(tpl.opsForValue().get("count"));
				if (count > 0) {
					tpl.setEnableTransactionSupport(true);
					tpl.watch("count");
					tpl.multi();
					tpl.opsForValue().set("count", String.valueOf(count - 1));
					List<Object> exec = tpl.exec();
					if (exec != null && !exec.isEmpty() && exec.stream().allMatch(p -> p.equals(true))) {
						System.out.println("第" + x + "抢到！");
					}

				}
			}).start();
		}
		Thread.sleep(6000);
		System.out.println(tpl.opsForValue().get("count"));
	}

	@Test
	public void test() throws InterruptedException {
		tpl.opsForList().leftPushAll("goods", Arrays.asList("1", "1", "1", "1"));

		/*for (int i = 0; i < 3000; i++) {
			final int x = i;
			new Thread(() -> {
				String leftPop = tpl.opsForList().leftPop("goods");
				if (StringUtils.isEmpty(leftPop)) {
//					System.out.println("第" + x + "没抢到");
				} else {
					System.out.println("第" + x + "抢到！");
				}

			}).start();
		}
		Thread.sleep(10000);*/
	}
}
