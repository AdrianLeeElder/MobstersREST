package com.adrian.mobstersrest.mobsters.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.repositories.MobsterReactiveRepository;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class MobsterServiceImplTest {

  @Mock
  private MobsterReactiveRepository mobsterReactiveRepository;
  @InjectMocks
  private MobsterServiceImpl mobsterServiceImpl;

  private String JOHN_SMITH = "John Smith";

  @Test
  public void readUserPassword() {
    Mobster mobster = Mobster.builder().username("zombie").password("hax").build();
    given(mobsterReactiveRepository.findByUsername("zombie")).willReturn(Mono.just(mobster));

    assertThat(mobsterServiceImpl.retrieveMobsterPassword("zombie"), equalTo("hax"));
  }

  @Test
  public void createMobsters() {
    Mobster mobster = new Mobster();
    mobster.setId("1");
    mobster.setUsername(JOHN_SMITH);

    given(mobsterReactiveRepository.saveAll(any(Publisher.class)))
        .willReturn(Flux.just(mobster));

    Flux<Mobster> createdMobster = mobsterServiceImpl.createMobsters(Flux.just(mobster));

    assertThat(mobster, is(equalTo(createdMobster.blockFirst())));
  }

  @Test
  public void addToQueue() {
    Mobster mobster = Mobster.builder().username("zombie").build();
    given(mobsterReactiveRepository.findByUsername(anyString()))
        .willReturn(Mono.just(mobster));
    given(mobsterReactiveRepository.save(mobster))
        .willReturn(Mono.just(mobster));

    mobsterServiceImpl.addToQueue("zombie");

    assertTrue(mobster.isPending());
  }

  @Test
  public void setComplete() {
    Mobster mobster = Mobster.builder().username("zombie").build();

    given(mobsterReactiveRepository.findByUsername("zombie")).willReturn(Mono.just(mobster));
    given(mobsterReactiveRepository.save(any())).willReturn(Mono.just(mobster));

    assertTrue(mobsterServiceImpl.setComplete(true, "zombie").block().isComplete());
  }
}
