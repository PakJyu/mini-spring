package com.pakjyu.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.PropertiesValue;
import com.pakjyu.springframework.beans.factory.PropertiesValues;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.beans.factory.factory.BeanReference;

import java.lang.reflect.Constructor;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    public Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) {
        Object bean = null;

        try {
            //实例化对象
            bean = instantiateBean(beanName, beanDefinition, bean, args);
            //注入属性
            applyPropertiesValue(bean, beanDefinition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (BeansException e) {
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
        PropertiesValues propertiesValues = beanDefinition.getPropertiesValues();
        //如果为空则退出
        if (propertiesValues == null) return;

        List<PropertiesValue> propertiesValuesList = propertiesValues.getPropertiesValuesList();
        //迭代赋值
        for (PropertiesValue propertiesValue : propertiesValuesList) {
            //获取属性名
            String name = propertiesValue.getName();
            //获取属性值
            Object value = propertiesValue.getValue();

            //如果是BeanReference的实例
            if (value instanceof BeanReference) {
                //从容器实例化/获取属性值
                value = getBean(name);
            }

            BeanUtil.setFieldValue(bean, name, value);
        }

    }
}
