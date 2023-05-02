package com.pakjyu.springframework.util;

import cn.hutool.core.util.StrUtil;

public interface Apply {

    public static String beanNameOfClass(Class clazz) {
        return StrUtil.lowerFirst(clazz.getSimpleName());
    }

    public static String beanNameOfClass(String xmlBeanId, String xmlBeanName) {
        return StrUtil.isNotEmpty(xmlBeanId) ? xmlBeanId : xmlBeanName;
    }

}
