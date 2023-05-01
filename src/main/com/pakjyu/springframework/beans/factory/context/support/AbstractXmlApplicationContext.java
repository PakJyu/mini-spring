package com.pakjyu.springframework.beans.factory.context.support;

import com.pakjyu.springframework.beans.factory.factory.XmlBeanDefinitionReader;
import com.pakjyu.springframework.beans.factory.support.DefaultListableBeanFactory;


public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    /**
     * @param defaultListableBeanFactory
     */
    protected void loadBeanDefinitions(DefaultListableBeanFactory defaultListableBeanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}

