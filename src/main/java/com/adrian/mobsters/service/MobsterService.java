package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MobsterService {
    /**
     * Get the mobsters password.
     *
     * @param username mobster username.
     * @return
     */
    String retrieveMobsterPassword(String username);

    Page<Mobster> getMobsters(String user, Pageable pageable, String status);

    List<Mobster> createMobsters(List<Mobster> mobster);
}
