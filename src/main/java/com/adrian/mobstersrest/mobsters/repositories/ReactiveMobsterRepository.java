package com.adrian.mobstersrest.mobsters.repositories;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ReactiveMobsterRepository extends ReactiveCrudRepository<Mobster, Long> {

  Mono<Mobster> save(Mono<Mobster> mobster);

  Mono<Mobster> saveAll(Mono<Mobster> mobster);

  Mono<Mobster> findByUsername(String username);
}
