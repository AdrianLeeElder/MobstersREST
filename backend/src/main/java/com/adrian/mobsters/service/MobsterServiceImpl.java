package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.MobsterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MobsterServiceImpl implements MobsterService {
    private MobsterRepository mobsterRepository;

    @Override
    public String retrieveMobsterPassword(String username) {
        Mobster mobster = mobsterRepository.findByUsername(username);
        return mobster.getPassword();
    }

    @Override
    public List<Mobster> getMobsters() {
        return mobsterRepository.findAll();
    }

    @Override
    public List<Mobster> createMobsters(List<Mobster> mobster) {
        return mobsterRepository.saveAll(mobster);
    }
}
