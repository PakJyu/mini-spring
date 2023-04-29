package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.io.Resource;
import com.pakjyu.springframework.io.ResourceLoader;

/**
 * Bean定义读取接口
 */
public interface BeanDefinitionReader {

    /**
     * getRegistry()、getResourceLoader()。
     * 用于提供给后面三个方法的工具，加载和注册，这两个方法的实现会包装到抽象类中，以免污染具体的接口实现方法。
     */

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;
    void loadBeanDefinitions(Resource... resource) throws BeansException;
    void loadBeanDefinitions(String location) throws BeansException;

}
