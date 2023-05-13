package com.pakjyu.springframework.beans.factory;

import com.pakjyu.springframework.beans.factory.factory.SingletonBeanRegistry;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;
import com.pakjyu.springframework.beans.factory.factory.dict.BeanDict;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry, BeanDict {

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    void destroySingletons();
    void setBeanClassLoader(ClassLoader classLoader);
    ClassLoader getBeanClassLoader();
}
