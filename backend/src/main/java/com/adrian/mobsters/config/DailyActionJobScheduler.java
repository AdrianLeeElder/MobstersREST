package com.adrian.mobsters.config;

import com.adrian.mobsters.service.DailyActionJobInitializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyActionJobScheduler {

    private DailyActionJobInitializer dailyActionJobInitializer;
    @Scheduled(cron = "0 0 1 * * ?")
    public void runDaily() {

    }
}
