package com.adrian.mobsters.exception;

/**
 * When proxies cannot connect. This helps us track which proxies need to be removed.
 */
public class ProxyCouldNotConnectException extends Throwable {

    public ProxyCouldNotConnectException(String proxyConfig) {
        super("Proxy couldn't connect: " + proxyConfig);
    }
}
