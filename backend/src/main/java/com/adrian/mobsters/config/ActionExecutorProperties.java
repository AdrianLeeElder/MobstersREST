package com.adrian.mobsters.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Configuration
@ConfigurationProperties("action-executor")
@Data
public class ActionExecutorProperties {

    @Min(1)
    @Max(30)
    private int threadCoreSize;

    @Min(1)
    @Max(30)
    private int threadMaxPoolSize;

    @Min(300)
    @Max(1000)
    private int threadQueueCapacity;

    @Min(1)
    @Max(10)
    private int maxFailures;
}
