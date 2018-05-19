package com.adrian.mobstersrest.mobsters.repositories;

import com.adrian.mobstersrest.mobsters.domain.DailyAction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DailyActionReactiveRepository extends
    ReactiveCrudRepository<DailyAction, String> {

}
