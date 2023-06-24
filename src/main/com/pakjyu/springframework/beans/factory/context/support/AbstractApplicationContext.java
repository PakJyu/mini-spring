package com.pakjyu.springframework.beans.factory.context.support;

import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.pakjyu.springframework.beans.factory.context.ApplicationEvent;
import com.pakjyu.springframework.beans.factory.context.ApplicationListener;
import com.pakjyu.springframework.beans.factory.context.ConfigurableApplicationContext;
import com.pakjyu.springframework.beans.factory.context.event.ApplicationEventMulticaster;
import com.pakjyu.springframework.beans.factory.context.event.ContextClosedEvent;
import com.pakjyu.springframework.beans.factory.context.event.ContextRefreshedEvent;
import com.pakjyu.springframework.beans.factory.context.event.SimpleApplicationEventMulticaster;
import com.pakjyu.springframework.beans.factory.factory.config.BeanFactoryPostProcessor;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;
import com.pakjyu.springframework.io.DefaultResourceLoader;
import com.pakjyu.springframework.io.Resource;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
    private ApplicationEventMulticaster applicationEventMulticaster;

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
        //3、添加ApplicationAwareProcessor，让继承自ApplicationContextAware的Bean对象都能感知所属的ApplicationContext
        factory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        //4、在Bean实例化之前，执行BeanFactoryProcessor(invoke factory registered as beans in the context)
        invokeBeanFactoryPostProcessor(factory);
        //5、Bean对象实例化之前注册操作BeanPostProcessor
        registerBeanPostProcessor(factory);
        //6、初始化事件发布者
        initApplicationEventMulticaster();
        //7、注册事件监听器
        registerListeners();
        //8、提前实例化单例Bean对象
        factory.preInstantiateSingletons();
        //9、发布容器刷新完成事件
        finishRefresh();
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    private void registerListeners() {
        Collection<ApplicationListener> listeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : listeners) {
            applicationEventMulticaster.addApplicationEventListener(listener);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME,applicationEventMulticaster);
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
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        publishEvent(new ContextClosedEvent(this));
        getBeanFactory().destroySingletons();
    }
}
