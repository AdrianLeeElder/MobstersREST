package com.adrian.mobstersrest.mobsters.config;

import com.mongodb.reactivestreams.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableReactiveMongoRepositories("com.adrian.mobstersrest.mobsters.repositories")
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
@RequiredArgsConstructor
public class AppConfig extends AbstractReactiveMongoConfiguration {

  @Value("${spring.data.mongodb.port}")
  private String port;
  @Value("${spring.data.mongodb.host}")
  private String host;

  @Override
  @Bean
  public com.mongodb.reactivestreams.client.MongoClient reactiveMongoClient() {
    return MongoClients.create(String.format("mongodb://%s:%s", host, port));
  }

  @Override
  protected String getDatabaseName() {
    return "mobsters";
  }
}
