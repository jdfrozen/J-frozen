package com.frozen.springbootsecurity.server;

import com.frozen.springbootsecurity.po.User;
import org.springframework.stereotype.Service;

/**
 * @Auther: frozen
 * @Date: 2019/4/21 08:27
 * @Description:
 */
@Service
public class UserService {

    public User getByUsername(String username) {
        User user = new User();
        user.setEnabled(true);
        //PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //user.setPassword(passwordEncoder.encode("123456"));
        user.setPassword("123456");
        return user;
    }
}
