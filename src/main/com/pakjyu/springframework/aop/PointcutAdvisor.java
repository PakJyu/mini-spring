package com.pakjyu.springframework.aop;

public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}
