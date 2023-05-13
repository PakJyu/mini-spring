package com.pakjyu.springframework.beans.factory.factory.dict;

public interface BeanDict {
    String NODENAME = "bean";
    String ID = "id";
    String NAME = "name";
    String CLASSNAME = "class";
    String INIT_METHOD = "init-method";
    String DESTROY_METHOD = "destroy-method";
    String SCOPE = "scope";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_SINGLETON = "singleton";

    interface BeanPropertyDict {
        String NODENAME = "property";
        String NAME = "name";
        String VALUE = "value";
        String REF = "ref";
    }

}
