package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.repository.MobsterReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class MobsterServiceImpl implements MobsterService {

    private MobsterReactiveRepository mobsterReactiveRepository;

    @Override
    public String retrieveMobsterPassword(String username) {
        Mobster mobster = mobsterReactiveRepository.findByUsername(username).block();
        return mobster.getPassword();
    }

    @Override
    public Flux<Mobster> getMobsters() {
        return mobsterReactiveRepository
                .findAll();
    }

    @Override
    public Flux<Mobster> createMobsters(Publisher<Mobster> mobster) {
        return mobsterReactiveRepository.saveAll(mobster);
    }
}
