package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.model.MobsterDto;

import java.util.List;

public interface MobsterService {

    String retrieveMobsterPassword(String username);

    List<MobsterDto> getMobsters();

    MobsterDto createMobster(String username);
}
