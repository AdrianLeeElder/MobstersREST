package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MobsterService {
    /**
     * Get the mobsters password.
     *
     * @param username mobster username.
     * @param user     current requesting user.
     * @return
     */
    String retrieveMobsterPassword(String username);

    List<Mobster> getMobsters(String user, Pageable pageable);

    List<Mobster> createMobsters(List<Mobster> mobster);
}
