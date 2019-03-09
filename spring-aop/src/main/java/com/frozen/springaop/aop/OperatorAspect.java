package com.frozen.springaop.aop;

import com.frozen.springaop.annocation.AopAnnocation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Auther: Frozen
 * @Date: 2019/1/16 15:27
 * @Description: aop定义
 */
@Component
@Aspect
@Slf4j
public class OperatorAspect {
    //@Pointcut("execution(* com.frozen.springaop.controller..*.*(..))")  //通过包的扫描方式
    @Pointcut("@annotation(com.frozen.springaop.annocation.AopAnnocation)")
    public void pointCut(){}

    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("AOP After Advice...");
    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("AOP Before Advice...");
    }

    @AfterReturning(pointcut="pointCut()",returning="returnVal")
    public void afterReturn(JoinPoint joinPoint,Object returnVal){
        System.out.println("AOP AfterReturning Advice:" + returnVal);
    }

    @AfterThrowing(pointcut="pointCut()",throwing="error")
    public void afterThrowing(JoinPoint joinPoint,Throwable error){
        System.out.println("AOP AfterThrowing Advice..." + error);
        System.out.println("AfterThrowing...");
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp){
        System.out.println("AOP Aronud before...");
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        AopAnnocation annotation = method.getAnnotation(AopAnnocation.class);
        //方法参数
        Object[] args = pjp.getArgs();
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            ;log.info(annotation.name()+annotation.desc()+e);
        }
        System.out.println("AOP Aronud after...");
        return null;
    }
}
