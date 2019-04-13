package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.Mobster;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MobsterRepository extends MongoRepository<Mobster, String> {
    Mobster findByUsername(String username);
    List<Mobster> findByUsernameRegex(String regex);
}
