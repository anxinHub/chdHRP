package com.chd.base.quartz;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHelper implements ApplicationContextAware{
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
    public static Object getBean(String beanName){return context.getBean(beanName);}

    public static <T> T getBean(Class<T> tClass){return context.getBean(tClass);}
}