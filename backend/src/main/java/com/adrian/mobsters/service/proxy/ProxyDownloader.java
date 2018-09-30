package com.adrian.mobsters.service.proxy;

/**
 * Used for loading a webpage containing proxies.
 */
public interface ProxyDownloader {

    /**
     * Load a page that contains a proxy list.
     *
     * @return the proxy page to load.
     */
    String getProxyPage();
}
