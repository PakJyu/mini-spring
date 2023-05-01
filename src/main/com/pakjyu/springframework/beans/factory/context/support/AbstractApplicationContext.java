package com.pakjyu.springframework.beans.factory.context.support;

import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.pakjyu.springframework.beans.factory.context.ConfigurableApplicationContext;
import com.pakjyu.springframework.beans.factory.factory.config.BeanFactoryPostProcessor;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;
import com.pakjyu.springframework.io.DefaultResourceLoader;
import com.pakjyu.springframework.io.Resource;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    /**
     * 刷新容器
     *
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException {
        //1、创建BeanFactory并加载BeanDefinition
        refreshBeanFactory();
        //2、获取BeanFactory
        ConfigurableListableBeanFactory factory = getBeanFactory();
        //3、在Bean实例化之前，执行BeanFactoryProcessor(invoke factory registered as beans in the context)
        invokeBeanFactoryPostProcessor(factory);
        //4、Bean对象实例化之前注册操作BeanPostProcessor
        registerBeanPostProcessor(factory);
        //5、提前实例化单例Bean对象
        factory.preInstantiateSingletons();

    }

    /**
     * 创建BeanFactory并加载BeanDefinition
     */
    protected abstract void refreshBeanFactory();

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    protected void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory factory) {
        Map<String, BeanFactoryPostProcessor> postProcessorMap = factory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor postProcessor : postProcessorMap.values()) {
            postProcessor.postProcessBeanFactory(factory);
        }
    }

    protected void registerBeanPostProcessor(ConfigurableListableBeanFactory factory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = factory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            factory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public Resource getResource(String location) {
        return super.getResource(location);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return null;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getBeanFactory().getBean(beanName, args);
    }
}
