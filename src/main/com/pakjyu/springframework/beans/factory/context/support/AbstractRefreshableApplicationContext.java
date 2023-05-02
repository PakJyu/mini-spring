package com.pakjyu.springframework.beans.factory.context.support;

import com.pakjyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.pakjyu.springframework.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory defaultListableBeanFactory;

    /**
     * 创建BeanFactory并加载BeanDefinition
     */
    @Override
    protected void refreshBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = createBeanFactory();
        loadBeanDefinitions(defaultListableBeanFactory);
        this.defaultListableBeanFactory = defaultListableBeanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory defaultListableBeanFactory);

    /**
     * @return
     */
    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return defaultListableBeanFactory;
    }
}
