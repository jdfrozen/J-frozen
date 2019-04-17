package com.frozen.springbootsecurity.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService customUserService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("123456").roles("role").build());
        return manager;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("hello").hasRole("role")
                .antMatchers("index").permitAll()
                .and().formLogin()
                .and().httpBasic();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/login1")
//                //设置默认登录成功跳转页面
//                .defaultSuccessUrl("/main").failureUrl("/login1?error").permitAll()
//                .and()
//                .logout()
//                //默认注销行为为logout，可以通过下面的方式来修改
//                .logoutUrl("/custom-logout")
//                //设置注销成功后跳转页面，默认是跳转到登录页面
//                .logoutSuccessUrl("/login1")
//                .permitAll()
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/myerror");//无权访问;
//    }
}