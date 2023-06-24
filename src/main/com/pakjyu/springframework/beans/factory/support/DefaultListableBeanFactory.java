package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.pakjyu.springframework.beans.factory.factory.AbstractAutowireCapableBeanFactory;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;
import com.pakjyu.springframework.util.Apply;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

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
    public void preInstantiateSingletons() throws BeansException {}

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> result = new HashMap<>();

        Set<Map.Entry<String, BeanDefinition>> entries = beanDefinitionMap.entrySet();
        for (Map.Entry<String, BeanDefinition> entry : entries) {
            Class beanClass = entry.getValue().getBeanClass();
            String beanName = entry.getKey();
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        }

        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return null;
    }

    @Override
    public void destroySingletons() {
        super.destroySingletons();
    }
}
