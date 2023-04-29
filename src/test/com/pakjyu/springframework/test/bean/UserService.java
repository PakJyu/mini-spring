package com.pakjyu.springframework.test.bean;

public class UserService {
    private String uId;
    private UserDao userDao;

    public UserService() {
    }

    public UserService(String uId) {
        this.uId = uId;
    }

    public String queryUserInfo() {
        String userName = "";
        if (userDao != null) {
            userName = userDao.queryUserName(uId);
            System.out.println("userName = " + userName);
        }

        //适配存量案例
        else {
            System.out.println("uId = " + uId);
        }

        return userName;
    }
}
