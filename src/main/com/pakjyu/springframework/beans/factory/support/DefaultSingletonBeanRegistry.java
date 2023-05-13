package com.pakjyu.springframework.beans.factory.support;

import com.pakjyu.springframework.beans.factory.DisposableBean;
import com.pakjyu.springframework.beans.factory.factory.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonBeans = new HashMap<>();
    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    protected static final Object NULL_OBJECT = new Object();

    @Override
    public Object getSingletonBean(String beanName) {
        return this.singletonBeans.get(beanName);
    }

    protected void addSingletonBean(String name, Object object) {
        singletonBeans.put(name, object);
    }

    //todo
    public void destroySingletons() {
        disposableBeans.forEach((beanName, disposableBean) -> {
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                System.out.println("Destruction of bean with name '" + beanName + "' threw an exception" + e);
            }
        });
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        synchronized (this.disposableBeans) {
            this.disposableBeans.put(beanName, bean);
        }
    }

}
