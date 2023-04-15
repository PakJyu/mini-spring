package com.pakjyu.springframwork.beans.factory.support;

import com.pakjyu.springframwork.beans.factory.BeanFactory;
import com.pakjyu.springframwork.beans.factory.factory.BeanDefinition;

public interface BeanDefinitionRegistry {
    public abstract BeanFactory registryBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
