package com.adrian.mobstersrest.mobsters.domain;

import java.util.List;
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
  private boolean queued;
  private boolean complete;
  private String botMode = "Daily";
  private String priority = "Medium";
  private List<DailyAction> dailyActions;
  private List<String> buyPropertyMessages;

  public Mobster(Mobster mobster) {
    this.id = mobster.id;
    this.username = mobster.getUsername();
    this.queued = mobster.isQueued();
    this.complete = mobster.isComplete();
  }
}