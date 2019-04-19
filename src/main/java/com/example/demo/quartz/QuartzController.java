package com.example.demo.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuartzController {

	@Autowired
	private SchedulerManager schedulerManager;

	@GetMapping("/add")
	public void addTrigger() {
		schedulerManager.addTrigger("*/2 * * * * ?");
	}

	@GetMapping("/remove")
	public void remove() {
		schedulerManager.removeTrigger();
	}
}
