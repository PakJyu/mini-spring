package com.pakjyu.springframework.test.bean;

public class UserService {
    private String name;

    public UserService(String name) {
        this.name = name;
    }

    public UserService() {
    }

    public void queryUserInfo(){
        System.out.println("查询用户信息");
    }
}
