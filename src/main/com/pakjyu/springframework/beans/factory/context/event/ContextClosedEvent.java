package com.pakjyu.springframework.beans.factory.context.event;

import com.pakjyu.springframework.beans.factory.context.ApplicationEvent;

public class ContextClosedEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
