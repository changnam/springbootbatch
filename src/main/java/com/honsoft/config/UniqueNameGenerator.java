package com.honsoft.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class UniqueNameGenerator extends AnnotationBeanNameGenerator {
	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		// use fully-qualified name as beanName
		String beanName = definition.getBeanClassName();
		return beanName;
	}
}