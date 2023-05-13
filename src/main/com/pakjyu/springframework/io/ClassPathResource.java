package com.pakjyu.springframework.io;

import com.pakjyu.springframework.util.Assert;
import com.pakjyu.springframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {
    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, new IllegalArgumentException("Path must be null"));
        this.path = path;
        this.classLoader = ClassUtils.getDefaultClassLoader(classLoader);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        Assert.notNull(is, new FileNotFoundException(path + " cannot be opened because it does not exist"));
        return is;
    }
}
