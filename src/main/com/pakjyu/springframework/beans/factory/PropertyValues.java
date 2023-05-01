package com.pakjyu.springframework.beans.factory;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
    private List<PropertyValue> propertyValuesList = new ArrayList<>();

    public PropertyValues addPropertyValue(PropertyValue propertyValue) {
        propertyValuesList.add(propertyValue);
        return this;
    }

    public PropertyValues addPropertyValue(String name, Object value) {
        propertyValuesList.add(new PropertyValue(name, value));
        return this;
    }

    public List<PropertyValue> getPropertiesValuesList() {
        return propertyValuesList;
    }
}
