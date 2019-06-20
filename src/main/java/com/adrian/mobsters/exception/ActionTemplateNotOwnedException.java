package com.adrian.mobsters.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActionTemplateNotOwnedException extends RuntimeException {
    public ActionTemplateNotOwnedException() {
        super("not authorized to save this action template");
    }
}
