package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.ConfigurableBeanFactory;
import com.pakjyu.springframework.beans.factory.FactoryBean;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;
import com.pakjyu.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    protected <T> T doGetBean(final String beanName, final Object[] args) {
        Object sharedInstance = getSingletonBean(beanName);
        if (sharedInstance != null) {
            return (T) getObjectFromBeanInstance(sharedInstance, beanName);
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition);

        return (T) getObjectFromBeanInstance(bean, beanName);
    }

    private Object getObjectFromBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        Object object = getCacheObjectForFactoryBean(beanName);
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectForFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName,args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> t) throws BeansException {
        return (T) getBean(beanName);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = ClassUtils.getDefaultClassLoader(classLoader);
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    public abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args);

    public abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }
}
