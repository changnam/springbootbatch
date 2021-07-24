package com.honsoft.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextAwareBean implements ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(ApplicationContextAwareBean.class);

	private ApplicationContext context;
	private HashSet<String> beansSet = new HashSet<>();

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ApplicationContextAwareBean(DataSource dataSource) {
		logger.debug("********************* instanciating ApplicationContextAwareBean ***********************");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;

		int cnt = 1;
		String[] beanNames = context.getBeanDefinitionNames();
		Object beanObj = null;
		beansSet.addAll(Arrays.asList(beanNames));

		logger.debug("== list of beans (" + beanNames.length + ")==");
		for (String beanName : beanNames) {
			beanObj = context.getBean(beanName);
			if (beanObj instanceof Ordered) {
				logger.debug(cnt++ + " , " + beanName + " , " + beanObj.getClass().toString() + " , "
						+ ((Ordered) beanObj).getOrder());
			} else {
				logger.debug(cnt++ + " , " + beanName + " , " + beanObj.getClass().toString());
			}
			// jdbcTemplate.update("insert into beans (beanname,description) values (?,?)",
			// beanName, "beandef");
		}
		logger.debug("====================");

		cnt = 1;
		String[] allBeans = printBeans();
		logger.debug("=== all beans including beans registered by spring (" + allBeans.length + ")====");

		// List<String> singletonArrays = Arrays.asList(allBeans);

		for (String bean : allBeans) {
			beanObj = context.getBean(bean);
			if (!beansSet.contains(bean)) {
				if (beanObj instanceof Ordered) {
					logger.debug(cnt++ + " , <== manual ==> " + bean + " , " + beanObj.getClass().toString() + " , "
							+ ((Ordered) beanObj).getOrder());
				} else {
					logger.debug(cnt++ + " , <== manual ==> " + bean + " , " + beanObj.getClass().toString());
				}
				// jdbcTemplate.update("insert into beans (beanname,description) values (?,?)",
				// bean, "all");
			} else {
				if (beanObj instanceof Ordered) {
					logger.debug(cnt++ + " , " + bean + " , " + beanObj.getClass().toString() + " , "
							+ ((Ordered) beanObj).getOrder());
				} else {
					logger.debug(cnt++ + " , " + bean + " , " + beanObj.getClass().toString());
				}
				// jdbcTemplate.update("insert into beans (beanname,description) values (?,?)",
				// bean, "all");

			}
		}
		logger.debug("====================");
	}

	private String[] printBeans() {
		AutowireCapableBeanFactory autowireCapableBeanFactory = context.getAutowireCapableBeanFactory();
		if (autowireCapableBeanFactory instanceof SingletonBeanRegistry) {
			String[] singletonNames = ((SingletonBeanRegistry) autowireCapableBeanFactory).getSingletonNames();

			for (String singleton : singletonNames) {
				// logger.debug(singleton);
			}
			return singletonNames;
		}
		return null;
	}

}
