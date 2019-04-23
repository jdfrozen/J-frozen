package com.frozen.springbootsecurity.main;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: frozen
 * @Date: 2019/4/17 08:09
 * @Description:
 */
public class SecurityMain {

    public static void main(String[] args) {
        UserDetailsService userDetailsService = new InMemoryUserDetailsManager();
        ((InMemoryUserDetailsManager) userDetailsService).createUser(User.withUsername("admin").password("123456").roles("admin").build());
        //获取用户信息并且打印
        UserDetails user = userDetailsService.loadUserByUsername("admin");
        System.out.println(user);

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(provider);
        AuthenticationManager manager = new ProviderManager(providers);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("admin","123456");
        Authentication result = null;
        result = manager.authenticate(authenticationToken);
        System.out.println(authenticationToken);
        System.out.println(result);

    }

}
