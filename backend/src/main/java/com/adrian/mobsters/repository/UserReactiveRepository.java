package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByUsername(String username);
}
