package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AccountReactiveRepository extends ReactiveCrudRepository<Account, String> {
}
