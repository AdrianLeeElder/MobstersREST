package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public interface MobsterService {

  String retrieveMobsterPassword(String username);

  Flux<Mobster> getMobsters();

  Flux<Mobster> createMobsters(Publisher<Mobster> mobster);

  Publisher<Void> addToQueue(String username);
}
