package com.adrian.mobsters.service.proxy;

import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.exception.ProxyNotAvailableException;
import com.adrian.mobsters.repository.ProxyRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyServiceImpl implements ProxyService {
    private final ProxyRepository proxyRepository;
    private static final int PROXY_ATTEMPTS_THRESHOLD = 10;

    @Override
    public Proxy getAvailableProxy() throws ProxyNotAvailableException {
        List<Proxy> availableProxies = getSortedProxyList(proxyRepository.findAll())
                .stream().filter(p -> !p.isInUse())
                .collect(Collectors.toList());

        if (availableProxies.isEmpty()) {
            throw new ProxyNotAvailableException();
        }

        return availableProxies.get(0);
    }

    protected List<Proxy> getSortedProxyList(@NonNull List<Proxy> proxies) {
        Collections.sort(proxies);
        return proxies;
    }

    @Override
    public void handleProxyFailure(@NonNull Proxy proxy) {
        proxy.setFailures(proxy.getFailures() + 1);

        if (shouldAttemptToCheckFailureToSuccessRatio(proxy) && shouldDeleteProxy(proxy)) {

            log.info("Removing proxy from list because of the success to failure ratio: {}", proxy);
            proxyRepository.delete(proxy);
        }
    }

    private boolean shouldDeleteProxy(Proxy proxy) {
        return (proxy.getSuccesses() / proxy.getFailures()) < 2;
    }

    private boolean shouldAttemptToCheckFailureToSuccessRatio(Proxy proxy) {
        return proxy.getAttempts() > PROXY_ATTEMPTS_THRESHOLD;
    }
}
