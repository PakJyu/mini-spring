package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.beans.factory.BeanFactory;

public interface BeanDefinitionRegistry {
    public abstract BeanFactory registryBeanDefinition(String beanName, BeanDefinition beanDefinition);
    public abstract boolean containsBeanDefinition(String beanName);
}
