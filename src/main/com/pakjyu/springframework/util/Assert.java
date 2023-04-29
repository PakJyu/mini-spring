package com.pakjyu.springframework.util;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Assert {

    public static <X extends Throwable> void notNull(Object object, X x) throws X {
        if (null == object) throw x;
    }

    

}
