package com.frozen.nettyws.controller;

import com.frozen.nettyws.server.FIMServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Frozen
 * @create: frozen-15 14:24
 * @description:
 **/
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private FIMServer fimServer;
    @RequestMapping(value = "sendMsg")
    @ResponseBody
    public String sendMsg(@RequestParam String msg){
        fimServer.sendAllMsg(msg);
        return "success";
    }
}
