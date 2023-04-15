package com.pakjyu.springframwork.beans.factory.support;

import com.pakjyu.springframwork.beans.factory.factory.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonBeans = new HashMap<>();

    @Override
    public Object getSingletonBean(String name) {
        return this.singletonBeans.get(name);
    }

    protected void addSingletonBean(String name, Object object) {
        singletonBeans.put(name,object);
    }

}
