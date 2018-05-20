package com.adrian.mobstersrest.mobsters.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mobster {

  @Id
  private String id;
  private String username;
  private String password;

  private boolean pending;
  private boolean complete;
  public Mobster(Mobster mobster) {
    this.id = mobster.id;
    this.username = mobster.getUsername();
    this.pending = mobster.isPending();
    this.complete = mobster.isComplete();
  }
}