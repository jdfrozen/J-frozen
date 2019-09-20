package com.frozen.springbootsecurity.config;

import com.frozen.springbootsecurity.dao.SysUserDAO;
import com.frozen.springbootsecurity.mode.SysRole;
import com.frozen.springbootsecurity.mode.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author qiumin
 * @create 2019/1/12 17:24
 * @desc
 **/
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = userDAO.findByUsername(s);
        if (sysUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        String pwd = passwordEncoder.encode(sysUser.getPassword());
        System.out.println(pwd);
        return new User(sysUser.getUsername(),pwd,getRoles(sysUser.getRoles()));
    }

    private Collection<GrantedAuthority> getRoles(List<SysRole> roles){
        List<GrantedAuthority> list = new ArrayList<>();
        for (SysRole role : roles){
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
            list.add(grantedAuthority);
        }
        return list;
    }
}
