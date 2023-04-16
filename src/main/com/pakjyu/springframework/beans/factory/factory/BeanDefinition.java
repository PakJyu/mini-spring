package com.pakjyu.springframework.beans.factory.factory;

import com.pakjyu.springframework.beans.factory.PropertiesValues;

/**
 * Bean定义类
 */
public class BeanDefinition {
    private Class beanClass;
    private PropertiesValues propertiesValues;

    public BeanDefinition(Class beanClass, PropertiesValues propertiesValues) {
        this.beanClass = beanClass;
        this.propertiesValues = propertiesValues;
    }

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public PropertiesValues getPropertiesValues() {
        return propertiesValues;
    }
}
