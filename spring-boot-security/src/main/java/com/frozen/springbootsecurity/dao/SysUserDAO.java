package com.frozen.springbootsecurity.dao;

import com.frozen.springbootsecurity.mode.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author qiumin
 * @create 2019/1/12 17:20
 * @desc
 **/
public interface SysUserDAO extends JpaRepository<SysUser,Integer> {

    /**
     * 根据用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    SysUser findByUsernameAndPassword(String username, String password);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    SysUser findByUsername(String username);
}
