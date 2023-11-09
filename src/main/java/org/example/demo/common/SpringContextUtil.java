package org.example.demo.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        this.applicationContext = context ;
    }

    public static <T> T getBean(String name, Class<T> reqType){
        return applicationContext.getBean(name,reqType);
    }

    public static <T> T getBean(Class<T> reqType) {
        return applicationContext.getBean(reqType);
    }
}
