package com.wingtip.sso;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

public class WingtipSSOApplicationContext implements ApplicationContextAware {
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = context;
	}
//	@Bean
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
}
