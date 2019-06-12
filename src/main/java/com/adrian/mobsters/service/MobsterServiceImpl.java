package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.exception.MobsterNotFoundException;
import com.adrian.mobsters.repository.MobsterRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class MobsterServiceImpl implements MobsterService {
    private final MobsterRepository mobsterRepository;
    private final UserService userService;

    @Override
    public String retrieveMobsterPassword(String username) {
        Optional<Mobster> mobster = mobsterRepository.findByUsername(username, userService.getUser());

        if (!mobster.isPresent()) {
            throw new MobsterNotFoundException(username);
        }

        return mobster.get().getPassword();
    }

    @Override
    public List<Mobster> getMobsters(String user, Pageable pageable) {
        Page<Mobster> mobsters = mobsterRepository.findAllByUser(user, pageable);
        return mobsters.getContent();
    }

    @Override
    public List<Mobster> createMobsters(List<Mobster> mobster) {
        return Lists.newArrayList(mobsterRepository.saveAll(mobster));
    }
}
