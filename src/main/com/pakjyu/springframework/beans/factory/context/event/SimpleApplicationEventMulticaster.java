package com.pakjyu.springframework.beans.factory.context.event;

import com.pakjyu.springframework.beans.factory.context.ApplicationEvent;
import com.pakjyu.springframework.beans.factory.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
            if (supportEvent(applicationListener, event)) {
                applicationListener.onApplicationEvent(event);
            }
        }
    }
}
