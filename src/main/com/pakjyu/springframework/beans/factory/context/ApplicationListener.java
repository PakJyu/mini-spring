package com.pakjyu.springframework.beans.factory.context;

public interface ApplicationListener<T extends ApplicationEvent> {
    public void onApplicationEvent(T event);
}
