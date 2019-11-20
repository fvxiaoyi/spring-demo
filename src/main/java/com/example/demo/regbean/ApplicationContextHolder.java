package com.example.demo.regbean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;

	public void registerBean(String beanName, Object singletonObject) {
		this.beanFactory.registerSingleton(beanName, singletonObject);
	}

	public <T> T getBean(String beanName, Class<? extends T> clazz) {
		return (T) this.beanFactory.getBean(beanName, clazz);
	}

	public <T> T getBean(String beanName) {
		return (T) this.beanFactory.getBean(beanName);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}

}
