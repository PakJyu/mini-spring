package com.pakjyu.springframework.beans.factory;

/**
 * Bean工厂
 */
public interface BeanFactory {
    public abstract Object getBean (String beanName, Object... args) throws BeansException;
}
