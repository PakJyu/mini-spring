package com.pakjyu.springframework.beans.factory.factory;

import com.pakjyu.springframework.beans.factory.BeanFactory;
import com.pakjyu.springframework.beans.factory.BeansException;

public interface AutowireCapableBeanFactory extends BeanFactory {
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
