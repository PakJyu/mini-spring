package com.pakjyu.springframework.test;

import com.pakjyu.springframework.AdvisedSupport;
import com.pakjyu.springframework.aop.TargetSource;
import com.pakjyu.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.pakjyu.springframework.aop.framework.Cglib2AopProxy;
import com.pakjyu.springframework.aop.framework.JdkDynamicAopProxy;
import com.pakjyu.springframework.beans.factory.context.support.ClassPathXmlApplicationContext;
import com.pakjyu.springframework.test.bean.IUserService;
import com.pakjyu.springframework.test.bean.UserService;
import com.pakjyu.springframework.test.bean.UserServiceAop;
import org.junit.Test;

import java.lang.reflect.Method;

public class AopProxyTest2 {
    @Test
    public void test_aop() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userServiceAop", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }


}
