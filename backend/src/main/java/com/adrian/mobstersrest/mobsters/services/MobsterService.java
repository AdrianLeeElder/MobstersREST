package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MobsterService {

    String retrieveMobsterPassword(String username);

    Flux<Mobster> getMobsters();

    Flux<Mobster> createMobsters(Publisher<Mobster> mobster);

    Publisher<Void> addToQueue(String username);

    Mono<Mobster> setComplete(boolean complete, String username);
}
