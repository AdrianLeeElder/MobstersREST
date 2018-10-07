package com.adrian.mobsters.service;

import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyActionJobInitializerImpl implements DailyActionJobInitializer {

    private final ActionJobCreator actionJobCreator;
    private final MobsterReactiveRepository mobsterReactiveRepository;
    private final ActionJobReactiveRepository actionJobReactiveRepository;
    private final SmsService smsService;

    @Override
    public void schedule() {
        actionJobReactiveRepository.deleteAll().subscribe();
        actionJobCreator.getNewDailyJobForAllMobsters().subscribe();
        smsService.sendSms(SmsMessage.BOT_STARTED.getMessage(LocalDateTime.now()));

        log.trace("Running scheduled daily actions.");
    }
}
