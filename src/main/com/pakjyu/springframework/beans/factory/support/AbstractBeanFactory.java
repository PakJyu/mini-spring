package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.BeanFactory;
import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        Object singletonBean = getSingletonBean(beanName);

        if (singletonBean != null) {
            System.out.println("AbstractBeanFactory.getBean:获取单例");
            return singletonBean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition, args);
    }

    public abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args);

    public abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
