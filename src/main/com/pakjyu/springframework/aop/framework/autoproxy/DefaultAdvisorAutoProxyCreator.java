package com.pakjyu.springframework.aop.framework.autoproxy;

import com.pakjyu.springframework.AdvisedSupport;
import com.pakjyu.springframework.aop.Advisor;
import com.pakjyu.springframework.aop.ClassFilter;
import com.pakjyu.springframework.aop.Pointcut;
import com.pakjyu.springframework.aop.TargetSource;
import com.pakjyu.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.pakjyu.springframework.aop.framework.ProxyFactory;
import com.pakjyu.springframework.beans.factory.BeanFactory;
import com.pakjyu.springframework.beans.factory.BeanFactoryAware;
import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.pakjyu.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

        if (isInfrastructureClass(beanClass)) return null;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(beanClass)) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = null;

            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }

            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }

        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    private boolean isInfrastructureClass(Class<?> beanClass) {
        boolean isAssignableFromAdvice = Advice.class.isAssignableFrom(beanClass);
        boolean isAssignableFromPointcut = Pointcut.class.isAssignableFrom(beanClass);
        boolean isAssignableFromAdvisor = Advisor.class.isAssignableFrom(beanClass);
        return isAssignableFromAdvice || isAssignableFromPointcut || isAssignableFromAdvisor;
    }
}
