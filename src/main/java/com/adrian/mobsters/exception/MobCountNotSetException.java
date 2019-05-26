package com.adrian.mobsters.exception;

/**
 * Thrown when unable to extract the mob count from the current page.
 */
public class MobCountNotSetException extends RuntimeException {
    public MobCountNotSetException() {
        super("The mob count was unable to be extracted.");
    }
}
