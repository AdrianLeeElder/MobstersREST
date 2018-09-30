package com.adrian.mobsters.service.proxy;

import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.exception.ProxyNotAvailableException;
import com.adrian.mobsters.repository.ProxyReactiveRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyServiceImpl implements ProxyService {

    private final ProxyReactiveRepository proxyReactiveRepository;
    private static final int PROXY_ATTEMPTS_THRESHOLD = 10;

    @Override
    public Proxy getAvailableProxy() throws ProxyNotAvailableException {
        Proxy availableProxy = getFirstProxyNotInUse(getSortedProxyList(proxyReactiveRepository.findAll()));

        if (availableProxy != null) {
            return availableProxy;
        }

        throw new ProxyNotAvailableException();
    }

    protected Proxy getFirstProxyNotInUse(@NonNull Flux<Proxy> sortedProxyList) {
        return getFirstFromList(sortedProxyList.filter(p -> !p.isInUse()));
    }

    private Proxy getFirstFromList(@NonNull Flux<Proxy> proxyFlux) {
        return proxyFlux.blockFirst();
    }

    protected Flux<Proxy> getSortedProxyList(@NonNull Flux<Proxy> proxies) {
        return proxies.sort();
    }

    @Override
    public void handleProxyFailure(@NonNull Proxy proxy) {
        proxy.setFailures(proxy.getFailures() + 1);

        if (shouldAttemptToCheckFailureToSuccessRatio(proxy) && shouldDeleteProxy(proxy)) {

            log.info("Removing proxy from list because of the success to failure ratio: {}", proxy);
            proxyReactiveRepository.delete(proxy).subscribe();
        }
    }

    private boolean shouldDeleteProxy(Proxy proxy) {
        return (proxy.getSuccesses() / proxy.getFailures()) < 2;
    }

    private boolean shouldAttemptToCheckFailureToSuccessRatio(Proxy proxy) {
        return proxy.getAttempts() > PROXY_ATTEMPTS_THRESHOLD;
    }
}
