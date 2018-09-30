package com.adrian.mobsters.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoMobsterUsernamesMatchRegex extends Throwable {
    public NoMobsterUsernamesMatchRegex(String usernameRegex) {
        super("No mobsters matched the supplied username regex: " + usernameRegex);
    }
}
