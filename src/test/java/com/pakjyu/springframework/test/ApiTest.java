package com.pakjyu.springframework.test;


import com.pakjyu.springframework.test.bean.UserService;
import com.pakjyu.springframwork.beans.factory.BeansException;
import com.pakjyu.springframwork.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframwork.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

public class ApiTest {

    /**
     * 测试单例
     *
     * @throws BeansException
     */
    @Test
    public void test_BeanFactory() throws BeansException {
        //1、初始化工厂类
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //2、注册Bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registryBeanDefinition("userService", beanDefinition);

        //3、第一次获取Bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
        //3、第二次获取Bean
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        userService1.queryUserInfo();
    }

    /**
     * 测试同一个Bean，不同构造函数
     *
     * @throws BeansException
     */
    @Test
    public void test_BeanFactoryV2() throws BeansException {
        //1、初始化工厂类
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //2、注册Bean
        DefaultListableBeanFactory factory = beanFactory.registryBeanDefinition("userService", new BeanDefinition(UserService.class));

        //3、第一次获取Bean
        UserService userService = (UserService) factory.getBean("userService", "2134");
        userService.queryUserInfo();

        //3、第二次获取Bean
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        userService1.queryUserInfo();
    }

}
