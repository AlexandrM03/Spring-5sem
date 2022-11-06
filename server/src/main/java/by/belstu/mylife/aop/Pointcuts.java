package by.belstu.mylife.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* by.belstu.mylife.web.AuthController.*(..))")
    public void authControllerPointcut() {
    }

    @Pointcut("execution(* by.belstu.mylife.web.CommentController.*(..))")
    public void commentControllerPointcut() {
    }

    @Pointcut("execution(* by.belstu.mylife.web.ImageController.*(..))")
    public void imageControllerPointcut() {
    }

    @Pointcut("execution(* by.belstu.mylife.web.PostController.*(..))")
    public void postControllerPointcut() {
    }

    @Pointcut("execution(* by.belstu.mylife.web.UserController.*(..))")
    public void userControllerPointcut() {
    }
}
