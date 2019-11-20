package com.example.demo.redis;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

	@Autowired
	public StringRedisTemplate tpl;

	@Test
	public void test() throws InterruptedException {
		Long add = tpl.opsForSet().add("abc", String.valueOf(1));
		Long add1 = tpl.opsForSet().add("abc", String.valueOf(1));
		Long add2 = tpl.opsForSet().add("abc", String.valueOf(2));
		System.out.println(add);
		System.out.println(add1);
		System.out.println(add2);
		/*tpl.opsForValue().set("abc", String.valueOf(1));
		int i = 0;
		int j = 0;
		do {
			j++;
			i = Integer.valueOf(tpl.opsForValue().get("abc"));
			i++;
			new Thread(new R(i)).start();
		} while (j < 10);
		Thread.sleep(3000);
		String string = tpl.opsForValue().get("abc");
		System.out.println(string);*/
	}

	class R implements Runnable {
		public int i;

		public R(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
//			tpl.opsForValue().set("abc", String.valueOf(i));

			try {
				merge(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	public void merge(int i) throws InterruptedException {
		tpl.setEnableTransactionSupport(true);
		tpl.watch("abc");
		tpl.multi();
		tpl.opsForValue().set("abc", String.valueOf(i));
		List<Object> exec = tpl.exec();
		if (exec.isEmpty() || !exec.stream().allMatch(p -> p.equals(true))) {
			System.out.println(Thread.currentThread().getName() + "并发失败" + i);
		} else {
			System.out.println(Thread.currentThread().getName() + "写成功" + i);
		}

	}
}
