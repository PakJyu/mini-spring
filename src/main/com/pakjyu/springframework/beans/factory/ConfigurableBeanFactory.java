package com.pakjyu.springframework.beans.factory;

import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;

public interface ConfigurableBeanFactory extends BeanFactory {
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
