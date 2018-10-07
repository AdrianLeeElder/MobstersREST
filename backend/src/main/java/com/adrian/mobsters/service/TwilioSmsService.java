package com.adrian.mobsters.service;

import com.adrian.mobsters.repository.AccountReactiveRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Profile("prod")
public class TwilioSmsService implements SmsService {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String twilioAccountSid;
    @Value("${TWILIO_AUTH_TOKEN}")
    private String twilioAuthToken;
    @Value("${TWILIO_PHONE_NUMBER}")
    private String twilioPhoneNumber;

    private AccountReactiveRepository accountReactiveRepository;

    @Override
    public void sendSms(String message) {
        List<PhoneNumber> phoneNumbers =
                accountReactiveRepository
                        .findAll()
                        .collectList()
                        .block()
                        .stream()
                        .map(account -> new PhoneNumber(account.getPhoneNumber()))
                        .collect(toList());

        Twilio.init(twilioAccountSid, twilioAuthToken);

        for (PhoneNumber phoneNumber : phoneNumbers) {
            Message.creator(phoneNumber, new PhoneNumber(twilioPhoneNumber), message).create();
        }
    }
}
