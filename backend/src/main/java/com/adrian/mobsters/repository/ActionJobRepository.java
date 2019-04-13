package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.ActionJob;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ActionJobRepository extends MongoRepository<ActionJob, String> {
    @Query("{'mobster.username': ?0}")
    List<ActionJob> findByMobsterUsername(String username);

    @Query("{'mobster.username': ?0}")
    List<ActionJob> findByDailyTrueAndMobsterUsernameRegex(String username);
}
