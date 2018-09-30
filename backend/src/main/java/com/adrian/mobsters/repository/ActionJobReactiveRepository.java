package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.ActionJob;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ActionJobReactiveRepository extends ReactiveCrudRepository<ActionJob, String> {

    @Query("{'mobster.username': ?0}")
    Flux<ActionJob> findByMobsterUsername(String username);

    @Query("{'mobster.username': ?0}")
    Flux<ActionJob> findByDailyTrueAndMobsterUsernameRegex(String username);
}
