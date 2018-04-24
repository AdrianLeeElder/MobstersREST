package com.adrian.mobstersrest.mobsters.repositories;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobsterRepository extends JpaRepository<Mobster, Long> {

}
