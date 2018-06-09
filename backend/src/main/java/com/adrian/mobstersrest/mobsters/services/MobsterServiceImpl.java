package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.repositories.MobsterReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class MobsterServiceImpl implements MobsterService {

  private MobsterReactiveRepository mobsterReactiveRepository;

  @Override
  public String retrieveMobsterPassword(String username) {
    Mobster mobster = mobsterReactiveRepository.findByUsername(username).block();
    return mobster.getPassword();
  }

  @Override
  public Flux<Mobster> getMobsters() {
    return mobsterReactiveRepository
        .findAll();
  }

  @Override
  public Flux<Mobster> createMobsters(Publisher<Mobster> mobster) {
    return mobsterReactiveRepository.saveAll(mobster);
  }

  @Override
  public Publisher<Void> addToQueue(String username) {
    if (username != null && !username.isEmpty()) {
      Mobster mobster = mobsterReactiveRepository.findByUsername(username).block();
      mobster.setQueued(true);

      return mobsterReactiveRepository.save(mobster).then();
    }

    log.error("No mobster with username " + username + " found.");

    return null;
  }

  @Override
  public Mono<Mobster> setComplete(boolean complete, String username) {
    return mobsterReactiveRepository
        .findByUsername(username)
        .flatMap(mobster -> {
          mobster.setComplete(complete);

          return mobsterReactiveRepository.save(mobster);
        });
  }
}
