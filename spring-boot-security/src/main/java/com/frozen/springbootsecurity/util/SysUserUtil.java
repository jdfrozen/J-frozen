package com.frozen.springbootsecurity.util;

import com.frozen.springbootsecurity.po.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
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
	    SecurityContext ctx = SecurityContextHolder.getContext();
	    Authentication auth = ctx.getAuthentication();
	    //return (User)auth.getPrincipal();
        return  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
