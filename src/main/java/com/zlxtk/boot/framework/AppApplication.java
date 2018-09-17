package com.zlxtk.boot.framework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class AppApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;


    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] activeProfiles = appContext.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            log.warn("Spring Boot load profile is: {}", profile);
            log.warn("Aplication started successfully, lets go and have fun......");
        }
    }
}
