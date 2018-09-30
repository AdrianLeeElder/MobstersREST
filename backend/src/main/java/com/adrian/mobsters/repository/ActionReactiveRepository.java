package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.Action;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ActionReactiveRepository extends
        ReactiveCrudRepository<Action, String> {

}
