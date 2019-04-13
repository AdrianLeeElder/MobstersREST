package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;

import java.util.List;

public interface MobsterService {
    String retrieveMobsterPassword(String username);
    List<Mobster> getMobsters();
    List<Mobster> createMobsters(List<Mobster> mobster);
}
