package com.frozen.springcloudserver.controller;

import com.frozen.springcloudserver.spi.rest.DiscoveryRestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Frozen
 * @Date: 2019/1/6 17:05
 * @Description:
 */
@RestController
public class DiscoveryController implements DiscoveryRestController {

    @Value("${server.port}")
    private String ip;

    @Override
    public String server() {
        String services = "spring-cloud-eureka-server  ip :"+ip;

        System.out.println(services);
        return services;
    }
}