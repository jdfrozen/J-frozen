package com.frozen.springcloudclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Frozen
 * @Date: 2019/1/6 17:05
 * @Description:
 */
@RestController
public class DiscoveryController {

    @Value("${server.port}")
    private String ip;

    @GetMapping("/client")
    public String client() {
        String services = "spring-cloud-eureka-client  ip :"+ip;

        System.out.println(services);
        return services;
    }
}