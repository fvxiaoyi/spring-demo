package com.example.demo.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerManager {

	public static final String TIGGER_KEY = "myTrigger";

	@Autowired
	public Scheduler scheduler;

	public void addTrigger(String cron) {
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(TIGGER_KEY).forJob("jobDetail")
				.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
		try {
			scheduler.scheduleJob(trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void removeTrigger() {
		try {
			scheduler.unscheduleJob(TriggerKey.triggerKey(TIGGER_KEY));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
