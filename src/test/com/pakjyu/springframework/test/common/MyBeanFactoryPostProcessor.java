package com.pakjyu.springframework.test.common;

import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.pakjyu.springframework.beans.factory.PropertyValue;
import com.pakjyu.springframework.beans.factory.PropertyValues;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.beans.factory.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制。
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }
}