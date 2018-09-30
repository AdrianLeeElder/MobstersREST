package com.adrian.mobsters.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoDailyActionsException extends Throwable{

    public NoDailyActionsException() {
        super("There are no daily actions saved in the daily actions collection.");
    }
}
