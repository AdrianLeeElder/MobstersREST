package com.adrian.mobstersrest.mobsters.bootstrap;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.repositories.MobsterReactiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {

  private MobsterReactiveRepository mobsterReactiveRepository;

  @Override
  public void run(String... args) {
    Mobster mobster = new Mobster();
    mobster.setUsername("zombiedevice");

    mobsterReactiveRepository.save(mobster).block();
  }
}