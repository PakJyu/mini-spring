package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.factory.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonBeans = new HashMap<>();

    @Override
    public Object getSingletonBean(String beanName) {
        return this.singletonBeans.get(beanName);
    }

    protected void addSingletonBean(String name, Object object) {
        singletonBeans.put(name,object);
    }

}
