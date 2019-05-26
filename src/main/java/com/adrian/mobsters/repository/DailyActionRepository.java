package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.DailyAction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DailyActionRepository extends MongoRepository<DailyAction, String> {
}
