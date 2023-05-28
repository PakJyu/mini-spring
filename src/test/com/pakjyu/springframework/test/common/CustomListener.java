package com.pakjyu.springframework.test.common;

import com.pakjyu.springframework.beans.factory.context.ApplicationListener;

import java.util.Date;

public class CustomListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }
}