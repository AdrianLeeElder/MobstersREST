package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.domain.ProxyContainer;
import com.adrian.mobsters.repository.ProxyReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProxyControllerTest {

    private static final String BASE_API = "api/v1/proxy";

    @Mock
    private ProxyReactiveRepository proxyReactiveRepository;
    private WebTestClient webTestClient;
    @InjectMocks
    private ProxyController proxyController;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToController(proxyController).build();
    }

    @Test
    public void addProxy() {
        Proxy proxy = new Proxy("localhost", 533);
        given(proxyReactiveRepository.save(proxy)).willReturn(Mono.just(proxy));
        webTestClient
                .post()
                .uri(BASE_API + "/add")
                .body(Mono.just(proxy), Proxy.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Proxy.class);
    }

    @Test
    public void addProxyList() {
        Proxy proxy = new Proxy("localhost", 533);
        List<Proxy> proxyList = Collections.singletonList(proxy);
        ProxyContainer proxyContainer = new ProxyContainer();
        proxyContainer.setProxies(proxyList);
        given(proxyReactiveRepository.saveAll(proxyList)).willReturn(Flux.just(proxy));

        webTestClient
                .post()
                .uri(BASE_API + "/addlist")
                .body(Mono.just(proxyContainer), ProxyContainer.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(Proxy.class);
    }
}