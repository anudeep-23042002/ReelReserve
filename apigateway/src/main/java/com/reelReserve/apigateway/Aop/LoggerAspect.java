package com.reelReserve.apigateway.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Before("execution(* com.reelReserve.apigateway.Controllers.*.*(..))")
    public void enterLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Entered method: {}", methodName);
    }

    @After("execution(* com.reelReserve.apigateway.*.*(..))")
    public void exitLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Entered"+methodName);
    }
    @Before("execution(* com.reelReserve.apigateway.repo.*.*(..))")
    public void dataAccessOperation() {
        System.out.println("Entered ");
    }
}
