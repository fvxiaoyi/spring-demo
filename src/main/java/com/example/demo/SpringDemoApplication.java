package com.example.demo;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.auth.AuthIntercepter;
import com.example.demo.quartz.MyQuartzJob;
import com.example.demo.query.MyBatisQueryParser;

@SpringBootApplication
public class SpringDemoApplication {

	@Configuration
	static class WebMvcConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods(CorsConfiguration.ALL).maxAge(3600);
		}

		@Bean
		public AuthIntercepter authIntercepter() {
			return new AuthIntercepter();
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(authIntercepter()).addPathPatterns("/report/**");
		}

		@Override
		public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
			resolvers.add(new HandlerExceptionResolver() {

				@Override
				public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
						Exception ex) {
					ModelAndView mv = new ModelAndView();
					//  ConstraintViolationException
					//	SQLIntegrityConstraintViolationException
					//	DataIntegrityViolationException:
					if (ex instanceof DataIntegrityViolationException) {
						mv.addObject("exception", ex.getMessage());
					}
					return mv;
				}

			});
		}

	}

	@Configuration
	class Config {

		// @Bean
		public TopicExchange topicExchange() {
			return new TopicExchange("test-topic-exchange");
		}

		// @Bean
		public Queue testEvent() {
			return new Queue("test-event-customA", true); // 队列持久
		}

		@Bean
		public Binding bindingTestEvent() {
			// return
			// BindingBuilder.bind(testEvent()).to(topicExchange()).with("test-event-*");
			return BindingBuilder.bind(new Queue("test-event-customA", true)).to(new TopicExchange("test-topic-exchange"))
					.with("test-event-*");
		}

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
