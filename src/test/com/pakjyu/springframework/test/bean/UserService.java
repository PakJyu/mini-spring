package com.pakjyu.springframework.test.bean;

public class UserService {
    private String uId;
    private UserDao userDao;

    public UserService() {
    }

    public void queryUserInfo() {
        if (userDao != null) {
            System.out.println("userName = " + userDao.queryUserName(uId));
        }

        //适配存量案例
        else {
            System.out.println("uId = " + uId);
        }

    }
}
