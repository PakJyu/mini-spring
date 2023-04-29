package com.pakjyu.springframework.util;

import java.util.function.Predicate;

public class ClassUtils {
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        Predicate<ClassLoader> isNull = classLoader -> classLoader == null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
        }

        if (isNull.test(cl)) {
            cl = ClassUtils.class.getClassLoader();
            if (isNull.test(cl)) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                }
            }
        }

        return cl;

    }
}
