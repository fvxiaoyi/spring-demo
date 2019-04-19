package com.example.demo.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MyQuartzJob extends QuartzJobBean {

	@Autowired
	private MyService myService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		myService.exec();
		System.out.println(Thread.currentThread().getName() + " : abc");
	}

}
