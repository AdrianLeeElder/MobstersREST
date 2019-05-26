package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MobsterService {
    String retrieveMobsterPassword(String username);

    List<Mobster> getMobsters(Pageable pageable);

    List<Mobster> createMobsters(List<Mobster> mobster);
}
