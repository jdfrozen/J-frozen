package com.frozen.springaop.annocation;

import java.lang.annotation.*;

/**
 * @Auther: 冯默风
 * @Date: 2019/1/16 15:31
 * @Description:
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopAnnocation {
    String name()  default "";
    String desc()  default "";
    boolean printReturn() default true;
}
