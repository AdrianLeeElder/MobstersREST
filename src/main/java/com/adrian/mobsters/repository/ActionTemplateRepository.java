package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.ActionTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ActionTemplateRepository extends MongoRepository<ActionTemplate, String> {
    List<ActionTemplate> findAllByUser(String user);

    Optional<ActionTemplate> findByIdAndUser(String id, String user);
}
