package com.adrian.mobsters.exception;

/**
 * Thrown when the action job was not found.
 */
public class ActionJobNotFound extends RuntimeException {
    public ActionJobNotFound(String id) {
        super("action job not found for id: " + id);
    }
}
