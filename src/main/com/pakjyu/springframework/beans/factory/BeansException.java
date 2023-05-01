package com.pakjyu.springframework.beans.factory;

public class BeansException extends RuntimeException {
    public BeansException(String s) {
        System.out.println(s);
    }

    public BeansException(String s, Exception e) {
        System.out.println(s);
        System.out.println(e);
    }
}
