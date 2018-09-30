package com.adrian.mobsters.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a request is made to create another daily action {@link com.adrian.mobsters.domain.ActionJob} a mobster
 * when one already exist.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DailyActionJobAlreadyExistForMobster extends RuntimeException {

    public DailyActionJobAlreadyExistForMobster(String usernames) {
        super("A daily action job already exist for the given mobster(s): " + usernames);
    }
}
