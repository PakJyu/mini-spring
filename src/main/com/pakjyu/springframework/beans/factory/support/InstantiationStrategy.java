package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {
    public abstract Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object... args);
}
