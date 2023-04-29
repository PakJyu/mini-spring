package com.pakjyu.springframework.io;

import com.pakjyu.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource implements Resource {

    private final URL url;

    /**
     * 把三种不同类型的资源处理方式进行了包装，分为：判断是否为ClassPath、URL以及文件。
     * @param url
     */
    public UrlResource(URL url) {
        Assert.notNull(url, new IllegalArgumentException("URL must not be null"));
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection connection = this.url.openConnection();
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            if (connection instanceof HttpURLConnection) ((HttpURLConnection) connection).disconnect();
            throw e;
        }
    }
}
