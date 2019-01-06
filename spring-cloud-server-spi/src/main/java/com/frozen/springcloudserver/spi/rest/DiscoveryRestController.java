package com.frozen.springcloudserver.spi.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: Frozen
 * @Date: 2019/1/6 18:08
 * @Description:
 */
@FeignClient("spring-cloud-eureka-server")
public interface DiscoveryRestController {
    @GetMapping("/server")
    public String server();
}
