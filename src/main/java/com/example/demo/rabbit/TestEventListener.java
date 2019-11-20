package com.example.demo.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
@RabbitListener(queues = "test-event-customA")
public class TestEventListener {

	@RabbitHandler
	public void process(String content) {
		System.out.println(Thread.currentThread().getName() + " 接收处理队列 的消息： " + content);
	}

}
