package com.frozen.springbootsecurity.config;

import com.frozen.springbootsecurity.util.JsonUtil;
import com.frozen.springbootsecurity.util.SysUserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author qiumin
 * @create 2019/1/13 12:59
 * @desc
 **/
@Component
public class LoginSuccessAuthenticationHandler implements AuthenticationSuccessHandler {



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print("登录成功:用户id"+ JsonUtil.beanToStr(SysUserUtil.getUser()));
        writer.flush();
    }
}
