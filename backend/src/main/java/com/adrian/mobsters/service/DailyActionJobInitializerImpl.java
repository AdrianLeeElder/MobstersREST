package com.adrian.mobsters.service;

import com.adrian.mobsters.repository.ActionJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyActionJobInitializerImpl implements DailyActionJobInitializer {

    private final ActionJobCreator actionJobCreator;
    private final ActionJobRepository actionJobRepository;
    private final SmsService smsService;

    @Override
    public void schedule() {
        actionJobRepository.deleteAll();
        actionJobCreator.getNewDailyJobForAllMobsters();
        smsService.sendSms(SmsMessage.BOT_STARTED.getMessage(LocalDateTime.now()));

        log.trace("Running scheduled daily actions.");
    }
}
