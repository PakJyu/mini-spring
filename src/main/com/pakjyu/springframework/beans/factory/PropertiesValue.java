package com.pakjyu.springframework.beans.factory;

public class PropertiesValue {
    private String name;
    private Object value;

    public PropertiesValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
