package com.pakjyu.springframework.beans.factory.context;

import com.pakjyu.springframework.beans.factory.Aware;
import com.pakjyu.springframework.beans.factory.BeansException;

public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
