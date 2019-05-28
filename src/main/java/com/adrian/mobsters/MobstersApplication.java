package com.adrian.mobsters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties
@EnableScheduling
public class MobstersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobstersApplication.class, args);
    }
}