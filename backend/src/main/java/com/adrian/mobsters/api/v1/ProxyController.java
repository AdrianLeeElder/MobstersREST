package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.domain.ProxyContainer;
import com.adrian.mobsters.repository.ProxyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/proxy")
@RequiredArgsConstructor
public class ProxyController {
    private final ProxyRepository proxyRepository;

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public Proxy addProxy(@RequestBody Proxy proxy) {
        return proxyRepository.save(proxy);
    }

    @PostMapping("add-list")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Proxy> addProxyList(@RequestBody ProxyContainer proxyContainer) {
        return proxyRepository.saveAll(proxyContainer.getProxies());
    }
}
