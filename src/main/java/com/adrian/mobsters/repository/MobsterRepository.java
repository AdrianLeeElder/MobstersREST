package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.Mobster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MobsterRepository extends PagingAndSortingRepository<Mobster, String> {
    Mobster findByUsername(String username);
    List<Mobster> findByUsernameRegex(String regex);
}
