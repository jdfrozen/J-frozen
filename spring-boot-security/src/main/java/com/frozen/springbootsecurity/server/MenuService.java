package com.frozen.springbootsecurity.server;

import com.frozen.springbootsecurity.po.Menu;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: frozen
 * @Date: 2019/4/21 08:27
 * @Description:
 */
@Service
public class MenuService {
    /**
     * 获取所有菜单
     * @return
     */
    public List<Menu> getAllMenu(){
        return new ArrayList<>();
    }

}
