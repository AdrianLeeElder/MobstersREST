package com.adrian.mobsters.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig implements AsyncConfigurer {

    @Autowired
    private ActionExecutorProperties actionExecutorProperties;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(actionExecutorProperties.getThreadCoreSize());
        threadPoolTaskExecutor.setMaxPoolSize(actionExecutorProperties.getThreadMaxPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(actionExecutorProperties.getThreadQueueCapacity());
        threadPoolTaskExecutor.setThreadNamePrefix("ActionJobExecutor-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
