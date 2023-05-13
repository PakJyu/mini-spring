package com.pakjyu.springframework.beans.factory;

public interface FactoryBean<T> {
    T getObject() throws RuntimeException;

    Class<?> getObjectType();

    boolean isSingleton();
}
