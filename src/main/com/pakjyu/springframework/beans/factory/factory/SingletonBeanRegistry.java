package com.pakjyu.springframework.beans.factory.factory;

public interface SingletonBeanRegistry {
    public Object getSingletonBean(String beanName);

    public void registerSingleton(String name, Object object);
}
