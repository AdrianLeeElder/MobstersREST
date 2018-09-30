package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import com.adrian.mobsters.service.MobsterService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MobsterControllerTest {

    @Mock
    private MobsterService mobsterService;
    @Mock
    private MobsterReactiveRepository mobsterReactiveRepository;

    private WebTestClient webTestClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(new MobsterController(mobsterReactiveRepository, mobsterService))
                .build();
    }

    @Test
    public void getAllMobsters() {
        BDDMockito.given(mobsterReactiveRepository.findAll())
                .willReturn(Flux.just(new Mobster("1", "Zombie", "")));

        Mobster mobster = new Mobster("1", "johnny", "h");

        Mobster mobster2 = new Mobster("2", "Adrian", "h");

        when(mobsterService.getMobsters()).thenReturn(Flux.fromArray(new Mobster[]{mobster, mobster2}));

        webTestClient.get().uri("/api/v1/mobster")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Mobster.class)
                .hasSize(2);
    }

    @Test
    public void createNewMobster() {
        BDDMockito.given(mobsterReactiveRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(new Mobster("1", "adrian", "")));

        BDDMockito.given(mobsterService.createMobsters(any(Publisher.class)))
                .willReturn(Flux.just(new Mobster("1", "adrian", "")));

        Mobster mobster = new Mobster("1", "john", "");

        Mono<Mobster> mobsterMono = Mono.just(new Mobster("1", "zombie", ""));

        webTestClient
                .post()
                .uri("/api/v1/mobster")
                .body(mobsterMono, Mobster.class).exchange()
                .expectStatus().isCreated();
    }
}
