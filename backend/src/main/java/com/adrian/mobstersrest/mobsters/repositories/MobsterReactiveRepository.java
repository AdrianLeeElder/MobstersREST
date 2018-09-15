package com.adrian.mobstersrest.mobsters.repositories;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MobsterReactiveRepository extends ReactiveCrudRepository<Mobster, Long> {

    Mono<Mobster> findByUsername(String username);
}
