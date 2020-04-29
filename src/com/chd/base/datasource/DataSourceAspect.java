package com.chd.base.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

@Aspect
public class DataSourceAspect implements Ordered{

    /**
    * 定义切面，当调用com.chd.hrp.service下的所有类的所有方法前都会执行beforeInvoke方法
    */
    @Pointcut("execution(* com.chd.hrp.*.serviceImpl..*.*(..))")
    public void pointCut(){};

    @Before(value = "pointCut()")
    public void beforeInvoke(JoinPoint joinpoint) {
        try {
            String clazzName = joinpoint.getTarget().getClass().getName();
            String methodName = joinpoint.getSignature().getName();
            Class targetClazz = Class.forName(clazzName);
            Method[] methods = targetClazz.getMethods();
            for(Method method : methods) {
                if(method.getName().equals(methodName)) {
                    // 首先查看方法是否使用注解
                    // 如果使用注解，则获取注解定义的值，并根据注解的值设置访问数据库的key
                    if(method.isAnnotationPresent(DataSource.class)) {
                        DataSource dataSource = method.getAnnotation(DataSource.class);
                        DatasourceHolder.setDataSource(dataSource.value());
                    }else{
                    	DatasourceHolder.setDataSource("oracle");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -9999;
	}
}