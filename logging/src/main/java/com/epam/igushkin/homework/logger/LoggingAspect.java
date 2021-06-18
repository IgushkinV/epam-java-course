package com.epam.igushkin.homework.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class LoggingAspect {
    @Pointcut("@annotation(com.epam.igushkin.homework.logger.Logging)")
    public void annotated() {
    }

    /**
     * Выводит в логи информацию о методе: имя метода и аргументы.
     * @param joinPoint
     * @throws Throwable
     */
    @Before("annotated()")
    public void logMethod(JoinPoint joinPoint) throws Throwable {
        var methodName = joinPoint.getSignature().getName();
        var methodArgs = joinPoint.getArgs();
        log.info("Вызов метода {}({})", methodName, methodArgs);
    }
}
