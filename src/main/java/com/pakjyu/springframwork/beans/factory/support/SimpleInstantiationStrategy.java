package com.pakjyu.springframwork.beans.factory.support;

import com.pakjyu.springframwork.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframwork.beans.factory.support.InstantiationStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object... args) {
        Class beanClass = beanDefinition.getBeanClass();
        Object bean;

        try {

            if (ctor != null) {
                bean = beanClass.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                bean = beanClass.getDeclaredConstructor().newInstance();
            }

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return bean;
    }
}
