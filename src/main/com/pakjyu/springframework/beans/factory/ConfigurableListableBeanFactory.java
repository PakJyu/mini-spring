package com.pakjyu.springframework.beans.factory;

import com.pakjyu.springframework.beans.factory.factory.AutowireCapableBeanFactory;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {
    BeanDefinition getBeanDefinition(String definitionName);

    void preInstantiateSingletons() throws BeansException;
}
