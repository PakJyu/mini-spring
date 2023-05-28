package com.pakjyu.springframework.util;

import java.util.function.Predicate;

public class ClassUtils {

    public static ClassLoader getDefaultClassLoader(ClassLoader classLoader) {
        return classLoader != null ? classLoader : getDefaultClassLoader();
    }

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

    /**
     * Check whether the specified class is a CGLIB-generated class.
     * @param clazz the class to check
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    /**
     * Check whether the specified class name is a CGLIB-generated class.
     * @param className the class name to check
     */
    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }
}
