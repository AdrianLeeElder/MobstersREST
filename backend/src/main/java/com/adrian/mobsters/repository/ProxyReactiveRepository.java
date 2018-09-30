package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.Proxy;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProxyReactiveRepository extends ReactiveCrudRepository<Proxy, String> {

    Mono<Proxy> findFirstByInUse(boolean inUse);

    Mono<Proxy> findByHost(String host);
}
