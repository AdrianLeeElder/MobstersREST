package com.adrian.mobstersrest.mobsters.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class DailyAction {

  @Id
  private String id;
  private String actionName;
}