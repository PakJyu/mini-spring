package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.ConfigurableBeanFactory;
import com.pakjyu.springframework.beans.factory.factory.AbstractAutowireCapableBeanFactory;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public DefaultListableBeanFactory registryBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
        return this;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }
}
