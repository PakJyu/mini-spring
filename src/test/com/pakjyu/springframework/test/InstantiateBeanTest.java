package com.pakjyu.springframework.test;

import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.PropertyValues;
import com.pakjyu.springframework.beans.factory.factory.BeanDefinition;
import com.pakjyu.springframework.beans.factory.factory.BeanReference;
import com.pakjyu.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.pakjyu.springframework.test.bean.UserDao;
import com.pakjyu.springframework.test.bean.UserService;
import org.junit.Test;

public class InstantiateBeanTest {

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
        beanFactory.registryBeanDefinition("userService", new BeanDefinition(UserService.class));

        //3、第一次获取Bean
        UserService userService = (UserService) beanFactory.getBean("userService", "2134");
        userService.queryUserInfo();

        //3、第二次获取Bean
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        userService1.queryUserInfo();
    }


    /**
     * 测试属性注入
     *
     * @throws BeansException
     */
    @Test
    public void test_BeanFactoryV3() throws BeansException {
        //1、初始化工厂类
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //2、注册Bean
        beanFactory.registryBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        PropertyValues propertyValues = new PropertyValues()
                .addPropertyValue("uId", "10001")
                .addPropertyValue("userDao", new BeanReference("userDao"));

        //2、注册Bean
        beanFactory.registryBeanDefinition("userService", new BeanDefinition(UserService.class, propertyValues));

        //3、第一次获取Bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

        //3、第二次获取Bean
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        userService1.queryUserInfo();
    }



}
