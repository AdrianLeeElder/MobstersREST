package com.adrian.mobstersrest.mobsters.bootstrap;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.repositories.MobsterRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private MobsterRepository mobsterRepository;

    @Override
    public void run(String... args) {
        Mobster mobster = new Mobster();
        mobster.setUsername("bigtrac");

        mobsterRepository.save(mobster);
    }
}
