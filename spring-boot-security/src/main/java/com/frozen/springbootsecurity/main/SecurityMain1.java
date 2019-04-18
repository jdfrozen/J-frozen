package com.frozen.springbootsecurity.main;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @Auther: frozen
 * @Date: 2019/4/18 22:00
 * @Description:
 */
public class SecurityMain1 {
    public static void main(String[] main){
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        String username = "admin";
        String password  = "123456";
        //可以添加加密算法
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //可以选择加密，对于下面的provider.setPasswordEncoder(passwordEncoder);
        passwordEncoder.encode(password);
        UserDetails userDetails = User.withUsername(username).password(password).roles("role").build();
        userDetailsManager.createUser(userDetails);
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsManager);
        //上面加密的同时，这里也要加密
        provider.setPasswordEncoder(passwordEncoder);
        //这个相当于外部输入的数据
        Authentication authentication = new UsernamePasswordAuthenticationToken("admin","123456");
        Class<? extends Authentication> toTest = authentication.getClass();
        //判断authentication类型是否与provider对应
        System.out.println(provider.supports(toTest));
        Authentication result = provider.authenticate(authentication);
        System.out.println(result);
    }
}
