package by.belstu.mylife.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

@Component
@org.aspectj.lang.annotation.Aspect@Slf4j
public class Aspect {
    @Around("Pointcuts.authControllerPointcut()")
    public Object aroundAuthenticateAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(joinPoint.getSignature().getName() + " called");
        return joinPoint.proceed();
    }

    @Around("Pointcuts.commentControllerPointcut()")
    public Object aroundCommentAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(joinPoint.getSignature().getName() + " called");
        return joinPoint.proceed();
    }

    @Around("Pointcuts.imageControllerPointcut()")
    public Object aroundImageAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(joinPoint.getSignature().getName() + " called");
        return joinPoint.proceed();
    }

    @Around("Pointcuts.postControllerPointcut()")
    public Object aroundPostAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(joinPoint.getSignature().getName() + " called");
        return joinPoint.proceed();
    }

    @Around("Pointcuts.userControllerPointcut()")
    public Object aroundUserAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(joinPoint.getSignature().getName() + " called");
        return joinPoint.proceed();
    }
}
