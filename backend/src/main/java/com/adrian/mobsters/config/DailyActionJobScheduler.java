package com.adrian.mobsters.config;

import com.adrian.mobsters.service.DailyActionJobInitializer;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DailyActionJobScheduler {

    private final DailyActionJobInitializer dailyActionJobInitializer;

    @Scheduled(cron = "0 0 1 * * ?")
    public void runDaily() {
        dailyActionJobInitializer.schedule();
    }
}
