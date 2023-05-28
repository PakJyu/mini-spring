package com.pakjyu.springframework.beans.factory.context.event;

import com.pakjyu.springframework.beans.factory.context.ApplicationEvent;
import com.pakjyu.springframework.beans.factory.context.ApplicationListener;

public interface ApplicationEventMulticaster {

    void addApplicationEventListener(ApplicationListener<ApplicationEvent> listener);

    void removeApplicationEventListener(ApplicationListener<ApplicationEvent> listener);

    void multicastEvent(ApplicationEvent event);

}
