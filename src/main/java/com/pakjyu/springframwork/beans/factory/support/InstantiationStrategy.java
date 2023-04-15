package com.pakjyu.springframwork.beans.factory.support;

import com.pakjyu.springframwork.beans.factory.factory.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {
    public abstract Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object... args);
}
