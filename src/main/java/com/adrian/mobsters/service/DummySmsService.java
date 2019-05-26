package com.adrian.mobsters.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
@Slf4j
public class DummySmsService implements SmsService {

    @Override
    public void sendSms(String message) {
        log.trace("Sent Dummy SMS message: " + message);
    }
}
