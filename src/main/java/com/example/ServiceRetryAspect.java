package com.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Aspect
@Component
public class ServiceRetryAspect {

    @Value("${service.api-request.retry-count}")
    private int retryCount;

    @Value("${service.api-request.wait-to-retry}")
    private int waitToRetry;

    private static final Logger logger = Logger.getLogger(ServiceRetryAspect.class.getName());

    @Pointcut("execution(* com.example.service.APIRequestService.doRequest(..))")
    public void doRequestPointCut() {}

    @Around("doRequestPointCut()")
    public String aroundDoRequest(ProceedingJoinPoint jp) throws Throwable {
        String result = null;
        int numOfTriesLeft = retryCount;

        while (numOfTriesLeft > 0) {
            try {
                result = (String) jp.proceed();
                break;
            } catch (IOException ex) {
                logger.severe("Error during connection: " + ex.getMessage());
                logger.info(String.format("Will wait %d seconds and try to connect %d more time(s).\n", waitToRetry, numOfTriesLeft--));
                Thread.sleep(waitToRetry);
            }
        }

        return result;
    }
}
