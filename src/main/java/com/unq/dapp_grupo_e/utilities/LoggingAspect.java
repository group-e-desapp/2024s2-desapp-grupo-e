package com.unq.dapp_grupo_e.utilities;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.unq.dapp_grupo_e.controller..*(..))")
    public Object logEntryWebService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        var userData = getCurrentUserData();
        var timeStart = System.currentTimeMillis();

        var methodName = proceedingJoinPoint.getSignature().toShortString();
        Object[] arguments = proceedingJoinPoint.getArgs();
        var argumentsProcessed = Arrays.stream(arguments).map(Object::toString).collect(Collectors.joining(", "));

        try {
            Object result = proceedingJoinPoint.proceed();
            String timestamp = CurrentDateAndTime.getNewDateAsString();
            var timeEnd = System.currentTimeMillis();

            LOGGER.info("Audit Log | timestamp: {}, user: {}, method: {}, params: {}, executionTime: {} ms",
                timestamp, userData, methodName, argumentsProcessed, (timeEnd - timeStart));

            return result;
        } catch (Exception e) {
            LOGGER.error("Error in execution of method: " + methodName, e);
            return proceedingJoinPoint.proceed();
        }
        
    }

    private String getCurrentUserData() {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser != null && authUser.isAuthenticated() && !"anonymousUser".equals(authUser.getPrincipal())) {
            return authUser.getName();
        }
        return "Anon User";
    }
    
}
