package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Mobster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ActionJobRepository extends MongoRepository<ActionJob, String> {
    List<ActionJob> findAllByStatus(String status);

    Page<ActionJob> findByUserAndMobster_Id(String user, String id, Pageable pageable);

    List<ActionJob> findByUserAndCreatedDateBetweenAndTemplate_Name(String user,
                                                                    LocalDateTime from,
                                                                    LocalDateTime to,
                                                                    String templateName);

    List<ActionJob> findByMobster_IdIn(List<String> ids);

    List<ActionJob> findByMobster_Id(String id);
}
