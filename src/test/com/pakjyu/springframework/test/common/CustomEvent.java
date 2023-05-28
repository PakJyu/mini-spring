package com.pakjyu.springframework.test.common;

import com.pakjyu.springframework.beans.factory.context.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {

    private String message;
    private int id;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public CustomEvent(Object source, String message, int id) {
        super(source);
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
    

