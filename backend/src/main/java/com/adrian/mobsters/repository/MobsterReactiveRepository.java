package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.Mobster;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MobsterReactiveRepository extends ReactiveCrudRepository<Mobster, String> {

    Mono<Mobster> findByUsername(String username);

    Flux<Mobster> findByUsernameRegex(String regex);
}
