package com.pakjyu.springframework.beans.factory.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.pakjyu.springframework.beans.factory.*;
import com.pakjyu.springframework.beans.factory.context.ApplicationContextAware;
import com.pakjyu.springframework.beans.factory.factory.config.BeanPostProcessor;
import com.pakjyu.springframework.beans.factory.support.AbstractBeanFactory;
import com.pakjyu.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
import com.pakjyu.springframework.beans.factory.support.DisposableBeanAdapter;
import com.pakjyu.springframework.beans.factory.support.InstantiationStrategy;
import com.pakjyu.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

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

        // 注册实现了 DisposableBean 接口的 Bean 对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 如果单例
        if (beanDefinition.isSingleton()) {
            //注入容器
            addSingletonBean(beanName, bean);
        }

        return bean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 不是单例的Bean不执行销毁方法
        if (!beanDefinition.isSingleton()) return;

        boolean isInstanceofDisposableBean = DisposableBeanAdapter.isInstanceofDisposableBean.test(bean);
        boolean isMethodNameNotEmpty = DisposableBeanAdapter.isMethodNameNotEmpty.test(beanDefinition.getDestroyMethodName());

        if (isInstanceofDisposableBean || isMethodNameNotEmpty) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
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
                value = getBean(((BeanReference) value).getBeanName());
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) ((BeanNameAware) bean).setBeanName(beanName);
            if (bean instanceof BeanFactoryAware) ((BeanFactoryAware) bean).setBeanFactory(this);
            if (bean instanceof BeanClassLoaderAware) ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
        }

        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
        // 1. 实现接口 InitializingBean
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }

        // 2. 配置信息 init-method {判断是为了避免二次执行销毁}
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {

            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            Assert.notNull(initMethod, new IllegalArgumentException("Could not find an init method named" + initMethodName));

            initMethod.invoke(wrappedBean);
        }

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
