package com.adrian.mobsters.exception;

import com.adrian.mobsters.domain.ActionTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when no {@link ActionTemplate} was found for the current user.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActionTemplateNotFoundException extends RuntimeException {
    public ActionTemplateNotFoundException(String id) {
        super("no action template with the given id found for this user: " + id);
    }
}
