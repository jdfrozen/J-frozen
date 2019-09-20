package com.frozen.springbootsecurity.dao;

import com.frozen.springbootsecurity.mode.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author qiumin
 * @create 2019/1/13 13:14
 * @desc
 **/
public interface SysMenuDAO extends JpaRepository<SysMenu,Integer> {
}
