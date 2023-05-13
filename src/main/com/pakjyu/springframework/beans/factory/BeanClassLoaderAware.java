package com.pakjyu.springframework.beans.factory;

public interface BeanClassLoaderAware extends Aware{
    void setBeanClassLoader(ClassLoader classLoader);
}
