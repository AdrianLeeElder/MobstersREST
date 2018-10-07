package com.adrian.mobsters.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "actionJobs")
@Data
@RequiredArgsConstructor
public class ActionJob {

    @Id
    private String id;
    private final Mobster mobster;
    private final List<Action> actionList;
    private final boolean daily;
    private final boolean buyProperty;
    private int failureCount;
    private boolean frozen;
    private boolean queued;
    private boolean running;
    private boolean complete;
}
