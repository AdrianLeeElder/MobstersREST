package com.adrian.mobsters.exception;

public class ProxyNotAvailableException extends RuntimeException {

    public ProxyNotAvailableException() {
        super("No proxies are available.");
    }
}
