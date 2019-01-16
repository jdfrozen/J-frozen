package com.frozen.springaop.controller;

import com.frozen.springaop.annocation.AopAnnocation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查
 */
@RestController
public class HealthCheckController {

    @RequestMapping("/info")
    @AopAnnocation(name = "测试方法", desc = "测试方法")
    public String info(String tt){return "success";}

    @RequestMapping("/faild")
    @AopAnnocation(name = "测试异常方法", desc = "方法描述")
    public void error(String tt){
        throw new RuntimeException("测试异常方法Runtime");
    }
}
