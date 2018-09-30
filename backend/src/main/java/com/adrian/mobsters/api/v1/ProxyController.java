package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.domain.ProxyContainer;
import com.adrian.mobsters.repository.ProxyReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/proxy")
@RequiredArgsConstructor
public class ProxyController {

    private final ProxyReactiveRepository proxyReactiveRepository;

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Proxy> addProxy(@RequestBody Proxy proxy) {
        return proxyReactiveRepository.save(proxy);
    }

    @PostMapping("addlist")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Proxy> addProxyList(@RequestBody ProxyContainer proxyContainer) {
        return proxyReactiveRepository.saveAll(proxyContainer.getProxies());
    }
}
