package com.adrian.mobstersrest.mobsters.bootstrap;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.repositories.ReactiveMobsterRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {

  private ReactiveMobsterRepository reactiveMobsterRepository;

  @Override
  public void run(String... args) {
    Mobster mobster = new Mobster();
    mobster.setUsername("zombiedevice");

    reactiveMobsterRepository.save(mobster).block();
  }
}
