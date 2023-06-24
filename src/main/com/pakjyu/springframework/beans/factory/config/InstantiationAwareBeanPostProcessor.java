package com.pakjyu.springframework.beans.factory.config;

import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}