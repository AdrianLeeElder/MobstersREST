package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.ActionJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ActionJobRepository extends MongoRepository<ActionJob, String> {
    List<ActionJob> findAllByStatus(String status);

    Page<ActionJob> findByUserAndMobster_Id(String user, String id, Pageable pageable);
}
