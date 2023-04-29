package com.pakjyu.springframework.beans.factory.factory.dict;

public interface BeanXmlDict {
    static final String NODENAME = "bean";
    static final String ID = "id";
    static final String NAME = "name";
    static final String CLASSNAME = "class";

    interface BeanPropertyXmlDict {
        static final String NODENAME = "property";
        static final String NAME = "name";
        static final String VALUE = "value";
        static final String REF = "ref";
    }

}
