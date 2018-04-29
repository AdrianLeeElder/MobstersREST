package com.adrian.mobstersrest.mobsters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:persistence-${envTarget:dev}.properties"})
public class MobstersApplication {

  public static void main(String[] args) {
    SpringApplication.run(MobstersApplication.class, args);
  }
}
