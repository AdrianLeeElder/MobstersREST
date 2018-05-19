package com.adrian.mobstersrest.mobsters.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.adrian.mobstersrest.mobsters.api.v1.controller.MobsterController;
import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.repositories.MobsterReactiveRepository;
import com.adrian.mobstersrest.mobsters.services.MobsterService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MobsterControllerTest {

  @Mock
  private MobsterService mobsterService;
  @Mock
  private MobsterReactiveRepository mobsterReactiveRepository;

  private WebTestClient webTestClient;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    webTestClient = WebTestClient.bindToController(new MobsterController(mobsterService))
        .build();
  }

  @Test
  public void getAllMobsters() {
    BDDMockito.given(mobsterReactiveRepository.findAll())
        .willReturn(Flux.just(Mobster.builder().username("adrian").build()));

    Mobster mobster = new Mobster();
    mobster.setUsername("johnny");

    Mobster mobster2 = new Mobster();
    mobster.setUsername("Adrian");

    when(mobsterService.getMobsters()).thenReturn(Flux.fromArray(new Mobster[]{mobster, mobster2}));

    webTestClient.get().uri("/api/v1/mobsters")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Mobster.class)
        .hasSize(2);
  }

  @Test
  public void createNewMobster() {
    BDDMockito.given(mobsterReactiveRepository.saveAll(any(Publisher.class)))
        .willReturn(Flux.just(Mobster.builder().username("adrian").build()));

    BDDMockito.given(mobsterService.createMobsters(any(Publisher.class)))
        .willReturn(Flux.just(Mobster.builder().username("adrian").build()));

    Mobster mobster = new Mobster();
    mobster.setUsername("john");
    mobster.setId("1");

    Mono<Mobster> mobsterMono = Mono.just(Mobster.builder().username("zombie").build());

    webTestClient
        .post()
        .uri("/api/v1/mobsters")
        .body(mobsterMono, Mobster.class).exchange()
        .expectStatus().isCreated();
  }
}
