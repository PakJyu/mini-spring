package com.pakjyu.springframework.beans.factory;

import com.pakjyu.springframework.beans.factory.factory.SingletonBeanRegistry;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void destroySingletons();
}
