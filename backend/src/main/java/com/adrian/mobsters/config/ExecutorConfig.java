package com.adrian.mobsters.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ExecutorConfig {

    @Autowired
    private ActionExecutorProperties actionExecutorProperties;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(actionExecutorProperties.getThreadCoreSize());
        threadPoolTaskExecutor.setQueueCapacity(actionExecutorProperties.getThreadQueueCapacity());

        return threadPoolTaskExecutor;
    }
}
