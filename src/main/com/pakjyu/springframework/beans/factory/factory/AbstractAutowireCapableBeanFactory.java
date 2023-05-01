package com.pakjyu.springframework.beans.factory.factory;

import cn.hutool.core.bean.BeanUtil;
import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.PropertyValue;
import com.pakjyu.springframework.beans.factory.PropertyValues;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;
import com.pakjyu.springframework.beans.factory.support.AbstractBeanFactory;
import com.pakjyu.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
import com.pakjyu.springframework.beans.factory.support.InstantiationStrategy;

import java.lang.reflect.Constructor;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    public Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) {
        Object bean = null;

        try {
            //实例化对象
            bean = instantiateBean(beanName, beanDefinition, bean, args);
            //注入属性
            applyPropertiesValue(bean, beanDefinition);
            //执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //注入容器
        addSingletonBean(beanName, bean);
        return bean;
    }

    private Object instantiateBean(String beanName, BeanDefinition beanDefinition, Object bean, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] constructors = beanClass.getDeclaredConstructors();
        for (Constructor ctor : constructors) {
            if (args != null && ctor.getParameterTypes().length == args.length) {
                bean = instantiationStrategy.instantiate(beanDefinition, beanName, ctor, args);
                break;
            }
        }
        return bean;
    }


    private void applyPropertiesValue(Object bean, BeanDefinition beanDefinition) throws BeansException {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        //如果为空则退出
        if (propertyValues == null) return;

        List<PropertyValue> propertyValuesList = propertyValues.getPropertiesValuesList();
        //迭代赋值
        for (PropertyValue propertyValue : propertyValuesList) {
            //获取属性名
            String name = propertyValue.getName();
            //获取属性值
            Object value = propertyValue.getValue();

            //如果是BeanReference的实例
            if (value instanceof BeanReference) {
                //从容器实例化/获取属性值
                value = getBean(name);
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {

    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }
}
