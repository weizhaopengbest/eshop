package com.roncoo.eshop.cache.spring;

import org.springframework.context.ApplicationContext;

/**
 * @author weizhaopeng
 * @date 2020/1/19
 */
public class SpringContext {


    private static ApplicationContext applicationContext;


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.applicationContext = applicationContext;
    }
}
