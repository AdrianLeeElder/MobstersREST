package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.DailyAction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DailyActionRepository extends MongoRepository<DailyAction, String> {
    List<DailyAction> findAllByUser(String user);
}
