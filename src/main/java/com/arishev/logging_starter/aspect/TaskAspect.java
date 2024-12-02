package com.arishev.logging_starter.aspect;

import com.arishev.logging_starter.config.LoggingConfigProperties;
import io.micrometer.common.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.Arrays;

@Aspect
public class TaskAspect {

    private static final String DEFAULT_LOG_LEVEL = "INFO";

    private final LoggingEventBuilder logger;

    private final LoggingConfigProperties configProperties;

    public TaskAspect(LoggingConfigProperties properties) {

        this.configProperties = properties;

        String logLevel = StringUtils.isEmpty(this.configProperties.getLevel()) ?
                                DEFAULT_LOG_LEVEL :
                                this.configProperties.getLevel();

        Logger logger = LoggerFactory.getLogger(TaskAspect.class);
        this.logger = logger.atLevel(Level.valueOf(logLevel));
    }

    @Before("@annotation(com.arishev.logging_starter.aspect.annotations.LogExecution)")
    public void logMethodStart(JoinPoint joinPoint) {
        logger.log("Execute method - " + joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(
            pointcut = "@annotation(com.arishev.logging_starter.aspect.annotations.LogException)",
            throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.log("Exception from method - " + joinPoint.getSignature().toShortString() + ": \n" +
                        "exception message: " + ex.getMessage() + "\n" +
                        "method input parameters: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
            pointcut = "@annotation(com.arishev.logging_starter.aspect.annotations.LogException)",
            returning = "res"
            )
    public void logExecutionResult(JoinPoint joinPoint, Object res) {
        logger.log("Result of calling method - " + joinPoint.getSignature().toShortString() + ":" + res);
    }

    @Around("@annotation(com.arishev.logging_starter.aspect.annotations.LogProfiling)")
    public Object logProfiling(ProceedingJoinPoint proceedingJoinPoint) {

        Object result;
        try {
            long startTime = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long stopTime = System.currentTimeMillis();

            logger.log("Execution time (ms) for method: " +
                    proceedingJoinPoint.getSignature().toShortString() + " = " +
                    (stopTime - startTime));
        } catch (Throwable e) {
           throw new RuntimeException(e.getMessage());
        }

        return result;
    }

    @Around("@annotation(com.arishev.logging_starter.aspect.annotations.EndPointDataToLog)")
    public Object logEndPointData(ProceedingJoinPoint proceedingJoinPoint) {

        Object result;
        try {
            logger.log("[End-Point Logging] Method " + proceedingJoinPoint.getSignature().toShortString());

            Object[] inputParams = proceedingJoinPoint.getArgs();
            logger.log("[End-Point Logging] Input parameters:\n" + Arrays.toString(inputParams));

            result = proceedingJoinPoint.proceed();
            logger.log("[End-Point Logging] Result:\n" + result);
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }

}
