package com.pakjyu.springframwork.beans.factory.support;

import com.pakjyu.springframwork.beans.factory.factory.BeanDefinition;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    public Object createBean(String name, BeanDefinition beanDefinition, Object... args) {
        Object bean = null;

        try {
            Class beanClass = beanDefinition.getBeanClass();
            Constructor[] constructors = beanClass.getDeclaredConstructors();

            for (Constructor ctor : constructors) {
                if (args != null && ctor.getParameterTypes().length == args.length) {
                    bean = instantiationStrategy.instantiate(beanDefinition, name, ctor, args);
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        addSingletonBean(name, bean);
        return bean;
    }
}
