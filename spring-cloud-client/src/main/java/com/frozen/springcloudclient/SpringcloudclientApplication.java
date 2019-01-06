package com.frozen.springcloudclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient //基于spring-cloud-netflix。服务采用eureka作为注册中心
//@EnableDiscoveryClient //基于spring-cloud-commons。服务采用其他注册中心
@EnableFeignClients(basePackages = {
        "com.frozen.springcloudserver.spi"
}) //开启SpringCloud Feign的支持功能
@SpringBootApplication
public class SpringcloudclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudclientApplication.class, args);
    }

}

