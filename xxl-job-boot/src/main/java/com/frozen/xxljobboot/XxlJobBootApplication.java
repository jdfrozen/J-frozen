package com.frozen.xxljobboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.frozen.xxljobboot.dao")
@ComponentScan
@SpringBootApplication
public class XxlJobBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxlJobBootApplication.class, args);
    }

}

