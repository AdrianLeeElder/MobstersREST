package com.adrian.mobsters.exception;

public class ActionNotFoundException extends RuntimeException {

    public ActionNotFoundException(String action) {
        super("Action (" + action + ") was not found or not configured.");
    }
}
