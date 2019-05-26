package com.adrian.mobsters.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MobsterNotFoundException extends RuntimeException {

    public MobsterNotFoundException(String username) {
        super("Mobster with username " + username + " not found.");
    }
}
