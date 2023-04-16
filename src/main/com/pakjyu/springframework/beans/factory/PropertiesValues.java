package com.pakjyu.springframework.beans.factory;

import java.util.ArrayList;
import java.util.List;

public class PropertiesValues {
    private List<PropertiesValue> propertiesValuesList = new ArrayList<>();

    public PropertiesValues addPropertiesValue(PropertiesValue propertiesValue) {
        propertiesValuesList.add(propertiesValue);
        return this;
    }

    public PropertiesValues addPropertiesValue(String name, Object value) {
        propertiesValuesList.add(new PropertiesValue(name,value));
        return this;
    }

    public List<PropertiesValue> getPropertiesValuesList() {
        return propertiesValuesList;
    }
}
