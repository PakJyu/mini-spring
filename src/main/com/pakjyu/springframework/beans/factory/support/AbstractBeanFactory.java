package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.BeanFactory;
import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;

import java.util.List;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        Object singletonBean = getSingletonBean(beanName);

        if (singletonBean != null) {
            System.out.println("AbstractBeanFactory.getBean:获取单例");
            return singletonBean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition, args);
    }

    public <T> T getBean(String beanName, Class<T> t) throws BeansException {
        return (T) getBean(beanName);
    }

    public abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args);

    public abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return null;
    }
}
