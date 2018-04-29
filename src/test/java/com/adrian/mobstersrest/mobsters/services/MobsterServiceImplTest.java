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
import com.adrian.mobstersrest.mobsters.repositories.ReactiveMobsterRepository;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MobsterServiceImplTest {

  @Mock
  private VaultTemplate vaultTemplate;
  @Mock
  private VaultResponse vaultResponse;
  @Mock
  private ReactiveMobsterRepository reactiveMobsterRepository;
  @InjectMocks
  private MobsterServiceImpl mobsterServiceImpl;

  private String JOHN_SMITH = "John Smith";

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void readUserPassword() {
    when(vaultTemplate.read(anyString())).thenReturn(vaultResponse);
    when(vaultResponse.getData()).thenReturn(Collections.singletonMap("bob", "hax"));

    assertThat(mobsterServiceImpl.retrieveMobsterPassword("bob"), equalTo("hax"));
  }

  @Test
  public void createMobsters() {
    Mobster mobster = new Mobster();
    mobster.setId("1");
    mobster.setUsername(JOHN_SMITH);

    given(reactiveMobsterRepository.saveAll(any(Publisher.class)))
        .willReturn(Flux.just(mobster));

    Flux<Mobster> createdMobster = mobsterServiceImpl.createMobsters(Flux.just(mobster));

    assertThat(mobster, is(equalTo(createdMobster.blockFirst())));
  }

  @Test
  public void addToQueue() {
    Mobster mobster = Mobster.builder().username("zombie").build();
    given(reactiveMobsterRepository.findByUsername(anyString()))
        .willReturn(Mono.just(mobster));
    given(reactiveMobsterRepository.save(mobster))
        .willReturn(Mono.just(mobster));

    mobsterServiceImpl.addToQueue("zombie");

    assertTrue(mobster.isPending());
  }
}
