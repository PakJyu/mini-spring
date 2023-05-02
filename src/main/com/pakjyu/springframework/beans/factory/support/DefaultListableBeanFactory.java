package com.pakjyu.springframework.beans.factory.support;

import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.StrUtil;
import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.ConfigurableBeanFactory;
import com.pakjyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.pakjyu.springframework.beans.factory.PropertyValues;
import com.pakjyu.springframework.beans.factory.factory.AbstractAutowireCapableBeanFactory;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;
import com.pakjyu.springframework.util.Apply;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;
import java.util.stream.Stream;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public DefaultListableBeanFactory registryBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
        return this;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {

    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        getBeanPostProcessors().add(beanPostProcessor);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {

        HashMap<String, T> result = new HashMap<>();
        String beanNameOfType = Apply.beanNameOfClass(type);

        for (BeanDefinition beanDefinition : beanDefinitionMap.values()) {
            Arrays.stream(beanDefinition.getBeanClass().getInterfaces())
                    .filter(aClass -> beanNameOfType.equals(Apply.beanNameOfClass(aClass)))
                    .forEach(aClass -> {
                        String beanNameOfaClass = Apply.beanNameOfClass(beanDefinition.getBeanClass());
                        T bean = (T) getBean(beanNameOfaClass, type.getClass());
                        result.put(beanNameOfaClass, bean);
                    });
        }

        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return null;
    }
}
