package org.dsu.eventlog.application;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AspectApplication {

    @Before("within(org.dsu.eventlog.presentation..*)")
    public void loggingControllerParams(JoinPoint joinPoint) {
        log.info("Before running on method={}", joinPoint.toShortString());
        log.info("Agruments Passed={}", Arrays.toString(joinPoint.getArgs()));
    }
    
}
