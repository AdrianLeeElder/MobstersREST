package com.adrian.mobsters.service.proxy;

import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.repository.ProxyReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Periodically update a proxy list.
 */
@Component
@Slf4j
public class ProxyUpdateScheduler {

    @Autowired
    private ProxyDownloader proxyDownloader;
    @Autowired
    private ProxyExtracter proxyExtracter;
    @Autowired
    private ProxyReactiveRepository proxyReactiveRepository;
    //update proxies twice a day
    private static final long PROXY_UPDATE_INTERVAL = 43200000;

    /**
     * Pull proxies from https://us-proxy.com
     */
    @Scheduled(fixedDelay = PROXY_UPDATE_INTERVAL)
    public void replaceProxies() {
        proxyReactiveRepository.deleteAll().subscribe();
        List<Proxy> proxies = proxyExtracter.getProxiesFromPageString(proxyDownloader.getProxyPage());
        proxyReactiveRepository.saveAll(proxies).subscribe();
        log.debug("Refreshed proxies: {}", proxies);
    }
}
