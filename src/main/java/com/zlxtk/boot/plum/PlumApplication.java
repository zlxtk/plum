package com.zlxtk.boot.plum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PlumApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlumApplication.class, args);
    }
}
