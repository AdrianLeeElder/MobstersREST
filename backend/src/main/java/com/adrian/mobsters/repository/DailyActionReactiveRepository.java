package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.DailyAction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DailyActionReactiveRepository extends ReactiveCrudRepository<DailyAction, String> {
}
