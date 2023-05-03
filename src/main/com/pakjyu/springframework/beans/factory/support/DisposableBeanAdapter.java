package com.pakjyu.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.pakjyu.springframework.beans.factory.DisposableBean;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class DisposableBeanAdapter implements DisposableBean {
    //核心领域的判断：是否销毁方法非空
    public static Predicate<String> isMethodNameNotEmpty = destroyMethodName -> StrUtil.isNotEmpty(destroyMethodName);
    //核心领域的判断：是否实现DisposableBean
    public static Predicate<Object> isInstanceofDisposableBean = (bean) -> bean instanceof DisposableBean;
    //核心领域的判断：方法名是否destroy
    public static Predicate<Object> isDestroyMethod = (destroyMethodName) -> "destroy".equals(destroyMethodName);
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;


    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {

        //执行实现DisposableBean接口的Bean的destroy方法
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        //执行配置文件定义销毁方法的Bean，同时避免二次销毁实现Disposable接口的Bean
        if (isMethodNameNotEmpty.test(destroyMethodName) && !(isInstanceofDisposableBean.test(bean) && isDestroyMethod.test(destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            Assert.notNull(destroyMethod, new IllegalArgumentException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'"));
            destroyMethod.invoke(bean);
        }

    }
}
