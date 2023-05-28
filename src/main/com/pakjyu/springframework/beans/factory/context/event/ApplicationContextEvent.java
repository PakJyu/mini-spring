package com.pakjyu.springframework.beans.factory.context.event;

import com.pakjyu.springframework.beans.factory.context.ApplicationContext;
import com.pakjyu.springframework.beans.factory.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}
