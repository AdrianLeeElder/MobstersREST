package com.adrian.mobsters.service.proxy;

import com.adrian.mobsters.domain.Proxy;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

/**
 * Used for extracting proxies from a webpage.
 */
public interface ProxyExtracter {

    /**
     * Extract a list of {@link Proxy}s from the given webpage String.
     *
     * @param webpage the web page containing the desired proxies.
     * @return a list of Proxies extracted from the webpage.
     */
    List<Proxy> getProxiesFromPageString(String webpage);
}
