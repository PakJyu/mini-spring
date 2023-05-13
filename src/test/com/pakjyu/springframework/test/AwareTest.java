package com.pakjyu.springframework.test;

import com.pakjyu.springframework.beans.factory.context.support.ClassPathXmlApplicationContext;
import com.pakjyu.springframework.test.bean.UserService;
import org.junit.Test;

public class AwareTest {
    @Test
    public void test_xml(){
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2. 获取Bean对象调用方法
        UserService userService = (UserService) applicationContext.getBean("userService");
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }
}
