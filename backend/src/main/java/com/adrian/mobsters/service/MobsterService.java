package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MobsterService {

    String retrieveMobsterPassword(String username);

    Flux<Mobster> getMobsters();

    Flux<Mobster> createMobsters(Publisher<Mobster> mobster);
}
