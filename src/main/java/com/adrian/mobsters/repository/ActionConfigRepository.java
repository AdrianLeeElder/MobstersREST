package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.ActionConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionConfigRepository extends MongoRepository<ActionConfig, String> {
}
