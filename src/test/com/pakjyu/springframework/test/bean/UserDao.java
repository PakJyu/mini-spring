package com.pakjyu.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "10001Name");
        hashMap.put("10002", "10002Name");
        hashMap.put("10003", "10003Name");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
