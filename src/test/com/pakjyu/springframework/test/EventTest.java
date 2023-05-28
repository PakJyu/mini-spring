package com.pakjyu.springframework.test;

import com.pakjyu.springframework.beans.factory.context.support.ClassPathXmlApplicationContext;
import com.pakjyu.springframework.test.common.CustomEvent;
import org.junit.Test;

public class EventTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, "成功了", 123456));
        applicationContext.registerShutdownHook();
    }
}
