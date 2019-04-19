package com.example.demo;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.support.TaskUtils;

import com.example.demo.quartz.MyQuartzJob;
import com.example.demo.query.MyBatisQueryParser;

@SpringBootApplication
public class SpringDemoApplication {

	@Configuration
	class Config {

		@Bean
		public MyBatisQueryParser myBatisQueryParser() {
			return new MyBatisQueryParser();
		}

		@Bean
		public JobDetailFactoryBean jobDetail() {
			JobDetailFactoryBean bean = new JobDetailFactoryBean();
			bean.setJobClass(MyQuartzJob.class);
			bean.setDurability(true);
			return bean;
		}

		@Bean
		public ThreadPoolTaskScheduler taskScheduler() {
			ThreadPoolTaskScheduler taskExecutor = new ThreadPoolTaskScheduler();
			taskExecutor.setThreadPriority(Thread.MAX_PRIORITY);
			taskExecutor.setThreadGroupName("DefaultCoreThreadGroup");
			taskExecutor.setThreadNamePrefix("DefaultCore");
			taskExecutor.setPoolSize(Runtime.getRuntime().availableProcessors() + 1);
			taskExecutor.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
			taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
			taskExecutor.setAwaitTerminationSeconds(60 * 2);
			taskExecutor.initialize();
			return taskExecutor;
		}

		@Bean
		public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer(ThreadPoolTaskScheduler taskScheduler) {
			return schedulerFactoryBean -> {
				schedulerFactoryBean.setTaskExecutor(taskScheduler);
			};
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}

}
