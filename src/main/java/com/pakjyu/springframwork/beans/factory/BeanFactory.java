package com.pakjyu.springframwork.beans.factory;

import com.pakjyu.springframwork.beans.factory.factory.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean工厂
 */
public interface BeanFactory {
    public abstract Object getBean (String name,Object... args) throws BeansException;
}
