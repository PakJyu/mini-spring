package com.pakjyu.springframework.test.bean;

import com.pakjyu.springframework.beans.factory.DisposableBean;
import com.pakjyu.springframework.beans.factory.InitializingBean;

public class UserService implements InitializingBean, DisposableBean {
    private String uId;
    private String userName;
    private String company;
    private String location;
    private UserDao userDao;

    public UserService() {
    }

    public UserService(String uId) {
        this.uId = uId;
    }

    public String queryUserInfo() {
        if (userDao != null) {
            userName = userDao.queryUserName(uId);
        }

        //适配存量案例
        else {
            System.out.println("uId = " + uId);
        }

        return this.toString();
    }

    @Override
    public String toString() {
        return "UserService{" +
                "uId='" + uId + '\'' +
                ", userName='" + userName + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行：UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("执行：UserService.afterPropertiesSet");
    }
}
