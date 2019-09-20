package com.frozen.springbootsecurity.util;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author qiumin
 * @create 2019/1/13 13:00
 * @desc
 **/
public class SysUserUtil {

    /**
     * 获取用户信息
     * @return
     */
    public static Object getUser(){
        return  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
