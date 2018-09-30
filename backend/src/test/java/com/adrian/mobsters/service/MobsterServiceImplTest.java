package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MobsterServiceImplTest {

    @Mock
    private MobsterReactiveRepository mobsterReactiveRepository;
    @InjectMocks
    private MobsterServiceImpl mobsterServiceImpl;

    private String JOHN_SMITH = "John Smith";

    @Test
    public void readUserPassword() {
        Mobster mobster = new Mobster("1", "zombie", "hax");
        given(mobsterReactiveRepository.findByUsername("zombie")).willReturn(Mono.just(mobster));

        assertThat(mobsterServiceImpl.retrieveMobsterPassword("zombie"), equalTo("hax"));
    }

    @Test
    public void createMobsters() {
        Mobster mobster = new Mobster("1", JOHN_SMITH, "");

        given(mobsterReactiveRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(mobster));

        Flux<Mobster> createdMobster = mobsterServiceImpl.createMobsters(Flux.just(mobster));

        assertThat(mobster, is(equalTo(createdMobster.blockFirst())));
    }
}
