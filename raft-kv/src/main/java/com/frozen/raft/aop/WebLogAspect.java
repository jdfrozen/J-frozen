package com.frozen.raft.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

	@Pointcut("@annotation(com.frozen.raft.aop.OperLog)")
	public void operLogPoinCut() {}

	@Before("operLogPoinCut()")
	public void logBeforeController(JoinPoint joinPoint) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		log.info("class={},method={},args={}" ,
				joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName(),Arrays.toString(joinPoint.getArgs()));
	}
}
