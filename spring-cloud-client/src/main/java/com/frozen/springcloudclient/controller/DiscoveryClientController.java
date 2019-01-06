package com.frozen.springcloudclient.controller;

import com.frozen.springcloudserver.spi.rest.DiscoveryRestController;
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
public class DiscoveryClientController {
    @Autowired
    private DiscoveryRestController discoveryRestController;

    @Value("${server.port}")
    private String ip;
    @GetMapping("/client")
    public String client() {
        String services = "spring-cloud-eureka-client  ip :"+ip;
        System.out.println(services);
        discoveryRestController.server();
        return services;
    }
}