package com.frozen.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author qiumin
 * @create 2019/1/12 17:22
 * @desc
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "userDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginSuccessAuthenticationHandler successAuthenticationHandler;

    @Autowired
    private LoginFailureAuthenticationHandler failureAuthenticationHandler;

    @Autowired
    private AuthenticationAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UrlAccessDecisionManager decisionManager;

    @Autowired
    private UrlPathFilterInvocationSecurityMetadataSource urlPathFilterInvocationSecurityMetadataSource;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/index.html","/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(decisionManager);
                        o.setSecurityMetadataSource(urlPathFilterInvocationSecurityMetadataSource);
                        return o;
                    }
                })

                .anyRequest()
                .authenticated()// 其他 url 需要身份认证

                .and()
                .formLogin()  //开启登录
                .successHandler(successAuthenticationHandler)
                .failureHandler(failureAuthenticationHandler)
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

}
