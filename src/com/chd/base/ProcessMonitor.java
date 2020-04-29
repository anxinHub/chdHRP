package com.chd.base;

import org.aspectj.lang.ProceedingJoinPoint;  
import org.aspectj.lang.annotation.Around;  
import org.aspectj.lang.annotation.Aspect;  
import org.aspectj.lang.annotation.Pointcut;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.stereotype.Component;  

@Aspect  
@Component  
public class ProcessMonitor {  
      
    private static final Logger logger = LoggerFactory.getLogger("monitor");  
     
    @Pointcut("execution(* com.chd.hrp.*.serviceImpl..*.*(..))")    
    private void pointCutMethod() {    
    }    
  
     //声明环绕通知    
    @Around("pointCutMethod()")    
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {    
        long begin = System.nanoTime();  
        Object o = pjp.proceed();    
        long end = System.nanoTime();  
        logger.info("{}:{}",pjp.getTarget().getClass()+"."+pjp.getSignature().getName(),(end-begin)/1000000);  
        return o;    
    }    
}  

