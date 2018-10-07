package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.ActionJobReactiveRepository;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import com.adrian.mobsters.service.MobsterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MobsterControllerTest {

    @Mock
    private MobsterService mobsterService;
    @Mock
    private MobsterReactiveRepository mobsterReactiveRepository;
    @Mock
    private ActionJobReactiveRepository actionJobReactiveRepository;
    @InjectMocks
    private MobsterController mobsterController;
    private WebTestClient webTestClient;
    private List<ActionJob> actionJobList;

    @Before
    public void setup() {
        webTestClient = WebTestClient
                .bindToController(mobsterController)
                .build();
    }

    @Test
    public void getAllMobsters() {
        actionJobList = Collections.singletonList(new ActionJob(null,null, true, false));
        Mobster mobster = new Mobster("1", "Zombie", "");

        given(mobsterReactiveRepository.findAll()).willReturn(Flux.just(mobster));

        given(actionJobReactiveRepository.findByMobsterUsername("Zombie"))
                .willReturn(Flux.fromIterable(actionJobList));

        webTestClient.get().uri("/api/v1/mobster")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Mobster.class)
                .hasSize(1)
                .consumeWith(response -> {
                    Objects.requireNonNull(response);

                    for (Mobster m : response.getResponseBody()) {
                        assertTrue(m.getActionJobs() != null && !m.getActionJobs().isEmpty());
                    }
                });
    }

    @Test
    public void createNewMobster() {
        given(mobsterReactiveRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(new Mobster("1", "adrian", "")));

        given(mobsterService.createMobsters(any(Publisher.class)))
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
