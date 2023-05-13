package com.pakjyu.springframework.test;

import com.pakjyu.springframework.beans.factory.context.support.ClassPathXmlApplicationContext;
import com.pakjyu.springframework.test.bean.UserService;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class PrototypeTest {
    @Test
    public void test_prototype() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2. 获取Bean对象调用方法
        UserService userService01 = (UserService) applicationContext.getBean("userService");
        UserService userService02 = (UserService) applicationContext.getBean("userService");

        // 3. 配置 scope="prototype/singleton"
        System.out.println("userService01 = " + userService01);
        System.out.println("userService02 = " + userService02);

        // 4. 打印十六进制哈希
        System.out.println(userService01 + " 十六进制哈希：" + Integer.toHexString(userService01.hashCode()));
        System.out.println(userService02 + " 十六进制哈希：" + Integer.toHexString(userService02.hashCode()));
        //System.out.println(ClassLayout.parseInstance(userService01).toPrintable());

        System.out.println("userService01.queryUserInfo() = " + userService01.queryUserInfo());
    }

}
