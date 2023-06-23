package com.pakjyu.springframework.test;

import com.pakjyu.springframework.AdvisedSupport;
import com.pakjyu.springframework.aop.TargetSource;
import com.pakjyu.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.pakjyu.springframework.aop.framework.Cglib2AopProxy;
import com.pakjyu.springframework.aop.framework.JdkDynamicAopProxy;
import com.pakjyu.springframework.test.bean.IUserService;
import com.pakjyu.springframework.test.bean.UserService;
import com.pakjyu.springframework.test.bean.UserServiceAop;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.Proxy;
import org.junit.Test;

import java.lang.reflect.Method;

public class AopProxyTest {
    private IUserService proxy;

    @Test
    public void test_dynamic() {
        // 目标对象
        IUserService userService = new UserServiceAop();

        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.pakjyu.springframework.test.bean.IUserService.*(..))"));

        // 代理对象(JdkDynamicAopProxy)
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

        // 代理对象(Cglib2AopProxy)
        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_cglib.register("花花"));
    }


    @Test
    public void testAspectJExpressionPointcut() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.pakjyu.springframework.test.bean.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));

        //true,true
    }

}
