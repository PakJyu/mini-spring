package com.pakjyu.springframwork.beans.factory.support;

import com.pakjyu.springframwork.beans.factory.BeanFactory;
import com.pakjyu.springframwork.beans.factory.BeansException;
import com.pakjyu.springframwork.beans.factory.factory.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        Object singletonBean = getSingletonBean(name);

        if (singletonBean != null) {
            System.out.println("AbstractBeanFactory.getBean:获取单例");
            return singletonBean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition, args);
    }

    public abstract Object createBean(String name, BeanDefinition beanDefinition, Object... args);

    public abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
