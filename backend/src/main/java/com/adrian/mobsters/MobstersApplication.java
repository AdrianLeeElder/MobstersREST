package com.adrian.mobsters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties
@EnableScheduling
public class MobstersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobstersApplication.class, args);
    }
}