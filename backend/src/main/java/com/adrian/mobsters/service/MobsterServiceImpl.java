package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.MobsterRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<Mobster> getMobsters(Pageable pageable) {
        Page<Mobster> mobsters = mobsterRepository.findAll(pageable);
        return mobsters.getContent();
    }

    @Override
    public List<Mobster> createMobsters(List<Mobster> mobster) {
        return Lists.newArrayList(mobsterRepository.saveAll(mobster));
    }
}
